package applab.veiligthuis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.FirebaseDatabase;

import applab.veiligthuis.activity.melding.MeldingLijstActivity;
import applab.veiligthuis.activity.SignInUp.LogInActivity;
import applab.veiligthuis.activity.tip.TipBeheren;
import applab.veiligthuis.activity.tip.TipInzien;
import applab.veiligthuis.activity.tip.TipMaken;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        initMeldingenButton();
        initTipsInzienButton();
        initTipsBeherenButton();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Toolbar toolbar = findViewById(R.id.veilig_thuis_toolbar);
        setSupportActionBar(toolbar);
        ImageView imageView_tb = findViewById(R.id.second_image_view);

        imageView_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        LogInActivity.class);
                startActivity(intent);
            }
        });

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
}