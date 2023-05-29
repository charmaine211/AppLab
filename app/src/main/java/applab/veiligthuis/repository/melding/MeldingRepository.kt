package applab.veiligthuis.repository.melding



import applab.veiligthuis.domain.model.melding.Melding
import kotlinx.coroutines.flow.Flow

interface MeldingRepository {
    fun<T: Melding> getMeldingen(paths: List<String>, meldingType: Class<T>): Flow<List<Melding?>>
    fun<T: Melding> getMelding(meldingKey: String, meldingType: Class<T>): Flow<Melding>
    fun addMelding(melding: Melding)
    fun editMelding(melding: Melding)
    fun deleteMelding(melding: Melding)
}