package applab.veiligthuis.data;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Repository {
    private static Repository instance;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference meldingenRef;

    public Repository() {
        firebaseAuth = FirebaseAuth.getInstance();
        meldingenRef = FirebaseDatabase.getInstance().getReference("Meldingen/");
        meldingenRef.keepSynced(true);
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
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

    public boolean userLoggedIn(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        return getCurrentUserId() != null && !user.isAnonymous();
    }
}

