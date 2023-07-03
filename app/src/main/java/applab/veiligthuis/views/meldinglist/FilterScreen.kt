package applab.veiligthuis.views.meldinglist


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import applab.veiligthuis.ui.composable.filter.*


@Composable
fun FilterScreen(
    navController: NavController,
    viewModel: MeldingLijstViewModel
) {
    val filterState = viewModel.filterState.collectAsState()

    Column(
        modifier = Modifier
            .padding(start = 40.dp, end = 40.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {

        FilterHeader(
            filterCount = 1, headerTitle = "filter",
            closeFilter = {
                viewModel.onEvent(MeldingLijstEvent.SluitFilter)
                navController.popBackStack(route = "melding_list_screen", inclusive = false)
            },
            modifier = Modifier.padding(bottom = 18.dp)
        )

        Column {
            Divider()
            Box(modifier = Modifier.clickable { }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 18.dp, bottom = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Plaats",
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Ga naar plaatsen",
                        modifier = Modifier.padding(end = 20.dp)
                    )
                }
            }
        }
        FilterCheckBoxList(
            headerText = "Soort Geweld",
            filterItems = filterState.value.soortGeweldFilter,
            onItemChecked = { id, checked ->
                viewModel.onEvent(
                    MeldingLijstEvent.FilterSoortGeweld(
                        id,
                        checked
                    )
                )
            }
        )
        FilterCheckBoxList(
            headerText = "Status",
            filterItems = filterState.value.statusFilter,
            onItemChecked = { id, checked ->
                viewModel.onEvent(
                    MeldingLijstEvent.FilterStatus(
                        id,
                        checked
                    )
                )
            }
        )
        FilterRadioButtonList(
            headerText = "Datum",
            items = listOf("Vandaag", "Deze Week", "Deze Maand", "Afgelopen Maand"),
            selected = filterState.value.datumSelectedFilter,
            onSelected = { selected -> viewModel.onEvent(MeldingLijstEvent.FilterDatum(selected)) }
        )
        FilterCheckBoxList(
            headerText = "Beroepsmatig",
            filterItems = filterState.value.beroepsmatigFilter,
            onItemChecked = { id, checked ->
                viewModel.onEvent(
                    MeldingLijstEvent.FilterBeroepsmatig(
                        id,
                        checked
                    )
                )
            }
        )
    }
}






