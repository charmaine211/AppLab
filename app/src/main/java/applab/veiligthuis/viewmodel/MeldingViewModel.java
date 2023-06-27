package applab.veiligthuis.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import applab.veiligthuis.data.Repository;
import applab.veiligthuis.data.melding.MeldingInsertException;
import applab.veiligthuis.data.melding.MeldingRepository;
import applab.veiligthuis.data.melding.MeldingRepositoryImpl;
import applab.veiligthuis.domain.model.melding.InkomendeMelding;
import applab.veiligthuis.domain.model.melding.Melding;
import applab.veiligthuis.domain.model.melding.MeldingStatus;


public class MeldingViewModel extends ViewModel {

    protected MeldingRepository meldingRepo;
    protected Repository repository;
    String uid;

    MutableLiveData<Boolean> successMessage;

    public MeldingViewModel(){
        meldingRepo = new MeldingRepositoryImpl();
        repository = new Repository();
        uid = repository.getCurrentUserId();
        successMessage = new MutableLiveData<>();

        if (uid == null){
            repository.signInAnonymously(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        uid = repository.getCurrentUserId();
                    }
                }
            });
        }
    }
    public LiveData<Boolean> getSuccessMessage(){
        return successMessage;
    }

    public void insertMelding(String plaatsnaam, String beschrijving, Long datum){
        Melding melding = new InkomendeMelding(datum, MeldingStatus.ONBEHANDELD, beschrijving, plaatsnaam, null, "ongecategoriseerd", userLoggedIn());
        try {
            meldingRepo.insertOrUpdateMelding(melding);
            successMessage.postValue(true);
        } catch(MeldingInsertException e) {
            successMessage.postValue(false);
        }
    }

    public boolean userLoggedIn(){
        return repository.userLoggedIn();
    }
}
