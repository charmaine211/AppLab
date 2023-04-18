package applab.veiligthuis.activity.tip;



import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import applab.veiligthuis.R;
import applab.veiligthuis.model.tipsmodel.Tip;

public class TipInzien extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private List<Tip> mTipList;
    private TipListAdapter mTipListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_inzien);

        initLogoClickEventHandler();

        mTipList = new ArrayList<>();
        mTipListAdapter = new TipListAdapter(this, mTipList);

        ExpandableListView tipListView = findViewById(R.id.tipListView);
        tipListView.setAdapter(mTipListAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference("tips");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTipList.clear();

                for (DataSnapshot tipSnapshot: dataSnapshot.getChildren()) {
                    Tip tip = tipSnapshot.getValue(Tip.class);
                    tip.setId(tipSnapshot.getKey());
                    if(!tip.isVerwijderd()){
                        mTipList.add(tip);
                    }
                }

                mTipListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


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