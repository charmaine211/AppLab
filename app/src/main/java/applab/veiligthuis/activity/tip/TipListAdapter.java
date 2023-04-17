package applab.veiligthuis.activity.tip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import applab.veiligthuis.R;
import applab.veiligthuis.model.tipsmodel.Tip;

public class TipListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Tip> mTipList;

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

        Tip tip = mTipList.get(groupPosition);

        TextView titleTextView = view.findViewById(R.id.tipTitleTextView);
        titleTextView.setText(tip.getTitel());

        ShapeableImageView tipShowImage = view.findViewById(R.id.tipShowImage);
        ShapeableImageView tipHideImage = view.findViewById(R.id.tipHideImage);

        if (isExpanded) {
            tipShowImage.setVisibility(View.GONE);
            tipHideImage.setVisibility(View.VISIBLE);
        } else {
            tipShowImage.setVisibility(View.VISIBLE);
            tipHideImage.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.tip_tonen_rowitem, null);
        }

        Tip tip = mTipList.get(groupPosition);

        LinearLayout detailLayout = view.findViewById(R.id.tipDetailLayout);
        ShapeableImageView tipShowImage = view.findViewById(R.id.tipShowImage);

        if (isLastChild) {
            detailLayout.setVisibility(View.VISIBLE);
            tipShowImage.setVisibility(View.GONE);
        } else {
            detailLayout.setVisibility(View.GONE);
        }

        TextView descriptionTextView = view.findViewById(R.id.tipDescriptionTextView);
        descriptionTextView.setText(tip.getBeschrijving());

        return view;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
