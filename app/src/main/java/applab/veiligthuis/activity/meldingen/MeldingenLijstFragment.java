package applab.veiligthuis.activity.meldingen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import applab.veiligthuis.R;
import applab.veiligthuis.model.Melding;
import applab.veiligthuis.viewmodel.MeldingenLijstViewModel;

public class MeldingenLijstFragment extends Fragment {

    private MeldingenLijstViewModel mViewModel;
    private RecyclerView recyclerView;

    public static MeldingenLijstFragment newInstance() {
        return new MeldingenLijstFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meldingen_lijst, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MeldingenLijstViewModel.class);

        recyclerView = getView().findViewById(R.id.meldingenRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Melding> start = new ArrayList<>();
        MeldingenAdapter meldingAdapter = new MeldingenAdapter(start);
        recyclerView.setAdapter(meldingAdapter);

        mViewModel.getMeldingenLijst().observe(getViewLifecycleOwner(), meldingen -> {
            meldingAdapter.setMeldingenList(meldingen);
            meldingAdapter.notifyDataSetChanged();
        });


    }

}