package applab.veiligthuis.activity.tip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;

import applab.veiligthuis.R;

public class DescriptionDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.dialog_description, container, false);
        final EditText descriptionEditText = view.findViewById(R.id.description_edit_text);

        view.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = descriptionEditText.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString(RequestKeyType.Beschrijving.name(), description);

                // Resultaat teruggeven aan parent activity
                getParentFragmentManager().setFragmentResult(RequestKeyType.Beschrijving.name(),bundle);

                dismiss();
            }
        });

        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }
}
