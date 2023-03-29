package applab.veiligthuis.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import applab.veiligthuis.model.Melding;

public interface MeldingRepository {

    LiveData<List<Melding>> getMeldingenListLiveData();
    LiveData<Melding> getMeldingById(String id);

}
