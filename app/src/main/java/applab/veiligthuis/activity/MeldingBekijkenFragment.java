package applab.veiligthuis.activity;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import applab.veiligthuis.R;
import applab.veiligthuis.model.Melding;
import applab.veiligthuis.viewmodel.MeldingenLijstViewModel;

public class MeldingBekijkenFragment extends Fragment {

    private MeldingenLijstViewModel mViewModel;

    public static MeldingBekijkenFragment newInstance() {
        return new MeldingBekijkenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.melding_bekijken_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(MeldingenLijstViewModel.class);

        int id = requireArguments().getInt("melding_id");

        Melding m = mViewModel.getMeldingById(id);

        TextView vw = view.findViewById(R.id.melding_bekijken_id);
        TextView info = view.findViewById(R.id.melding_bekijken_info);
        vw.setText(""+m.getId());
        info.setText(m.getMeldingInfo());


    }

}