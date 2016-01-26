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
    public MainActivity mainActivity;

    // retrieves all data from Parse and saves it locally
    public void syncLocalDatabase() {
        if (hasInternetConnection()) {
            // TODO Get all data when larger than 100/1000 (query.each() ?)
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        for (ParseObject product : objects) {
                            product.pinInBackground();
                        }
                        productCount();
                    } else {
                        Log.d("Error", e.getMessage());
                    }
                }
            });
            addSearchNamesToLocalDatabase();
        }
    }

    // converts the product names to lowercase for case-insensitive searching
    private void addSearchNamesToLocalDatabase() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject product : objects) {
                        product.put("searchName", product.getString("productName").toLowerCase());
                    }
                } else {
                    Log.d("Error", e.getMessage());
                }
            }
        });
    }

    // deletes the local database if not empty already
    public void eraseLocalDatabase() {
        if (!isLocalDatabaseEmpty()) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
            query.fromLocalDatastore();
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        for (ParseObject product : objects) {
                            product.unpinInBackground();
                        }
                        if (isLocalDatabaseEmpty()) {
                            Toast.makeText(mainActivity.getApplicationContext(),
                                    "All data in local database deleted.",
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
        } else {
            Toast.makeText(mainActivity.getApplicationContext(), "Local database is already empty.",
                    Toast.LENGTH_SHORT).show();
        }
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

    // returns if device is connected to the internet (needs permissions INTERNET and
    // ACCESS_NETWORK_STATE)
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

    // attempts to retrieve a list of products that match the input
    //TODO Put more relevant products higher in the list.
    public void getProductsFromInput(String input) {
        if (!input.matches("\\s+")) {
            ParseQuery<ParseObject> mainQuery = combineQueries(input);
            mainQuery.fromLocalDatastore();
            mainQuery.addAscendingOrder("productName");
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
                            noProductsFound();
                        }
                    } else {
                        Log.d("Error", e.getMessage());
                    }
                }
            });
        }
        else {
            noProductsFound();
        }
    }

    // provides feedback when no products could be found
    //TODO Change this to TextView for simplicity? (also delete the check in SearchFragment if so)
    private void noProductsFound() {
        ArrayList<String> productNames = new ArrayList<>();
        ArrayList<Boolean> isVeganList = new ArrayList<>();
        productNames.add(
                "Sorry, couldn't find any matching products! Make sure the database is synced.");
        isVeganList.add(false);
        mainActivity.searchFragment.createList(productNames, isVeganList);
    }

    // combines queries of each word in input into one big query that finds all matching products
    private ParseQuery<ParseObject> combineQueries(String input) {
        input = input.toLowerCase();
        String[] words = input.trim().split("\\s+");
        List<ParseQuery<ParseObject>> queries = new ArrayList<>();
        for (String word : words) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
            if (word.length() == 13 && word.matches("[0-9]+")) {
                query.whereEqualTo("productBarcode", word);
            } else {
                query.whereContains("searchName", word);
            }
            queries.add(query);
        }
        return ParseQuery.or(queries);
    }

    // attempts to retrieve a product with a certain barcode from the local database
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
                } else {
                    Log.d("Error", e.getMessage());
                }
            }
        });
    }

    // sends user submission to Parse database
    public void saveSubmission(String barcode, String name, String vegan, String comment) {
        ParseObject submission = new ParseObject("Submission");
        submission.put("productBarcode", barcode);
        submission.put("productName", name);
        submission.put("isVegan", vegan);
        submission.put("productComment", comment);
        submission.saveEventually();
    }

    // shows Toast with amount of objects in local database
    //TODO Probably not interesting for user, maybe only use as check for something?
    private void productCount(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        query.fromLocalDatastore();
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                Toast.makeText(mainActivity.getApplicationContext(),
                        "Products in local database: " + count,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
