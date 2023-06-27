package applab.veiligthuis.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
