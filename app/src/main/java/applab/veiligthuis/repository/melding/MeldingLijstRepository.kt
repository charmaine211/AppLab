package applab.veiligthuis.repository.melding

import applab.veiligthuis.model.Melding
import kotlinx.coroutines.flow.Flow

interface MeldingLijstRepository {
    fun getMeldingen() : Flow<List<Melding?>>
}