package applab.veiligthuis.activity.SignInUp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import applab.veiligthuis.R;

public class LogInActivity extends AppCompatActivity {
    EditText editTextUserName;
    EditText editTextPassword;
    TextView textViewForgotPassWord;
    TextView textViewRegister;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        editTextUserName = findViewById(R.id.editTextEmailSignIn);
        editTextPassword = findViewById(R.id.editTextPasswordSignIn);
        textViewForgotPassWord = findViewById(R.id.textViewForgetPassword);
        textViewRegister = findViewById(R.id.textViewRegister);
        btnSignIn = findViewById(R.id.buttonLogIn);

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
        Toast.makeText(getApplicationContext(),"optie nog niet geactiveerd", Toast.LENGTH_SHORT).show();
    }

    public void signInButtonClicked(){
        String userName = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(userName).matches()){
            editTextUserName.setError("Please enter valid email address");
            editTextUserName.requestFocus();
        }
        if(editTextPassword.length()<7){
            editTextPassword.setError("Please enter password at least 7 characters");
            editTextPassword.requestFocus();
        }

        Toast.makeText(getApplicationContext(),"optie nog niet voledig geïmplementeerd", Toast.LENGTH_SHORT).show();
    }
}