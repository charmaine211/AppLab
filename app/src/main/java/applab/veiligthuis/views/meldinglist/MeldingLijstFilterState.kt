package applab.veiligthuis.views.meldinglist

import applab.veiligthuis.domain.model.melding.Melding


data class MeldingLijstFilterState(
    val soortGeweldFilter: List<String> = listOf(),
    val statusFilter: List<String> = listOf(),
    val datumSelectedFilter: String? = null,
    val beroepsmatigFilter: List<String> = listOf(),
    val filterStatusPredicates: List<(Melding) -> Boolean> = listOf(),
    val filterSoortGeweldPredicates: List<(Melding) -> Boolean> = listOf(),
    val filterDatumPredicates: List<(Melding) -> Boolean> = listOf(),
    val filterBeroepsmatigPredicates: List<(Melding) -> Boolean> = listOf(),
    val filterCountSelected: Int = 0,
    val filterPlaatsen: List<String> = listOf(),
)
