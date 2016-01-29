package nl.mprog.bubbles.veganproductscanner;

import android.app.Application;

import com.parse.Parse;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 *
 * ParseApplication makes sure the LocalDatastore is initialised exactly once.
 */

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "ygaBQHWyNlnm8uq3pFyCsSgCqLgRSsvuI0isYES7",
                "NMQe2x8dMk6lFEQjN0OrrlXQ3ng9uwdhhlQKpOll");
    }
}
