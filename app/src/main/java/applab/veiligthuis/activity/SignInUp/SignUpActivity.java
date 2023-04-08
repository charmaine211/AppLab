package applab.veiligthuis.activity.SignInUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import applab.veiligthuis.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button button = (Button) findViewById(R.id.buttonAfterInput);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence text = "Nog niet geactiveerd";
                int duration = Toast.LENGTH_LONG;
                Toast.makeText(context, text,duration).show();
            }
        });
    }


}