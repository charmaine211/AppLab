package applab.veiligthuis.views.meldingbewerken

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import applab.veiligthuis.domain.model.melding.AfgeslotenMelding
import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.model.melding.MeldingStatus
import applab.veiligthuis.domain.usecase.MeldingUseCases
import applab.veiligthuis.domain.util.MeldingType
import applab.veiligthuis.data.melding.MeldingNotFoundException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeldingBewerkenViewModel @Inject constructor(
    private val meldingUseCases: MeldingUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(MeldingBewerkenState())
    val uiState: StateFlow<MeldingBewerkenState> = _uiState.asStateFlow()

    private var getMeldingJob: Job? = null

    init {
        val meldingTypeStr = savedStateHandle.get<String>("meldingtype")
        val meldingType: MeldingType
        if(meldingTypeStr == "inkomend"){
            meldingType = MeldingType.Inkomend
        } else {
            meldingType = MeldingType.Afgesloten
        }
        val key = savedStateHandle.get<String>("meldingKey")
        if(key != null) {
            try {
                getMelding(key, meldingType)
            } catch (e: MeldingNotFoundException) {
                Log.w("MBVM", "Melding niet gevonden")
            }
        }
    }

    fun onEvent(event: MeldingBewerkenEvent){
        when(event) {
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
                    lateinit var newMelding: Melding
                    if(_uiState.value.uneditedMelding != null) {
                        if(_uiState.value.uneditedMelding!!.status != MeldingStatus.AFGESLOTEN && _uiState.value.status == MeldingStatus.AFGESLOTEN) {
                            newMelding = AfgeslotenMelding(
                                datum = _uiState.value.uneditedMelding!!.datum,
                                status = _uiState.value.status,
                                beschrijving = _uiState.value.uneditedMelding!!.beschrijving,
                                plaatsNaam = _uiState.value.uneditedMelding!!.plaatsNaam,
                                key = _uiState.value.uneditedMelding!!.key,
                                typeGeweld = _uiState.value.typeGeweld,
                                beroepsmatig = _uiState.value.beroepsmatig,
                            )
                        } else {
                            newMelding = _uiState.value.uneditedMelding!!.copy(status = _uiState.value.status, typeGeweld = _uiState.value.typeGeweld, beroepsmatig = _uiState.value.beroepsmatig)
                        }
                    }
                    if(_uiState.value.uneditedMelding != null) {
                        meldingUseCases.editMelding(_uiState.value.uneditedMelding!!, _uiState.value.status, _uiState.value.typeGeweld)
                    }
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
                        uneditedMelding = melding
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}