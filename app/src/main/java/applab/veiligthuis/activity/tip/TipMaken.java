package applab.veiligthuis.activity.tip;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import applab.veiligthuis.R;
import applab.veiligthuis.model.tipsmodel.Tip;
import applab.veiligthuis.model.tipsmodel.TipCategorie;

public class TipMaken extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    EditText titelEditText, beschrijvingEditText;
    Spinner categorieSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_maken);

        Button submitButton = findViewById(R.id.btn_tipSubmit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the tip data from the UI
                titelEditText = findViewById(R.id.editTextTitel);
                beschrijvingEditText = findViewById(R.id.editTextBeschrijving);
                categorieSpinner = findViewById(R.id.spinner_categorie);

                String titel = titelEditText.getText().toString();
                String beschrijving = beschrijvingEditText.getText().toString();
                String categorie = categorieSpinner.getSelectedItem().toString();

                // Create a new Tip object with the retrieved data
                Tip tip = new Tip(titel, beschrijving, TipCategorie.valueOf(categorie));

                // Push the Tip object to the Firebase database
                databaseReference.child("tips").push().setValue(tip);

                finishActivity();
            }
        });
    }

    private void finishActivity(){
        // Show a success message to the user
        Toast.makeText(TipMaken.this, "Tip succesvol aangemaakt!", Toast.LENGTH_SHORT).show();

        // Clear the UI fields
        titelEditText.setText("");
        beschrijvingEditText.setText("");
        categorieSpinner.setSelection(0);

        finish();
    }

}
