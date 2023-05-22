package applab.veiligthuis.domain.util

sealed class MeldingOrder(
    val orderType: OrderType
) {
    class Datum(orderType: OrderType): MeldingOrder(orderType)
    class Status(orderType: OrderType): MeldingOrder(orderType)
}
