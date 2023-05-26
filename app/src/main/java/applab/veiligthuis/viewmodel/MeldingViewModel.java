package applab.veiligthuis.viewmodel;

import androidx.lifecycle.ViewModel;


import java.time.LocalDateTime;

import applab.veiligthuis.domain.model.MeldingStatus;
import applab.veiligthuis.domain.model.melding.Melding;
import applab.veiligthuis.repository.melding.MeldingRepository;
import applab.veiligthuis.repository.melding.MeldingRepositoryImpl;

public class MeldingViewModel extends ViewModel {

    MeldingRepository meldingRepo;

    public MeldingViewModel(){
        meldingRepo = new MeldingRepositoryImpl();
    }

//    public void insertMelding(String plaatsnaam, boolean beroepsmatig, String beschrijving, String datum){
//        Melding melding = new Melding(plaatsnaam, beroepsmatig, beschrijving, datum);
//        meldingRepo.addMelding(melding);
//    }

    public void insertMelding(String plaatsnaam, String beschrijving, Long datum){
        Melding melding = new Melding(datum, MeldingStatus.ONBEHANDELD, beschrijving, plaatsnaam, null, "Ongecategoriseerd" , false);
        meldingRepo.addMelding(melding);
    }
}
