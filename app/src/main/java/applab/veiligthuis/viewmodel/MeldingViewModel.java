package applab.veiligthuis.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import applab.veiligthuis.data.FirebaseRepository;
import applab.veiligthuis.data.melding.MeldingInsertException;
import applab.veiligthuis.data.melding.MeldingRepository;
import applab.veiligthuis.data.melding.MeldingRepositoryImpl;
import applab.veiligthuis.domain.model.melding.InkomendeMelding;
import applab.veiligthuis.domain.model.melding.Melding;
import applab.veiligthuis.domain.model.melding.MeldingStatus;


public class MeldingViewModel extends ViewModel {

    MeldingRepository meldingRepo;
    FirebaseRepository firebaseRepository;
    String uid;

    MutableLiveData<Boolean> successMessage;

    public MeldingViewModel(){

        meldingRepo = new MeldingRepositoryImpl();
        firebaseRepository = new FirebaseRepository();
        uid = firebaseRepository.getCurrentUserId();
        successMessage = new MutableLiveData<>();

        if (uid == null){
            firebaseRepository.signInAnonymously(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        uid = firebaseRepository.getCurrentUserId();
                    }
                }
            });
        }
    }
    public LiveData<Boolean> getSuccessMessage(){
        return successMessage;
    }

    public void insertMelding(String plaatsnaam, String beschrijving, Long datum){
        Melding melding = new InkomendeMelding(datum, MeldingStatus.ONBEHANDELD, beschrijving, plaatsnaam, null, "ongecategoriseerd", false);
        try {
            meldingRepo.insertOrUpdateMelding(melding);
            successMessage.postValue(true);
        } catch(MeldingInsertException e) {
            successMessage.postValue(false);
        }
    }

    // Voor het maken van een beroepsmatige melding
    public void insertMelding(String plaatsnaam, boolean beroepsmatig, String beschrijving, Long datum){
        Melding melding = new InkomendeMelding(datum, MeldingStatus.ONBEHANDELD, beschrijving, plaatsnaam, null, "ongecategoriseerd", false);
        try {
            meldingRepo.insertOrUpdateMelding(melding);
            successMessage.postValue(true);
        } catch(MeldingInsertException e) {
            successMessage.postValue(false);
        }
    }
}
