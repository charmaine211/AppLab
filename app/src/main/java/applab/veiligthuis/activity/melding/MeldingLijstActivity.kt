package applab.veiligthuis.activity.melding



import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import applab.veiligthuis.model.Melding
import applab.veiligthuis.model.MeldingStatus
import applab.veiligthuis.ui.theme.*
import applab.veiligthuis.viewmodel.MeldingLijstViewModel
import kotlinx.coroutines.flow.asFlow

class MeldingLijstActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                meldingLijstScreen(MeldingLijstViewModel())
            }
        }
    }
}

@Composable
private fun meldingLijstScreen(meldingenLijstViewModel: MeldingLijstViewModel = MeldingLijstViewModel(), modifier: Modifier = Modifier.padding(1.dp)) {

    meldingenLijstViewModel.loadMeldingen()
    val meldingenLijstUiState by meldingenLijstViewModel.uiState.collectAsState()

    Scaffold(
        topBar = { topBar(meldingenLijstUiState.filterMeldingenInkomend,
            { meldingenLijstViewModel.swapFilterMeldingenInkomend() }) },
        content = { contentPadding -> Box(modifier = Modifier.padding(contentPadding)) { meldingList(
            filterInkomendSelected = meldingenLijstUiState.filterMeldingenInkomend,
            meldingen = meldingenLijstUiState.meldingen
        ) } },
        bottomBar = {
            BottomAppBar(modifier = modifier.fillMaxHeight(0.1F), backgroundColor = Color.White, elevation = 0.dp) {
                Row(horizontalArrangement = Arrangement.Center, modifier = modifier.fillMaxWidth()){
                    Text(
                        text = "Privacy verklaring",
                    )
                }

            }
        },
        modifier = modifier.padding(22.dp, 10.dp)
    )
}

@Composable
private fun topBar(
    filterInkomendSelected: Boolean,
    onClickFilterChange: () -> Unit,
    modifier: Modifier = Modifier
) {
        Column(
            modifier = modifier
                .padding(5.dp)
                .background(Color.White),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp, top = 4.dp)
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = blue_rect,
                    modifier = modifier
                        .size(48.dp)
                        .padding(start = 4.dp)
                )
                Icon(Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = blue_rect,
                    modifier = modifier
                        .size(48.dp)
                        .padding(end = 4.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ){
                meldingFilterButtons(inkomendSelected = filterInkomendSelected, onClick = onClickFilterChange)
                Text(
                    text = "Filter",
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = modifier
                        .background(color = filter_blue, shape = RoundedCornerShape(50))
                        .padding(15.dp, 5.dp)
                )
            }
            Divider(
                color = Color.Black,
                modifier = modifier
                    .padding(4.dp, 15.dp, 4.dp, 0.dp),
                thickness = 1.dp
            )
        }
}


@Composable
private fun meldingList(
    filterInkomendSelected: Boolean,
    meldingen: List<Melding?>,
    modifier: Modifier = Modifier){
    Log.i("ACT", "Lijst aanmaken")
    val filterMeldingen : List<Melding?>
    if(filterInkomendSelected){
        filterMeldingen = meldingen.filter { melding -> melding?.status != MeldingStatus.AFGEROND }
    } else {
        filterMeldingen = meldingen.filter { melding -> melding?.status == MeldingStatus.AFGEROND }
    }

    LazyColumn() {
        items(filterMeldingen){ melding ->
            if(melding != null) {
                meldingCard(datum = "placeholder", shortDescription = melding.info, status = melding.status, anoniem = melding.anoniem)
                Log.i("ACT", "Kaart gemaakt")
            }
        }
    }
}

@Composable
private fun meldingCard(
    datum: String?,
    shortDescription: String?,
    status: MeldingStatus?,
    anoniem: Boolean?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = 4.dp

    ) {
        Column(modifier = modifier.padding(10.dp)){
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                Column(modifier = modifier.fillMaxWidth(0.6F)){
                    if (datum != null) {
                        Text(
                            text = datum,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Anoniem",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
                    if (shortDescription != null) {
                        Text(
                            text = shortDescription,
                            fontSize = 12.sp,
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)){
                    statusMelding(status)
                }
            }
        }
    }
}
@Composable
private fun meldingFilterButtons(
    inkomendSelected: Boolean,
    onClick: () -> Unit
){
    Row(
    ){
        meldingFilterButton("Inkomend", inkomendSelected, onClick )
        meldingFilterButton("Afgesloten", !inkomendSelected, onClick )
    }
}

@Composable
private fun meldingFilterButton(
    buttonText: String,
    enabledButton: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(45),
        colors = ButtonDefaults.buttonColors(disabledBackgroundColor = filter_blue, backgroundColor = filter_grey),
        modifier = Modifier.minimumInteractiveComponentSize(),
        enabled = !enabledButton
    ) {
        Text(
            text = buttonText,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}
@Composable
private fun statusMelding(meldingStatus: MeldingStatus?){
    if (meldingStatus != null) {
        Text (
            text = meldingStatus.status,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier
                .background(color = meldingStatus.color, shape = RoundedCornerShape(45))
                .padding(8.dp, 2.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun previewMeldingCard() {
    AppTheme {
        meldingCard(datum = "23 maart 2023 - 18.19", shortDescription = "Mijn melding gaat over...", status = MeldingStatus.AFGEROND, anoniem = true)
    }
}

@Preview
@Composable
fun previewTopbar() {
    AppTheme {
        //topBar(true)
    }
}

@Preview(showBackground = true)
@Composable
fun previewMeldingenScreen() {
    AppTheme {
        meldingLijstScreen(meldingenLijstViewModel = MeldingLijstViewModel())
    }
}

@Preview(showBackground = true)
@Composable
fun previewMeldingFilterButton(){
    AppTheme {
        meldingFilterButtons(true, { Unit })
    }
}

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
     MaterialTheme(
         colors = lightColors(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
         ),
        typography = Typography(
            body1 = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )),
        content = content
     )
}
