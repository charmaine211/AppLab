package applab.veiligthuis.activity.tip;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import applab.veiligthuis.R;
import applab.veiligthuis.domain.model.model.tipsmodel.Tip;
import applab.veiligthuis.domain.model.model.tipsmodel.TipCategorie;

public class TipBuilder extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tips");
    EditText titelEditText, beschrijvingEditText;
    Spinner categorieSpinner;
    private boolean editing;
    private Tip tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_builder);

        setSpinnerAdapter();

        editing = getIntent().getBooleanExtra("editing", false);
        String tipJson = getIntent().getStringExtra("tipJson");

        Gson gson = new Gson();
        tip = gson.fromJson(tipJson, Tip.class);

        titelEditText = findViewById(R.id.editTextTitel);
        beschrijvingEditText = findViewById(R.id.editTextBeschrijving);
        categorieSpinner = findViewById(R.id.spinner_categorie);

        if(editing){
            titelEditText.setText(tip.getTitel());
            beschrijvingEditText.setText(tip.getBeschrijving());
            categorieSpinner.setSelection(findSpinnerPosition(tip.getCategorie()));
        }
        initSubmitButton();
    }

    private int findSpinnerPosition(TipCategorie itemCategorie) {

        TipCategorie[] enumValues = TipCategorie.values();
        int position = -1;
        for (int i = 0; i < enumValues.length; i++) {
            if (enumValues[i] == itemCategorie) {
                position = i;
                break;
            }
        }

        return position + 1;
    }

    private void initSubmitButton() {
        Button submitButton = findViewById(R.id.btn_tipSubmit);
        String buttonTitel = editing ? "Sla wijziging op" : "Sla tip op";
        submitButton.setText(buttonTitel);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titel = titelEditText.getText().toString();
                String beschrijving = beschrijvingEditText.getText().toString();
                String categorie = categorieSpinner.getSelectedItem().toString();

                if(titel.isEmpty()){
                    titelEditText.setError("Titel mag niet leeg zijn");
                    titelEditText.requestFocus();
                    return;
                }

                if(beschrijving.isEmpty()){
                    beschrijvingEditText.setError("beschrijving mag niet leeg zijn");
                    beschrijvingEditText.requestFocus();
                    return;
                }

                if(categorie.isEmpty() || categorie == "" || categorie == "Type geweld"){
                    ((TextView)categorieSpinner.getSelectedView()).setError("Category cannot be empty");
                    return;
                }

                if(editing){
                    tip.setTitel(titel);
                    tip.setBeschrijving(beschrijving);
                    tip.setCategorie(TipCategorie.parseString(categorie));

                    TipCategorie test = tip.getCategorie();

                    HashMap<String, Object> updateValues = new HashMap<>();
                    updateValues.put(tip.getId() + "/titel", titel);
                    updateValues.put(tip.getId() + "/beschrijving", beschrijving);
                    updateValues.put(tip.getId() + "/categorie", tip.getCategorie());

                    databaseReference
                            .updateChildren(updateValues);
                } else{
                    String tipId = databaseReference.push().getKey();
                    Tip tip = new Tip(tipId, titel, beschrijving, TipCategorie.valueOf(categorie));

                    databaseReference.child(tipId).setValue(tip);
                }

                finishActivity();
            }
        });
    }

    private void setSpinnerAdapter() {
        Spinner spinner = findViewById(R.id.spinner_categorie);
        ArrayList<String> items = new ArrayList<String>();
        items.add("Type geweld");
        for(TipCategorie categorie : TipCategorie.values()){
            items.add(categorie.toUserfriendlyname());
        }
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    private void finishActivity(){
        Toast.makeText(TipBuilder.this, editing ? "Tip succesvol gewijzigd" : "Tip succesvol aangemaakt!", Toast.LENGTH_SHORT).show();

        titelEditText.setText("");
        beschrijvingEditText.setText("");
        categorieSpinner.setSelection(0);

        finish();
    }

}
