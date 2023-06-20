package applab.veiligthuis.views.meldinglist

import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.ui.composable.CheckBoxItemState

data class MeldingLijstFilterState(
    val soortGeweldFilter: List<CheckBoxItemState> = listOf(CheckBoxItemState(1, false, "Ongecategoriseerd"), CheckBoxItemState(2, false, "Lichamelijke geweld"), CheckBoxItemState(3, false, "Stalking"), CheckBoxItemState(4, false, "Psychisch geweld"), CheckBoxItemState(5, false, "Financieel misbruik")),
    val statusFilter: List<CheckBoxItemState> = listOf(CheckBoxItemState(1,false, "Onbehandeld"), CheckBoxItemState(2,false, "In behandeling")),
    val datumFilter: List<CheckBoxItemState> = listOf(CheckBoxItemState(1,false, "Vandaag"), CheckBoxItemState(2,false, "Deze week"), CheckBoxItemState(3,false, "Deze maand"), CheckBoxItemState(4,false, "Afgelopen 6 maanden")),
    val beroepsmatigFilter: List<CheckBoxItemState> = listOf(CheckBoxItemState(1, false, "Ja"), CheckBoxItemState(2, false, "Nee")),
    val filterPredicates: List<(Melding) -> Boolean> = listOf()
)
