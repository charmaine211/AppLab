package applab.veiligthuis.activity.meldingen;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import applab.veiligthuis.R;
import applab.veiligthuis.common.BaseFragment;
import applab.veiligthuis.viewmodel.MeldingViewModel;

public class MeldingMakenFragment extends BaseFragment {

    private MeldingViewModel meldingViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_melding_maken, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        meldingViewModel = new ViewModelProvider(requireActivity()).get(MeldingViewModel.class);
        initMeldingObservers();
        initPlaatsnaamSpinner();
        initOpslaanButton();
        super.initSluitAppButton();

    }

    public void initPlaatsnaamSpinner(){
        Spinner plaatsnaamSpinner = getView().findViewById(R.id.plaatsnaam_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.plaatsnamen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plaatsnaamSpinner.setAdapter(adapter);
    }


    public void initOpslaanButton(){

        getView().findViewById(R.id.opslaan_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plaatsnaam = ((Spinner) getView().findViewById(R.id.plaatsnaam_spinner)).getSelectedItem().toString();
                final EditText meldingEditText = getView().findViewById(R.id.meldingmaken_editTextTextMultiLine);
                String beschrijving = meldingEditText.getText().toString().trim();

                if (plaatsnaam.isEmpty() || beschrijving.isEmpty()){
                    Toast.makeText(getActivity(), "Zorg dat de beschrijving en de plaatsnaam ingevuld zijn.", Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MeldingMakenFragment.this.getContext());
                    builder.setMessage("Wil je je melding opsturen?")
                            .setCancelable(true)
                            .setPositiveButton("Bevestig", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    slaMeldingOp(plaatsnaam, beschrijving);
                                    returnToMain();
                                }
                            })
                            .setNegativeButton("Annuleer", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }


    public void slaMeldingOp(String plaatsnaam, String beschrijving){
        LocalDateTime datum = LocalDateTime.now();

        meldingViewModel.insertMelding(plaatsnaam, beschrijving, datum.toEpochSecond(ZoneOffset.UTC));
    }

    public void initMeldingObservers(){
        meldingViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), successfull -> {
            if (successfull) {
                // Show a Toast or handle the error message
                Toast.makeText(requireContext(), "Bedankt voor het maken van de melding.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Fout bij opslaan melding ", Toast.LENGTH_SHORT).show();
            }
        });
    }

}