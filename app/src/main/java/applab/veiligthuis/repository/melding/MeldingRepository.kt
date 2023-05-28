package applab.veiligthuis.repository.melding



import applab.veiligthuis.domain.model.melding.Melding
import kotlinx.coroutines.flow.Flow

interface MeldingRepository {
    fun<T: Melding> getMeldingen(paths: List<String>, valueType: Class<T>): Flow<List<Melding?>>
    fun addMelding(melding: Melding)
    fun editMelding(melding: Melding)
    fun deleteMelding(melding: Melding)
}