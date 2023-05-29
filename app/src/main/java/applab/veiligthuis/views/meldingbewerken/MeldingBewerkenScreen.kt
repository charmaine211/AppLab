package applab.veiligthuis.views.meldingbewerken

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import applab.veiligthuis.ui.common.MeldingStatusDisplay
import applab.veiligthuis.ui.veiligThuisToolbar
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun MeldingBewerkenScreen(
    navController: NavController,
    viewModel: MeldingBewerkenViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { veiligThuisToolbar(onHome = Unit, onProfile = Unit)},
        bottomBar = { BottomAppBar(modifier = Modifier.fillMaxHeight(0.1F), backgroundColor = Color.White, elevation = 0.dp) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                Text(
                    text = "Privacy verklaring",
                )
            }
        }
        }
    ) { it->
        Box(modifier = Modifier.padding(it)){
            Column() {
                Row {
                    Column() {
                        if(state.value.uneditedMelding != null){
                            val datumParsed = LocalDateTime.ofEpochSecond(state.value.uneditedMelding.datum!!, 0 , ZoneOffset.UTC)
                            Text(text = "" + datumParsed.dayOfMonth + "-" + datumParsed.monthValue + "-" + datumParsed.year + " " + datumParsed.hour + ":" + datumParsed.minute)
                            Text(text = state.value.uneditedMelding.plaatsNaam)
                        }
                    }
                    MeldingStatusDisplay(meldingStatus = state.value.uneditedMelding.status!!)
                }
                Text(text = state.value.uneditedMelding.beschrijving)

                Button(onClick = { navController.navigate("melding_list_screen") }) {
                    Text("Terug")
                }
            }


    }

    }

}