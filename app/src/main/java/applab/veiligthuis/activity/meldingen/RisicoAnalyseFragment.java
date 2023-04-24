package applab.veiligthuis.activity.meldingen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import applab.veiligthuis.R;

import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RisicoAnalyseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RisicoAnalyseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RisicoAnalyseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RisicoAnalyseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RisicoAnalyseFragment newInstance(String param1, String param2) {
        RisicoAnalyseFragment fragment = new RisicoAnalyseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initJaButton();
        initTwijfelButton();
        initNeeButton();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_risico_analyse, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the button by ID
        Button neeButton = view.findViewById(R.id.nee_button);

        // Set an onClickListener to the neeButton
        neeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new instance of the maakMeldingFragment
                MeldingMakenFragment meldingMakenFragment = new MeldingMakenFragment();

                // Start a new fragment transaction to display the fragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Replace the fragment_container with the maakMeldingFragment
                transaction.replace(R.id.fragment_container, meldingMakenFragment);

                // Add the transaction to the back stack
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
    }

    public void initJaButton() {
        Button jaButton =  getView().findViewById(R.id.ja_button);
        jaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RisicoAnalyseFragment.this.getContext(), "112 bellen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initTwijfelButton() {
        Button twijfelButton =  getView().findViewById(R.id.twijfel_button);
        twijfelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RisicoAnalyseFragment.this.getContext(), "VeiligThuis bellen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initNeeButton() {
        Button neeButton =  getView().findViewById(R.id.nee_button);
        neeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new MeldingMakenFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

}