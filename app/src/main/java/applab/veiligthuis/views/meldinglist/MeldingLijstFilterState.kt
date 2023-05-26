package applab.veiligthuis.views.meldinglist

import applab.veiligthuis.ui.composable.CheckBoxItem

data class MeldingLijstFilterState(
    val soortGeweldFilter: List<CheckBoxItem> = listOf(CheckBoxItem(1, false, "Ongecategoriseerd"), CheckBoxItem(2, false, "Lichamelijke geweld"), CheckBoxItem(3, false, "Stalking"), CheckBoxItem(4, false, "Psychisch geweld"), CheckBoxItem(5, false, "Financieel misbruik")),
    val statusFilter: List<CheckBoxItem> = listOf(CheckBoxItem(1,false, "Onbehandeld"), CheckBoxItem(2,false, "In behandeling")),
    val datumFilter: List<Any> = emptyList(),
    val beroepsmatigFilter: List<CheckBoxItem> = listOf(CheckBoxItem(1, false, "Ja"), CheckBoxItem(2, false, "Nee"))
)
