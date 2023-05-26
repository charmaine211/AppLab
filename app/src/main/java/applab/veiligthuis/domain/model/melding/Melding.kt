package applab.veiligthuis.domain.model.melding

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import applab.veiligthuis.domain.model.MeldingStatus
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.time.LocalDateTime

@IgnoreExtraProperties
data class Melding(
    val datum: Long? = null,
    val status: MeldingStatus? = null,
    val beschrijving: String = "",
    val plaatsNaam: String = "",
    val key: String? = null,
    val typeGeweld: String = "Ongecategoriseerd",
    val beroepsmatig: Boolean = false
) {

    @Exclude
    fun toMap():  Map<String, Any?> {
        return mapOf(
            "datum" to datum,
            "status" to status,
            "beschrijving" to beschrijving,
            "plaatsNaam" to plaatsNaam,
            "key" to key,
            "typeGeweld" to typeGeweld,
            "beroepsmatig" to beroepsmatig
        )
    }
}