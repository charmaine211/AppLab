package applab.veiligthuis.data.melding


import android.util.Log
import applab.veiligthuis.domain.model.melding.Melding
import applab.veiligthuis.domain.util.MeldingType

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MeldingRepositoryImpl : MeldingRepository {
    val database = Firebase.database
    private val ref = database.getReference("Tests/meldingen/")

    init {
        ref.keepSynced(true)
    }

    override fun getMeldingen(meldingType: MeldingType): Flow<List<Melding?>> {
        return callbackFlow {
            val path = if (meldingType == MeldingType.Inkomend) {
                MeldingPaths.INKOMEND.path
            } else {
                MeldingPaths.AFGESLOTEN.path
            }
            val meldingenListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val items = arrayListOf<Melding?>()
                    snapshot.child(path).children.map { ds ->
                        val melding = ds.getValue(meldingType.classType)
                        items.add(melding)
                    }
                    trySend(items)
                }

                override fun onCancelled(error: DatabaseError) {
                    cancel()
                }
            }
            ref.addValueEventListener(meldingenListener)
            awaitClose { ref.removeEventListener(meldingenListener) }
        }
    }

    override fun getMeldingenPlaatsen(
        plaatsen: List<String>,
        meldingType: MeldingType
    ): Flow<List<Melding?>> {
        return callbackFlow {
            val paths: List<String> = when (meldingType) {
                is MeldingType.Inkomend -> {
                    plaatsen.map { plaats -> "${MeldingPaths.INKOMEND_PLAATS.path}/$plaats" }
                }
                is MeldingType.Afgesloten -> {
                    plaatsen.map { plaats -> "${MeldingPaths.AFGESLOTEN_PLAATS.path}/$plaats" }
                }
            }
            val meldingenListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val items = arrayListOf<Melding?>()
                    paths.forEach { path ->
                        snapshot.child(path).children.map { ds ->
                            val melding = ds.getValue(meldingType.classType)
                            items.add(melding)
                        }
                    }
                    trySend(items)
                }

                override fun onCancelled(error: DatabaseError) {
                    cancel()
                }
            }
            ref.addValueEventListener(meldingenListener)
            awaitClose { ref.removeEventListener(meldingenListener) }
        }
    }

    override fun getMelding(meldingKey: String, meldingType: MeldingType): Flow<Melding> {
        return callbackFlow {
            val meldingListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val melding = snapshot.child(MeldingPaths.MELDINGEN.path).child(meldingKey)
                        .getValue(meldingType.classType)
                    if (melding != null) {
                        trySend(melding)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    cancel()
                }
            }
            ref.addValueEventListener(meldingListener)
            awaitClose { ref.removeEventListener(meldingListener) }
        }
    }

    @Throws(MeldingInsertException::class)
    override fun insertOrUpdateMelding(melding: Melding) {
        if (melding.key == null) {
            val key = ref.child(melding.getPaths()[0]).push().key
            if (key == null) {
                Log.w("REP", "Melding niet naar db gepushed.")
                throw MeldingInsertException("Aanmaken van meldingen mislukt.")
            }
            melding.key = key
        }
        val childUpdates = hashMapOf<String, Any>()
        melding.getPaths().forEach { path ->
            childUpdates[path] = melding.toMap()
        }
        ref.updateChildren(childUpdates)
    }

    override fun deleteMelding(melding: Melding) {
        val childUpdates = hashMapOf<String, Any?>()
        melding.getPaths().forEach { path ->
            childUpdates[path] = null
        }
        ref.updateChildren(childUpdates)
    }

}

