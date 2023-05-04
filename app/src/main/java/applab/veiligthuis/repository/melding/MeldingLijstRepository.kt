package applab.veiligthuis.repository.melding

import applab.veiligthuis.model.MeldingData
import kotlinx.coroutines.flow.Flow

interface MeldingLijstRepository {
    fun getMeldingen() : Flow<List<MeldingData?>>
}