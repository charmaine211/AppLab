package applab.veiligthuis.domain.util

sealed class MeldingType {
    object Inkomend: MeldingType()
    object Afgesloten: MeldingType()
}
