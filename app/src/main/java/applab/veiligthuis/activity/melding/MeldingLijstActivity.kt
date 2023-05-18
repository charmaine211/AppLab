package applab.veiligthuis.activity.melding



import android.os.Bundle

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

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

import applab.veiligthuis.model.MeldingData
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
            topBar = {
                topBar(
                    filterInkomendSelected = meldingenLijstUiState.filterMeldingenInkomend,
                    expandedFilter = meldingenLijstUiState.filterExpanded,
                    onClickExpandFilter = { meldingenLijstViewModel.updateFilterButtonExpanded() },
                    onClickFilterChange = { meldingenLijstViewModel.updateFilterMeldingenInkomend() }
                ) },
            content = { contentPadding -> Box(modifier = Modifier.padding(contentPadding)) {
                meldingList(
                    filterInkomendSelected = meldingenLijstUiState.filterMeldingenInkomend,
                    meldingen = meldingenLijstUiState.meldingen,
                    onCardClick = { meldingData: MeldingData -> meldingenLijstViewModel.updateMeldingBekijkenScreen(meldingData = meldingData) }
                )
            } },
            bottomBar = {
                BottomAppBar(modifier = modifier.fillMaxHeight(0.1F), backgroundColor = Color.White, elevation = 0.dp) {
                    Row(horizontalArrangement = Arrangement.Center, modifier = modifier.fillMaxWidth()){
                        Text(
                            text = "Privacy verklaring",
                        )
                    }
                }
            },
        )
    } else {
        MeldingBekijkenScreen(
            meldingData = meldingenLijstUiState.selectedMeldingData,
            onBackButtonClicked = { meldingenLijstViewModel.resetMeldingenLijstScreen() }
        )
    }
}

@Composable
private fun topBar(
    filterInkomendSelected: Boolean,
    expandedFilter: Boolean,
    onClickExpandFilter: () -> Unit,
    onClickFilterChange: () -> Unit,
    modifier: Modifier = Modifier
) {
        Column(
            modifier = modifier
                .background(Color.White),
        ) {
            Row {
                Toolbar()
            }

            filterButtonsBar(
                filterInkomendSelected = filterInkomendSelected,
                expandedFilter = expandedFilter,
                onClickFilterChange = onClickFilterChange,
                onClickExpandFilter = onClickExpandFilter
            )

            Divider(
                color = Color.Black,
                modifier = modifier
                    .padding(11.dp, 0.dp, 11.dp, 4.dp),
                thickness = 1.dp
            )
        }
}

@Composable
private fun filterButtonsBar (
    filterInkomendSelected: Boolean,
    expandedFilter: Boolean,
    onClickExpandFilter: () -> Unit,
    onClickFilterChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.animateContentSize(animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMediumLow))) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(11.dp, 4.dp, 11.dp, 0.dp)
        ){
            meldingFilterButtons(inkomendSelected = filterInkomendSelected, onClick = onClickFilterChange)
            Button(
                onClick = onClickExpandFilter,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(backgroundColor = filter_blue),
                modifier = modifier.height(30.dp)
            ){
                Text(
                    text = "Filter",
                    fontSize = 10.sp,
                    color = Color.White
                )
            }
        }
        if(expandedFilter){
            Column(
                modifier = Modifier
                    .padding(11.dp)
            ) {
                Text(text = "Datum")

                val locaties = listOf("Amsterdam", "Rotterdam", "Eindhoven")

                DropdownMenu(expanded = false, onDismissRequest = { /*TODO*/ }) {
                    locaties.forEach{
                        DropdownMenuItem(onClick = { /*TODO*/ }) {
                            Text(text = it)
                        }
                    }
                }

                Text(text = "Datum")
                Text(text = "Datum")
                Text(text = "Datum")
            }
        }
    }
}


@Composable
private fun filterButton(
    text: String
){
    Button(
        onClick = {},
        shape = RoundedCornerShape(45),
        colors = ButtonDefaults.buttonColors(backgroundColor = filter_blue)
    ){
        Text(text = text)
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
        topBar(true, true, {}, {})
    }
}

@Preview(showBackground = true)
@Composable
fun previewFilterButtonsBar() {
    AppTheme {
        filterButtonsBar(true, true, {}, {})
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

@Preview(showBackground = true)
@Composable
fun previewFilterButton(){

}
