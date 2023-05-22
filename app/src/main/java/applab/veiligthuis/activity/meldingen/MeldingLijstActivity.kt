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

import applab.veiligthuis.ui.screens.MeldingListScreen
import applab.veiligthuis.ui.theme.AppTheme
import applab.veiligthuis.viewmodel.MeldingLijstViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MeldingLijstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MeldingLijstViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setContent {
                    AppTheme {
                        MeldingListScreen()
                    }
                }
            }
        }
    }
}


