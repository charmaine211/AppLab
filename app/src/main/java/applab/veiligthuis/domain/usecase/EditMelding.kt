package applab.veiligthuis.domain.usecase

import applab.veiligthuis.domain.model.melding.InkomendeMelding
import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.repository.melding.MeldingRepository

class EditMelding(
    private val repository: MeldingRepository
) {
    operator fun invoke(oldMelding: Melding, newMelding: Melding) {
        if(oldMelding::class.java == newMelding::class.java && oldMelding::class.java == InkomendeMelding::class.java) {
            repository.deleteMelding(oldMelding)
            repository.addMelding(newMelding)
        }
    }
}