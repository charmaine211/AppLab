package applab.veiligthuis.domain.usecase

import applab.veiligthuis.data.melding.MeldingRepository
import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.util.MeldingOrder
import applab.veiligthuis.domain.util.MeldingType
import applab.veiligthuis.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMeldingen(
    private val repository: MeldingRepository
) {
    operator fun invoke(
        meldingOrder: MeldingOrder = MeldingOrder.Datum(OrderType.Descending),
        meldingType: MeldingType = MeldingType.Inkomend,
        plaatsen: List<String> = listOf(),
        filterStatusPredicates: List<(Melding) -> Boolean> = listOf(),
        filterSoortGeweldPredicates: List<(Melding) -> Boolean> = listOf(),
        filterDatumPredicates: List<(Melding) -> Boolean> = listOf(),
        filterBeroepsmatigPredicates: List<(Melding) -> Boolean> = listOf()

    ): Flow<List<Melding?>> {
        val meldingenRes: Flow<List<Melding?>> = if (plaatsen.isEmpty()) {
            repository.getMeldingen(meldingType)
        } else {
            repository.getMeldingenPlaatsen(plaatsen = plaatsen, meldingType = meldingType)
        }

        return meldingenRes
            .map { meldingen ->
                meldingen
                    .filter { melding ->
                        if (filterStatusPredicates.isNotEmpty()) {
                            filterStatusPredicates.any { it(melding!!) }
                        } else {
                            true
                        }
                    }
                    .filter { melding ->
                        if (filterSoortGeweldPredicates.isNotEmpty()) {
                            filterSoortGeweldPredicates.any { it(melding!!) }
                        } else {
                            true
                        }
                    }
                    .filter { melding ->
                        if (filterDatumPredicates.isNotEmpty()) {
                            filterDatumPredicates.all { it(melding!!) }
                        } else {
                            true
                        }
                    }
                    .filter { melding ->
                        if (filterBeroepsmatigPredicates.isNotEmpty()) {
                            filterBeroepsmatigPredicates.any { it(melding!!) }
                        } else {
                            true
                        }
                    }
            }
            .map { meldingen ->
                when (meldingOrder.orderType) {
                    is OrderType.Ascending -> {
                        when (meldingOrder) {
                            is MeldingOrder.Datum -> meldingen.sortedBy { it?.datum }
                            is MeldingOrder.Status -> meldingen.sortedBy { it?.status }
                        }
                    }
                    is OrderType.Descending -> {
                        when (meldingOrder) {
                            is MeldingOrder.Datum -> meldingen.sortedByDescending { it?.datum }
                            is MeldingOrder.Status -> meldingen.sortedByDescending { it?.status }
                        }
                    }
                }
            }
    }
}
