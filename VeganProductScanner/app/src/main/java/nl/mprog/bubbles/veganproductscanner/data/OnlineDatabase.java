package nl.mprog.bubbles.veganproductscanner.data;

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

import nl.mprog.bubbles.veganproductscanner.MainActivity;
import nl.mprog.bubbles.veganproductscanner.R;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 *
 * OnlineDatabase contains all methods concerning the online databases Product and Submission.
 */

public class OnlineDatabase {
    public MainActivity mainActivity;

    private int localCount;
    private int onlineCount;

    /** retrieves all Product data from Parse and saves it locally, provides user feedback */
    public void syncLocalDatabase() {
        if (hasInternetConnection()) {
            if (!isDatabaseUpToDate()) {
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
                            longToast(mainActivity.getString(R.string.toast_online_updated) +
                                    onlineCount +
                                    mainActivity.getString(R.string.toast_online_products));
                            mainActivity.localDatabase.addLowercaseNamesToLocalDatabase();
                        } else {
                            shortToast(R.string.toast_try_again);
                            mainActivity.infoFragment.enableSyncButton(true);
                        }
                    }
                });
            } else {
                longToast(mainActivity.getString(R.string.toast_online_uptodate) + localCount +
                        mainActivity.getString(R.string.toast_online_products));
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
            shortToast(R.string.toast_online_no_internet);
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

    private void shortToast(int id) {
        Toast.makeText(mainActivity.getApplicationContext(), id, Toast.LENGTH_SHORT).show();
    }

    private void longToast(String message) {
        Toast.makeText(mainActivity.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}