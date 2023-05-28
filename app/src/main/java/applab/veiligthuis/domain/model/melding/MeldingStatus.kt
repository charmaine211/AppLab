package applab.veiligthuis.domain.model.melding

import androidx.compose.ui.graphics.Color
import applab.veiligthuis.ui.theme.status_afgesloten
import applab.veiligthuis.ui.theme.status_in_behandeling
import applab.veiligthuis.ui.theme.status_onbehandeld

enum class MeldingStatus(val status: String, val color: Color) {
    ONBEHANDELD("Onbehandeld", status_onbehandeld),
    IN_BEHANDELING("In behandeling", status_in_behandeling),
    AFGESLOTEN("Afgesloten", status_afgesloten)
}