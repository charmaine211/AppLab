package applab.veiligthuis.repository.melding


import android.util.Log
import applab.veiligthuis.domain.model.melding.Melding

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
    val ref = database.getReference("Tests/meldingen/")

    init {
        ref.keepSynced(true)
    }

    override fun <T: Melding> getMeldingen(paths: List<String>, meldingtype: Class<T>): Flow<List<Melding?>> {
        return callbackFlow {
            val postListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val items = arrayListOf<Melding?>()
                    paths.forEach { path ->
                        snapshot.child(path).children.map { ds ->
                            val melding = ds.getValue(meldingtype)
                            items.add(melding)
                        }
                    }
                    trySend(items)
                }
                override fun onCancelled(error: DatabaseError) {
                    cancel()
                }
            }
            ref.addValueEventListener(postListener)
            awaitClose { ref.removeEventListener(postListener) }
        }
    }

    override fun <T : Melding> getMelding(meldingKey: String, meldingType: Class<T>): Flow<Melding> {
        return callbackFlow {
            val meldingListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val melding = snapshot.child(meldingKey).getValue(meldingType)
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


    override fun addMelding(melding: Melding) {
        if(melding.key == null) {
            val key = ref.child(melding.getPaths()[0]).push().key
            if(key == null) {
                Log.w("REP", "Melding niet naar db gepushed.")
            }
            melding.key = key
        }
        editMelding(melding)
    }

    override fun editMelding(melding: Melding) {
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

