package applab.veiligthuis.activity.meldingen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import applab.veiligthuis.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeldingMakenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeldingMakenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MeldingMakenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeldingMakenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeldingMakenFragment newInstance(String param1, String param2) {
        MeldingMakenFragment fragment = new MeldingMakenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_melding_maken, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create spinners
        initPlaatsnaamSpinner();
        initJaNeeSpinner();
    }

    public void initPlaatsnaamSpinner(){
        Spinner plaatsnaamSpinner = getView().findViewById(R.id.plaatsnaam_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.plaatsnamen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plaatsnaamSpinner.setAdapter(adapter);
    }

    public void initJaNeeSpinner(){
        Spinner jaNeeSpinner = getView().findViewById(R.id.janee_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.janee, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jaNeeSpinner.setAdapter(adapter);
    }

}