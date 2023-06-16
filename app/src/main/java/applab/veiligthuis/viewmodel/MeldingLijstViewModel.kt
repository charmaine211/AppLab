package applab.veiligthuis.viewmodel


import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.model.melding.MeldingStatus
import applab.veiligthuis.domain.usecase.MeldingUseCases
import applab.veiligthuis.domain.util.MeldingOrder
import applab.veiligthuis.domain.util.MeldingType
import applab.veiligthuis.domain.util.OrderType
import applab.veiligthuis.views.meldinglist.MeldingLijstEvent
import applab.veiligthuis.views.meldinglist.MeldingLijstFilterState
import applab.veiligthuis.views.meldinglist.MeldingLijstState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.function.BiPredicate
import javax.inject.Inject


@HiltViewModel
class MeldingLijstViewModel @Inject constructor(
    private val meldingUseCases: MeldingUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(MeldingLijstState())
    val uiState: StateFlow<MeldingLijstState> = _uiState.asStateFlow()

    private val _filterState = MutableStateFlow(MeldingLijstFilterState())
    val filterState: StateFlow<MeldingLijstFilterState> = _filterState.asStateFlow()

    private var getMeldingenJob: Job? = null


    init {
        getMeldingen(MeldingOrder.Datum(OrderType.Descending), MeldingType.Inkomend, _filterState.value.filterPredicates)
        Log.w("VM", "Meldingen opgehaald.")
    }

    fun onEvent(event: MeldingLijstEvent) {
        when(event) {
            is MeldingLijstEvent.Order -> {
                if (_uiState.value.meldingOrder::class == event.meldingOrder::class &&
                    _uiState.value.meldingOrder.orderType == event.meldingOrder.orderType
                ) {
                    return
                }
                getMeldingen(event.meldingOrder, _uiState.value.meldingType, _filterState.value.filterPredicates)
            }
            is MeldingLijstEvent.ToggleFilterSection -> {
                viewModelScope.launch {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isFilterExpanded = currentState.isFilterExpanded.not()
                        )
                    }
                }
            }
            is MeldingLijstEvent.ToggleMeldingStatusLijst -> {
                viewModelScope.launch {
                    val newMeldingPath: MeldingType
                    if(_uiState.value.isInkomendSelected){
                        newMeldingPath = MeldingType.Afgesloten
                    } else {
                        newMeldingPath = MeldingType.Inkomend
                    }
                    _uiState.update { currentState ->
                        currentState.copy(
                            meldingType = newMeldingPath,
                            isInkomendSelected = currentState.isInkomendSelected.not()
                        )
                    }
                    getMeldingen(_uiState.value.meldingOrder, _uiState.value.meldingType, _filterState.value.filterPredicates)
                }
            }
            is MeldingLijstEvent.Status -> {
                val status = _filterState.value.statusFilter
                _filterState.update { currentState ->
                    currentState.copy(
                        statusFilter = status.map { item -> if(event.id == item.id) item.copy(checked = event.checked) else item }
                    )
                }
            }
            is MeldingLijstEvent.Beroepsmatig -> {
                val beroepsmatig = _filterState.value.beroepsmatigFilter
                _filterState.update { currentState ->
                    currentState.copy(
                        beroepsmatigFilter = beroepsmatig.map { item -> if(event.id == item.id) item.copy(checked = event.checked) else item }
                    )
                }
            }
            is MeldingLijstEvent.SoortGeweld -> {
                val soortGeweld = _filterState.value.soortGeweldFilter
                _filterState.update { currentState ->
                    currentState.copy(
                        soortGeweldFilter = soortGeweld.map { item -> if(event.id == item.id) item.copy(checked = event.checked) else item }
                    )
                }
            }
            is MeldingLijstEvent.ApplyFilter -> {
                val filterPredicates = mutableListOf<(Melding) -> Boolean>()
                if(_filterState.value.statusFilter[0].checked && _filterState.value.statusFilter[1].checked) {
                    filterPredicates.add { melding: Melding -> melding.status == MeldingStatus.ONBEHANDELD || melding.status == MeldingStatus.IN_BEHANDELING}
                } else if (_filterState.value.statusFilter[0].checked && !_filterState.value.statusFilter[1].checked) {
                    filterPredicates.add { melding: Melding -> melding.status == MeldingStatus.ONBEHANDELD }
                } else if (!_filterState.value.statusFilter[0].checked && _filterState.value.statusFilter[1].checked) {
                    filterPredicates.add { melding: Melding -> melding.status == MeldingStatus.IN_BEHANDELING }
                }
                _filterState.update { currentState ->
                    currentState.copy(
                        filterPredicates = filterPredicates
                    )
                }
                getMeldingen(_uiState.value.meldingOrder, _uiState.value.meldingType, _filterState.value.filterPredicates)

            }
        }
    }

    private fun getMeldingen(meldingOrder: MeldingOrder, meldingType: MeldingType, filterPredicate: List<(Melding) -> Boolean>) {
        getMeldingenJob?.cancel()
        getMeldingenJob = meldingUseCases.getMeldingen(meldingOrder, meldingType, filterPredicates = filterPredicate)
            .onEach { meldingen ->
                _uiState.update { currentState ->
                    currentState.copy(
                        meldingen = meldingen,
                        meldingOrder = meldingOrder,
                        meldingType = meldingType
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}