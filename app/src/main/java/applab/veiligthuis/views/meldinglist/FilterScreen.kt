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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import applab.veiligthuis.R
import applab.veiligthuis.ui.composable.filter.FilterCheckBoxList
import applab.veiligthuis.ui.composable.filter.FilterHeader
import applab.veiligthuis.ui.composable.filter.FilterRadioButtonList
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
            .verticalScroll(
                rememberScrollState()
            )
    ) {

        FilterHeader(
            filterCount = filterState.filterCountSelected,
            headerTitle = stringResource(R.string.filter_header_title),
            closeFilter = {
                onEvent(MeldingLijstEvent.SluitFilter)
                navController.popBackStack(route = Screens.MeldingLijst.route, inclusive = false)
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
        }
        FilterCheckBoxList(
            headerText = stringResource(R.string.filter_h1_soort_geweld),
            filterItems = filterState.soortGeweldFilter,
            onItemChecked = { id, checked ->
                onEvent(
                    MeldingLijstEvent.FilterSoortGeweld(
                        id,
                        checked
                    )
                )
            }
        )
        FilterCheckBoxList(
            headerText = stringResource(R.string.filter_h1_status),
            filterItems = filterState.statusFilter,
            onItemChecked = { id, checked ->
                onEvent(
                    MeldingLijstEvent.FilterStatus(
                        id,
                        checked
                    )
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
            filterItems = filterState.beroepsmatigFilter,
            onItemChecked = { id, checked ->
                onEvent(
                    MeldingLijstEvent.FilterBeroepsmatig(
                        id,
                        checked
                    )
                )
            }
        )
    }
}






