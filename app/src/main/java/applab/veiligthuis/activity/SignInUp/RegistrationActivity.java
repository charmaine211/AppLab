package applab.veiligthuis.activity.SignInUp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import applab.veiligthuis.R;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private EditText emailEditText, passwordEditText;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.buttonRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    /**
     *
     */
    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Voer een email en wachtwoord in", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null && currentUser.isAnonymous()) {
            AuthCredential credential = EmailAuthProvider.getCredential(email, password);
            currentUser.linkWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User conversion successful");
                                Toast.makeText(RegistrationActivity.this, "Gebruiker succesvol geconverteerd", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e(TAG, "User conversion failed", task.getException());
                                Toast.makeText(RegistrationActivity.this, "Gebruiker conversie mislukt", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User registration successful");
                                Toast.makeText(RegistrationActivity.this, "Registratie succesvol", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e(TAG, "User registration failed", task.getException());
                                Toast.makeText(RegistrationActivity.this, "Registratie niet gelukt", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
