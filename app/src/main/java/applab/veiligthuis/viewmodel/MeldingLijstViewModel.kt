package applab.veiligthuis.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import applab.veiligthuis.model.MeldingData
import applab.veiligthuis.repository.melding.MeldingLijstRepository
import applab.veiligthuis.repository.melding.MeldingLijstRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class MeldingenLijstUiState(
    val meldingen : List<MeldingData?> = listOf(),
    val filterMeldingenInkomend : Boolean = true,
    val selectedMeldingData: MeldingData? = MeldingData(),
    val showingLijstScreen: Boolean = true,
    val filterExpanded: Boolean = false,
    val filterLocatie: String? = null,
    val filterDatum: String? = null
)

class MeldingLijstViewModel(
    private val meldingLijstRepository: MeldingLijstRepository = MeldingLijstRepositoryImpl()
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MeldingenLijstUiState())
        val uiState: StateFlow<MeldingenLijstUiState> = _uiState.asStateFlow()

        fun loadMeldingen() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    meldingLijstRepository.getMeldingen().collect() {
                        _uiState.update { currentState ->
                            currentState.copy(
                                meldingen = it
                            )
                        }
                    }
                }
                Log.i("VM", "Meldingen geladen")
            }
        }

        fun updateFilterMeldingenInkomend(){
            _uiState.update { currentState ->
                currentState.copy(
                    filterMeldingenInkomend = currentState.filterMeldingenInkomend.not()
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
            _uiState.update { currentState ->
                currentState.copy(
                    filterLocatie = locatie
                )
            }
        }

        fun resetFilter() {
            Log.i("ResetFilter", "Filter gereset")
            _uiState.update {currentState ->
                currentState.copy(
                    filterLocatie = null,
                    filterDatum = null
                )
            }
        }


}