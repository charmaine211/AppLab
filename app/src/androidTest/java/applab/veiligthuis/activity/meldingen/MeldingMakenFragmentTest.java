package applab.veiligthuis.activity.meldingen;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;

import applab.veiligthuis.R;
import junit.framework.TestCase;

import org.hamcrest.Matchers;
import org.junit.Test;

public class MeldingMakenFragmentTest extends TestCase {
    FragmentScenario<MeldingMakenFragment> scenario;

    public void setUp(){
        scenario = FragmentScenario.launchInContainer(MeldingMakenFragment.class);
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

    }


}