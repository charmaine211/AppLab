package applab.veiligthuis.activity.meldingen;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.Root;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;

import applab.veiligthuis.R;
import applab.veiligthuis.activity.home.MainActivity;

import junit.framework.TestCase;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MeldingMakenFragmentTest extends TestCase {
    FragmentScenario<MeldingMakenFragment> scenario;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule= new ActivityScenarioRule<>(MainActivity.class);

    public void setUp() {
        scenario = FragmentScenario.launchInContainer(MeldingMakenFragment.class);
    }

    @Before
    public void setUpIntent() {
        Intents.init();
    }

    @After
    public void cleanup() {
        Intents.release();
    }

    @Test
    public void testWeergave() {
        try{
            onView(withId(R.id.meldingmaken_editTextTextMultiLine)).check(matches(isDisplayed()));
            onView(withId(R.id.opslaan_button)).check(matches(isDisplayed()));

            // Controleer of de spinner niet leeg is
            onView(withId(R.id.plaatsnaam_spinner)).check(matches(isDisplayed()));

            onData(anything())
                    .inAdapterView(withId(R.id.plaatsnaam_spinner))
                    .atPosition(0) // Adjust the position if needed
                    .check(matches(isDisplayed()));
        } catch(Exception e){
            fail("Foutmelding: " + e.toString());
        }
    }

    @Test
    public void testMaakMelding() {
        try{
            onView(withId(R.id.meldingmaken_editTextTextMultiLine)).perform(typeText("Ik wil een melding maken van..."), closeSoftKeyboard());
            onView(withId(R.id.plaatsnaam_spinner)).perform(click());
            onData(allOf(is(instanceOf(String.class)), is("Groningen"))).perform(click());
            onView(withId(R.id.meldingmaken_editTextTextMultiLine)).perform(typeText(" "), closeSoftKeyboard());
            onView(allOf(withId(R.id.opslaan_button), withText(R.string.meldingOpslaan_button))).perform(click());
            Intents.intended(IntentMatchers.hasComponent(MainActivity.class.getName()));
        } catch (Exception e){
            fail("Foutmelding: " + e.toString());
        }
    }

    @Test
    public void testMaakMeldingLeeg() {
        try{
            onView(withId(R.id.opslaan_button)).check(matches(isDisplayed())).perform(click());
            //onView(withText("Zorg dat de beschrijving en de plaatsnaam ingevuld zijn."))
            //TODO: test toast
        } catch (Exception e){
            fail("Foutmelding: " + e.toString());
        }
    }

    private class ToastMatcher extends TypeSafeMatcher<Root> {

        private final List<Integer> windowTypes = Arrays.asList(
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
        );

        @Override
        public void describeTo(Description description){
            description.appendText("is toast");
        }

        @Override
        public boolean matchesSafely(Root root){
            int type = root.getWindowLayoutParams().get().type;
            if (windowTypes.contains(type)){
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken){
                    return true;
                }
            }
            return false;
        }
    }
}