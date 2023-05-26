package applab.veiligthuis.domain.model.melding

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import applab.veiligthuis.domain.model.MeldingStatus
import java.time.LocalDateTime

class Melding(
    val datum: LocalDateTime,
    initialStatus: MeldingStatus = MeldingStatus.ONBEHANDELD,
    val beschrijving: String = "",
    val plaatsNaam: String = "",
    val key: String? = null,
    val typeGeweld: String? = null,
    val beroepsmatig: Boolean = false
) {
    var status by mutableStateOf(initialStatus)
}