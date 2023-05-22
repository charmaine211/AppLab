package applab.veiligthuis.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import applab.veiligthuis.model.Melding;
import applab.veiligthuis.repository.MeldingRepositoryImpl;

public class MeldingViewModel extends ViewModel {

    MeldingRepositoryImpl meldingRepo;

    public MeldingViewModel(){

        meldingRepo = new MeldingRepositoryImpl();
    }

    public void insertMelding(String plaatsnaam, boolean beroepsmatig, String beschrijving, String datum){
        Melding melding = new Melding(plaatsnaam, beroepsmatig, beschrijving, datum);
        meldingRepo.addMelding(melding);
    }

    public LiveData<String> getErrorMessage(){
        return this.meldingRepo.getErrorMessage();
    }

    public LiveData<String> getSuccessMessage(){
        return this.meldingRepo.getSuccessMessage();
    }

    public void insertMelding(String plaatsnaam, String beschrijving, String datum){
        Melding melding = new Melding(plaatsnaam, beschrijving, datum);
        meldingRepo.addMelding(melding);
    }
}
