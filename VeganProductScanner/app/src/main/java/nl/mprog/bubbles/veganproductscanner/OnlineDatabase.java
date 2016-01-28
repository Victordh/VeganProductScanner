package nl.mprog.bubbles.veganproductscanner;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 *
 * OnlineDatabase contains all methods concerning the online databases Product and Submission.
 * TODO Make this and InfoFragment better, user doesn't need to know most of this stuff,
 * TODO Maybe update database automatically and remove clear button?
 * TODO Toast strings to strings.xml
 */

public class OnlineDatabase {
    public MainActivity mainActivity;

    private int localCount;
    private int onlineCount;

    /** retrieves all Product data from Parse and saves it locally, provides user feedback */
    public void syncLocalDatabase() {
        if (hasInternetConnection()) {
            if (!isDatabaseUpToDate()) {
                // TODO Get all data when larger than 100/1000 (query.each() ?)
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            for (ParseObject product : objects) {
                                product.pinInBackground();
                            }
                        } else {
                            Log.d("Error", e.getMessage());
                        }

                        if (isDatabaseUpToDate()) {
                            Toast.makeText(mainActivity.getApplicationContext(),
                                    mainActivity.getString(R.string.toast_online_database_updated) +
                                            onlineCount + mainActivity.getString(
                                            R.string.toast_online_database_products),
                                    Toast.LENGTH_SHORT).show();
                            mainActivity.localDatabase.addLowercaseNamesToLocalDatabase();
                        } else {
                            Toast.makeText(mainActivity.getApplicationContext(),
                                    R.string.error_message_try_again,
                                    Toast.LENGTH_SHORT).show();
                            mainActivity.infoFragment.enableSyncButton(true);
                        }
                    }
                });
            } else {
                Toast.makeText(mainActivity.getApplicationContext(),
                        mainActivity.getString(R.string.toast_online_database_uptodate) + localCount
                                + mainActivity.getString(R.string.toast_online_database_products),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            mainActivity.infoFragment.enableSyncButton(true);
        }
    }

    /** returns if device is connected to the internet (needs permissions INTERNET and
     * ACCESS_NETWORK_STATE) */
    private boolean hasInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if ((ni != null) && (ni.isConnected())) {
            return true;
        } else {
            Toast.makeText(mainActivity.getApplicationContext(),
                    "No internet connection detected. Could not sync database.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /** sends user submission to Parse database */
    public void saveSubmission(String barcode, String name, String vegan, String comment) {
        ParseObject submission = new ParseObject("Submission");
        submission.put("productBarcode", barcode);
        submission.put("productName", name);
        submission.put("isVegan", vegan);
        submission.put("productComment", comment);
        submission.saveEventually();
    }

    /** compares amount of items in both local and online database, returns if they're the same */
    private boolean isDatabaseUpToDate() {
        localCount = mainActivity.localDatabase.amountOfProductsInLocalDatabase();

        ParseQuery<ParseObject> online = ParseQuery.getQuery("Product");
        try {
            onlineCount = online.count();
        } catch (ParseException e1) {
            Log.d("Error", e1.getMessage());
        }

        return localCount == onlineCount;
    }
}
