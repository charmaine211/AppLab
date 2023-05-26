package applab.veiligthuis.activity.meldingen



import android.os.Bundle

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import applab.veiligthuis.ui.screens.MeldingLijstScreen
import applab.veiligthuis.ui.theme.AppTheme
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
                        NavHost(navController = navController, startDestination = "melding_list_screen") {
                            composable(route = "melding_list_screen") {
                                MeldingLijstScreen(navController = navController)
                            }
                            composable(route = "melding_list_filter") {
                                FilterScreen(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}


