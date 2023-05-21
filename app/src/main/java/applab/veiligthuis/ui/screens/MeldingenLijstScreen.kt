package applab.veiligthuis.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import applab.veiligthuis.ui.meldingenlijst.filterButtonsBar
import applab.veiligthuis.ui.meldingenlijst.meldingList
import applab.veiligthuis.ui.theme.filter_blue
import applab.veiligthuis.ui.theme.filter_grey
import applab.veiligthuis.viewmodel.MeldingLijstViewModel
import applab.veiligthuis.viewmodel.MeldingenLijstUiState

@Composable
fun meldingLijstScreen(
    meldingenLijstViewModel: MeldingLijstViewModel,
    meldingenLijstUiState: MeldingenLijstUiState,
    modifier: Modifier = Modifier.padding(1.dp)
) {
    if(meldingenLijstUiState.showingLijstScreen) {
        Scaffold(
            topBar = {
                topBar(
                    filterInkomendSelected = meldingenLijstUiState.filterMeldingenInkomend,
                    expandedFilter = meldingenLijstUiState.filterExpanded,
                    onClickExpandFilter = { meldingenLijstViewModel.updateFilterButtonExpanded() },
                    onClickFilterChange = { meldingenLijstViewModel.updateFilterMeldingenInkomend() },
                    updateSelectedLoc = {locatie: String -> meldingenLijstViewModel.updateFilterLocatie(locatie) },
                    selectedLocatie = meldingenLijstUiState.filterLocatie,
                    resetFilter = { meldingenLijstViewModel.resetFilter() }
                ) },
            content = { contentPadding -> Box(modifier = Modifier.padding(contentPadding)) {
                meldingList(
                    meldingen = meldingenLijstUiState.meldingenFiltered,
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
    updateSelectedLoc: (String) -> Unit,
    selectedLocatie: String?,
    resetFilter:() -> Unit,
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
            onClickExpandFilter = onClickExpandFilter,
            selectedLocatie = selectedLocatie,
            updateSelectedLocatie = updateSelectedLoc,
            resetFilter = resetFilter
        )
        Divider(
            color = Color.Black,
            modifier = modifier
                .padding(11.dp, 0.dp, 11.dp, 4.dp),
            thickness = 1.dp
        )
    }
}




@Preview
@Composable
fun previewTopbar() {
    AppTheme {
        topBar(true, true, {}, {}, {}, null, { })
    }
}

@Preview(showBackground = true)
@Composable
fun previewFilterButtonsBar() {
    AppTheme {
        filterButtonsBar(true, true, {}, {}, {}, null, {  })
    }
}

@Preview(showBackground = true)
@Composable
fun previewMeldingenScreen() {
    AppTheme {
        //meldingLijstScreen(meldingenLijstViewModel = MeldingLijstViewModel())
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
            )
        ),
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun previewFilterButton(){

}