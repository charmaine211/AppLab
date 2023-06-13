package applab.veiligthuis.activity.meldingen



import android.os.Bundle

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
                        NavHost(navController = navController, startDestination = "melding_list_screen") {
                            composable(route = "melding_list_screen") {
                                MeldingLijstScreen(navController = navController)
                            }
                            composable(route = "melding_list_filter") {
                                FilterScreen(navController = navController)
                            }
                            composable(
                                route = "melding_bewerken_screen" +
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
                                val viewModel = hiltViewModel<MeldingBewerkenViewModel>()
                                val state by viewModel.uiState.collectAsState()
                                MeldingBewerkenScreen(state = state, onEvent = viewModel::onEvent, navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}


