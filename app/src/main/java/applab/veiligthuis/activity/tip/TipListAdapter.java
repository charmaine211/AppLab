package applab.veiligthuis.activity.tip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import applab.veiligthuis.R;
import applab.veiligthuis.model.tipsmodel.Tip;

public class TipListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Tip> mTipList;

    public TipListAdapter(Context context, List<Tip> tipList) {
        mContext = context;
        mTipList = tipList;
    }

    @Override
    public int getCount() {
        return mTipList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTipList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tip_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titleTextView = convertView.findViewById(R.id.titleTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Tip tip = (Tip) getItem(position);
        viewHolder.titleTextView.setText(tip.getTitel());

        return convertView;
    }

    private static class ViewHolder {
        TextView titleTextView;
    }
}
