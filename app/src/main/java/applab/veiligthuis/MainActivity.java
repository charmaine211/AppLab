package applab.veiligthuis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

import applab.veiligthuis.activity.meldingen.MeldingenActivity;
import applab.veiligthuis.activity.meldingen.RisicoAnalyseActivity;
import applab.veiligthuis.activity.tip.TipBeheren;
import applab.veiligthuis.activity.tip.TipInzien;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initMeldingenButton();
        initTipsInzienButton();
        initTipsBeherenButton();
        initMaakMeldingButton();
        initSluitAppButton();

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }

    private void initSluitAppButton() {
        View sluitButton = findViewById(R.id.sluitApp);
        sluitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initMeldingenButton(){
        Button meldingenBtn = findViewById(R.id.meldingenButton);
        meldingenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent meldingenIntent = new Intent(MainActivity.this, MeldingenActivity.class);
                startActivity(meldingenIntent);
            }
        });
    }

    public void initTipsBeherenButton(){
        Button meldingenBtn = findViewById(R.id.tipsBeherenButton);
        meldingenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tipsIntent = new Intent(MainActivity.this, TipBeheren.class);
                startActivity(tipsIntent);
            }
        });
    }

    public void initTipsInzienButton(){
        Button meldingenBtn = findViewById(R.id.tipsInzienButton);
        meldingenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tipsIntent = new Intent(MainActivity.this, TipInzien.class);
                startActivity(tipsIntent);
            }
        });
    }

    public void initMaakMeldingButton(){
        Button maakMeldingBtn = (Button) findViewById(R.id.maakMeldingButton);
        maakMeldingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RisicoAnalyseActivity.class);
                startActivity(intent);
            }
        });
    }
}