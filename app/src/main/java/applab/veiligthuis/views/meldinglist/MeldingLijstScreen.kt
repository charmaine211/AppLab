package applab.veiligthuis.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import applab.veiligthuis.ui.meldingenlijst.MeldingList
import applab.veiligthuis.ui.meldingenlijst.filterButtonsBar
import applab.veiligthuis.ui.veiligThuisToolbar
import applab.veiligthuis.views.meldinglist.MeldingLijstEvent
import applab.veiligthuis.viewmodel.MeldingLijstViewModel



@Composable
fun MeldingLijstScreen(
    navController: NavController,
    viewModel: MeldingLijstViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { veiligThuisToolbar(Unit) },
        bottomBar = {
            BottomAppBar(modifier = Modifier.fillMaxHeight(0.1F), backgroundColor = Color.White, elevation = 0.dp) {
                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                        Text(
                            text = "Privacy verklaring",
                        )
                    }
                }
        }
    ) { it ->
        Box(modifier = Modifier.padding(horizontal = 10.dp)) {
            Column() {
                filterButtonsBar(
                    isInkomendSelected = state.value.isInkomendSelected,
                    toggleInkomendSelected = { viewModel.onEvent(MeldingLijstEvent.ToggleMeldingStatusLijst) },
                    expandedFilter = state.value.isFilterExpanded,
                    onClickExpandFilter = { navController.navigate("melding_list_filter") },
                    selectedLocatie = ""
                )
                Divider(
                        color = Color.Black,
                        modifier = Modifier
                            .padding(11.dp, 0.dp, 11.dp, 4.dp),
                        thickness = 1.dp
                    )
                MeldingList(list = state.value.meldingen, onCardClick = {
                        meldingKey -> navController.navigate(
                    "melding_bewerken_screen" +
                            "?meldingtype=${state.value.meldingType.value}&meldingKey=$meldingKey")
                } )
            }
        }
    }
}