package applab.veiligthuis.activity.tip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import applab.veiligthuis.R;

public class TitleDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_title, container, false);
        final EditText titleEditText = view.findViewById(R.id.title_edit_text);

        view.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = titleEditText.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString(RequestKeyType.Titel.name(), description);

                // Resultaat teruggeven aan parent activity
                getParentFragmentManager().setFragmentResult(RequestKeyType.Titel.name(),bundle);

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
