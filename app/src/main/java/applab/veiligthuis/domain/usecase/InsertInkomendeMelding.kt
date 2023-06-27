package applab.veiligthuis.domain.usecase

import applab.veiligthuis.domain.model.melding.InkomendeMelding
import applab.veiligthuis.domain.model.melding.MeldingStatus
import applab.veiligthuis.data.melding.MeldingRepository
import java.time.LocalDateTime
import java.time.ZoneOffset

class InsertInkomendeMelding(
    private val repository: MeldingRepository
) {
    operator fun invoke(datum: LocalDateTime, beschrijving: String, plaatsNaam: String, typeGeweld: String, beroepsmatig: Boolean) {
        repository.insertOrUpdateMelding(
            InkomendeMelding(
                datum = datum.toEpochSecond(ZoneOffset.UTC),
                status = MeldingStatus.ONBEHANDELD,
                beschrijving = beschrijving,
                plaatsNaam = plaatsNaam,
                typeGeweld = typeGeweld,
                beroepsmatig = beroepsmatig
            )
        )
    }
}