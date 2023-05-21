package applab.veiligthuis.activity.meldingen



import android.os.Bundle

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import applab.veiligthuis.ui.screens.AppTheme
import applab.veiligthuis.ui.screens.meldingLijstScreen
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
                        meldingLijstScreen(
                            meldingenLijstViewModel = viewModel,
                            meldingenLijstUiState = meldingenLijstUiState
                        )
                    }
                }
            }
        }
    }
}


