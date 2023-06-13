package applab.veiligthuis.activity.meldingen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import applab.veiligthuis.R;

import androidx.fragment.app.testing.FragmentScenario;

import junit.framework.TestCase;

import org.junit.Test;

public class RisicoAnalyseFragmentTest extends TestCase {

    FragmentScenario<RisicoAnalyseFragment> scenario;

    public void setUp(){
        scenario = FragmentScenario.launchInContainer(RisicoAnalyseFragment.class);
    }

    @Test
    public void testKnopZichtbaar(){
        onView(withId(R.id.ja_button)).check(matches(isDisplayed()));
        onView(withId(R.id.twijfel_button)).check(matches(isDisplayed()));
        onView(withId(R.id.nee_button)).check(matches(isDisplayed()));
    }

    @Test
    public void testJaButton() {
        onView(withId(R.id.ja_button)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        // Voeg hier eventuele assertions toe om te controleren of de telefoonapp is gestart
    }

    @Test
    public void testNeeButton() {
        onView(withId(R.id.nee_button)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        // Voeg hier eventuele assertions toe om te controleren of de telefoonapp is gestart
    }

    @Test
    public void testTwijfelButton() {
        onView(withId(R.id.twijfel_button)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        // Voeg hier eventuele assertions toe om te controleren of de telefoonapp is gestart
    }


}