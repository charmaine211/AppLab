package applab.veiligthuis.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import applab.veiligthuis.domain.usecase.MeldingUseCases
import applab.veiligthuis.domain.util.MeldingOrder
import applab.veiligthuis.domain.util.MeldingType
import applab.veiligthuis.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MeldingLijstViewModel @Inject constructor(
    private val meldingUseCases: MeldingUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(MeldingLijstState())
    val uiState: StateFlow<MeldingLijstState> = _uiState.asStateFlow()

    private var getMeldingenJob: Job? = null

    init {
        getMeldingen(MeldingOrder.Datum(OrderType.Descending), MeldingType.Inkomend)
    }

    fun onEvent(event: MeldingEvent) {
        when(event) {
            is MeldingEvent.Order -> {
                if (_uiState.value.meldingOrder::class == event.meldingOrder::class &&
                    _uiState.value.meldingOrder.orderType == event.meldingOrder.orderType
                ) {
                    return
                }
                getMeldingen(event.meldingOrder, _uiState.value.meldingType)
            }
            is MeldingEvent.ToggleFilterSection -> {
                viewModelScope.launch {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isFilterExpanded = currentState.isFilterExpanded.not()
                        )
                    }
                }
            }
            is MeldingEvent.ToggleMeldingStatus -> {
                viewModelScope.launch {
                    val newMeldingType: MeldingType
                    if(_uiState.value.isInkomendSelected){
                        newMeldingType = MeldingType.Afgesloten
                    } else {
                        newMeldingType = MeldingType.Inkomend
                    }
                    _uiState.update { currentState ->
                        currentState.copy(
                            meldingType = newMeldingType,
                            isInkomendSelected = currentState.isInkomendSelected.not()
                        )
                    }
                    getMeldingen(_uiState.value.meldingOrder, _uiState.value.meldingType)
                }
            }
        }
    }

    private fun getMeldingen(meldingOrder: MeldingOrder, meldingType: MeldingType) {
        getMeldingenJob?.cancel()
        getMeldingenJob = meldingUseCases.getMeldingen(meldingOrder, meldingType)
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