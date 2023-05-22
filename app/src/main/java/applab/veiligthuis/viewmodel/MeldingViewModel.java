package applab.veiligthuis.viewmodel;

import androidx.lifecycle.ViewModel;

import applab.veiligthuis.domain.model.Melding;
import applab.veiligthuis.repository.melding.MeldingRepository;
import applab.veiligthuis.repository.melding.MeldingRepositoryImpl;

public class MeldingViewModel extends ViewModel {

    MeldingRepository meldingRepo;

    public MeldingViewModel(){
        meldingRepo = new MeldingRepositoryImpl();
    }

    public void insertMelding(String plaatsnaam, boolean beroepsmatig, String beschrijving, String datum){
        Melding melding = new Melding(plaatsnaam, beroepsmatig, beschrijving, datum);
        meldingRepo.addMelding(melding);
    }

    public void insertMelding(String plaatsnaam, String beschrijving, String datum){
        Melding melding = new Melding(plaatsnaam, beschrijving, datum);
        meldingRepo.addMelding(melding);
    }
}
