package applab.veiligthuis.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import applab.veiligthuis.model.Melding
import applab.veiligthuis.repository.MeldingRepository
import applab.veiligthuis.repository.MeldingRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MeldingenLijstUiState(
    val meldingen : List<Melding> = listOf()
)

class MeldingLijstViewModel() : ViewModel() {
    val meldingRepo : MeldingRepository =  MeldingRepositoryImpl()

    val meldingen: LiveData<List<Melding>> = meldingRepo.meldingenListLiveData

}