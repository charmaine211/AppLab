package applab.veiligthuis.domain.usecase

import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.util.MeldingType
import applab.veiligthuis.data.melding.MeldingNotFoundException
import applab.veiligthuis.data.melding.MeldingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class GetMelding(
    private val repository: MeldingRepository
) {
    @Throws(MeldingNotFoundException::class)
    operator fun invoke(
        key: String,
        meldingType: MeldingType
    ): Flow<Melding> {
        return repository.getMelding(key, meldingType).catch { throw MeldingNotFoundException("Melding niet gevonden") }
    }
}