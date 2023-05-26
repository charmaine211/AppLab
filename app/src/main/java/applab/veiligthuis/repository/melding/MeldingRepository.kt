package applab.veiligthuis.repository.melding



import applab.veiligthuis.domain.model.melding.Melding
import kotlinx.coroutines.flow.Flow

interface MeldingRepository {
    fun getMeldingen() : Flow<List<Melding>>
    fun getMeldingenFilter(filter: String): Flow<List<Melding>>
    fun addMelding(melding: applab.veiligthuis.domain.model.Melding)
}