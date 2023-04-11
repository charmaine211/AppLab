package applab.veiligthuis.activity.meldingen;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import applab.veiligthuis.R;

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