package applab.veiligthuis.activity.meldingen;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import applab.veiligthuis.R;
import applab.veiligthuis.common.BaseActivity;
import applab.veiligthuis.viewmodel.MeldingViewModel;

public class MeldingMakenActivity extends BaseActivity {

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

