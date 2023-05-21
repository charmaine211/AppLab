package applab.veiligthuis.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import applab.veiligthuis.model.MeldingData
import applab.veiligthuis.model.MeldingStatus
import applab.veiligthuis.repository.melding.MeldingRepository
import applab.veiligthuis.repository.melding.MeldingRepositoryImpl
import applab.veiligthuis.ui.meldingenlijst.MeldingenLijstFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class MeldingenLijstUiState(
    val meldingen : List<MeldingData?> = listOf(),
    val meldingenFiltered: List<MeldingData?> = listOf(),
    val selectedMeldingData: MeldingData? = MeldingData(),
    val showingLijstScreen: Boolean = true,
    val filterShowInkomend : Boolean = true,
    val filterExpanded: Boolean = false,
    val filterLocatie: String? = null,
    val filterDatum: String? = null,
    val sortDateDesc: Boolean = true
    )

class MeldingLijstViewModel(
    private val meldingRepository: MeldingRepository = MeldingRepositoryImpl()
    ) : ViewModel(), MeldingenLijstFilter {

    private val _uiState = MutableStateFlow(MeldingenLijstUiState())
    val uiState: StateFlow<MeldingenLijstUiState> = _uiState.asStateFlow()

    fun loadMeldingen() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                meldingRepository.getMeldingen().collect() {
                    _uiState.update { currentState ->
                        currentState.copy(
                            meldingen = it
                        )
                    }
                }
            }
        }
        Log.i("VM", "Meldingen geladen")
    }

    fun updateFilterMeldingenInkomend(){
        _uiState.update { currentState ->
            currentState.copy(
                filterShowInkomend = currentState.filterShowInkomend.not()
            )
        }
    }

    fun updateMeldingBekijkenScreen(meldingData: MeldingData?) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedMeldingData = meldingData,
                showingLijstScreen = false
            )
        }
    }

    fun resetMeldingenLijstScreen() {
        _uiState.update { currentState ->
            currentState.copy(
                selectedMeldingData = MeldingData(),
                showingLijstScreen = true
            )
        }
    }

    fun updateFilterButtonExpanded(){
        _uiState.update { currentState ->
            currentState.copy(
                filterExpanded = currentState.filterExpanded.not()
            )
        }
    }

    fun updateFilterLocatie(locatie: String) {
        Log.i("Filter", "Filter locatie update")
        _uiState.update { currentState ->
            currentState.copy(
                filterLocatie = locatie
            )
        }
    }

    fun updateSortDateDesc() {
        _uiState.update { currentState ->
            currentState.copy(
                sortDateDesc = currentState.sortDateDesc.not()
            )
        }
    }

    override fun filterPlaatsnaam(plaatsnaam: String) {
        updateFilterLocatie(plaatsnaam)
    }

    override fun filterInkomend() {
        updateFilterMeldingenInkomend()
    }

    override fun resetFilter() {
        Log.i("Filter", "Filter gereset")
        _uiState.update {currentState ->
            currentState.copy(
                filterLocatie = null,
                filterDatum = null
            )
        }
    }

    override fun expandedFilter() {
        updateFilterButtonExpanded()
    }

    override fun sortDate() {
        updateSortDateDesc()
    }

    fun applyFilters() {
        Log.i("Filter", "Filter toegepast")
        var filterMeldingen = _uiState.value.meldingen

        if(_uiState.value.filterShowInkomend) {
            filterMeldingen = filterMeldingen.filterByStatus(MeldingStatus.IN_BEHANDELING) + filterMeldingen.filterByStatus(MeldingStatus.ONBEHANDELD)
        } else {
            filterMeldingen = filterMeldingen.filterByStatus(MeldingStatus.AFGEROND)
        }
        if(_uiState.value.filterLocatie != null) {
            filterMeldingen = filterMeldingen.filterByLocatie(_uiState.value.filterLocatie!!)
        }
        if(_uiState.value.filterDatum != null) {
            filterMeldingen = filterMeldingen.filterByDate(_uiState.value.filterDatum!!)
        }

        filterMeldingen = sortByDate(filterMeldingen, _uiState.value.sortDateDesc)

        _uiState.update { currentState ->
            currentState.copy (
                meldingenFiltered = filterMeldingen
            )
        }
    }

    private fun sortByDate(list: List<MeldingData?>, desc: Boolean): List<MeldingData?> {
        if(desc) {
           return list.sortByDateDesc()
        } else {
            return list.sortByDateAscend()
        }
    }

    private fun List<MeldingData?>.filterByLocatie(locatie: String) = this.filter {it?.locatie == locatie}
    private fun List<MeldingData?>.filterByDate(date: String) = this.filter {it?.datum.toString() == date}
    private fun List<MeldingData?>.filterByStatus(status: MeldingStatus) = this.filter{it?.status == status}

    private fun List<MeldingData?>.sortByDateDesc() = this.sortedByDescending { it?.datum }
    private fun List<MeldingData?>.sortByDateAscend() = this.sortedBy { it?.datum }


}