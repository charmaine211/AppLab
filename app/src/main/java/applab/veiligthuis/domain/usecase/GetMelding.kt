package applab.veiligthuis.domain.usecase

import applab.veiligthuis.domain.model.melding.AfgeslotenMelding
import applab.veiligthuis.domain.model.melding.InkomendeMelding
import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.util.MeldingType
import applab.veiligthuis.repository.melding.MeldingRepository
import kotlinx.coroutines.flow.Flow

class GetMelding(
    private val repository: MeldingRepository
) {
    operator fun invoke(
        key: String,
        meldingType: MeldingType
    ): Flow<Melding> {
        when(meldingType) {
            is MeldingType.Inkomend -> {
                return repository.getMelding(key, InkomendeMelding::class.java)
            }
            is MeldingType.Afgesloten -> {
                return repository.getMelding(key, AfgeslotenMelding::class.java)
            }
        }

    }
}