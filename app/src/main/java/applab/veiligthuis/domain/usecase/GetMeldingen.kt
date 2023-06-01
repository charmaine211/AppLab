package applab.veiligthuis.domain.usecase

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
        plaatsen: List<String> = listOf()
    ): Flow<List<Melding?>> {
        lateinit var paths: List<String>
        when(meldingType) {
            is MeldingType.Inkomend -> {
                paths = if(plaatsen.isEmpty()) listOf(MeldingPaths.INKOMEND.path) else plaatsen.map { plaats -> "${MeldingPaths.INKOMEND_PLAATS.path}/$plaats" }
            }
            is MeldingType.Afgesloten -> {
                paths = if(plaatsen.isEmpty()) listOf(MeldingPaths.AFGESLOTEN.path) else plaatsen.map { plaats -> "${MeldingPaths.AFGESLOTEN_PLAATS.path}/$plaats" }
            }
        }
        return repository.getMeldingen(paths, meldingType)
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