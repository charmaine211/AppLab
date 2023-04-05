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
    private final DatabaseReference meldingenRef;
    public MeldingRepositoryImpl(){
        meldingenRef = FirebaseDatabase.getInstance().getReference("Tests/meldingen/");
        meldingenRef.keepSynced(true);
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
