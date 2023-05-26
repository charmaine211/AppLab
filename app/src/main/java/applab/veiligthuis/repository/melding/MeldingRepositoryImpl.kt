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

    override fun getMeldingenFilter(
        filter: String
    ): Flow<List<Melding>> {
        val dbRef = database.getReference(filter)
        return callbackFlow {

            val postListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var items: ArrayList<Melding> = arrayListOf()
                    snapshot.children.forEach { ds ->
                        val datum = LocalDateTime.parse(ds.child("datum").getValue().toString()).toEpochSecond(
                            ZoneOffset.UTC)
                        val plaatsnaam = ds.child("plaatsnaam").getValue().toString()
                        val beschrijving = ds.child("beschrijving").getValue().toString()
                        val status = ds.child("status").getValue().toString()
                        val beroepsmatig: Boolean = ds.child("beroepsmatig").getValue() as Boolean
                        val typeGeweld = ds.child("typeGeweld").toString()
                        items.add(Melding(datum = datum,  beschrijving = beschrijving, plaatsNaam = plaatsnaam, beroepsmatig = beroepsmatig, typeGeweld = typeGeweld))
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

    override fun addMelding(melding: Melding) {
        val key = ref.push().key
        if(key == null) {
            Log.w("REP", "Melding niet naar db gepushed.")
        }
        val meldingNew = melding.copy(key = key!!)
        val meldingValues = meldingNew.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/status/${meldingNew.status.toString()}/$key" to meldingValues,
            "/$key" to meldingValues,
        )

        ref.updateChildren(childUpdates)
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

