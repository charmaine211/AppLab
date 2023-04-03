package applab.veiligthuis.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import applab.veiligthuis.repository.MeldingRepository;

public class MeldingVMFactory implements ViewModelProvider.Factory {

    private final MeldingRepository meldingRepository;

    public MeldingVMFactory(MeldingRepository meldingRepository){
        this.meldingRepository = meldingRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MeldingBekijkenViewModel(meldingRepository);
    }
}
