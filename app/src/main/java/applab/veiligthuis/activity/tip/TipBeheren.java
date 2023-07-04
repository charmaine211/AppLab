package applab.veiligthuis.activity.tip;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import applab.veiligthuis.R;
import applab.veiligthuis.domain.model.tipsmodel.Tip;


public class TipBeheren extends AppCompatActivity {

    private boolean showDeleted = false;
    private DatabaseReference mDatabase;
    private List<Tip> tipList;
    private TipListAdapter tipListAdapter;
    private Switch switchToggle;
    private ShapeableImageView addTipButton;
    private FirebaseAuth mAuth;

    private void initAddButton(){
        addTipButton = findViewById(R.id.addTipButton);
        addTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TipBeheren.this, TipBuilder.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_beheren);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference("tips");
        initSwitchToggle();
        initAddButton();
        initTipListView();
        populateTipListView();

        setTopDividerColor();
    }

    private void setTopDividerColor() {
        int dividerColor = getResources().getColor(R.color.veiligthuis_grijs);

        MaterialDivider topDivider = findViewById(R.id.topListDivider);
        topDivider.setDividerColor(dividerColor);
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        mAuth = FirebaseAuth.getInstance();
//
//        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                    // start the login activity
//                    startActivity(new Intent(TipBeheren.this, LogInActivity.class));
//                }
//            }
//        });
//    }

    private void initSwitchToggle(){
        switchToggle = findViewById(R.id.toggleSwitch);
        switchToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showDeleted = !isChecked;
                addTipButton.setVisibility(showDeleted ? View.GONE : View.VISIBLE);
                populateTipListView();
            }
        });
    }

    private void initTipListView() {
        ExpandableListView tipListView = findViewById(R.id.tipListView);
        tipListView.setGroupIndicator(null);
        tipList = new ArrayList<>();
        tipListAdapter = new TipListAdapter(this, tipList, true, mDatabase);
        tipListView.setAdapter(tipListAdapter);
    }

    private void populateTipListView() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tipList.clear();

                for (DataSnapshot tipSnapshot : dataSnapshot.getChildren()) {
                    Tip tip;
                    try{
                        tip = tipSnapshot.getValue(Tip.class);
                    } catch(Exception e){
                        continue;
                    }
                    if (showDeleted && tip.isVerwijderd()) {
                        tipList.add(tip);
                    } else if (!showDeleted && !tip.isVerwijderd()) {
                        tipList.add(tip);
                    }
                }

                tipListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}
