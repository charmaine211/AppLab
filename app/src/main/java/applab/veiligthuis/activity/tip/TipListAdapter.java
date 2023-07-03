package applab.veiligthuis.activity.tip;

import static android.view.View.GONE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.util.List;

import applab.veiligthuis.R;
import applab.veiligthuis.domain.model.tipsmodel.Tip;

public class TipListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Tip> mTipList;
    private DatabaseReference mDatabase = null;
    private boolean mBeheren = false;

    public TipListAdapter(Context context, List<Tip> tipList, boolean beheren, DatabaseReference databaseReference) {
        mContext = context;
        mTipList = tipList;
        mBeheren = beheren;
        mDatabase = databaseReference;
    }

    public TipListAdapter(Context context, List<Tip> tipList) {
        mContext = context;
        mTipList = tipList;
    }

    @Override
    public int getGroupCount() {
        return mTipList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mTipList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mTipList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.tip_tonen_rowitem, null);
        }

        ImageView deleteImage = view.findViewById(R.id.deleteImageView);
        ImageView restoreImage = view.findViewById(R.id.restoreImageView);
        ImageView editImage = view.findViewById(R.id.editImageView);

        Tip tip = mTipList.get(groupPosition);

        TextView titleTextView = view.findViewById(R.id.tipTitleTextView);
        titleTextView.setText(tip.getTitel());

        ShapeableImageView tipShowImage = view.findViewById(R.id.tipShowImage);
        ShapeableImageView tipHideImage = view.findViewById(R.id.tipHideImage);

        initImageViewsTipItems(deleteImage, restoreImage, editImage, tip);

        if(!tip.isVerwijderd()){
            deleteImage.setVisibility(View.VISIBLE);
            restoreImage.setVisibility(View.GONE);

        } else{
            deleteImage.setVisibility(View.GONE);
            restoreImage.setVisibility(View.VISIBLE);
        }

        if (isExpanded) {
            tipShowImage.setVisibility(GONE);
            tipHideImage.setVisibility(View.VISIBLE);
        } else {
            tipShowImage.setVisibility(View.VISIBLE);
            tipHideImage.setVisibility(GONE);
        }

        if(!mBeheren){
            view.findViewById(R.id.beheerLayout).setVisibility(GONE);
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.tip_detail_omschrijving, null);
        }

        TextView omschrijving = view.findViewById(R.id.tipDescriptionTextView);
        Tip tip = mTipList.get(groupPosition);
        omschrijving.setText(tip.getBeschrijving());

        return view;

    }

    private void initImageViewsTipItems(ImageView deleteImage, ImageView restoreImage, ImageView editImage, Tip selectedTip) {
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event for editDeleteView
                mDatabase
                        .child(selectedTip.getId())
                        .child("verwijderd")
                        .setValue(true);
            }
        });

        restoreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event for editDeleteView
                mDatabase
                        .child(selectedTip.getId())
                        .child("verwijderd")
                        .setValue(false);
            }
        });

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //serialiseren omdat alleen primitives als parameter gebruikt kunnen worden voor intent
                String tipJson = new Gson().toJson(selectedTip);
                Intent intent = new Intent(mContext, TipBuilder.class);
                intent.putExtra("tipJson", tipJson);
                intent.putExtra("editing", true);
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
