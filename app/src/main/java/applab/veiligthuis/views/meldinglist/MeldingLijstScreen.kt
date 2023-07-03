package applab.veiligthuis.views.meldinglist

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import applab.veiligthuis.ui.meldingenlijst.MeldingList
import applab.veiligthuis.ui.meldingenlijst.FilterButtonsBar
import applab.veiligthuis.ui.VeiligThuisToolbar
import applab.veiligthuis.views.Screens


@Composable
fun MeldingLijstScreen(
    navController: NavController,
    viewModel: MeldingLijstViewModel,
) {
    val state = viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { VeiligThuisToolbar(Unit) },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxHeight(0.1F),
                backgroundColor = Color.White,
                elevation = 0.dp,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Privacy verklaring",
                    )
                }
            }
        }
    ) { it ->
        Box(modifier = Modifier.padding(horizontal = 10.dp)) {
            Column {
                FilterButtonsBar(
                    toggleInkomendSelected = { inkomend ->
                        viewModel.onEvent(
                            MeldingLijstEvent.ToggleMeldingStatusLijst(
                                inkomend
                            )
                        )
                    },
                    expandedFilter = state.value.isFilterExpanded,
                    onClickExpandFilter = { navController.navigate(Screens.FilterMeldingen.route) },
                    selectedLocatie = "",
                )
                Divider(
                    color = Color.Black,
                    modifier = Modifier
                        .padding(11.dp, 0.dp, 11.dp, 4.dp),
                    thickness = 1.dp,
                )
                MeldingList(
                    list = state.value.meldingen,
                    onCardClick = { meldingKey ->
                        navController.navigate(route = Screens.MeldingBewerken.route + "?meldingtype=${state.value.meldingType.value}&meldingKey=$meldingKey")
                    },
                    modifier = Modifier.padding(top = 10.dp, start = 15.dp, end = 15.dp),
                )
            }
        }
    }
}