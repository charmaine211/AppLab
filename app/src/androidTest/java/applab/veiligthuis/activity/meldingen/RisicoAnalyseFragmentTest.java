package applab.veiligthuis.activity.meldingen;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;



import android.content.Intent;
import applab.veiligthuis.R;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.matcher.ViewMatchers;
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
        onView(withId(R.id.ja_button)).check(matches(isDisplayed()));
        onView(withId(R.id.twijfel_button)).check(matches(isDisplayed()));
        onView(withId(R.id.nee_button)).check(matches(isDisplayed()));

    }

    @Test
    public void testTextViewZichtbaar() {
        onView(withId(R.id.risicoAnalyse_textView))
                .check(matches(isDisplayed()))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(isCompletelyDisplayed()));
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