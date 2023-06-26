package applab.veiligthuis.domain.model.melding

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
abstract class Melding(
    var datum: Long?,
    var status: MeldingStatus,
    var beschrijving: String,
    var plaatsNaam: String,
    var key: String?,
    var typeGeweld: String,
    var beroepsmatig: Boolean
) {

    @Exclude
    abstract fun getPaths(): List<String>

    @Exclude
    abstract fun copy(datum: Long? = this.datum, status:MeldingStatus = this.status, beschrijving: String = this.beschrijving, plaatsNaam: String = this.plaatsNaam, key: String? = this.key, typeGeweld: String = this.typeGeweld, beroepsmatig: Boolean = this.beroepsmatig): Melding

    @Exclude
    fun toMap(): Map<String, Any?> {
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