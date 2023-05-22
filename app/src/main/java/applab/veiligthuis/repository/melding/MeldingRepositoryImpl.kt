package applab.veiligthuis.repository.melding


import androidx.lifecycle.MutableLiveData
import applab.veiligthuis.domain.model.melding.Melding

import applab.veiligthuis.domain.model.MeldingStatus
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.time.LocalDateTime

class MeldingRepositoryImpl : MeldingRepository {
    val database = Firebase.database
    val ref = database.getReference("Tests/meldingen/")

    private val mSuccessMessage = MutableLiveData<String>()
    private val mErrorMessage = MutableLiveData<String>()


    init {
        ref.keepSynced(true)
    }

    override fun getMeldingen(): Flow<List<Melding>> {
        return callbackFlow {
            val postListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var items : ArrayList<Melding> = arrayListOf()
                    snapshot.children.forEach(){ ds ->
                        val datum = LocalDateTime.parse(ds.child("datum").getValue().toString())
                        val locatie = ds.child("locatie").getValue().toString()
                        val description = ds.child("beschrijving").getValue().toString()
                        val status = ds.child("status").getValue().toString()
                        val anoniem = ds.child("anoniem").getValue()
                        items.add(Melding(datum = datum, initialStatus = parseMeldingStatus(status), description = description, plaatsNaam = locatie))
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

    override fun addMelding(melding: applab.veiligthuis.domain.model.Melding) {
        val key: String? = ref.push().getKey()
        melding.key = key
        if (key != null) {
            ref.child(key).setValue(melding)
                .addOnSuccessListener(OnSuccessListener<Void> { aVoid: Void? ->
                    mSuccessMessage.setValue(
                        "Melding opgeslagen in database."
                    )
                })
                .addOnFailureListener(OnFailureListener { e: Exception -> mErrorMessage.setValue("Fout bij opslaan melding: " + e.message) })
        }
    }

    private fun parseMeldingStatus(status: String) : MeldingStatus {
        if(status == "AFGEROND"){
            return MeldingStatus.AFGESLOTEN
        } else if (status == "IN_BEHANDELING") {
            return MeldingStatus.IN_BEHANDELING
        } else {
            return MeldingStatus.ONBEHANDELD
        }
    }
}

