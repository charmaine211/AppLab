package applab.veiligthuis.views.meldingbewerken

import applab.veiligthuis.domain.model.melding.MeldingStatus

sealed class MeldingBewerkenEvent {
    data class Status(val status: MeldingStatus) : MeldingBewerkenEvent()
    data class TypeGeweld(val typeGeweld: String) : MeldingBewerkenEvent()
    data class Beroepsmatig(val beroepmatig: Boolean) : MeldingBewerkenEvent()
    object SaveMelding : MeldingBewerkenEvent()
    object OnClickTypeGeweld : MeldingBewerkenEvent()
}