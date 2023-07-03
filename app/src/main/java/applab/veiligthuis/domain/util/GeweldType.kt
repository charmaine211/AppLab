package applab.veiligthuis.domain.util

enum class GeweldType(val value: String) {
    ONGECATEGORISEERD(value = "Ongecategoriseerd"),
    LICHAMELIJK(value = "Lichamelijk geweld"),
    STALKING(value = "Stalking"),
    FINANCIEEL(value = "Financieel misbruik"),
    PSYCHISCH(value = "Psychisch geweld"),
}