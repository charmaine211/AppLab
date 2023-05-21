package applab.veiligthuis.repository.melding

import applab.veiligthuis.model.Melding
import applab.veiligthuis.model.MeldingData
import kotlinx.coroutines.flow.Flow

interface MeldingRepository {
    fun getMeldingen() : Flow<List<MeldingData?>>
    fun addMelding(melding: Melding)
}