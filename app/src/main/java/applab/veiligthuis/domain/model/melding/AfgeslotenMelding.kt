package applab.veiligthuis.domain.model.melding

import applab.veiligthuis.data.melding.MeldingPaths
import applab.veiligthuis.domain.util.GeweldType
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class AfgeslotenMelding(
    datum: Long? = null,
    status: MeldingStatus = MeldingStatus.AFGESLOTEN,
    beschrijving: String = "",
    plaatsNaam: String = "",
    key: String? = null,
    typeGeweld: String = GeweldType.ONGECATEGORISEERD.value,
    beroepsmatig: Boolean = false
) : Melding(datum, status, beschrijving, plaatsNaam, key, typeGeweld, beroepsmatig) {
    @Exclude
    override fun getPaths(): List<String> {
        return listOf(
            "${MeldingPaths.MELDINGEN.path}/$key",
            "${MeldingPaths.AFGESLOTEN.path}/$key",
            "${MeldingPaths.AFGESLOTEN_PLAATS.path}/$plaatsNaam/$key",
        )
    }

    @Exclude
    override fun copy(
        datum: Long?,
        status: MeldingStatus,
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