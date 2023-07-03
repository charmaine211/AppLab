package applab.veiligthuis.views.meldinglist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import applab.veiligthuis.ui.composable.filter.FilterCheckBoxList
import applab.veiligthuis.ui.composable.filter.FilterHeader
import applab.veiligthuis.ui.theme.AppTheme
import applab.veiligthuis.R
import applab.veiligthuis.views.Screens

@Composable
fun FilterPlaatsScreen(
    navController: NavController,
    filterState: MeldingLijstFilterState,
    onEvent: (MeldingLijstEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(start = 40.dp, end = 40.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        FilterHeader(
            filterCount = filterState.filterCountSelected, headerTitle = stringResource(id = R.string.filter_header_title),
            closeFilter = {
                onEvent(MeldingLijstEvent.SluitFilter)
                navController.popBackStack(route = Screens.FilterMeldingen.route, inclusive = false)
            },
            modifier = Modifier.padding(bottom = 18.dp)
        )

        Column {
            Divider()
            Box(modifier = Modifier.clickable {
                navController.popBackStack()
            }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 18.dp, bottom = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.filter_h1_plaats),
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Sluit plaats",
                        modifier = Modifier.padding(end = 20.dp)
                    )
                }
            }



            FilterCheckBoxList(
                headerText = "",
                filterItems = stringArrayResource(id = R.array.plaatsnamen).asList(),
                checkedItems = filterState.filterPlaatsen,
                onItemChecked = { selected -> onEvent(MeldingLijstEvent.FilterPlaatsen(selected)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterPlaatsScreen() {

    val filterState = MeldingLijstFilterState(filterPlaatsen = listOf("Amsterdam", "Den Haag"))
    AppTheme {
        FilterPlaatsScreen(
            navController = rememberNavController(),
            filterState = filterState,
            onEvent = {}
        )
    }
}
