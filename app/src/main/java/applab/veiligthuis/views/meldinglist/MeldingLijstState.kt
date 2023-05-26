package applab.veiligthuis.views.meldinglist

import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.util.MeldingOrder
import applab.veiligthuis.domain.util.MeldingType
import applab.veiligthuis.domain.util.OrderType

data class MeldingLijstState(
    val meldingen: List<Melding> = emptyList(),
    val meldingOrder: MeldingOrder = MeldingOrder.Datum(OrderType.Descending),
    val meldingType: MeldingType = MeldingType.Inkomend,
    val isFilterExpanded: Boolean = false,
    val isInkomendSelected: Boolean = true,
)
