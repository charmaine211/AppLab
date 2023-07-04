package applab.veiligthuis.activity.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import applab.veiligthuis.R;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        initProfile(user);
        initSignOutButton();
    }

    private void initSignOutButton() {
        Button signOut = findViewById(R.id.sign_out_button);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
            }
        });
    }

    private void initProfile(FirebaseUser user) {
        TextView username = findViewById(R.id.display_name);
        username.setText(user.getDisplayName());

        TextView email = findViewById(R.id.email_address);
        email.setText(user.getEmail());
    }

}
