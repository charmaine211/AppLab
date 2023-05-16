package applab.veiligthuis.activity.tip;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import applab.veiligthuis.R;
import applab.veiligthuis.model.tipsmodel.Tip;

public class TipDetailActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private boolean beheren;
    private Button changeTitleButton;
    private Button changeDescriptionButton;
    private Button deleteTipButton;
    private Button reactivateTipButton;
    private Tip tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_detail);

        mDatabase = FirebaseDatabase.getInstance().getReference("tips");

        beheren = getIntent().getBooleanExtra("beheren", false);
        String tipJson = getIntent().getStringExtra("tipJson");

        Gson gson = new Gson();
        tip = gson.fromJson(tipJson, Tip.class);

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView contentTextView = findViewById(R.id.contentTextView);

        titleTextView.setText(tip.getTitel());
        contentTextView.setText(tip.getBeschrijving());

        changeTitleButton = findViewById(R.id.changeTitleButton);
        changeDescriptionButton = findViewById(R.id.changeDescriptionButton);
        deleteTipButton = findViewById(R.id.deleteTipButton);
        reactivateTipButton = findViewById(R.id.reactivateTipButton);

        if (beheren) {
            initDetailAsBeheren();
        } else{
            initDetailsAsReadOnly();
        }

    }

    private FragmentManager buildSupportFragmentManager(RequestKeyType requestKeyType){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.setFragmentResultListener(requestKeyType.name(), this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKeyType, @NonNull Bundle result) {
                if(RequestKeyType.Beschrijving.name().equals(requestKeyType)){
                    mDatabase
                            .child(tip.getId())
                            .child(requestKeyType.toLowerCase())
                            .setValue(result.get(requestKeyType));
                }

                if(RequestKeyType.Titel.name().equals(requestKeyType)){
                    mDatabase
                            .child(tip.getId())
                            .child(requestKeyType.toLowerCase())
                            .setValue(result.get(requestKeyType));
                }
            }
        });
        return supportFragmentManager;
    }

    private void initDetailAsBeheren() {

        initBeheerButtons();
        initVisibilityViewComponents();
        initLogoClickEventHandler();
    }

    private void initBeheerButtons() {
        changeTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TitleDialogFragment dialog = new TitleDialogFragment();
                dialog.show(buildSupportFragmentManager(RequestKeyType.Titel), "title_dialog");
            }
        });

        changeDescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DescriptionDialogFragment dialog = new DescriptionDialogFragment();
                dialog.show(buildSupportFragmentManager(RequestKeyType.Beschrijving), "description_dialog");
            }
        });

       initDeleteAndUndeleteButtons();
    }

    private void initDeleteAndUndeleteButtons() {
        if(!tip.isVerwijderd()){
            deleteTipButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatabase
                            .child(tip.getId())
                            .child("verwijderd")
                            .setValue(true);
                    finish();
                }
            });
        }else{
            reactivateTipButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabase
                            .child(tip.getId())
                            .child("verwijderd")
                            .setValue(false);
                    finish();
                }
            });
        }
    }

    private void initVisibilityViewComponents() {

        changeTitleButton.setVisibility(View.VISIBLE);
        changeDescriptionButton.setVisibility(View.VISIBLE);

        initVisibilityDeleteAndUndeleteButtons();
    }

    private void initVisibilityDeleteAndUndeleteButtons() {
        if(!tip.isVerwijderd()){

            deleteTipButton.setVisibility(View.VISIBLE);
            reactivateTipButton.setVisibility(View.GONE);
        }else{
            deleteTipButton.setVisibility(View.GONE);
            reactivateTipButton.setVisibility(View.VISIBLE);
        }
    }

    // TODO: 13/04/2023 implementeer details als readonly
    private void initDetailsAsReadOnly() {
        // Bij alleen inzien de beheermogelijkheden wegnemen.
        changeTitleButton.setVisibility(View.GONE);
        changeDescriptionButton.setVisibility(View.GONE);
        deleteTipButton.setVisibility(View.GONE);
        reactivateTipButton.setVisibility(View.GONE);
    }

    private void initLogoClickEventHandler() {
        ImageView logo = findViewById(R.id.image_view);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
