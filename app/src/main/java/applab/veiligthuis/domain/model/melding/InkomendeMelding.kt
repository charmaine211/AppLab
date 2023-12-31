package applab.veiligthuis.domain.model.melding

import applab.veiligthuis.data.melding.MeldingPaths
import applab.veiligthuis.domain.util.GeweldType
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class InkomendeMelding(
    datum: Long? = null,
    status: MeldingStatus = MeldingStatus.ONBEHANDELD,
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
            "${MeldingPaths.INKOMEND.path}/$key",
            "${MeldingPaths.INKOMEND_PLAATS.path}/$plaatsNaam/$key"
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
        return InkomendeMelding(
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