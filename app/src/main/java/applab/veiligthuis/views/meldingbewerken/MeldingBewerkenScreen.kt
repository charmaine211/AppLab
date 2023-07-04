package applab.veiligthuis.views.meldingbewerken

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import applab.veiligthuis.R
import applab.veiligthuis.domain.model.melding.InkomendeMelding
import applab.veiligthuis.domain.model.melding.MeldingStatus
import applab.veiligthuis.domain.util.GeweldType
import applab.veiligthuis.ui.common.MeldingStatusDisplay
import applab.veiligthuis.ui.theme.AppTheme
import applab.veiligthuis.ui.theme.filter_blue
import applab.veiligthuis.ui.VeiligThuisToolbar
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun MeldingBewerkenScreen(
    state: MeldingBewerkenState,
    onEvent: (MeldingBewerkenEvent) -> Unit,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { VeiligThuisToolbar(onProfile = Unit) },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxHeight(0.1F),
                backgroundColor = Color.White,
                elevation = 0.dp
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
        Box(modifier = Modifier.padding(top = 50.dp)) {
            Column(modifier = Modifier.padding(horizontal = 35.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        if (state.uneditedMelding.datum != null) {
                            DisplayDatum(datum = state.uneditedMelding.datum!!)
                        }
                        Text(
                            text = state.uneditedMelding.plaatsNaam
                        )
                    }
                    MeldingStatusDisplay(meldingStatus = state.status)
                }
                Text(
                    text = state.uneditedMelding.beschrijving,
                    modifier = Modifier.padding(top = 40.dp)
                )
                Box {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp)
                    ) {
                        var dropDownExpanded by remember { mutableStateOf(false) }
                        TextField(
                            enabled = false,
                            value = TextFieldValue(state.typeGeweld),
                            onValueChange = {},
                            shape = RoundedCornerShape(10),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                                textColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .clickable { dropDownExpanded = true }
                                .border(
                                    border = BorderStroke(1.dp, Color.Black),
                                    shape = RoundedCornerShape(10)
                                )
                                .width(190.dp)
                                .height(55.dp),
                            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)

                        )

                        DropdownMenu(
                            expanded = dropDownExpanded,
                            onDismissRequest = { dropDownExpanded = false }) {
                            DropdownMenuItem(onClick = {
                                onEvent(MeldingBewerkenEvent.TypeGeweld(GeweldType.ONGECATEGORISEERD.value))
                                dropDownExpanded = false
                            }) {
                                Text(text = stringResource(R.string.geweld_type_ongecategoriseerd))
                            }
                            DropdownMenuItem(onClick = {
                                onEvent(MeldingBewerkenEvent.TypeGeweld(GeweldType.FINANCIEEL.value))
                                dropDownExpanded = false
                            }) {
                                Text(text = stringResource(R.string.geweld_type_financieel_misbruik))
                            }
                            DropdownMenuItem(onClick = {
                                onEvent(MeldingBewerkenEvent.TypeGeweld(GeweldType.LICHAMELIJK.value))
                                dropDownExpanded = false
                            }) {
                                Text(text = stringResource(R.string.geweld_type_lichamelijk))
                            }
                            DropdownMenuItem(onClick = {
                                onEvent(MeldingBewerkenEvent.TypeGeweld(GeweldType.STALKING.value))
                                dropDownExpanded = false
                            }) {
                                Text(text = stringResource(R.string.geweld_type_stalking))
                            }
                            DropdownMenuItem(onClick = {
                                onEvent(MeldingBewerkenEvent.TypeGeweld(GeweldType.PSYCHISCH.value))
                                dropDownExpanded = false
                            }) {
                                Text(text = stringResource(R.string.geweld_type_psychisch))
                            }
                        }

                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { onEvent(MeldingBewerkenEvent.SaveMelding) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = filter_blue),
                        elevation = ButtonDefaults.elevation(defaultElevation = 10.dp),
                        modifier = Modifier.padding(top = 40.dp).width(190.dp).height(55.dp)

                    ) {
                        Text(
                            text = stringResource(R.string.melding_bewerken_edit_button),
                            color = Color.White
                        )
                    }
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = filter_blue),
                        elevation = ButtonDefaults.elevation(defaultElevation = 10.dp),
                        modifier = Modifier.padding(top = 20.dp).width(190.dp).height(55.dp)

                    ) {
                        Text(
                            text = stringResource(R.string.melding_bewerken_back_button),
                            color = Color.White
                        )
                    }
                    Button(
                        onClick = {
                            onEvent(MeldingBewerkenEvent.Status(MeldingStatus.AFGESLOTEN))
                            onEvent(MeldingBewerkenEvent.SaveMelding)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = filter_blue),
                        elevation = ButtonDefaults.elevation(defaultElevation = 10.dp),
                        modifier = Modifier.padding(top = 20.dp).width(190.dp).height(55.dp)

                    ) {
                        Text(
                            text = stringResource(R.string.melding_bewerken_close_melding),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
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
fun PreviewDisplayDatum() {
    AppTheme {
        DisplayDatum(1685555476)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMeldingBewerkenScreen() {

    val testState = MeldingBewerkenState(
        uneditedMelding = InkomendeMelding(
            datum = 1685555476,
            status = MeldingStatus.ONBEHANDELD,
            beschrijving = "Dit si een test melding blablablablabal adad dasd asd ",
            plaatsNaam = "Rotterdam",
            typeGeweld = "Ongecategoriseerd",
            beroepsmatig = false
        )
    )
    AppTheme {
        MeldingBewerkenScreen(
            state = testState,
            onEvent = {},
            navController = rememberNavController()
        )
    }

}