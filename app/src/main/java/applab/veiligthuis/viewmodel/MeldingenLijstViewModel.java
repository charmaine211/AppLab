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
    private LiveData<Melding> meldingLiveData;

    public MeldingenLijstViewModel(){
        meldingRepository = new MeldingRepositoryImpl();
    }

    public LiveData<List<Melding>> getMeldingenLijst(){
        if(meldingenLijstLiveData == null){
            meldingenLijstLiveData = meldingRepository.getMeldingenListLiveData();
        }
        return meldingenLijstLiveData;
    }

    public LiveData<Melding> getMeldingByIdLiveData(int id){
        if(meldingLiveData == null){
            meldingLiveData = meldingRepository.getMeldingById("melding"+id);
        }
        return meldingLiveData;
    }

    public Melding getMeldingById(int id){
        for(Melding m : meldingenLijstLiveData.getValue()){
            if(m.getId() == id){
                return m;
            }
        }
        return null;
    }


}
