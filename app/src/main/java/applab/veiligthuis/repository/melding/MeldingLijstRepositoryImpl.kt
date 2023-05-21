package applab.veiligthuis.repository.melding


import applab.veiligthuis.model.MeldingStatus
import applab.veiligthuis.model.MeldingData
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
import kotlinx.coroutines.flow.mapLatest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MeldingLijstRepositoryImpl : MeldingLijstRepository {
    val database = Firebase.database
    val ref = database.getReference("Tests/meldingen/")

    init {
        ref.keepSynced(true)
    }

    override fun getMeldingen(): Flow<List<MeldingData?>> {
        return callbackFlow {
            val postListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var items : ArrayList<MeldingData> = arrayListOf()
                    snapshot.children.forEach(){ ds ->
                        val datum = LocalDateTime.parse(ds.child("datum").getValue().toString())
                        val locatie = ds.child("locatie").getValue().toString()
                        val info = ds.child("info").getValue().toString()
                        val status = ds.child("status").getValue().toString()
                        val anoniem = ds.child("anoniem").getValue()
                        items.add(MeldingData(datum = datum, locatie = locatie, info = info, status = parseMeldingStatus(status), anoniem = true))
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

    fun parseMeldingStatus(status: String) : MeldingStatus {
        if(status == "AFGEROND"){
            return MeldingStatus.AFGEROND
        } else if (status == "IN_BEHANDELING") {
            return MeldingStatus.IN_BEHANDELING
        } else {
            return MeldingStatus.ONBEHANDELD
        }
    }
}

