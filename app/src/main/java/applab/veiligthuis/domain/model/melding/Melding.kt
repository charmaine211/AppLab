package applab.veiligthuis.domain.model.melding

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
abstract class Melding(
    var datum: Long?,
    var status: MeldingStatus?,
    var beschrijving: String,
    var plaatsNaam: String,
    var key: String?,
    var typeGeweld: String,
    var beroepsmatig: Boolean
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