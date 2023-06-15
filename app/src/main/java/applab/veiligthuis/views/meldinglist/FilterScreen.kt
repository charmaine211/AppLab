package applab.veiligthuis.views.meldinglist


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import applab.veiligthuis.viewmodel.MeldingLijstViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import applab.veiligthuis.ui.composable.CheckBoxList
import applab.veiligthuis.ui.theme.AppTheme


@Composable
fun FilterScreen(
    navController: NavController,
    viewModel: MeldingLijstViewModel = hiltViewModel()
) {
    val filterState = viewModel.filterState.collectAsState()

    Column() {
        Box() {
            CheckBoxList(items = filterState.value.statusFilter, onChecked = { id, checked -> viewModel.onEvent(MeldingLijstEvent.Status(id, checked))})
        }

        Box() {
            CheckBoxList(items = filterState.value.beroepsmatigFilter, onChecked = { id, checked -> viewModel.onEvent(MeldingLijstEvent.Beroepsmatig(id, checked)) })
        }

        Box() {
            CheckBoxList(items = filterState.value.soortGeweldFilter, onChecked = { id, checked -> viewModel.onEvent(MeldingLijstEvent.SoortGeweld(id, checked)) })
        }

        Button( onClick = {
            viewModel.onEvent(MeldingLijstEvent.ApplyFilter)
            navController.popBackStack()
        }) {
            Text( text = "Apply Filter")
        }
    }
}






