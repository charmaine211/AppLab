package applab.veiligthuis.activity.meldingen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import applab.veiligthuis.R;

import android.os.Bundle;

public class RisicoAnalyseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risico_analyse);

        // Create a new instance of the risicoAnalyseFragment
        RisicoAnalyseFragment risicoAnalyseFragment = new RisicoAnalyseFragment();

        // Start a new fragment transaction to display the fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace the fragment_container with the risicoAnalyseFragment
        transaction.replace(R.id.risicoAnalyse_FragmentContainerView, risicoAnalyseFragment);

        // Commit the transaction
        transaction.commit();
    }
}