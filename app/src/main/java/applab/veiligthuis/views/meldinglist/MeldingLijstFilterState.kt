package applab.veiligthuis.views.meldinglist

import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.ui.composable.CheckBoxItemState

data class MeldingLijstFilterState(
    val soortGeweldFilter: List<CheckBoxItemState> = listOf(
        CheckBoxItemState(
            1,
            false,
            "Ongecategoriseerd"
        ),
        CheckBoxItemState(2, false, "Lichamelijk geweld"),
        CheckBoxItemState(3, false, "Stalking"),
        CheckBoxItemState(4, false, "Psychisch geweld"),
        CheckBoxItemState(5, false, "Financieel misbruik")
    ),
    val statusFilter: List<CheckBoxItemState> = listOf(
        CheckBoxItemState(1, false, "Onbehandeld"),
        CheckBoxItemState(2, false, "In behandeling")
    ),
    val datumSelectedFilter: String? = null,
    val beroepsmatigFilter: List<CheckBoxItemState> = listOf(
        CheckBoxItemState(1, false, "Ja"),
        CheckBoxItemState(2, false, "Nee")
    ),
    val filterStatusPredicates: List<(Melding) -> Boolean> = listOf(),
    val filterSoortGeweldPredicates: List<(Melding) -> Boolean> = listOf(),
    val filterDatumPredicates: List<(Melding) -> Boolean> = listOf(),
    val filterBeroepsmatigPredicates: List<(Melding) -> Boolean> = listOf(),
    val filterCountSelected: Int = 0,
)
