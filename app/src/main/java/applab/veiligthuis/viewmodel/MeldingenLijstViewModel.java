package applab.veiligthuis.viewmodel;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import applab.veiligthuis.model.Melding;
import applab.veiligthuis.repository.MeldingRepository;
import applab.veiligthuis.repository.MeldingRepositoryImpl;

public class MeldingenLijstViewModel extends ViewModel {
    private MeldingRepository meldingRepository;
    private LiveData<List<Melding>> meldingenLijstLiveData;

    public MeldingenLijstViewModel(){
        meldingRepository = new MeldingRepositoryImpl();
    }

    public LiveData<List<Melding>> getMeldingenLijst(){
        if(meldingenLijstLiveData == null){
            meldingenLijstLiveData = meldingRepository.getMeldingenListLiveData();
        }
        return meldingenLijstLiveData;
    }
}
