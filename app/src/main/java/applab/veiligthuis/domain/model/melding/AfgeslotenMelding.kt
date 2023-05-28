package applab.veiligthuis.domain.model.melding

import applab.veiligthuis.domain.model.MeldingStatus
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class AfgeslotenMelding(
    datum: Long,
    status: MeldingStatus?,
    beschrijving: String,
    plaatsNaam: String,
    key: String?,
    typeGeweld: String,
    beroepsmatig: Boolean
) : Melding(datum, status, beschrijving, plaatsNaam, key, typeGeweld, beroepsmatig) {
    @Exclude
    override fun getPaths(): List<String> {
        return listOf(
            "/$key",
            "/afgesloten/${status.toString()}/$key"
        )
    }
}