package applab.veiligthuis.views.meldinglist


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import applab.veiligthuis.R
import applab.veiligthuis.ui.composable.filter.FilterCheckBoxList
import applab.veiligthuis.ui.composable.filter.FilterHeader
import applab.veiligthuis.ui.composable.filter.FilterRadioButtonList
import applab.veiligthuis.ui.composable.filter.SelectedItem
import applab.veiligthuis.ui.theme.AppTheme
import applab.veiligthuis.ui.theme.veilig_thuis_oranje
import applab.veiligthuis.views.Screens


@Composable
fun FilterScreen(
    navController: NavController,
    filterState: MeldingLijstFilterState,
    onEvent: (MeldingLijstEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(start = 40.dp, end = 40.dp)
    ) {
        FilterHeader(
            filterCount = filterState.filterCountSelected,
            headerTitle = stringResource(R.string.filter_header_title),
            closeFilter = {
                onEvent(MeldingLijstEvent.SluitFilter)
                navController.popBackStack(
                    route = Screens.MeldingLijst.route,
                    inclusive = false
                )
            },
            modifier = Modifier.padding(bottom = 18.dp)
        )
        Column(
            modifier = Modifier.verticalScroll(
                rememberScrollState()
            )
        ) {
            Column {
                Divider()
                Box(modifier = Modifier.clickable { navController.navigate(Screens.FilterPlaatsen.route) }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 18.dp, bottom = 18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.filter_h1_plaats),
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = stringResource(R.string.filter_plaats_description),
                            modifier = Modifier.padding(end = 20.dp)
                        )
                    }
                }
                if (filterState.filterPlaatsen.isNotEmpty()) {
                    filterState.filterPlaatsen.map { plaats ->
                        SelectedItem(
                            strValue = plaats,
                            onClose = { onEvent(MeldingLijstEvent.FilterPlaatsen(plaats)) })
                    }
                }
            }
            FilterCheckBoxList(
                headerText = stringResource(R.string.filter_h1_soort_geweld),
                filterItems = stringArrayResource(id = R.array.filter_soort_geweld).asList(),
                checkedItems = filterState.soortGeweldFilter,
                onItemChecked = { selected ->
                    onEvent(
                        MeldingLijstEvent.FilterSoortGeweld(selected)
                    )
                }
            )
            FilterCheckBoxList(
                headerText = stringResource(R.string.filter_h1_status),
                filterItems = stringArrayResource(id = R.array.filter_status).asList(),
                checkedItems = filterState.statusFilter,
                onItemChecked = { selected ->
                    onEvent(
                        MeldingLijstEvent.FilterStatus(selected)
                    )
                }
            )
            FilterRadioButtonList(
                headerText = stringResource(R.string.filter_h1_datum),
                items = stringArrayResource(id = R.array.datum_keuzes).asList(),
                selected = filterState.datumSelectedFilter,
                onSelected = { selected -> onEvent(MeldingLijstEvent.FilterDatum(selected)) }
            )
            FilterCheckBoxList(
                headerText = stringResource(R.string.filter_h1_beroepsmatig),
                filterItems = stringArrayResource(id = R.array.filter_beroepsmatig).asList(),
                checkedItems = filterState.beroepsmatigFilter,
                onItemChecked = { selected ->
                    onEvent(
                        MeldingLijstEvent.FilterBeroepsmatig(selected)
                    )
                }
            )
            if (filterState.filterCountSelected > 0) {
                Box(modifier = Modifier.height(100.dp))
            }
        }
    }
    if (filterState.filterCountSelected > 0) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.8F)
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        onEvent(MeldingLijstEvent.SluitFilter)
                        navController.popBackStack(
                            route = Screens.MeldingLijst.route,
                            inclusive = false
                        )
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = veilig_thuis_oranje),
                    elevation = ButtonDefaults.elevation(defaultElevation = 10.dp),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .width(250.dp)
                        .height(55.dp)

                ) {
                    Text(
                        text = stringResource(R.string.filter_pas_toe),
                        color = Color.White
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewFilterScreen() {
    AppTheme {
        FilterScreen(
            navController = rememberNavController(),
            filterState = MeldingLijstFilterState(),
            onEvent = {})
    }
}




