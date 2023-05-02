package applab.veiligthuis.activity.meldingen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDateTime;

import applab.veiligthuis.R;
import applab.veiligthuis.viewmodel.MeldingViewModel;

public class MeldingMakenFragment extends Fragment {

    private MeldingViewModel meldingViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meldingViewModel = new ViewModelProvider(requireActivity()).get(MeldingViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_melding_maken, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create spinners
        initPlaatsnaamSpinner();
        initJaNeeSpinner();
        initSaveButton();
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

    public void initSaveButton(){

        // Get reference to EditText
        final EditText meldingEditText = getView().findViewById(R.id.meldingmaken_editTextTextMultiLine);

        // Save button click listener
        getView().findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from spinners and EditText

                boolean beroepsmatig;

                String plaatsnaam = ((Spinner) getView().findViewById(R.id.plaatsnaam_spinner)).getSelectedItem().toString();

                // TODO: Dit kan ook niet een spinner zijn. Even met het UI ontwerp controleren.
                String jaNee = ((Spinner) getView().findViewById(R.id.janee_spinner)).getSelectedItem().toString();

                if (jaNee.toLowerCase().equals("ja")){
                    beroepsmatig = true;
                }
                else {
                    beroepsmatig = false;
                }
                String beschrijving = meldingEditText.getText().toString().trim();

                // TODO: Gebruiker moet gekoppeld worden aan melding. Dat kan nu een anonieme gebruiker zijn
                String gebruiker = "";
                LocalDateTime datum = LocalDateTime.now();

                // Save melding in ViewModel
                meldingViewModel.insertMelding(gebruiker, plaatsnaam, beroepsmatig, beschrijving, datum);

                // Clear EditText
                meldingEditText.setText("");
            }
        });
    }
}