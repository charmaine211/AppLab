package applab.veiligthuis.activity.meldingen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.time.LocalDateTime;

import applab.veiligthuis.R;
import applab.veiligthuis.data.FirebaseRepository;
import applab.veiligthuis.viewmodel.MeldingViewModel;

public class MeldingMakenFragment extends Fragment {

    private MeldingViewModel meldingViewModel;

    private FirebaseRepository firebaseRepository;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseRepository = FirebaseRepository.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_melding_maken, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        meldingViewModel = new ViewModelProvider(requireActivity()).get(MeldingViewModel.class);
        // Create spinners
        initPlaatsnaamSpinner();
        initSaveButton();
    }

    public void initPlaatsnaamSpinner(){
        Spinner plaatsnaamSpinner = getView().findViewById(R.id.plaatsnaam_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.plaatsnamen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plaatsnaamSpinner.setAdapter(adapter);
    }

    public void initSaveButton(){

        final EditText meldingEditText = getView().findViewById(R.id.meldingmaken_editTextTextMultiLine);

        getView().findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String plaatsnaam = ((Spinner) getView().findViewById(R.id.plaatsnaam_spinner)).getSelectedItem().toString();
                String beschrijving = meldingEditText.getText().toString().trim();
                LocalDateTime datum = LocalDateTime.now();
                String uid = firebaseRepository.getCurrentUserId();

                if (uid != null) {
                    meldingViewModel.insertMelding(plaatsnaam, beschrijving, datum.toString(), uid);
                    Toast.makeText(getActivity(), "Melding is succesvol verstuurd", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseRepository.signInAnonymously(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        String uid = firebaseRepository.getCurrentUserId();

                                        meldingViewModel.insertMelding(plaatsnaam, beschrijving, datum.toString(), uid);
                                        Toast.makeText(getActivity(), "Melding is succesvol verstuurd", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String errorMessage = task.getException().getMessage();
                                        Log.e("Firebase", "Error creating anonymous user: " + errorMessage);
                                        Toast.makeText(getActivity(), "Fout bij het anoniem versturen van melding", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                meldingEditText.setText("");
            }
        });
    }
}