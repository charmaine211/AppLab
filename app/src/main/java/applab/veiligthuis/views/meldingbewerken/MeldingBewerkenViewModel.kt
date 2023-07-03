package applab.veiligthuis.views.meldingbewerken

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import applab.veiligthuis.data.melding.MeldingNotFoundException
import applab.veiligthuis.domain.usecase.MeldingUseCases
import applab.veiligthuis.domain.util.MeldingType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeldingBewerkenViewModel @Inject constructor(
    private val meldingUseCases: MeldingUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(MeldingBewerkenState())
    val uiState: StateFlow<MeldingBewerkenState> = _uiState.asStateFlow()

    private var getMeldingJob: Job? = null

    init {
        val meldingTypeStr = savedStateHandle.get<String>("meldingtype")
        val meldingType: MeldingType = if (meldingTypeStr == MeldingType.Inkomend.value) {
            MeldingType.Inkomend
        } else {
            MeldingType.Afgesloten
        }
        val key = savedStateHandle.get<String>("meldingKey")
        if (key != null) {
            try {
                getMelding(key, meldingType)
            } catch (e: MeldingNotFoundException) {
                Log.w("MBVM", "Melding niet gevonden")
            }
        }
    }

    fun onEvent(event: MeldingBewerkenEvent) {
        when (event) {
            is MeldingBewerkenEvent.TypeGeweld -> {
                viewModelScope.launch {
                    _uiState.update { currentState ->
                        currentState.copy(
                            typeGeweld = event.typeGeweld
                        )
                    }
                }
            }
            is MeldingBewerkenEvent.OnClickTypeGeweld -> {
                viewModelScope.launch {
                    _uiState.update { currentState ->
                        currentState.copy(
                            typeGeweldExpanded = currentState.typeGeweldExpanded.not()
                        )
                    }
                }
            }
            is MeldingBewerkenEvent.Status -> {
                viewModelScope.launch {
                    _uiState.update { currentState ->
                        currentState.copy(
                            status = event.status
                        )
                    }
                }
            }
            is MeldingBewerkenEvent.Beroepsmatig -> {
                viewModelScope.launch {
                    _uiState.update { currentState ->
                        currentState.copy(
                            beroepsmatig = event.beroepmatig
                        )
                    }
                }
            }
            is MeldingBewerkenEvent.SaveMelding -> {
                viewModelScope.launch {
                    meldingUseCases.editMelding(
                        _uiState.value.uneditedMelding,
                        _uiState.value.status,
                        _uiState.value.typeGeweld
                    )
                }
            }
        }
    }

    private fun getMelding(key: String, meldingType: MeldingType) {
        getMeldingJob?.cancel()
        getMeldingJob = meldingUseCases.getMelding(key, meldingType)
            .onEach { melding ->
                _uiState.update { currentState ->
                    currentState.copy(
                        uneditedMelding = melding,
                        status = melding.status,
                        typeGeweld = melding.typeGeweld
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}