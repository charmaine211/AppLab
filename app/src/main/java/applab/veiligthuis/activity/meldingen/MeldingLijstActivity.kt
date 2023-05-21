package applab.veiligthuis.activity.meldingen



import android.content.Intent
import android.os.Bundle

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import applab.veiligthuis.MainActivity
import applab.veiligthuis.ui.screens.meldingLijstScreen
import applab.veiligthuis.ui.theme.AppTheme
import applab.veiligthuis.viewmodel.MeldingLijstViewModel
import kotlinx.coroutines.launch


class MeldingLijstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MeldingLijstViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setContent {
                    AppTheme {
                        viewModel.loadMeldingen()
                        viewModel.applyFilters()
                        val meldingenLijstUiState by viewModel.uiState.collectAsState()


                        val localContext = LocalContext.current

                        meldingLijstScreen(
                            meldingenLijstViewModel = viewModel,
                            meldingenLijstUiState = meldingenLijstUiState,
                            onHome = {
                                val intent = Intent(localContext, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                startActivity(intent)
                                     },
                            onProfile = {}

                        )
                    }
                }
            }
        }
    }
}


