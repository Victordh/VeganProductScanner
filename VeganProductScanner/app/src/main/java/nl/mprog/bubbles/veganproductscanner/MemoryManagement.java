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

import java.util.ArrayList;
import java.util.List;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */
public class MemoryManagement {

    public void syncLocalDatabase(final MainActivity mainActivity) {
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
                        if (!isLocalDatabaseEmpty()) {
                            productCount(mainActivity);
                        }
                    } else {
                        Log.d("Error", e.getMessage());
                    }
                }
            });
        }
    }

    public void eraseLocalDatabase(final MainActivity mainActivity) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        query.fromLocalDatastore();

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    productCount(mainActivity);
                    for (ParseObject product : objects) {
                        product.unpinInBackground();
                    }
                    if (isLocalDatabaseEmpty()) {
                        Toast.makeText(mainActivity.getApplicationContext(),
                                "Done! All data in local database deleted.",
                                Toast.LENGTH_SHORT).show();
                    } else {
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

    private boolean isLocalDatabaseEmpty() {
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

    // TODO Make input case-insensitive.
    // TODO Put more relevant products higher in the list.
    public void getProductsFromInput(String input, final SearchFragment searchFragment) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        if (input.length() == 13 && input.matches("[0-9]+")) {
                query.whereEqualTo("productBarcode", input);
        }
        else {
            query.whereContains("productName", input);
        }
        query.fromLocalDatastore();

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    ArrayList<String> productNames = new ArrayList<String>();
                    ArrayList<Boolean> isVeganList = new ArrayList<Boolean>();
                    for (ParseObject product : objects) {
                        productNames.add(product.getString("productName"));
                        isVeganList.add(product.getBoolean("isVegan"));
                    }
                    if (!productNames.isEmpty()) {
                        // TODO If only 1 result, switch to resultFragment to show result
                        searchFragment.createList(productNames, isVeganList);
                    }
                } else {
                    Log.d("Error", e.getMessage());
                }
            }
        });
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

    private void productCount(final MainActivity mainActivity){
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
