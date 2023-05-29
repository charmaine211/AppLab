package applab.veiligthuis.domain.usecase

import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.repository.melding.MeldingRepository

class DeleteMelding(
    private val repository: MeldingRepository
) {
    operator fun invoke(melding: Melding) {
        repository.deleteMelding(melding)
    }
}