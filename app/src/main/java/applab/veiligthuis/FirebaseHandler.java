package applab.veiligthuis;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class FirebaseHandler extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
