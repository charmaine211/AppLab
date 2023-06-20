package applab.veiligthuis.activity.meldingen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import applab.veiligthuis.R;

public class MeldingMakenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_melding_maken);

        MeldingMakenFragment meldingMakenFragment = new MeldingMakenFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.meldingMaken_FragmentContainerView, meldingMakenFragment);

        transaction.commit();
    }

}

