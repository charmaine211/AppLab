package applab.veiligthuis.common;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import applab.veiligthuis.activity.home.MainActivity;

public class BaseFragment extends Fragment implements ReturnToMainListener {

    @Override
    public void returnToMain() {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        getActivity().finish();
    }
}
