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


import android.content.Intent;

import applab.veiligthuis.R;
import applab.veiligthuis.activity.home.MainActivity;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RisicoAnalyseFragmentTest extends TestCase {

    FragmentScenario<RisicoAnalyseFragment> scenario;
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule;

    public void setUp(){
        scenario = FragmentScenario.launchInContainer(RisicoAnalyseFragment.class);
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
    public void testKnopZichtbaar(){
        onView(withId(R.id.ja_button)).check(matches(isDisplayed()));
        onView(withId(R.id.twijfel_button)).check(matches(isDisplayed()));
        onView(withId(R.id.nee_button)).check(matches(isDisplayed()));
    }

    @Test
    public void testJaButtonPos() {
        onView(withId(R.id.ja_button)).perform(click());
        onView(withText("We raden u aan om 112 te bellen.")).check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click()); // PositiveButton
        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_DIAL));
    }

    @Test
    public void testJaButtonNeg() {
        onView(withId(R.id.ja_button)).perform(click());
        onView(withText("We raden u aan om 112 te bellen.")).check(matches(isDisplayed()));
        onView(withId(android.R.id.button2)).perform(click()); // NegativeButton
        onView(withText("We raden u aan om 112 te bellen.")).check(doesNotExist());
    }

    @Test
    public void testTwijfelButtonPos() {
        onView(withId(R.id.twijfel_button)).perform(click());
        onView(withText("We raden u aan ons te bellen.")).check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click()); // PositiveButton
        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_DIAL));

    }

    @Test
    public void testTwijfelButtonNeg() {
        onView(withId(R.id.twijfel_button)).perform(click());
        onView(withText("We raden u aan ons te bellen.")).check(matches(isDisplayed()));
        onView(withId(android.R.id.button2)).perform(click()); // NegativeButton
        onView(withText("We raden u aan ons te bellen.")).check(doesNotExist());
    }

    @Test
    public void testNeeButton() {
        onView(withId(R.id.nee_button)).perform(click());
        Intents.intended(IntentMatchers.hasComponent(MeldingMakenActivity.class.getName()));
    }


}