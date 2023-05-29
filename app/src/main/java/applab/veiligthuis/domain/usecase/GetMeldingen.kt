package applab.veiligthuis.domain.usecase

import applab.veiligthuis.domain.model.melding.AfgeslotenMelding
import applab.veiligthuis.domain.model.melding.InkomendeMelding
import applab.veiligthuis.domain.model.melding.MeldingStatus
import applab.veiligthuis.domain.util.MeldingOrder
import applab.veiligthuis.domain.util.OrderType
import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.util.MeldingType
import applab.veiligthuis.repository.melding.MeldingPaths
import applab.veiligthuis.repository.melding.MeldingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMeldingen(
    private val repository: MeldingRepository
) {
    operator fun invoke(
        meldingOrder: MeldingOrder = MeldingOrder.Datum(OrderType.Descending),
        meldingType: MeldingType = MeldingType.Inkomend,
        paths: List<String> = listOf(MeldingPaths.INKOMEND.path)
    ): Flow<List<Melding?>> {
        when(meldingType) {
            is MeldingType.Inkomend -> {
                return repository.getMeldingen(paths, InkomendeMelding::class.java)
                    .map { meldingen ->
                        when(meldingOrder.orderType) {
                            is OrderType.Ascending -> {
                                when(meldingOrder) {
                                    is MeldingOrder.Datum -> meldingen.sortedBy { it?.datum }
                                    is MeldingOrder.Status -> meldingen.sortedBy { it?.status }
                                }
                            }
                            is OrderType.Descending -> {
                                when(meldingOrder) {
                                    is MeldingOrder.Datum -> meldingen.sortedByDescending { it?.datum }
                                    is MeldingOrder.Status -> meldingen.sortedByDescending { it?.status }
                                }
                            }
                        }
                    }
            }
            is MeldingType.Afgesloten -> {
                val afgeslotenPaths: List<String>
                if(paths.isEmpty()){
                    afgeslotenPaths = listOf(MeldingPaths.AFGESLOTEN.path)
                } else {
                    afgeslotenPaths = paths
                }
                return repository.getMeldingen(afgeslotenPaths, AfgeslotenMelding::class.java)
                    .map { meldingen ->
                        when(meldingOrder.orderType) {
                            is OrderType.Ascending -> {
                                when(meldingOrder) {
                                    is MeldingOrder.Datum -> meldingen.sortedBy { it?.datum }
                                    is MeldingOrder.Status -> meldingen.sortedBy { it?.status }
                                }
                            }
                            is OrderType.Descending -> {
                                when(meldingOrder) {
                                    is MeldingOrder.Datum -> meldingen.sortedByDescending { it?.datum }
                                    is MeldingOrder.Status -> meldingen.sortedByDescending { it?.status }
                                }
                            }
                        }
                    }
            }
        }
    }
}