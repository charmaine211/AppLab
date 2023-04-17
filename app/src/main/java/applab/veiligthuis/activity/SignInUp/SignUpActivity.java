package applab.veiligthuis.activity.SignInUp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import applab.veiligthuis.R;
import applab.veiligthuis.model.Users.User;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextUserName;
    EditText editTextPassword;
    EditText editTextPhoneNo;
    EditText editTextEmail;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPhoneNo = (EditText) findViewById(R.id.editTextMobileNo);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);


        Button button = (Button) findViewById(R.id.buttonAfterInput);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txtUserName = editTextUserName.getText().toString().trim();
                String txtPassword = editTextPassword.getText().toString().trim();
                String txtPhoneNo = editTextPhoneNo.getText().toString().trim();
                String txtEmail = editTextEmail.getText().toString().trim();
                mAuth = FirebaseAuth.getInstance();

                if(txtUserName.isEmpty()){
                    editTextUserName.setError("please enter Username");
                    editTextUserName.requestFocus();
                }
                if(txtPassword.isEmpty() || txtPassword.length()<7){
                    editTextPassword.setError("please enter password containing at least 7 characters");
                    editTextPassword.requestFocus();
                }
                if (txtPhoneNo.isEmpty()){
                    editTextPhoneNo.setError("please enter mobile number");
                    editTextPhoneNo.requestFocus();
                }
                if (txtEmail.isEmpty()){
                    editTextEmail.setError("please enter email adress");
                    editTextEmail.requestFocus();
                }
                if(txtUserName.isEmpty() || txtEmail.isEmpty() || txtPassword.length()<7 || txtPassword.isEmpty() || txtPhoneNo.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"formulier moet volledig ingevuld worden",Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(txtEmail,txtPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete (@NonNull Task<AuthResult> task){
                                if (task.isSuccessful()){
                                    User user = new User(txtUserName, txtPassword,txtPhoneNo,txtEmail);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(SignUpActivity.this,"User successfully registered",Toast.LENGTH_LONG).show();
                                                    }else{
                                                        Toast.makeText(SignUpActivity.this,"faillure to register",Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                }else{
                                    Toast.makeText(SignUpActivity.this,"faillure authentification",Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                //
                Toast.makeText(SignUpActivity.this,"faillure",Toast.LENGTH_LONG).show();
            }
        });
    }


}