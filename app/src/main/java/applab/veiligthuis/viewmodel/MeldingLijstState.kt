package applab.veiligthuis.viewmodel

import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.util.MeldingOrder
import applab.veiligthuis.domain.util.OrderType

data class MeldingLijstState(
    val meldingen: List<Melding> = emptyList(),
    val meldingOrder: MeldingOrder = MeldingOrder.Datum(OrderType.Descending),
    val isFilterExpanded: Boolean = false
)
