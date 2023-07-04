package applab.veiligthuis.views.meldinglist

import applab.veiligthuis.domain.util.MeldingOrder

sealed class MeldingLijstEvent {
    data class Order(val meldingOrder: MeldingOrder) : MeldingLijstEvent()
    object ToggleFilterSection : MeldingLijstEvent()
    data class ToggleMeldingStatusLijst(val inkomend: Boolean) : MeldingLijstEvent()
    data class FilterStatus(val status: String) : MeldingLijstEvent()
    data class FilterSoortGeweld(val geweld: String) : MeldingLijstEvent()
    data class FilterBeroepsmatig(val beroepsmatig: String) : MeldingLijstEvent()
    data class FilterDatum(val selected: String?) : MeldingLijstEvent()
    data class FilterPlaatsen(val plaats: String) : MeldingLijstEvent()
    object SluitFilter : MeldingLijstEvent()

}
