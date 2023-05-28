package applab.veiligthuis.repository.melding

enum class MeldingPaths(
    val path: String
) {
    ROOT(""),
    INKOMEND("/inkomend"),
    AFGESLOTEN("/afgesloten")
}
