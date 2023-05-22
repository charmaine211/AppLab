//package applab.veiligthuis.ui.screens
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import applab.veiligthuis.domain.model.MeldingData
//import applab.veiligthuis.ui.veiligThuisToolbar
//import applab.veiligthuis.ui.meldingenlijst.MeldingBekijkenScreen
//import applab.veiligthuis.ui.meldingenlijst.MeldingenLijstFilter
//import applab.veiligthuis.ui.meldingenlijst.filterButtonsBar
//import applab.veiligthuis.ui.meldingenlijst.meldingList
//import applab.veiligthuis.viewmodel.MeldingLijstViewModel
//import applab.veiligthuis.viewmodel.MeldingenLijstUiState
//
//@Composable
//fun meldingLijstScreen(
//    meldingenLijstViewModel: MeldingLijstViewModel,
//    onHome: () -> Unit,
//    onProfile: () -> Unit,
//    modifier: Modifier = Modifier.padding(1.dp)
//) {
//    if(meldingenLijstUiState.showingLijstScreen) {
//        Scaffold(
//            topBar = {
//                veiligThuisToolbar(Unit, Unit)
//                     },
//            content = { contentPadding -> Box(modifier = Modifier.padding(contentPadding)) {
//                Column() {
//                    filterButtonsBar(
//                        filterInkomendSelected = meldingenLijstUiState.filterShowInkomend,
//                        expandedFilter = meldingenLijstUiState.filterExpanded,
//                        onClickExpandFilter = { filter.expandedFilter()  },
//                        selectedLocatie = meldingenLijstUiState.filterLocatie,
//                        meldingenFilter = filter
//                    )
//                    Divider(
//                        color = Color.Black,
//                        modifier = modifier
//                            .padding(11.dp, 0.dp, 11.dp, 4.dp),
//                        thickness = 1.dp
//                    )
//                    meldingList(
//                        meldingen = meldingenLijstUiState.meldingenFiltered,
//                        onCardClick = { meldingData: MeldingData -> meldingenLijstViewModel.updateMeldingBekijkenScreen(meldingData) }
//                    )
//                }
//            } },
//            bottomBar = {
//                BottomAppBar(modifier = modifier.fillMaxHeight(0.1F), backgroundColor = Color.White, elevation = 0.dp) {
//                    Row(horizontalArrangement = Arrangement.Center, modifier = modifier.fillMaxWidth()){
//                        Text(
//                            text = "Privacy verklaring",
//                        )
//                    }
//                }
//            },
//        )
//    } else {
//        MeldingBekijkenScreen(
//            meldingData = meldingenLijstUiState.selectedMeldingData,
//            onBackButtonClicked = { meldingenLijstViewModel.resetMeldingenLijstScreen() }
//        )
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
