package applab.veiligthuis.views.meldinglist

import applab.veiligthuis.domain.util.MeldingOrder

sealed class MeldingLijstEvent {
    data class Order(val meldingOrder: MeldingOrder): MeldingLijstEvent()
    object ToggleFilterSection: MeldingLijstEvent()
    object ToggleMeldingStatusLijst: MeldingLijstEvent()
    data class Status(val id: Int, val checked: Boolean ): MeldingLijstEvent()
    data class SoortGeweld(val id: Int, val checked: Boolean): MeldingLijstEvent()
    data class Beroepsmatig(val id: Int, val checked: Boolean): MeldingLijstEvent()

}
