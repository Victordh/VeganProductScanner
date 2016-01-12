package nl.mprog.bubbles.veganproductscanner;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */
public class MemoryManagement {

    public void syncDatabase(final MainActivity mainActivity) {
        if (internetConnection(mainActivity)) {
            // TODO Get all data when larger than 100/1000 (query.each() ?)
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        for (ParseObject product : objects) {
                            product.pinInBackground();
                        }
                        if (!localDatabaseEmpty()) {
                            objectCount(mainActivity);
                        }
                    } else {
                        Log.d("Error", e.getMessage());
                    }
                }
            });
        }
    }

    public void eraseDatabase(final MainActivity mainActivity) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        query.fromLocalDatastore();

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    objectCount(mainActivity);
                    for (ParseObject product : objects) {
                        product.unpinInBackground();
                    }
                    if (localDatabaseEmpty()) {
                        Toast.makeText(mainActivity.getApplicationContext(),
                                "Done! All data in local database deleted.",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(mainActivity.getApplicationContext(),
                                "Something went wrong, please try again.",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d("Error", e.getMessage());
                }
            }
        });
    }

    private boolean localDatabaseEmpty() {
        ParseQuery<ParseObject> check = ParseQuery.getQuery("Product");
        check.fromLocalDatastore();
        try {
            return check.count() == 0;
        } catch (ParseException e1) {
            Log.d("Error", e1.getMessage());
            return false;
        }
    }

    private boolean internetConnection(MainActivity mainActivity) {
        // checks for internet connection (needs permissions)
        ConnectivityManager cm =
                (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if ((ni != null) && (ni.isConnected())) {
            return true;
        }
        else {
            Toast.makeText(mainActivity.getApplicationContext(),
                    "No internet connection detected. Could not sync database.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void getProductFromBarcode(String barcode, final ResultFragment resultFragment) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        query.whereEqualTo("productBarcode", barcode);
        query.fromLocalDatastore();

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject product, ParseException e) {
                if (product != null) {
                    if (e == null) {
                        resultFragment.showProductName(product.getString("productName"));
                        resultFragment.showIsVegan(product.getBoolean("isVegan"));
                    } else {
                        Log.d("Error", e.getMessage());
                    }
                }
            }
        });
    }

    private void objectCount(final MainActivity mainActivity){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        query.fromLocalDatastore();

        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                Toast.makeText(mainActivity.getApplicationContext(),
                        "Data mutated: " + count,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
