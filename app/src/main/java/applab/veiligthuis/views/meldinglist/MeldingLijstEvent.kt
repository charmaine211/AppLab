package applab.veiligthuis.views.meldinglist

import applab.veiligthuis.domain.util.MeldingOrder

sealed class MeldingLijstEvent {
    data class Order(val meldingOrder: MeldingOrder): MeldingLijstEvent()
    object ToggleFilterSection: MeldingLijstEvent()
    object ToggleMeldingStatusLijst: MeldingLijstEvent()
    data class FilterStatus(val id: Int, val checked: Boolean ): MeldingLijstEvent()
    data class FilterSoortGeweld(val id: Int, val checked: Boolean): MeldingLijstEvent()
    data class FilterBeroepsmatig(val id: Int, val checked: Boolean): MeldingLijstEvent()
    data class FilterDatum(val id: Int, val checked: Boolean): MeldingLijstEvent()
    object SluitFilter: MeldingLijstEvent()

}
