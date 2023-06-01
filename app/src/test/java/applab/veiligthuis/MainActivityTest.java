package applab.veiligthuis;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.os.Build;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.lang.reflect.Field;

import applab.veiligthuis.activity.home.FragmentNotLoggedIn;
import applab.veiligthuis.activity.home.MainActivity;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private MainActivityMock mainActivityMock;
    private FrameLayout notLoggedInContainer;
    private FrameLayout loggedInContainer;

    //Nullpointer failure op themes?????????
    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        FirebaseAuth auth = mock(FirebaseAuth.class);
        FirebaseUser user = mock(FirebaseUser.class);

        when(auth.getCurrentUser()).thenReturn(user);
        when(user.isAnonymous()).thenReturn(false);

        mainActivityMock = Robolectric.buildActivity(MainActivityMock.class).create().start().resume().get();
        mainActivityMock.setTheme(R.style.Theme_VeiligThuis_Test);

        mainActivityMock.setAuth(auth);

        notLoggedInContainer = new FrameLayout(mainActivityMock);
        loggedInContainer = new FrameLayout(mainActivityMock);

        mainActivityMock.setNotLoggedInContainer(notLoggedInContainer);
        mainActivityMock.setLoggedInContainer(loggedInContainer);
    }

    @Test
    public void testNotLoggedInState() {

        FragmentManager fragmentManager = mainActivityMock.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentNotLoggedIn fragmentNotLoggedIn = new FragmentNotLoggedIn();
        fragmentTransaction.add(notLoggedInContainer.getId(), fragmentNotLoggedIn);
        fragmentTransaction.commitNow();

        assertEquals(View.VISIBLE, notLoggedInContainer.getVisibility());
        assertEquals(View.GONE, loggedInContainer.getVisibility());
    }

    //Mock class om toegang the verkrijgen tot private properties
    private static class MainActivityMock extends MainActivity {

        public MainActivityMock() {
            super();
        }

        public void setAuth(FirebaseAuth auth) {
            mAuth = auth;
        }

        public void setNotLoggedInContainer(FrameLayout notLoggedInContainer) throws NoSuchFieldException, IllegalAccessException {
            Field field = MainActivity.class.getDeclaredField("notLoggedInContainer");
            field.setAccessible(true);
            field.set(this, notLoggedInContainer);
        }

        public void setLoggedInContainer(FrameLayout loggedInContainer) throws NoSuchFieldException, IllegalAccessException {
            Field field = MainActivity.class.getDeclaredField("loggedInContainer");
            field.setAccessible(true);
            field.set(this, loggedInContainer);
        }

        public FirebaseAuth getAuth() {
            return mAuth;
        }

        public FrameLayout getNotLoggedInContainer() {
            return notLoggedInContainer;
        }

        public FrameLayout getLoggedInContainer() {
            return loggedInContainer;
        }
    }

}
