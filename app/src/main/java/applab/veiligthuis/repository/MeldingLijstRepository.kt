package applab.veiligthuis.repository

import applab.veiligthuis.model.Melding
import kotlinx.coroutines.flow.Flow

interface MeldingLijstRepository {
    fun getMeldingen() : Flow<List<Melding?>>
}