package applab.veiligthuis.domain.model.melding

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class AfgeslotenMelding(
    datum: Long? = null,
    status: MeldingStatus? = null,
    beschrijving: String = "",
    plaatsNaam: String = "",
    key: String? = null,
    typeGeweld: String = "Ongecategoriseerd",
    beroepsmatig: Boolean = false
) : Melding(datum, status, beschrijving, plaatsNaam, key, typeGeweld, beroepsmatig) {
    @Exclude
    override fun getPaths(): List<String> {
        return listOf(
            "/$key",
            "/afgesloten/${status.toString()}/$key"
        )
    }
}