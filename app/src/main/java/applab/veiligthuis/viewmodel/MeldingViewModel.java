package applab.veiligthuis.viewmodel;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import applab.veiligthuis.data.FirebaseRepository;
import applab.veiligthuis.model.Melding;
import applab.veiligthuis.repository.MeldingRepositoryImpl;

public class MeldingViewModel extends ViewModel {

    MeldingRepositoryImpl meldingRepo;
    FirebaseRepository firebaseRepository;
    String uid;

    public MeldingViewModel(){

        meldingRepo = new MeldingRepositoryImpl();
        firebaseRepository = new FirebaseRepository();
        uid = firebaseRepository.getCurrentUserId();

        //TODO juiste error/successmessage voor het inloggen van de gebruiker.
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

    public LiveData<String> getErrorMessage(){
        return this.meldingRepo.getErrorMessage();
    }

    public LiveData<String> getSuccessMessage(){
        return this.meldingRepo.getSuccessMessage();
    }

    public void insertMelding(String plaatsnaam, String beschrijving, String datum){
        Melding melding = new Melding(plaatsnaam, beschrijving, datum, this.uid);
        meldingRepo.addMelding(melding);
    }

    public void insertMelding(String plaatsnaam, boolean beroepsmatig, String beschrijving, String datum){
        Melding melding = new Melding(plaatsnaam, beroepsmatig, beschrijving, datum);
        MeldingRepositoryImpl meldingRepo = new MeldingRepositoryImpl();
        meldingRepo.addMelding(melding);
    }
}
