package applab.veiligthuis.viewmodel

import applab.veiligthuis.domain.util.MeldingOrder
import applab.veiligthuis.domain.util.MeldingType

sealed class MeldingEvent {
    data class Order(val meldingOrder: MeldingOrder): MeldingEvent()
    object ToggleFilterSection: MeldingEvent()
    object ToggleMeldingStatus: MeldingEvent()
}
