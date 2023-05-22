package applab.veiligthuis.domain.usecase

import applab.veiligthuis.domain.model.MeldingStatus
import applab.veiligthuis.domain.util.MeldingOrder
import applab.veiligthuis.domain.util.OrderType
import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.util.MeldingType
import applab.veiligthuis.repository.melding.MeldingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class GetMeldingen(
    private val repository: MeldingRepository
) {
    operator fun invoke(
        meldingOrder: MeldingOrder = MeldingOrder.Datum(OrderType.Descending),
        meldingType: MeldingType = MeldingType.Inkomend
    ): Flow<List<Melding>> {
        return repository.getMeldingen()
            .map { meldingen ->
                when(meldingType) {
                    is MeldingType.Inkomend -> {
                        meldingen.filter { it.status == MeldingStatus.ONBEHANDELD || it.status == MeldingStatus.IN_BEHANDELING }
                    }
                    is MeldingType.Afgesloten -> {
                        meldingen.filter { it.status == MeldingStatus.AFGESLOTEN }
                    }
                }
            }
            .map { meldingen ->
                when(meldingOrder.orderType) {
                    is OrderType.Ascending -> {
                        when(meldingOrder) {
                            is MeldingOrder.Datum -> meldingen.sortedBy { it.datum }
                            is MeldingOrder.Status -> meldingen.sortedBy { it.status }
                        }
                    }
                    is OrderType.Descending -> {
                        when(meldingOrder) {
                            is MeldingOrder.Datum -> meldingen.sortedByDescending { it.datum }
                            is MeldingOrder.Status -> meldingen.sortedByDescending { it.status }
                        }
                    }
                }
        }
    }
}