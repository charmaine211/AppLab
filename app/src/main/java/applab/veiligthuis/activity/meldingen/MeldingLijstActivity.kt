package applab.veiligthuis.activity.meldingen


import android.os.Bundle

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import applab.veiligthuis.views.meldinglist.MeldingLijstScreen
import applab.veiligthuis.ui.theme.AppTheme
import applab.veiligthuis.views.meldinglist.MeldingLijstViewModel
import applab.veiligthuis.views.Screens
import applab.veiligthuis.views.meldingbewerken.MeldingBewerkenScreen
import applab.veiligthuis.views.meldingbewerken.MeldingBewerkenViewModel
import applab.veiligthuis.views.meldinglist.FilterScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MeldingLijstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setContent {
                    AppTheme {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = Screens.MeldingLijst.route
                        ) {
                            composable(route = Screens.MeldingLijst.route) {
                                val meldingLijstViewModel = hiltViewModel<MeldingLijstViewModel>()
                                val meldingLijstUiState by meldingLijstViewModel.uiState.collectAsState()
                                val meldingLijstFilterState by meldingLijstViewModel.filterState.collectAsState()
                                MeldingLijstScreen(
                                    navController = navController,
                                    uiState = meldingLijstUiState,
                                    filterState = meldingLijstFilterState,
                                    onEvent = meldingLijstViewModel::onEvent
                                )
                            }
                            composable(route = Screens.FilterMeldingen.route) { backStackEntry ->
                                val parentEntry = remember(backStackEntry) {
                                    navController.getBackStackEntry(Screens.MeldingLijst.route)
                                }
                                val parentViewModel =
                                    hiltViewModel<MeldingLijstViewModel>(parentEntry)
                                val filterState by parentViewModel.filterState.collectAsState()
                                FilterScreen(
                                    navController = navController,
                                    filterState = filterState,
                                    onEvent = parentViewModel::onEvent
                                )
                            }
                            composable(
                                route = Screens.MeldingBewerken.route +
                                        "?meldingtype={meldingtype}&meldingKey={meldingKey}",
                                arguments = listOf(
                                    navArgument(
                                        name = "meldingtype"
                                    ) {
                                        type = NavType.StringType
                                        defaultValue = "inkomend"
                                    },
                                    navArgument(
                                        name = "meldingKey"
                                    ) {
                                        type = NavType.StringType
                                        defaultValue = ""
                                    }
                                )
                            ) {
                                val meldingBewerkenViewModel =
                                    hiltViewModel<MeldingBewerkenViewModel>()
                                val state by meldingBewerkenViewModel.uiState.collectAsState()
                                MeldingBewerkenScreen(
                                    state = state,
                                    onEvent = meldingBewerkenViewModel::onEvent,
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


