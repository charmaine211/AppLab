package applab.veiligthuis.common;

import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import applab.veiligthuis.R;
import applab.veiligthuis.activity.home.MainActivity;

public class BaseFragment extends Fragment implements ReturnToMainListener {

    protected void initSluitAppButton() {
        androidx.appcompat.widget.AppCompatImageView sluitButton = getView().findViewById(R.id.sluitApp);
        sluitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finishAffinity();
            }
        });
    }

    @Override
    public void returnToMain() {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        getActivity().finish();
    }
}
