package applab.veiligthuis.domain.usecase

import applab.veiligthuis.domain.model.melding.AfgeslotenMelding
import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.model.melding.MeldingStatus
import applab.veiligthuis.repository.melding.MeldingRepository

class UpdateMelding(
    private val repository: MeldingRepository
) {
    operator fun invoke(melding: Melding, newStatus: MeldingStatus, newTypeGeweld: String) {
        lateinit var updatedMelding: Melding
        if(newStatus == MeldingStatus.AFGESLOTEN && (melding.status == MeldingStatus.ONBEHANDELD || melding.status == MeldingStatus.IN_BEHANDELING )) {
            updatedMelding = AfgeslotenMelding(datum = melding.datum, status = newStatus, beschrijving = melding.beschrijving, plaatsNaam = melding.plaatsNaam, key = melding.key, typeGeweld = newTypeGeweld, beroepsmatig = melding.beroepsmatig)
            repository.deleteMelding(melding)
        } else {
            updatedMelding = melding.copy(status = newStatus, typeGeweld = newTypeGeweld)
        }
        repository.addMelding(updatedMelding)
    }
}