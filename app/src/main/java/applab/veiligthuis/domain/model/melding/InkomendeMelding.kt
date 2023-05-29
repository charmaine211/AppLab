package applab.veiligthuis.domain.model.melding

import applab.veiligthuis.repository.melding.MeldingPaths
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class InkomendeMelding(
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
            "${MeldingPaths.INKOMEND.path}/$key",
            "${MeldingPaths.INKOMEND_PLAATS.path}/$plaatsNaam/$key"
        )
    }
}