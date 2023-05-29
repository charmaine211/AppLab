package applab.veiligthuis.views.meldingbewerken

import applab.veiligthuis.domain.model.melding.InkomendeMelding
import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.model.melding.MeldingStatus
import applab.veiligthuis.domain.util.MeldingType

data class MeldingBewerkenState(
    val uneditedMelding: Melding = InkomendeMelding(1, MeldingStatus.ONBEHANDELD, "", "", ""),
    val meldingKey: String = "",
    val meldingType: MeldingType = MeldingType.Inkomend,
    val status: MeldingStatus = MeldingStatus.ONBEHANDELD,
    val typeGeweld: String = "",
    val beroepsmatig: Boolean = false,
)