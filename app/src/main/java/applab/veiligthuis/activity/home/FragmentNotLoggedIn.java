package applab.veiligthuis.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import applab.veiligthuis.R;
import applab.veiligthuis.activity.meldingen.MeldingLijstActivity;
import applab.veiligthuis.activity.meldingen.RisicoAnalyseActivity;
import applab.veiligthuis.activity.tip.TipBeheren;
import applab.veiligthuis.activity.tip.TipInzien;

public class FragmentNotLoggedIn extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_not_logged_in, container, false);
        initTipsInzienButton(view);
        initMaakMeldingButton(view);
        return view;
    }

    public void initTipsInzienButton(View view) {
        Button tipsInzienBtn = view.findViewById(R.id.tipsInzienButton);
        tipsInzienBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tipsIntent = new Intent(requireActivity(), TipInzien.class);
                startActivity(tipsIntent);
            }
        });
    }

    public void initMaakMeldingButton(View view) {
        Button maakMeldingBtn = view.findViewById(R.id.maakMeldingButton);
        maakMeldingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), RisicoAnalyseActivity.class);
                startActivity(intent);
            }
        });
    }
}
