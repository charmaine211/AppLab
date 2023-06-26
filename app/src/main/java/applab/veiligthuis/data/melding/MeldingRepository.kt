package applab.veiligthuis.data.melding



import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.util.MeldingType
import kotlinx.coroutines.flow.Flow

interface MeldingRepository {
    fun getMeldingen(paths: List<String>, meldingType: MeldingType): Flow<List<Melding?>>
    fun getMelding(meldingKey: String, meldingType: MeldingType): Flow<Melding>

    @Throws(MeldingInsertException::class)
    fun insertOrUpdateMelding(melding: Melding)
    fun deleteMelding(melding: Melding)
}