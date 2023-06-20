package applab.veiligthuis.domain.model.melding

import androidx.compose.ui.graphics.Color
import applab.veiligthuis.ui.theme.veilig_thuis_groen
import applab.veiligthuis.ui.theme.veilig_thuis_oranje
import applab.veiligthuis.ui.theme.veilig_thuis_rood

enum class MeldingStatus(val status: String, val color: Color) {
    ONBEHANDELD("Onbehandeld", veilig_thuis_rood),
    IN_BEHANDELING("In behandeling", veilig_thuis_oranje),
    AFGESLOTEN("Afgesloten", veilig_thuis_groen)
}