package applab.veiligthuis.activity.meldingen;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import applab.veiligthuis.R;
import applab.veiligthuis.activity.home.MainActivity;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        onView(withId(R.id.meldingmaken_editTextTextMultiLine)).check(matches(isDisplayed()));
        onView(withId(R.id.opslaan_button)).check(matches(isDisplayed()));

        // Controleer of de spinner niet leeg is
        onView(withId(R.id.plaatsnaam_spinner)).check(matches(isDisplayed()));

        onData(anything())
                .inAdapterView(withId(R.id.plaatsnaam_spinner))
                .atPosition(0) // Adjust the position if needed
                .check(matches(isDisplayed()));

    }

    @Test
    // Deze test faalt: androidx.test.espresso.PerformException
    // Ik heb verschillende dingen geprobeerd maar krijg het toch niet aan de praat
    public void testMaakMelding() {
        onView(withId(R.id.plaatsnaam_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Groningen"))).perform(click());
        onView(withId(R.id.meldingmaken_editTextTextMultiLine)).perform(typeText("Ik wil een melding maken van..."), closeSoftKeyboard());
        IdlingPolicies.setMasterPolicyTimeout(240, TimeUnit.SECONDS);
        IdlingPolicies.setIdlingResourceTimeout(120, TimeUnit.SECONDS);
        closeSoftKeyboard();
        onView(withId(R.id.opslaan_button)).check(matches(isDisplayed()));
        onView(withId(R.id.opslaan_button))
                .perform(ViewActions.click());
        Intents.intended(IntentMatchers.hasComponent(MainActivity.class.getName()));
    }

    @Test
    // Deze test is ook niet goed geïmplementeerd in verband met het niet lukken van het testen van de toast.
    public void testMaakMeldingLeeg() {
        onView(withId(R.id.opslaan_button)).check(matches(isDisplayed())).perform(click());
        //onView(withText("Zorg dat de beschrijving en de plaatsnaam ingevuld zijn."))
        // Test Toast melding
    }
}