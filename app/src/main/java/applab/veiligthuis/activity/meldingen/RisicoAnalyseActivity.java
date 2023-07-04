package applab.veiligthuis.activity.meldingen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import applab.veiligthuis.R;

public class RisicoAnalyseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risico_analyse);

        RisicoAnalyseFragment risicoAnalyseFragment = new RisicoAnalyseFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.risicoAnalyse_FragmentContainerView, risicoAnalyseFragment);

        transaction.commit();
    }
}