package applab.veiligthuis.data;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRepository {
    private static FirebaseRepository instance;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference meldingenRef;

    public FirebaseRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        meldingenRef = FirebaseDatabase.getInstance().getReference("Meldingen/");
        meldingenRef.keepSynced(true);
    }

    public static FirebaseRepository getInstance() {
        if (instance == null) {
            instance = new FirebaseRepository();
        }
        return instance;
    }

    public void signInAnonymously(OnCompleteListener<AuthResult> onCompleteListener) {
        firebaseAuth.signInAnonymously().addOnCompleteListener(onCompleteListener);
    }

    public String getCurrentUserId() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        }
        return null;
    }

}

