package applab.veiligthuis.activity.SignInUp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import applab.veiligthuis.MainActivity;
import applab.veiligthuis.R;

public class LogInActivity extends AppCompatActivity {
    EditText editTextUserName;
    EditText editTextPassword;
    TextView textViewForgotPassWord;
    TextView textViewRegister;
    Button btnSignIn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        editTextUserName = findViewById(R.id.editTextEmailSignIn);
        editTextPassword = findViewById(R.id.editTextPasswordSignIn);
        textViewForgotPassWord = findViewById(R.id.textViewForgetPassword);
        textViewRegister = findViewById(R.id.textViewRegister);
        btnSignIn = findViewById(R.id.buttonLogIn);
        mAuth = FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInButtonClicked();
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInRegisterClicked();
            }
        });

        textViewForgotPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInForgottenPassWord();
            }
        });

    }

    private void signInForgottenPassWord() {

        Toast.makeText(getApplicationContext(),"optie nog niet geactiveerd", Toast.LENGTH_SHORT).show();
    }

    private void signInRegisterClicked() {
        Intent intent = new Intent(LogInActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void signInButtonClicked(){
        String userName = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(userName.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(userName).matches()){
            editTextUserName.setError("Please enter valid email address");
            editTextUserName.requestFocus();
        }
        if(password.isEmpty() || editTextPassword.length()<7){
            editTextPassword.setError("Please enter password at least 7 characters");
            editTextPassword.requestFocus();
        }

        else{
            mAuth.signInWithEmailAndPassword(userName, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(getApplicationContext(), "Authentication Successful.",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}