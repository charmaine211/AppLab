package applab.veiligthuis.domain.model.melding

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import applab.veiligthuis.domain.model.MeldingStatus
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.time.LocalDateTime

@IgnoreExtraProperties
abstract class Melding(
    var datum: Long? = null,
    var status: MeldingStatus? = null,
    var beschrijving: String = "",
    var plaatsNaam: String = "",
    var key: String? = null,
    var typeGeweld: String = "Ongecategoriseerd",
    var beroepsmatig: Boolean = false
) {

    @Exclude
    abstract fun getPaths(): List<String>

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