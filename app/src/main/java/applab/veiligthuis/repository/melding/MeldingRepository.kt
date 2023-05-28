package applab.veiligthuis.repository.melding



import applab.veiligthuis.domain.model.melding.Melding
import kotlinx.coroutines.flow.Flow

interface MeldingRepository {
    fun getMeldingen() : Flow<List<Melding?>>
    fun addMelding(melding: Melding, paths: List<String>)
    fun editMelding(melding: Melding, paths: List<String>)
    fun deleteMelding(melding: Melding, paths: List<String>)
}