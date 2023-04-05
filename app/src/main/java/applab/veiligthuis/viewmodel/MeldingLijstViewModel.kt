package applab.veiligthuis.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import applab.veiligthuis.model.Melding
import applab.veiligthuis.repository.melding.MeldingLijstRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class MeldingenLijstUiState(
    val meldingen : List<Melding?> = listOf(),
    val filterMeldingenInkomend : Boolean = true
)

class MeldingLijstViewModel(
    private val meldingLijstRepository: MeldingLijstRepositoryImpl = MeldingLijstRepositoryImpl()
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

        fun swapFilterMeldingenInkomend(){
            _uiState.update { currentState ->
                currentState.copy(
                    filterMeldingenInkomend = currentState.filterMeldingenInkomend.not()
                )
            }
        }
}