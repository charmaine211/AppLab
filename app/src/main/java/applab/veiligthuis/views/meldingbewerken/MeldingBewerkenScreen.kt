package applab.veiligthuis.views.meldingbewerken

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import applab.veiligthuis.domain.model.melding.InkomendeMelding
import applab.veiligthuis.domain.model.melding.MeldingStatus
import applab.veiligthuis.ui.common.MeldingStatusDisplay
import applab.veiligthuis.ui.theme.AppTheme
import applab.veiligthuis.ui.veiligThuisToolbar
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun MeldingBewerkenScreen(
    state: MeldingBewerkenState,
    onEvent: (MeldingBewerkenEvent) -> Unit,
    backButton: () -> Unit
) {
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
        Box(modifier = Modifier.padding(top= 50.dp)){
            Column(modifier = Modifier.padding(horizontal = 35.dp)) {
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column() {
                        if(state.uneditedMelding.datum != null) {
                            DisplayDatum(datum = state.uneditedMelding.datum!!)
                        }
                        Text(
                            text = state.uneditedMelding.plaatsNaam
                        )
                    }
                    MeldingStatusDisplay(state.status)
                }
                Text(text = state.uneditedMelding.beschrijving, modifier = Modifier.padding(top = 40.dp))
                Box() {

                    var dropDownExpanded by remember { mutableStateOf(false) }
                    TextField(enabled = false, value = TextFieldValue("Type geweld"), onValueChange = {}, modifier = Modifier.clickable { dropDownExpanded = true })

                    DropdownMenu(expanded = dropDownExpanded, onDismissRequest = { dropDownExpanded = false }) {
                        DropdownMenuItem(onClick = { /*TODO*/ }) {
                            Text(text = "Ongecategoriseerd")
                        }

                        DropdownMenuItem(onClick = { /*TODO*/ }) {
                            Text(text = "Ongecategoriseerd")
                        }

                        DropdownMenuItem(onClick = { /*TODO*/ }) {
                            Text(text = "Ongecategoriseerd")
                        }
                    }
                }
                Button(onClick = { backButton }) {
                    Text("Terug")
                }
            }
        }
    }
}

@Composable
fun MeldingBekijkenHeader() {

}

@Composable
fun DisplayDatum(datum: Long) {
    val datumFromLong = LocalDateTime.ofEpochSecond(datum, 0, ZoneOffset.UTC)
    Text(
        text = "${datumFromLong.dayOfMonth} ${datumFromLong.monthValue} ${datumFromLong.year}, ${datumFromLong.hour}.${datumFromLong.minute}"
    )
}

@Preview(showBackground = true)
@Composable
fun previewDisplayDatum() {
    AppTheme {
        DisplayDatum(1685555476)
    }
}

@Preview(showBackground = true)
@Composable
fun previewMeldingBewerkenScreen() {

    val testState = MeldingBewerkenState(uneditedMelding = InkomendeMelding(1685555476, MeldingStatus.ONBEHANDELD,  "Dit si een test melding blablablablabal adad dasd asd ", "Rotterdam", typeGeweld = "Ongecategoriseerd", beroepsmatig = false))
    AppTheme {
        MeldingBewerkenScreen(state = testState, onEvent = {}, backButton = {})
    }

}