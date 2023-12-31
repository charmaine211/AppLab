package applab.veiligthuis.views.meldinglist

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import applab.veiligthuis.R
import applab.veiligthuis.ui.meldingenlijst.MeldingList
import applab.veiligthuis.ui.meldingenlijst.FilterButtonsBar
import applab.veiligthuis.ui.VeiligThuisToolbar
import applab.veiligthuis.views.Screens


@Composable
fun MeldingLijstScreen(
    navController: NavController,
    uiState: MeldingLijstState,
    filterState: MeldingLijstFilterState,
    onEvent: (MeldingLijstEvent) -> Unit,
) {
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
                        text = stringResource(R.string.privacy_verklaring_text),
                    )
                }
            }
        }
    ) { it ->
        Box(modifier = Modifier.padding(horizontal = 10.dp)) {
            Column {
                FilterButtonsBar(
                    toggleInkomendSelected = { inkomend ->
                        onEvent(
                            MeldingLijstEvent.ToggleMeldingStatusLijst(
                                inkomend
                            )
                        )
                    },
                    onClickExpandFilter = { navController.navigate(Screens.FilterMeldingen.route) },
                    selectedFiltersCount = filterState.filterCountSelected
                )
                Divider(
                    color = Color.Black,
                    modifier = Modifier
                        .padding(11.dp, 0.dp, 11.dp, 4.dp),
                    thickness = 1.dp,
                )
                MeldingList(
                    list = uiState.meldingen,
                    onCardClick = { meldingKey ->
                        navController.navigate(route = Screens.MeldingBewerken.route + "?meldingtype=${uiState.meldingType.value}&meldingKey=$meldingKey")
                    },
                    modifier = Modifier.padding(top = 10.dp, start = 15.dp, end = 15.dp),
                )
            }
        }
    }
}