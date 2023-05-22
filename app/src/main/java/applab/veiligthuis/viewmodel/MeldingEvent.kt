package applab.veiligthuis.viewmodel

import applab.veiligthuis.domain.util.MeldingOrder

sealed class MeldingEvent {
    data class Order(val meldingOrder: MeldingOrder): MeldingEvent()
    object ToggleFilterSection: MeldingEvent()
}
