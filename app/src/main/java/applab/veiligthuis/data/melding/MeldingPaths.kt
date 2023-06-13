package applab.veiligthuis.data.melding

enum class MeldingPaths(
    val path: String
) {
    MELDINGEN("/meldingen"),
    INKOMEND("/meldingen_inkomend"),
    AFGESLOTEN("/meldingen_afgesloten"),
    INKOMEND_PLAATS("/inkomend_plaatsnaam"),
    AFGESLOTEN_PLAATS("/afgesloten_plaatsnaam")
}
