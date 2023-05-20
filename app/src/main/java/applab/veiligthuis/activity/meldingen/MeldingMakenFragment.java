package applab.veiligthuis.activity.meldingen;

import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;

import applab.veiligthuis.R;
import applab.veiligthuis.viewmodel.MeldingViewModel;

public class MeldingMakenFragment extends Fragment {

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

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if (currentUser != null) {
                    String uid = currentUser.getUid();
                    meldingViewModel.insertMelding(plaatsnaam, beschrijving, datum.toString(), uid);
                } else {
                    FirebaseAuth.getInstance().signInAnonymously()
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        String uid = user.getUid();

                                        meldingViewModel.insertMelding(plaatsnaam, beschrijving, datum.toString(), uid);

                                        meldingEditText.setText("");
                                    } else {
                                        Toast.makeText(getActivity(), "Error creating anonymous user", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                meldingViewModel.insertMelding(plaatsnaam, beschrijving, datum.toString());

                meldingEditText.setText("");
            }
        });
    }
}