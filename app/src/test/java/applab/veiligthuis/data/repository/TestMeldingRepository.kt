package applab.veiligthuis.data.repository

import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.util.MeldingType
import applab.veiligthuis.repository.melding.MeldingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestMeldingRepository: MeldingRepository {

    private val meldingenList = mutableListOf<Melding>()
    private val meldingenMap = mutableMapOf<String, ArrayList<Melding>>()

    override fun <T : Melding> getMeldingen(
        paths: List<String>,
        meldingType: Class<T>,
    ): Flow<List<Melding?>> {
        var meldingen: ArrayList<Melding> = arrayListOf()
        paths.forEach { path ->
            meldingenMap[path]?.let { meldingen.addAll(it) }
        }
        return flow { emit(meldingen) }
    }

    override fun  getMelding(
        meldingKey: String,
        meldingType: MeldingType,
    ): Flow<Melding> {
        var melding: Melding? = null
        meldingenList.forEach{
            if(it.key == meldingKey) {
                melding = it
            }
        }
        return flow { emit(melding!!) }
    }

    override fun addMelding(melding: Melding) {
        meldingenList.add(melding)
        melding.getPaths().forEach { path ->
            meldingenMap[path]?.add(melding)
        }
    }

    override fun editMelding(melding: Melding) {
        TODO("Not yet implemented")
    }

    override fun deleteMelding(melding: Melding) {
        meldingenList.remove(melding)
        melding.getPaths().forEach { path ->
            meldingenMap[path]?.remove(melding)
        }
    }
}