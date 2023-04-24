package applab.veiligthuis.activity.meldingen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import applab.veiligthuis.R;

public class MeldingMakenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_melding_maken);

        // Create a new instance of the risicoAnalyseFragment
        RisicoAnalyseFragment risicoAnalyseFragment = new RisicoAnalyseFragment();

        // Start a new fragment transaction to display the fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace the fragment_container with the risicoAnalyseFragment
        transaction.replace(R.id.fragment_container, risicoAnalyseFragment);

        // Commit the transaction
        transaction.commit();
    }
}

