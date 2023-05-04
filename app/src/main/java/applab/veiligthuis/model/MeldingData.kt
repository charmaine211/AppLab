package applab.veiligthuis.model


data class MeldingData(
    val datum: String? = null,
    val locatie: String? = null,
    val info: String? = null,
    val status: MeldingStatus? = null,
    val anoniem: Boolean? = null,
)