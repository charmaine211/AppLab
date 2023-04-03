package applab.veiligthuis.meldingenbekijken;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.arch.core.util.Function;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import applab.veiligthuis.model.Melding;
import applab.veiligthuis.model.MeldingDisplay;
import applab.veiligthuis.repository.MeldingRepository;
import applab.veiligthuis.viewmodel.MeldingBekijkenViewModel;

@RunWith(MockitoJUnitRunner.class)
public class MeldingBekijkenViewModelTest {

    Melding testMelding = new Melding(1, false, "testmelding");

    private MeldingBekijkenViewModel viewmodel;
    @Mock
    private MeldingRepository repository;


    @Test
    public void testViewModel(){
        MeldingBekijkenViewModel viewModel = new MeldingBekijkenViewModel(repository);

        MutableLiveData<Melding> meldingLiveData = new MutableLiveData<>(testMelding);
        when(repository.getMeldingById(""+1)).thenReturn(meldingLiveData);

        viewModel.getMeldingByIdLiveData(1).observeForever(meldingDisplay -> {
            assertEquals(testMelding.getId(), meldingDisplay.getDisplayId());
        });

    }
}
