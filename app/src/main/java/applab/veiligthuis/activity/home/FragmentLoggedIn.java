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
import applab.veiligthuis.activity.meldingen.MeldingMakenActivity;
import applab.veiligthuis.activity.meldingen.RisicoAnalyseActivity;
import applab.veiligthuis.activity.tip.TipBeheren;
import applab.veiligthuis.activity.tip.TipInzien;

public class FragmentLoggedIn extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logged_in, container, false);
        initMeldingenButton(view);
        initTipsInzienButton(view);
        initTipsBeherenButton(view);
        initMaakMeldingButton(view);
        return view;
    }

    public void initMeldingenButton(View view) {
        Button meldingenBtn = view.findViewById(R.id.meldingenButton);
        meldingenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent meldingenIntent = new Intent(requireActivity(), MeldingLijstActivity.class);
                startActivity(meldingenIntent);
            }
        });
    }

    public void initTipsBeherenButton(View view) {
        Button tipsBeherenBtn = view.findViewById(R.id.tipsBeherenButton);
        tipsBeherenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tipsIntent = new Intent(requireActivity(), TipBeheren.class);
                startActivity(tipsIntent);
            }
        });
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
                Intent intent = new Intent(requireActivity(), MeldingMakenActivity.class);
                startActivity(intent);
            }
        });
    }
}
