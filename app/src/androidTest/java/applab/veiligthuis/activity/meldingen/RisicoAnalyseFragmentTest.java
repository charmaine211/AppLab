package applab.veiligthuis.activity.meldingen;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.app.Activity;
import android.content.Intent;

import applab.veiligthuis.R;
import applab.veiligthuis.activity.home.MainActivity;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RisicoAnalyseFragmentTest extends TestCase {

    FragmentScenario<RisicoAnalyseFragment> scenario;

    @Rule
    public ActivityScenarioRule<RisicoAnalyseActivity> activityRule = new ActivityScenarioRule<>(RisicoAnalyseActivity.class);

    public void setUp() {
        scenario = FragmentScenario.launchInContainer(RisicoAnalyseFragment.class);
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
    public void testKnopZichtbaar() {
        try{
            onView(withId(R.id.ja_button)).check(matches(isDisplayed()));
            onView(withId(R.id.twijfel_button)).check(matches(isDisplayed()));
            onView(withId(R.id.nee_button)).check(matches(isDisplayed()));
        } catch (Exception e){
            fail("Foutmelding: " + e.toString());
        }
    }

    @Test
    public void testJaButtonPos() {
        try{
            onView(withId(R.id.ja_button)).perform(click());
            onView(withText("We raden u aan om 112 te bellen.")).check(matches(isDisplayed()));
            onView(withId(android.R.id.button1)).perform(click()); // PositiveButton
            Intents.intended(IntentMatchers.hasAction(Intent.ACTION_DIAL));
        } catch (Exception e){
            fail("Foutmelding: " + e.toString());
        }
    }

    @Test
    public void testJaButtonNeg() {
        try{
            onView(withId(R.id.ja_button)).perform(click());
            onView(withText("We raden u aan om 112 te bellen.")).check(matches(isDisplayed()));
            onView(withId(android.R.id.button2)).perform(click()); // NegativeButton
            onView(withText("We raden u aan om 112 te bellen.")).check(doesNotExist());
        } catch (Exception e){
            fail("Foutmelding: " + e.toString());
        }
    }

    @Test
    public void testTwijfelButtonPos() {
        try{
            onView(withId(R.id.twijfel_button)).perform(click());
            onView(withText("We raden u aan ons te bellen.")).check(matches(isDisplayed()));
            onView(withId(android.R.id.button1)).perform(click()); // PositiveButton
            Intents.intended(IntentMatchers.hasAction(Intent.ACTION_DIAL));
        } catch (Exception e){
            fail("Foutmelding: " + e.toString());
        }
    }

    @Test
    public void testTwijfelButtonNeg() {
        try{
            onView(withId(R.id.twijfel_button)).perform(click());
            onView(withText("We raden u aan ons te bellen.")).check(matches(isDisplayed()));
            onView(withId(android.R.id.button2)).perform(click()); // NegativeButton
            onView(withText("We raden u aan ons te bellen.")).check(doesNotExist());
        } catch (Exception e){
            fail("Foutmelding: " + e.toString());
        }
    }

    @Test
    public void testNeeButton() {
        try{
            onView(withId(R.id.nee_button)).perform(click());
            Intents.intended(IntentMatchers.hasComponent(MeldingMakenActivity.class.getName()));
        } catch (Exception e){
            fail("Foutmelding: " + e.toString());
        }
    }
}