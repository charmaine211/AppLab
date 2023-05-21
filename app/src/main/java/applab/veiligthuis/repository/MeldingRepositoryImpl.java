package applab.veiligthuis.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import applab.veiligthuis.model.Melding;

public class MeldingRepositoryImpl {
    private DatabaseReference meldingenRef;
    private MutableLiveData<String> mSuccessMessage = new MutableLiveData<>();
    private MutableLiveData<String> mErrorMessage = new MutableLiveData<>();

    FirebaseDatabase firebaseDatabase;

    public LiveData<String> getSuccessMessage() {
        return mSuccessMessage;
    }

    public LiveData<String> getErrorMessage() {
        return mErrorMessage;
    }

    public MeldingRepositoryImpl(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        meldingenRef = firebaseDatabase.getReference("Meldingen/");
        meldingenRef.keepSynced(true);
    }

    public void addMelding(Melding melding){

        String key = meldingenRef.push().getKey();
        melding.setKey(key);
        meldingenRef.child(key).setValue(melding)
                .addOnSuccessListener(aVoid -> mSuccessMessage.setValue("Melding opgeslagen in database."))
                .addOnFailureListener(e -> mErrorMessage.setValue("Fout bij opslaan melding: " + e.getMessage()));

    }

    public LiveData<List<Melding>> getMeldingenListLiveData(){
        MutableLiveData<List<Melding>> meldingenLiveData = new MutableLiveData<>();
        meldingenRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Melding> meldingen = new ArrayList<>();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Melding melding = snap.getValue(Melding.class);
                    if(melding != null){
                        meldingen.add(melding);
                    }
                }
                meldingenLiveData.setValue(meldingen);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: ");
            }
        });
        return meldingenLiveData;
    }
}
