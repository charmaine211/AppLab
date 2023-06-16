package applab.veiligthuis.activity.meldingen



import android.os.Bundle

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import applab.veiligthuis.domain.util.MeldingType

import applab.veiligthuis.ui.screens.MeldingLijstScreen
import applab.veiligthuis.ui.theme.AppTheme
import applab.veiligthuis.viewmodel.MeldingLijstViewModel
import applab.veiligthuis.views.Screens
import applab.veiligthuis.views.meldingbewerken.MeldingBewerkenScreen
import applab.veiligthuis.views.meldingbewerken.MeldingBewerkenViewModel
import applab.veiligthuis.views.meldinglist.FilterScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
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
                        NavHost(navController = navController, startDestination = Screens.MeldingLijst.route) {
                            composable(route = Screens.MeldingLijst.route) {
                                val meldingLijstViewModel = hiltViewModel<MeldingLijstViewModel>()
                                MeldingLijstScreen(navController = navController, meldingLijstViewModel)
                            }
                            composable(route = Screens.FilterMeldingen.route) { backStackEntry ->
                                val parentEntry = remember(backStackEntry) {
                                    navController.getBackStackEntry(Screens.MeldingLijst.route)
                                }
                                val parentViewModel = hiltViewModel<MeldingLijstViewModel>(parentEntry)
                                FilterScreen(navController = navController, parentViewModel)
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
                                val meldingBewerkenViewModel = hiltViewModel<MeldingBewerkenViewModel>()
                                val state by meldingBewerkenViewModel.uiState.collectAsState()
                                MeldingBewerkenScreen(state = state, onEvent = meldingBewerkenViewModel::onEvent, navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}


