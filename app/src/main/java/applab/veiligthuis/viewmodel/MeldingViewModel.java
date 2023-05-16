package applab.veiligthuis.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import applab.veiligthuis.model.Melding;
import applab.veiligthuis.repository.MeldingRepositoryImpl;

public class MeldingViewModel extends ViewModel {

    MeldingRepositoryImpl meldingRepo;

    public MeldingViewModel(){
    }


    public void insertMelding(String plaatsnaam, boolean beroepsmatig, String beschrijving, String datum){
        meldingRepo = new MeldingRepositoryImpl();
        Melding melding = new Melding(plaatsnaam, beroepsmatig, beschrijving, datum);
        MeldingRepositoryImpl meldingRepo = new MeldingRepositoryImpl();
        meldingRepo.addMelding(melding);

    }

    public void insertMelding(String plaatsnaam, String beschrijving, String datum){

        Melding melding = new Melding(plaatsnaam, beschrijving, datum);
        meldingRepo.addMelding(melding);

    }
}
