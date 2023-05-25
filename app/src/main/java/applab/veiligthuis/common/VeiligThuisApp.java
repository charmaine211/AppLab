package applab.veiligthuis.common;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

/**
 *  Aangepaste applicatieklasse voor de VeiligThuis Android-applicatie.
 *  Deze klasse breidt de basis {@link android.app.Application} klasse uit en
 *  maakt het mogelijk om de levenscyclus van de applicatie te beheren en actieve activiteiten bij te houden.
 */
public class VeiligThuisApp extends Application {

    private ArrayList<Activity> activeActivities = new ArrayList<>();

    /**
     * Voegt de opgegeven activiteit toe aan de lijst van actieve activiteiten.
     *
     * @param activity De activiteit die moet worden toegevoegd.
     */
    public void addActivity(Activity activity) {
        activeActivities.add(activity);
    }

    /**
     * Verwijdert de opgegeven activiteit uit de lijst van actieve activiteiten.
     *
     * @param activity De activiteit die moet worden verwijderd.
     */
    public void removeActivity(Activity activity) {
        activeActivities.remove(activity);
    }

    /**
     * Controleert of de opgegeven activiteit de laatste activiteit is in de lijst van actieve activiteiten.
     *
     * @param activity De activiteit die moet worden gecontroleerd.
     * @return {@code true} als de activiteit de laatste is, {@code false} anders.
     */
    public boolean isLastActivity(Activity activity) {
        return activeActivities.size() == 1 && activeActivities.contains(activity);
    }

}
