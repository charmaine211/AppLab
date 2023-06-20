package applab.veiligthuis.views.meldinglist


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.model.melding.MeldingStatus
import applab.veiligthuis.domain.usecase.MeldingUseCases
import applab.veiligthuis.domain.util.MeldingOrder
import applab.veiligthuis.domain.util.MeldingType
import applab.veiligthuis.domain.util.OrderType
import applab.veiligthuis.ui.composable.CheckBoxItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
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
        getMeldingen(MeldingOrder.Datum(OrderType.Descending), MeldingType.Inkomend, _filterState.value.filterStatusPredicates, _filterState.value.filterSoortGeweldPredicates, _filterState.value.filterDatumPredicates, _filterState.value.filterBeroepsmatigPredicates)
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
                getMeldingen(event.meldingOrder, _uiState.value.meldingType, _filterState.value.filterStatusPredicates, _filterState.value.filterSoortGeweldPredicates, _filterState.value.filterDatumPredicates, _filterState.value.filterBeroepsmatigPredicates )
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
                    getMeldingen(_uiState.value.meldingOrder, _uiState.value.meldingType, _filterState.value.filterStatusPredicates, _filterState.value.filterSoortGeweldPredicates, _filterState.value.filterDatumPredicates, _filterState.value.filterBeroepsmatigPredicates)
                }
            }
            is MeldingLijstEvent.FilterStatus -> {
                val status = _filterState.value.statusFilter
                _filterState.update { currentState ->
                    currentState.copy(
                        statusFilter = status.map {updateCheckItem(it, event.id, event.checked)},
                    )
                }
            }
            is MeldingLijstEvent.FilterBeroepsmatig -> {
                val beroepsmatig = _filterState.value.beroepsmatigFilter
                _filterState.update { currentState ->
                    currentState.copy(
                        beroepsmatigFilter = beroepsmatig.map {updateCheckItem(it, event.id, event.checked) }
                    )
                }
            }
            is MeldingLijstEvent.FilterSoortGeweld -> {
                val soortGeweld = _filterState.value.soortGeweldFilter
                _filterState.update { currentState ->
                    currentState.copy(
                        soortGeweldFilter = soortGeweld.map {updateCheckItem(it, event.id, event.checked) }

                    )
                }
            }
            is MeldingLijstEvent.FilterDatum -> {
                var newDatum: String?
                if(event.selected == _filterState.value.datumSelectedFilter) {
                    newDatum = null
                } else {
                    newDatum = event.selected
                }
                _filterState.update { currentState ->
                    currentState.copy(
                        datumSelectedFilter = newDatum
                    )
                }
            }

            is MeldingLijstEvent.SluitFilter -> {
                //Status
                val filterStatusPred = mutableListOf<(Melding) -> Boolean>()
                if(_filterState.value.statusFilter[0].checked && _filterState.value.statusFilter[1].checked) {
                    filterStatusPred.add { melding: Melding -> melding.status == MeldingStatus.ONBEHANDELD || melding.status == MeldingStatus.IN_BEHANDELING}
                } else if (_filterState.value.statusFilter[0].checked && !_filterState.value.statusFilter[1].checked) {
                    filterStatusPred.add { melding: Melding -> melding.status == MeldingStatus.ONBEHANDELD }
                } else if (!_filterState.value.statusFilter[0].checked && _filterState.value.statusFilter[1].checked) {
                    filterStatusPred.add { melding: Melding -> melding.status == MeldingStatus.IN_BEHANDELING }
                }
                // Soort geweld
                val filterSoortGeweldPred = mutableListOf<(Melding) -> Boolean>()
                if(_filterState.value.soortGeweldFilter[0].checked) {
                    filterSoortGeweldPred.add { melding: Melding -> melding.typeGeweld == "Ongecategoriseerd" }
                }
                if(_filterState.value.soortGeweldFilter[1].checked) {
                    filterSoortGeweldPred.add { melding: Melding -> melding.typeGeweld == "Lichamelijk geweld" }
                }
                if(_filterState.value.soortGeweldFilter[2].checked) {
                    filterSoortGeweldPred.add { melding: Melding -> melding.typeGeweld == "Stalking" }
                }
                if(_filterState.value.soortGeweldFilter[3].checked) {
                    filterSoortGeweldPred.add { melding: Melding -> melding.typeGeweld == "Psychisch geweld" }
                }
                if(_filterState.value.soortGeweldFilter[4].checked) {
                    filterSoortGeweldPred.add { melding: Melding -> melding.typeGeweld == "Financieel misbruik" }
                }
                val filterDatumPred = mutableListOf<(Melding) -> Boolean>()
                filterDatumPred.add { melding: Melding -> melding.datum!! <= LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) }
                // Datum
                if(_filterState.value.datumSelectedFilter == "Vandaag") {
                    filterDatumPred.add { melding: Melding -> melding.datum!! > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - 86400 }
                } else if (_filterState.value.datumSelectedFilter == "Deze Week") {
                    filterDatumPred.add { melding: Melding -> melding.datum!! > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - (86400*7) }
                } else if (_filterState.value.datumSelectedFilter == "Deze Maand") {
                    filterDatumPred.add { melding: Melding -> melding.datum!! > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - (86400*30) }
                } else if (_filterState.value.datumSelectedFilter == "Afgelopen 6 maanden"){
                    filterDatumPred.add { melding: Melding -> melding.datum!! > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - (86400*180) }
                }

                // Beroepsmatig
                val filterBeroepsmatigPred = mutableListOf<(Melding) -> Boolean>()
                if(_filterState.value.beroepsmatigFilter[0].checked) {
                    filterBeroepsmatigPred.add { melding: Melding -> melding.beroepsmatig }
                }
                if(_filterState.value.beroepsmatigFilter[1].checked) {
                    filterBeroepsmatigPred.add { melding: Melding -> !melding.beroepsmatig }
                }

                _filterState.update { currentState ->
                    currentState.copy(
                        filterBeroepsmatigPredicates = filterBeroepsmatigPred,
                        filterSoortGeweldPredicates = filterSoortGeweldPred,
                        filterStatusPredicates = filterStatusPred,
                        filterDatumPredicates = filterDatumPred
                    )
                }

                getMeldingen(_uiState.value.meldingOrder, _uiState.value.meldingType, _filterState.value.filterStatusPredicates, _filterState.value.filterSoortGeweldPredicates, _filterState.value.filterDatumPredicates, _filterState.value.filterBeroepsmatigPredicates)
            }
        }
    }

    private fun updateCheckItem(item: CheckBoxItemState, id: Int, checked: Boolean): CheckBoxItemState {
         return if(id == item.id) item.copy(checked = checked) else item
    }

    private fun getMeldingen(meldingOrder: MeldingOrder, meldingType: MeldingType, filterStatusPredicates: List<(Melding) -> Boolean>, filterSoortGeweldPrediactes: List<(Melding) -> Boolean>, filterDatumPredicates: List<(Melding) -> Boolean>, filterBeroepsmatigPredicates: List<(Melding) -> Boolean>) {
        getMeldingenJob?.cancel()
        getMeldingenJob = meldingUseCases.getMeldingen(meldingOrder, meldingType, filterStatusPredicates = filterStatusPredicates, filterSoortGeweldPredicates = filterSoortGeweldPrediactes, filterDatumPredicates = filterDatumPredicates, filterBeroepsmatigPredicates = filterBeroepsmatigPredicates)
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