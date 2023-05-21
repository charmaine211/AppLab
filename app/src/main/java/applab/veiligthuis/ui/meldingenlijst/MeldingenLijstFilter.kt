package applab.veiligthuis.ui.meldingenlijst

interface MeldingenLijstFilter {
    fun filterPlaatsnaam(plaatsnaam: String)
    fun filterInkomend()
    fun resetFilter()
    fun expandedFilter()
    fun sortDate()
}