package applab.veiligthuis.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import applab.veiligthuis.model.Melding;
import applab.veiligthuis.repository.MeldingRepository;

public class MeldingBekijkenViewModel extends ViewModel {
    private MeldingRepository meldingRepository;
    private LiveData<Melding> mMelding;
}
