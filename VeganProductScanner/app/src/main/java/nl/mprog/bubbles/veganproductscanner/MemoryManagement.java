package nl.mprog.bubbles.veganproductscanner;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class MemoryManagement {

    MainActivity mainActivity;

    public void setMainActivity(MainActivity main){
        mainActivity = main;
    }

    public void syncLocalDatabase() {
        if (internetConnection()) {
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
                            productCount();
                        }
                    } else {
                        Log.d("Error", e.getMessage());
                    }
                }
            });
        }
    }

    public void eraseLocalDatabase() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        query.fromLocalDatastore();

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    productCount();
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

    private boolean internetConnection() {
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
    public void getProductsFromInput(String input) {
        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        if (!input.matches("\\s+")) {
            String[] input_pieces = input.trim().split("\\s+");
            for (String input_piece : input_pieces) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
                if (input_piece.length() == 13 && input_piece.matches("[0-9]+")) {
                    query.whereEqualTo("productBarcode", input_piece);
                } else {
                    query.whereContains("productName", input_piece);
                }
                queries.add(query);
            }
            ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
            mainQuery.fromLocalDatastore();

            mainQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        ArrayList<String> productNames = new ArrayList<>();
                        ArrayList<Boolean> isVeganList = new ArrayList<>();
                        for (ParseObject product : objects) {
                            productNames.add(product.getString("productName"));
                            isVeganList.add(product.getBoolean("isVegan"));
                        }
                        if (!productNames.isEmpty()) {
                            if (productNames.size() == 1) {
                                mainActivity.productToResult(productNames.get(0), isVeganList.get(0));
                            } else {
                                mainActivity.searchFragment.createList(productNames, isVeganList);
                            }
                        } else {
                            productNames.add("No products found");
                            isVeganList.add(false);
                            mainActivity.searchFragment.createList(productNames, isVeganList);
                        }
                    } else {
                        Log.d("Error", e.getMessage());
                    }
                }
            });
        }
        else {
            ArrayList<String> productNames = new ArrayList<>();
            ArrayList<Boolean> isVeganList = new ArrayList<>();
            productNames.add("No products found");
            isVeganList.add(false);
            mainActivity.searchFragment.createList(productNames, isVeganList);
        }
    }

    public void getProductFromBarcode(final String barcode) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        query.whereEqualTo("productBarcode", barcode);
        query.fromLocalDatastore();

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject product, ParseException e) {
                if (e == null) {
                    if (product != null) {
                        mainActivity.resultFragment.productFound(product.getString("productName"),
                                product.getBoolean("isVegan"));
                    }
                } else if (e.getMessage().equals("no results found for query")){
                    mainActivity.containerFragment.productNotFound(barcode);
                }
                else {
                    Log.d("Error", e.getMessage());
                }
            }
        });
    }

    public void saveSubmission(String barcode, String name, String vegan, String comment) {
        ParseObject submission = new ParseObject("Submission");
        submission.put("productBarcode", barcode);
        submission.put("productName", name);
        submission.put("isVegan", vegan);
        submission.put("productComment", comment);
        submission.saveEventually();
    }

    private void productCount(){
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
