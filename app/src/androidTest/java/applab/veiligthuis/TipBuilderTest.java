package applab.veiligthuis;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import applab.veiligthuis.activity.tip.TipBuilder;

public class TipBuilderTest {

    @Rule
    public ActivityScenarioRule<TipBuilder> activityRule = new ActivityScenarioRule<>(TipBuilder.class);

    @Test
    public void toonErrorBijLeeglatenVelden() {
        onView(withId(R.id.editTextTitel)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.btn_tipSubmit)).perform(click());
        onView(withId(R.id.editTextTitel)).check(matches(hasErrorText("Titel mag niet leeg zijn")));
    }

    @Test
    public void tipPropertiesTonenInCorresponderendeVelden() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), TipBuilder.class);
        intent.putExtra("editing", true);
        intent.putExtra("tipJson", getTipJson());
        ActivityScenario<TipBuilder> scenario = ActivityScenario.launch(intent);

        onView(withId(R.id.editTextTitel)).check(matches(withText("Test titel")));
        onView(withId(R.id.editTextBeschrijving)).check(matches(withText("Test beschrijving")));
        onView(withId(R.id.spinner_categorie)).check(matches(withSpinnerText("Fysiek geweld")));

        scenario.close();
    }

    private String getTipJson() {
        return "{ \"titel\": \"Test titel\", \"beschrijving\": \"Test beschrijving\", \"categorie\": \"FysiekGeweld\" }";
    }

}
