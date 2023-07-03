package applab.veiligthuis.views

sealed class Screens(val route: String) {
    object MeldingLijst : Screens("melding_list_screen")
    object MeldingBewerken : Screens("melding_bewerken_screen")
    object FilterMeldingen : Screens("filter_screen")
}
