package applab.veiligthuis.data.repository

import applab.veiligthuis.domain.model.melding.AfgeslotenMelding
import applab.veiligthuis.domain.model.melding.InkomendeMelding
import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.util.MeldingType
import applab.veiligthuis.data.melding.MeldingPaths
import applab.veiligthuis.data.melding.MeldingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestMeldingRepository : MeldingRepository {

    private val meldingenList = mutableListOf<Melding>()
    private val meldingenMap = mutableMapOf<String, ArrayList<Melding>>()

    override fun getMeldingen(
        meldingType: MeldingType,
    ): Flow<List<Melding?>> {
        val meldingen: ArrayList<Melding> = arrayListOf()
        val path = if (meldingType == MeldingType.Inkomend) {
            MeldingPaths.INKOMEND.path
        } else {
            MeldingPaths.AFGESLOTEN.path
        }
        meldingenMap[path]?.let { meldingen.addAll(it) }

        return flow { emit(meldingen) }
    }

    override fun getMeldingenPlaatsen(
        plaatsen: List<String>,
        meldingType: MeldingType,
    ): Flow<List<Melding?>> {
        val meldingen: ArrayList<Melding> = arrayListOf()
        val paths = if (meldingType == MeldingType.Inkomend) {
            plaatsen.map { plaats -> "${MeldingPaths.INKOMEND_PLAATS.path}/$plaats" }
        } else {
            plaatsen.map { plaats -> "${MeldingPaths.AFGESLOTEN_PLAATS.path}/$plaats" }
        }
        paths.forEach { path ->
            meldingenMap[path]?.let { meldingen.addAll(it) }
        }
        return flow { emit(meldingen) }
    }

    override fun getMelding(
        meldingKey: String,
        meldingType: MeldingType,
    ): Flow<Melding> {
        var melding: Melding? = null
        meldingenList.forEach {
            if (it.key == meldingKey) {
                melding = it
            }
        }
        return flow { emit(melding!!) }
    }

    override fun insertOrUpdateMelding(melding: Melding) {
        meldingenList.add(melding)
        lateinit var paths: List<String>
        when (melding) {
            is InkomendeMelding -> {
                paths = listOf(
                    MeldingPaths.MELDINGEN.path,
                    MeldingPaths.INKOMEND.path,
                    "${MeldingPaths.INKOMEND_PLAATS.path}/${melding.plaatsNaam}"
                )
            }
            is AfgeslotenMelding -> {
                paths = listOf(
                    MeldingPaths.MELDINGEN.path,
                    MeldingPaths.AFGESLOTEN.path,
                    "${MeldingPaths.AFGESLOTEN_PLAATS.path}/${melding.plaatsNaam}"
                )
            }
        }
        paths.forEach { path ->
            if (!meldingenMap.containsKey(path)) {
                meldingenMap[path] = arrayListOf(melding)
            } else {
                val list = meldingenMap[path]
                if (list != null) {
                    list.add(melding)
                    meldingenMap[path] = list
                }
            }
        }
    }

    override fun deleteMelding(melding: Melding) {
        meldingenList.remove(melding)
        lateinit var paths: List<String>
        when (melding) {
            is InkomendeMelding -> {
                paths = listOf(MeldingPaths.MELDINGEN.path, MeldingPaths.INKOMEND.path)
            }
            is AfgeslotenMelding -> {
                paths = listOf(MeldingPaths.MELDINGEN.path, MeldingPaths.AFGESLOTEN.path)
            }
        }
        paths.forEach { path ->
            meldingenMap[path]?.remove(melding)
        }
    }
}