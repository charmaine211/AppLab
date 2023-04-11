package applab.veiligthuis.activity.meldingen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import applab.veiligthuis.R;
import applab.veiligthuis.model.Melding;

public class MeldingenAdapter extends RecyclerView.Adapter<MeldingenAdapter.MeldingViewHolder> {

    List<Melding> meldingen;

    public MeldingenAdapter(List<Melding> meldingen){
        this.meldingen = meldingen;
    }

    @NonNull
    @Override
    public MeldingenAdapter.MeldingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MeldingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.melding_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MeldingenAdapter.MeldingViewHolder holder, int position) {
        holder.idTextView.setText(""+meldingen.get(position).getId());
        holder.infoTextView.setText(meldingen.get(position).getMeldingInfo());
    }

    @Override
    public int getItemCount() {
        return meldingen.size();
    }

    public void setMeldingenList(List<Melding> meldingen){
        this.meldingen = meldingen;
    }

    public static class MeldingViewHolder extends RecyclerView.ViewHolder{

        TextView idTextView;
        TextView infoTextView;

        public MeldingViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.meldingId);
            infoTextView = itemView.findViewById(R.id.meldingInfo);
        }
    }
}
