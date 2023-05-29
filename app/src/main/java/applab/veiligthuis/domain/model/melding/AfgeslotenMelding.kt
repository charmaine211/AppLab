package applab.veiligthuis.domain.model.melding

import applab.veiligthuis.repository.melding.MeldingPaths
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
            "${MeldingPaths.AFGESLOTEN.path}/$key",
            "${MeldingPaths.AFGESLOTEN_PLAATS.path}/$plaatsNaam/$key",
        )
    }

    @Exclude
    override fun copy(
        datum: Long?,
        status: MeldingStatus?,
        beschrijving: String,
        plaatsNaam: String,
        key: String?,
        typeGeweld: String,
        beroepsmatig: Boolean
    ): Melding {
        return AfgeslotenMelding(
            datum = datum,
            status = status,
            beschrijving = beschrijving,
            plaatsNaam = plaatsNaam,
            key = key,
            typeGeweld = typeGeweld,
            beroepsmatig = beroepsmatig
        )
    }
}