package applab.veiligthuis.domain.model.melding

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import applab.veiligthuis.domain.model.MeldingStatus
import java.time.LocalDateTime

class Melding(
    val datum: LocalDateTime,
    initialStatus: MeldingStatus = MeldingStatus.ONBEHANDELD,
    val description: String = "",
    val plaatsNaam: String = ""
) {
    var status by mutableStateOf(initialStatus)
}