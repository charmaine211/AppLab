package applab.veiligthuis.activity.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import applab.veiligthuis.R;
import applab.veiligthuis.activity.meldingen.MeldingenActivity;
import applab.veiligthuis.activity.meldingen.RisicoAnalyseActivity;
import applab.veiligthuis.activity.tip.TipBeheren;
import applab.veiligthuis.activity.tip.TipInzien;

public class MainActivity extends AppCompatActivity {

    protected FirebaseAuth mAuth;
    private Guideline guideline;
    protected FrameLayout notLoggedInContainer;
    protected FrameLayout loggedInContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initSluitAppButton();

        mAuth = FirebaseAuth.getInstance();

        boolean isLoggedIn = checkLoginState();

        initializeFragments();

        FrameLayout notLoggedInContainer = findViewById(R.id.not_logged_in_fragment_container);
        FrameLayout loggedInContainer = findViewById(R.id.logged_in_fragment_container);

        if (isLoggedIn) {
            notLoggedInContainer.setVisibility(View.GONE);
            loggedInContainer.setVisibility(View.VISIBLE);
        } else {
            notLoggedInContainer.setVisibility(View.VISIBLE);
            loggedInContainer.setVisibility(View.GONE);
            setGuidelinePercentage(0.5f);
        }
    }

    private void initializeFragments() {
        FragmentLoggedIn fragmentLoggedIn = new FragmentLoggedIn();
        FragmentNotLoggedIn fragmentNotLoggedIn = new FragmentNotLoggedIn();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.logged_in_fragment_container, fragmentLoggedIn);
        fragmentTransaction.add(R.id.not_logged_in_fragment_container, fragmentNotLoggedIn);
        fragmentTransaction.commit();
    }

    private boolean checkLoginState() {
        FirebaseUser user = mAuth.getCurrentUser();
        return (user != null && !user.isAnonymous());
    }

    private void initSluitAppButton() {
        androidx.appcompat.widget.AppCompatImageView sluitButton = findViewById(R.id.sluitApp);
        sluitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        guideline = findViewById(R.id.guideline);
        notLoggedInContainer = findViewById(R.id.not_logged_in_fragment_container);
        loggedInContainer = findViewById(R.id.logged_in_fragment_container);
    }

    private void setGuidelinePercentage(float percentage) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
        layoutParams.guidePercent = percentage;
        guideline.setLayoutParams(layoutParams);
    }

}