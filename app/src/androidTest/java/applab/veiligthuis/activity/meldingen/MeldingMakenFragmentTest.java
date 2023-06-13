package applab.veiligthuis.activity.meldingen;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import applab.veiligthuis.R;
import applab.veiligthuis.activity.home.MainActivity;

import junit.framework.TestCase;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MeldingMakenFragmentTest extends TestCase {
    FragmentScenario<MeldingMakenFragment> scenario;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule;

    public void setUp(){
        scenario = FragmentScenario.launchInContainer(MeldingMakenFragment.class);
        activityRule = new ActivityScenarioRule<>(MainActivity.class);
    }

    @Before
    public void setUpIntent(){
        Intents.init();
    }

    @After
    public void cleanup() {
        Intents.release();
    }

    @Test
    public void testWeergave(){

        onView(withId(R.id.meldingmaken_editTextTextMultiLine)).check(matches(isDisplayed()));
        onView(withId(R.id.opslaan_button)).check(matches(isDisplayed()));

        // Controleer of de spinner niet leeg is
        onView(withId(R.id.plaatsnaam_spinner))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(ViewMatchers.isAssignableFrom(RecyclerView.class))
                .check(matches(isDisplayed()));

        onView(ViewMatchers.isAssignableFrom(RecyclerView.class))
                .perform(RecyclerViewActions.scrollToPosition(0))
                .check(matches(withSpinnerText(Matchers.not(""))));
    }

    @Test
    public void testMaakMelding(){
        onView(withId(R.id.meldingmaken_editTextTextMultiLine)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.opslaan_button)).check(matches(isDisplayed())).perform(click());
        //TODO: Test of de toast wordt getoond
        onView(withId(R.id.meldingmaken_editTextTextMultiLine)).perform(typeText("Ik wil een melding maken van..."), closeSoftKeyboard());
        onView(withId(R.id.opslaan_button)).check(matches(isDisplayed())).perform(click());
        Intents.intended(IntentMatchers.hasComponent(MainActivity.class.getName()));
    }
}