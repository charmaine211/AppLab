package applab.veiligthuis.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import applab.veiligthuis.model.Melding;
import applab.veiligthuis.model.MeldingDisplay;
import applab.veiligthuis.repository.MeldingRepository;

public class MeldingBekijkenViewModel extends ViewModel {
    private MeldingRepository meldingRepository;
    private LiveData<Melding> melding;

    public MeldingBekijkenViewModel(MeldingRepository meldingRepository){
        this.meldingRepository = meldingRepository;
    }

    public LiveData<MeldingDisplay> getMeldingByIdLiveData(int id){
        if(melding == null){
            melding = meldingRepository.getMeldingById("melding"+id);
        }
        return Transformations.map(melding, meldingDisplay -> meldingDisplay);
    }
}
