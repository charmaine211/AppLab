package applab.veiligthuis.repository.melding


import android.util.Log
import androidx.lifecycle.MutableLiveData
import applab.veiligthuis.domain.model.melding.Melding

import applab.veiligthuis.domain.model.MeldingStatus
import applab.veiligthuis.ui.meldingenlijst.meldingList
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.time.LocalDateTime
import java.time.ZoneOffset

class MeldingRepositoryImpl : MeldingRepository {
    val database = Firebase.database
    val ref = database.getReference("Tests/meldingen/")

    private val mSuccessMessage = MutableLiveData<String>()
    private val mErrorMessage = MutableLiveData<String>()


    init {
        ref.keepSynced(true)
    }

    override fun getMeldingen(): Flow<List<Melding?>> {
        return callbackFlow {
            val postListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val items = snapshot.children.map {ds ->
                        ds.getValue(Melding::class.java)
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


    override fun addMelding(melding: Melding, paths: List<String>) {
        val key = ref.push().key
        if(key == null) {
            Log.w("REP", "Melding niet naar db gepushed.")
        }
        melding.key = key
        editMelding(melding, paths)
    }

    override fun editMelding(melding: Melding, paths: List<String>) {
        val childUpdates = hashMapOf<String, Any>()
        paths.forEach { path ->
            childUpdates[path+"/${melding.key}"] = melding.toMap()
        }
        ref.updateChildren(childUpdates)
    }

    override fun deleteMelding(melding: Melding, paths: List<String>) {
        val childUpdates = hashMapOf<String, Any?>()
        paths.forEach { path ->
            childUpdates[path] = null
        }
        ref.updateChildren(childUpdates)
    }

}

