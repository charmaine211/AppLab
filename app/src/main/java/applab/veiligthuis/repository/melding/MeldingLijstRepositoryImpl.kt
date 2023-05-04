package applab.veiligthuis.repository.melding


import applab.veiligthuis.model.MeldingStatus
import applab.veiligthuis.model.MeldingData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.mapLatest

class MeldingLijstRepositoryImpl : MeldingLijstRepository {
    val database = Firebase.database
    val ref = database.getReference("Tests/meldingen/")

    init {
        ref.keepSynced(true)
    }
    fun writeNewMelding(datum: String, locatie: String, info: String, status: MeldingStatus, anoniem: Boolean){
        val meldingData = MeldingData(datum, locatie, info, status, anoniem)
        ref.push().setValue(meldingData)
    }
    override fun getMeldingen(): Flow<List<MeldingData?>> {
        return callbackFlow {
            val postListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val items = snapshot.children.map { ds ->
                        ds.getValue(MeldingData::class.java)
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

    fun getOnbehandeld(flowList: Flow<List<MeldingData?>>) : Flow<List<MeldingData?>> {
        return flowList.mapLatest { list ->
            list.filter { it?.status == MeldingStatus.ONBEHANDELD }
        }
    }
}