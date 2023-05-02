package applab.veiligthuis.activity.melding



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import applab.veiligthuis.R
import applab.veiligthuis.model.Melding
import applab.veiligthuis.ui.Toolbar
import applab.veiligthuis.ui.meldingenlijst.MeldingBekijkenScreen
import applab.veiligthuis.ui.meldingenlijst.meldingList
import applab.veiligthuis.ui.theme.*
import applab.veiligthuis.viewmodel.MeldingLijstViewModel


class MeldingLijstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppTheme {
            meldingLijstScreen(MeldingLijstViewModel())
        } }
    }
}

@Composable
private fun meldingLijstScreen(
    meldingenLijstViewModel: MeldingLijstViewModel = MeldingLijstViewModel(),
    modifier: Modifier = Modifier.padding(1.dp)
) {
    meldingenLijstViewModel.loadMeldingen()
    val meldingenLijstUiState by meldingenLijstViewModel.uiState.collectAsState()

    if(meldingenLijstUiState.showingLijstScreen) {
        Scaffold(
            topBar = { topBar(meldingenLijstUiState.filterMeldingenInkomend,
                { meldingenLijstViewModel.updateFilterMeldingenInkomend() }) },
            content = { contentPadding -> Box(modifier = Modifier.padding(contentPadding)) { meldingList(
                filterInkomendSelected = meldingenLijstUiState.filterMeldingenInkomend,
                meldingen = meldingenLijstUiState.meldingen,
                onCardClick = { melding: Melding -> meldingenLijstViewModel.updateMeldingBekijkenScreen(melding = melding) }
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
    } else {
        MeldingBekijkenScreen(
            melding =meldingenLijstUiState.selectedMelding,
            onBackButtonClicked = { meldingenLijstViewModel.resetMeldingenLijstScreen() }
        )
    }
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
            Row {
                Toolbar()

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



@Preview
@Composable
fun previewTopbar() {
    AppTheme {
        topBar(true, {})
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
