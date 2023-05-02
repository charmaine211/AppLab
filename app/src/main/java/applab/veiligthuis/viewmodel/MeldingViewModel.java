package applab.veiligthuis.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;

import applab.veiligthuis.model.Melding;

public class MeldingViewModel extends ViewModel {
    private MutableLiveData<String> mSuccessMessage = new MutableLiveData<>();
    private MutableLiveData<String> mErrorMessage = new MutableLiveData<>();

    private DatabaseReference mDatabaseRef;

    public LiveData<String> getSuccessMessage() {
        return mSuccessMessage;
    }

    public LiveData<String> getErrorMessage() {
        return mErrorMessage;
    }

    public void insertMelding(String gebruiker, String plaatsnaam, boolean beroepsmatig, String beschrijving, LocalDateTime datum){
        if (mDatabaseRef == null) {
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("meldingen");
        }

        String key = mDatabaseRef.push().getKey();
        Melding melding = new Melding(key, gebruiker, plaatsnaam, beroepsmatig, beschrijving, datum);

        mDatabaseRef.child(key).setValue(melding)
                .addOnSuccessListener(aVoid -> mSuccessMessage.setValue("Melding opgeslagen in database."))
                .addOnFailureListener(e -> mErrorMessage.setValue("Fout bij opslaan melding: " + e.getMessage()));
    }
}
