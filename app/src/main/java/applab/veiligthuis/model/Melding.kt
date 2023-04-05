package applab.veiligthuis.model


data class Melding(
    val datum: String? = null,
    val locatie: String? = null,
    val info: String? = null,
    val status: MeldingStatus? = null,
    val anoniem: Boolean? = null,
)