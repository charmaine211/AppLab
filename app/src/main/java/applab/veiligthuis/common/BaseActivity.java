package applab.veiligthuis.common;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import applab.veiligthuis.R;
import applab.veiligthuis.activity.home.MainActivity;

public class BaseActivity extends AppCompatActivity {

    protected void initSluitAppButton() {
        androidx.appcompat.widget.AppCompatImageView sluitButton = findViewById(R.id.sluitApp);
        sluitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finishAffinity();
            }
        });
    }

    protected void returnToMain() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        finish();
    }
}
