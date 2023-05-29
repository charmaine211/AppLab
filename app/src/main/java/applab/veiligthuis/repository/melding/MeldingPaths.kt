package applab.veiligthuis.repository.melding

enum class MeldingPaths(
    val path: String
) {
    ROOT(""),
    INKOMEND("/inkomend"),
    AFGESLOTEN("/afgesloten"),
    INKOMEND_PLAATS("/inkomend_plaatsnaam"),
    AFGESLOTEN_PLAATS("/afgesloten_plaatsnaam")
}
