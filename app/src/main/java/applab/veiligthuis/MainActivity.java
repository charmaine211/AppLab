package applab.veiligthuis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

import applab.veiligthuis.activity.MeldingLijstActivity;
import applab.veiligthuis.activity.MeldingenActivity;
import applab.veiligthuis.activity.tip.TipInzien;
import applab.veiligthuis.activity.tip.TipMaken;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMeldingenButton();
        initTipsInzienButton();
        initTipsMakenButton();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public void initMeldingenButton(){
        Button meldingenBtn = findViewById(R.id.meldingenButton);
        meldingenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent meldingenIntent = new Intent(MainActivity.this, MeldingLijstActivity.class);
                startActivity(meldingenIntent);
            }
        });
    }

    public void initTipsMakenButton(){
        Button meldingenBtn = findViewById(R.id.tipsMakenButton);
        meldingenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tipsIntent = new Intent(MainActivity.this, TipMaken.class);
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
}