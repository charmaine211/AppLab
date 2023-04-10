package applab.veiligthuis.activity.tip;



import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import applab.veiligthuis.R;
import applab.veiligthuis.model.tipsmodel.Tip;
import applab.veiligthuis.model.tipsmodel.TipCategorie;

public class TipInzien extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private List<Tip> mTipList;
    private TipListAdapter mTipListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_inzien);

        mTipList = new ArrayList<>();
        mTipListAdapter = new TipListAdapter(this, mTipList);

        ListView tipListView = findViewById(R.id.tipListView);
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

        tipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tip selectedTip = mTipList.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(TipInzien.this);
                builder.setTitle(selectedTip.getTitel());
                builder.setMessage(selectedTip.getBeschrijving());
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setNegativeButton("verwijder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tipId = mTipList.get(position).getId();
                        mDatabase
                                .child(tipId)
                                .child("verwijderd")
                                .setValue(true);
                    }
                });
                builder.setNegativeButtonIcon(getDrawable(R.drawable.delete_btn));
                builder.show();
            }
        });
    }
}