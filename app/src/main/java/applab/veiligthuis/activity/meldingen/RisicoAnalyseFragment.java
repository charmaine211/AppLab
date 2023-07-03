package applab.veiligthuis.activity.meldingen;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import applab.veiligthuis.R;

public class RisicoAnalyseFragment extends Fragment {

    public RisicoAnalyseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_risico_analyse, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initJaButton();
        initTwijfelButton();
        initNeeButton();
        initSluitAppButton();

    }

    public void initJaButton() {
        Button jaButton =  getView().findViewById(R.id.ja_button);
        if (jaButton == null) {
            throw new RuntimeException("Knop met id 'ja_button' niet gevonden");
        }
        jaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RisicoAnalyseFragment.this.getContext());
                builder.setMessage("We raden u aan om 112 te bellen.")
                        .setCancelable(true)
                        .setPositiveButton("Bel 112", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:112"));
                                startActivity(callIntent);
                            }
                        })
                        .setNegativeButton("Annuleer", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void initTwijfelButton() {

        Button twijfelButton = getView().findViewById(R.id.twijfel_button);
        if (twijfelButton == null) {
            throw new RuntimeException("Knop met id 'twijfel_button' niet gevonden");
        }

        twijfelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RisicoAnalyseFragment.this.getContext());
                builder.setMessage("We raden u aan ons te bellen.")
                        .setCancelable(true)
                        .setPositiveButton("Bel Veilig Thuis", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:08002000"));
                                startActivity(callIntent);
                            }
                        })
                        .setNegativeButton("Annuleer", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void initNeeButton() {
        Button neeButton =  getView().findViewById(R.id.nee_button);
        if (neeButton == null) {
            throw new RuntimeException("Knop met id 'nee_button' niet gevonden");
        }
        neeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigeer naar de MeldingMakenActivity
                Intent meldingMakenIntent = new Intent(getActivity(), MeldingMakenActivity.class);
                startActivity(meldingMakenIntent);
            }
        });
    }

    private void initSluitAppButton() {
        View sluitButton = getView().findViewById(R.id.sluitApp);
        sluitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0); // Sluit de hele app
            }
        });
    }
}