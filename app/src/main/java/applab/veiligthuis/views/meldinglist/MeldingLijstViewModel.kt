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
        getMeldingen(
            MeldingOrder.Datum(OrderType.Descending),
            MeldingType.Inkomend,
            _filterState.value.filterStatusPredicates,
            _filterState.value.filterSoortGeweldPredicates,
            _filterState.value.filterDatumPredicates,
            _filterState.value.filterBeroepsmatigPredicates
        )
        Log.w("VM", "Meldingen opgehaald.")
    }

    fun onEvent(event: MeldingLijstEvent) {
        when (event) {
            is MeldingLijstEvent.Order -> {
                if (_uiState.value.meldingOrder::class == event.meldingOrder::class &&
                    _uiState.value.meldingOrder.orderType == event.meldingOrder.orderType
                ) {
                    return
                }
                getMeldingen(
                    event.meldingOrder,
                    _uiState.value.meldingType,
                    _filterState.value.filterStatusPredicates,
                    _filterState.value.filterSoortGeweldPredicates,
                    _filterState.value.filterDatumPredicates,
                    _filterState.value.filterBeroepsmatigPredicates
                )
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
                    Log.i("VM", " Verander meldingtype")
                    val newMeldingPath: MeldingType = if (!event.inkomend) {
                        MeldingType.Afgesloten
                    } else {
                        MeldingType.Inkomend
                    }
                    _uiState.update { currentState ->
                        currentState.copy(
                            meldingType = newMeldingPath,
                            isInkomendSelected = event.inkomend
                        )
                    }
                    getMeldingen(
                        _uiState.value.meldingOrder,
                        _uiState.value.meldingType,
                        _filterState.value.filterStatusPredicates,
                        _filterState.value.filterSoortGeweldPredicates,
                        _filterState.value.filterDatumPredicates,
                        _filterState.value.filterBeroepsmatigPredicates
                    )
                }
            }
            is MeldingLijstEvent.FilterStatus -> {
                viewModelScope.launch {
                    val updatedStatus: List<String> =
                        if (_filterState.value.statusFilter.contains(event.status)) {
                            _filterState.value.statusFilter.minus(event.status)
                        } else {
                            _filterState.value.statusFilter.plus(event.status)
                        }
                    val filterCountChange =
                        updatedStatus.size - _filterState.value.statusFilter.size

                    _filterState.update { currentState ->
                        currentState.copy(
                            statusFilter = updatedStatus,
                            filterCountSelected = currentState.filterCountSelected + filterCountChange
                        )
                    }
                }
            }
            is MeldingLijstEvent.FilterBeroepsmatig -> {
                viewModelScope.launch {
                    val updatedBeroepsmatig: List<String> =
                        if (_filterState.value.beroepsmatigFilter.contains(event.beroepsmatig)) {
                            _filterState.value.beroepsmatigFilter.minus(event.beroepsmatig)
                        } else {
                            _filterState.value.beroepsmatigFilter.plus(event.beroepsmatig)
                        }
                    val filterCountChange =
                        updatedBeroepsmatig.size - _filterState.value.beroepsmatigFilter.size

                    _filterState.update { currentState ->
                        currentState.copy(
                            beroepsmatigFilter = updatedBeroepsmatig,
                            filterCountSelected = currentState.filterCountSelected + filterCountChange
                        )
                    }
                }
            }
            is MeldingLijstEvent.FilterSoortGeweld -> {
                viewModelScope.launch {
                    val updatedSoortGeweld: List<String> =
                        if (_filterState.value.soortGeweldFilter.contains(event.geweld)) {
                            _filterState.value.soortGeweldFilter.minus(event.geweld)
                        } else {
                            _filterState.value.soortGeweldFilter.plus(event.geweld)
                        }
                    val change = updatedSoortGeweld.size - _filterState.value.soortGeweldFilter.size

                    _filterState.update { currentState ->
                        currentState.copy(
                            soortGeweldFilter = updatedSoortGeweld,
                            filterCountSelected = currentState.filterCountSelected + change
                        )
                    }
                }
            }
            is MeldingLijstEvent.FilterDatum -> {
                viewModelScope.launch {
                    val newDatum: String? =
                        if (event.selected == _filterState.value.datumSelectedFilter) {
                            null
                        } else {
                            event.selected
                        }
                    var changeCount = 0
                    if (_filterState.value.datumSelectedFilter == null) {
                        changeCount = 1
                    } else if (_filterState.value.datumSelectedFilter != null && event.selected == _filterState.value.datumSelectedFilter) {
                        changeCount = -1
                    }
                    _filterState.update { currentState ->
                        currentState.copy(
                            datumSelectedFilter = newDatum,
                            filterCountSelected = currentState.filterCountSelected + changeCount
                        )
                    }
                }
            }
            is MeldingLijstEvent.FilterPlaatsen -> {
                viewModelScope.launch {
                    val updatedPlaatsen =
                        if (_filterState.value.filterPlaatsen.contains(event.plaats)) {
                            _filterState.value.filterPlaatsen.minus(event.plaats)
                        } else {
                            _filterState.value.filterPlaatsen.plus(event.plaats)
                        }

                    val change = updatedPlaatsen.size - _filterState.value.filterPlaatsen.size

                    _filterState.update { currentState ->
                        currentState.copy(
                            filterPlaatsen = updatedPlaatsen,
                            filterCountSelected = currentState.filterCountSelected + change
                        )
                    }
                }
            }

            is MeldingLijstEvent.SluitFilter -> {
                viewModelScope.launch {
                    val filterStatusPred =
                        _filterState.value.statusFilter.map { status -> { melding: Melding -> melding.status.status == status } }

                    val filterSoortGeweldPred =
                        _filterState.value.soortGeweldFilter.map { soortGeweld -> { melding: Melding -> melding.typeGeweld == soortGeweld } }

                    val filterBeroepsmatigPred =
                        _filterState.value.beroepsmatigFilter.map { beroepsmatig -> { melding: Melding -> melding.beroepsmatig == (beroepsmatig == "Ja") } }


                    val filterDatumPred = mutableListOf<(Melding) -> Boolean>()
                    filterDatumPred.add { melding: Melding ->
                        melding.datum!! <= LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
                    }
                    // Datum
                    when (_filterState.value.datumSelectedFilter) {
                        "Vandaag" -> filterDatumPred.add { melding: Melding ->
                            melding.datum!! > LocalDateTime.now()
                                .toEpochSecond(ZoneOffset.UTC) - 86400
                        }
                        "Deze Week" -> filterDatumPred.add { melding: Melding ->
                            melding.datum!! > LocalDateTime.now()
                                .toEpochSecond(ZoneOffset.UTC) - (86400 * 7)
                        }
                        "Deze Maand" -> filterDatumPred.add { melding: Melding ->
                            melding.datum!! > LocalDateTime.now()
                                .toEpochSecond(ZoneOffset.UTC) - (86400 * 30)
                        }
                        "Afgelopen 6 maanden" -> filterDatumPred.add { melding: Melding ->
                            melding.datum!! > LocalDateTime.now()
                                .toEpochSecond(ZoneOffset.UTC) - (86400 * 180)
                        }
                    }
                    _filterState.update { currentState ->
                        currentState.copy(
                            filterBeroepsmatigPredicates = filterBeroepsmatigPred,
                            filterSoortGeweldPredicates = filterSoortGeweldPred,
                            filterStatusPredicates = filterStatusPred,
                            filterDatumPredicates = filterDatumPred
                        )
                    }

                    getMeldingen(
                        _uiState.value.meldingOrder,
                        _uiState.value.meldingType,
                        _filterState.value.filterStatusPredicates,
                        _filterState.value.filterSoortGeweldPredicates,
                        _filterState.value.filterDatumPredicates,
                        _filterState.value.filterBeroepsmatigPredicates
                    )
                }
            }
        }
    }


    private fun getMeldingen(
        meldingOrder: MeldingOrder,
        meldingType: MeldingType,
        filterStatusPredicates: List<(Melding) -> Boolean>,
        filterSoortGeweldPrediactes: List<(Melding) -> Boolean>,
        filterDatumPredicates: List<(Melding) -> Boolean>,
        filterBeroepsmatigPredicates: List<(Melding) -> Boolean>
    ) {
        getMeldingenJob?.cancel()
        getMeldingenJob = meldingUseCases.getMeldingen(
            meldingOrder = meldingOrder,
            meldingType = meldingType,
            filterStatusPredicates = filterStatusPredicates,
            filterSoortGeweldPredicates = filterSoortGeweldPrediactes,
            filterDatumPredicates = filterDatumPredicates,
            filterBeroepsmatigPredicates = filterBeroepsmatigPredicates,
            plaatsen = _filterState.value.filterPlaatsen
        )
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