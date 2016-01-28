package nl.mprog.bubbles.veganproductscanner.data;

import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import nl.mprog.bubbles.veganproductscanner.MainActivity;
import nl.mprog.bubbles.veganproductscanner.R;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 *
 * LocalDatabase contains all methods concerning the local database.
 * TODO Make this and InfoFragment better, user doesn't need to know most of this stuff, ideally update database automatically?
 */

public class LocalDatabase {
    public MainActivity mainActivity;

    /** converts product names to lowercase, adds them to products for case-insensitive searching */
    public void addLowercaseNamesToLocalDatabase() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject product : objects) {
                        product.put("lowercaseName", product.getString("productName").toLowerCase());
                    }
                } else {
                    Log.d("Error", e.getMessage());
                }
            }
        });
    }

    /** clears all products in the local database if not empty already, provides user feedback */
    public void clearLocalDatabase() {
        int amount = amountOfProductsInLocalDatabase();
        if (amount > 0) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
            query.fromLocalDatastore();
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        for (ParseObject product : objects) {
                            product.unpinInBackground();
                        }
                        if (amountOfProductsInLocalDatabase() == 0) {
                            shortToast(R.string.toast_local_deleted);
                        } else {
                            shortToast(R.string.toast_try_again);
                            mainActivity.infoFragment.enableClearButton(true);
                        }
                    } else {
                        Log.d("Error", e.getMessage());
                        mainActivity.infoFragment.enableClearButton(true);
                    }
                }
            });
        } else if (amount < 0){
            shortToast(R.string.toast_try_again);
        } else {
            shortToast(R.string.toast_local_empty);
        }
    }

    public int amountOfProductsInLocalDatabase() {
        ParseQuery<ParseObject> check = ParseQuery.getQuery("Product");
        check.fromLocalDatastore();
        try {
            return check.count();
        } catch (ParseException e1) {
            Log.d("Error", e1.getMessage());
            return -1;
        }
    }

    /** attempts to retrieve a list of products that match the input, using Parse queries */
    //TODO Put more relevant products higher in the list.
    public void getProductsFromInput(String input) {
        if (!input.matches("\\s+") && !input.isEmpty()) {
            // combine queries of each word into one
            ParseQuery<ParseObject> mainQuery = combineQueries(input);
            mainQuery.fromLocalDatastore();
            mainQuery.addAscendingOrder("productName");
            mainQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> products, ParseException e) {
                    if (e == null) {
                        ArrayList<String> productNames = new ArrayList<>();
                        ArrayList<Boolean> isVeganList = new ArrayList<>();
                        // store products in lists
                        for (ParseObject product : products) {
                            productNames.add(product.getString("productName"));
                            isVeganList.add(product.getBoolean("isVegan"));
                        }
                        if (!productNames.isEmpty()) {
                            // go to ResultFragment if only one match, else fill ListView
                            if (productNames.size() == 1) {
                                mainActivity.productToResult(productNames.get(0), isVeganList.get(0));
                            } else {
                                mainActivity.searchFragment.createList(productNames, isVeganList);
                            }
                        } else {
                            mainActivity.searchFragment.noProductsFound();
                        }
                    } else {
                        Log.d("Error", e.getMessage());
                    }
                }
            });
        }
        else {
            mainActivity.searchFragment.noProductsFound();
        }
    }

    /** combines queries of each word in input into one query that finds all matching products */
    private ParseQuery<ParseObject> combineQueries(String input) {
        input = input.toLowerCase();
        String[] words = input.trim().split("\\s+");
        List<ParseQuery<ParseObject>> queries = new ArrayList<>();
        for (String word : words) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
            if (word.length() == 13 && word.matches("[0-9]+")) {
                query.whereEqualTo("productBarcode", word);
            } else {
                query.whereContains("lowercaseName", word);
            }
            queries.add(query);
        }
        return ParseQuery.or(queries);
    }

    /** attempts to retrieve a product with a certain barcode from the local database */
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
                } else if (e.getMessage().equals("no results found for query")) {
                    mainActivity.containerFragment.productNotFound(barcode);
                } else {
                    Log.d("Error", e.getMessage());
                }
            }
        });
    }

    private void shortToast(int id) {
        Toast.makeText(mainActivity.getApplicationContext(), id, Toast.LENGTH_SHORT).show();
    }
}
