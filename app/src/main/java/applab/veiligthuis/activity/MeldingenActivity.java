package applab.veiligthuis.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import applab.veiligthuis.R;
import applab.veiligthuis.model.Melding;
import applab.veiligthuis.viewmodel.MeldingenLijstViewModel;

public class MeldingenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meldingen);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.fragmentContainerView, MeldingenLijstFragment.class, null).commit();
        }


    }

}