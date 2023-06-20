package applab.veiligthuis.views.meldinglist


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import applab.veiligthuis.ui.composable.CheckBoxList
import applab.veiligthuis.ui.composable.FilterList
import applab.veiligthuis.ui.theme.AppTheme
import applab.veiligthuis.ui.theme.veilig_thuis_oranje


@Composable
fun FilterScreen(
    navController: NavController,
    viewModel: MeldingLijstViewModel
) {
    val filterState = viewModel.filterState.collectAsState()

    Column(modifier = Modifier.padding(start = 40.dp, end = 40.dp)) {

        FilterHeader(filterCount = 1, headerTitle = "filter",
            closeFilter = {
                viewModel.onEvent(MeldingLijstEvent.ApplyFilter)
                navController.popBackStack(route = "melding_list_screen", inclusive = false)
            },
            modifier = Modifier.padding(bottom = 18.dp)
        )

        Column() {
            Divider()
            Box(modifier = Modifier.clickable {  }) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp, bottom = 18.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Plaats", style = MaterialTheme.typography.h1, modifier = Modifier.padding(start = 20.dp))
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Ga naar plaatsen", modifier = Modifier.padding(end = 20.dp))
                }
            }
        }
        FilterList(headerText = "Soort Geweld", filterItems = filterState.value.soortGeweldFilter, onItemChecked = { id, checked -> viewModel.onEvent(MeldingLijstEvent.SoortGeweld(id, checked)) })
        FilterList(headerText = "Status", filterItems = filterState.value.statusFilter, onItemChecked = { id, checked -> viewModel.onEvent(MeldingLijstEvent.Status(id, checked)) })
        FilterList(headerText = "Datum", filterItems = filterState.value.datumFilter, onItemChecked = { id, checked -> viewModel.onEvent(MeldingLijstEvent.Status(id, checked)) })
        FilterList(headerText = "Beroepsmatig", filterItems = filterState.value.beroepsmatigFilter, onItemChecked = { id, checked -> viewModel.onEvent(MeldingLijstEvent.Beroepsmatig(id, checked)) })
    }
}

@Composable
private fun FilterHeader(
    filterCount: Int,
    headerTitle: String,
    closeFilter: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceBetween , verticalAlignment = Alignment.CenterVertically) {
        Row(verticalAlignment = Alignment.CenterVertically){
            Box(modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(color = veilig_thuis_oranje)
                .wrapContentSize(Alignment.Center)){
                Text(text = filterCount.toString(), color = Color.White, fontSize = 18.sp)
            }
            Text(text = headerTitle, modifier = Modifier.padding(start = 20.dp), fontSize = 18.sp)
        }
        IconButton(onClick = closeFilter) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Sluit filter")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHeader() {
    AppTheme {
        FilterHeader(filterCount = 1, headerTitle = "filters", closeFilter = { /*TODO*/ })
    }

}





