package applab.veiligthuis.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import applab.veiligthuis.domain.usecase.MeldingUseCases
import applab.veiligthuis.domain.util.MeldingOrder
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
        getMeldingen(MeldingOrder.Datum(OrderType.Descending))
    }

    fun onEvent(event: MeldingEvent) {
        when(event) {
            is MeldingEvent.Order -> {
                if (_uiState.value.meldingOrder::class == event.meldingOrder::class &&
                    _uiState.value.meldingOrder.orderType == event.meldingOrder.orderType
                ) {
                    return
                }
                getMeldingen(event.meldingOrder)
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
        }
    }

    private fun getMeldingen(meldingOrder: MeldingOrder) {
        getMeldingenJob?.cancel()
        getMeldingenJob = meldingUseCases.getMeldingen(meldingOrder)
            .onEach { meldingen ->
                _uiState.update { currentState ->
                    currentState.copy(
                        meldingen = meldingen,
                        meldingOrder = meldingOrder
                    )
                }
            }
            .launchIn(viewModelScope)
    }

}