package applab.veiligthuis.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import applab.veiligthuis.ui.meldingenlijst.MeldingList
import applab.veiligthuis.ui.veiligThuisToolbar
import applab.veiligthuis.viewmodel.MeldingLijstViewModel



@Composable
fun MeldingListScreen(
   viewModel: MeldingLijstViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { veiligThuisToolbar(Unit , Unit) },
        bottomBar = { Text(text = "Privacyverklaring") }
    ) { it ->
        Box(modifier = Modifier.padding(it)) {
            MeldingList(list = state.value.meldingen)
        }
    }
}