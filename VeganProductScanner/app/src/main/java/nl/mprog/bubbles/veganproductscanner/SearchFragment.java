package nl.mprog.bubbles.veganproductscanner;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class SearchFragment extends Fragment {

    Context context;
    MainActivity mainActivity;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);
        context = container.getContext();
        return view;
    }

    /*@Override
    public void onStart() {
        super.onStart();
        loadFromPrefs();
    }*/

    public void loadFromPrefs() {
        EditText etInput = (EditText) mainActivity.findViewById(R.id.search_frg_input_et);
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mainActivity.prefs.edit().putString("searchText", s.toString()).apply();
                }
            }
        });
        etInput.setText(mainActivity.prefs.getString("searchText", ""));
        etInput.setHint(mainActivity.prefs.getString("searchHint",
                mainActivity.getString(R.string.search_fragment_et_input_hint)));

        mainActivity.localDatabase.getProductsFromInput(mainActivity.prefs.getString(
                "searchInput", "naturel"));
    }

    public void createList(final ArrayList<String> productNames,
                           final ArrayList<Boolean> isVeganList) {
        ListView lvResult = (ListView) view.findViewById(R.id.search_frg_result_lv);
        ArrayAdapter<String> adapter = new SearchListArrayAdapter(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, productNames, isVeganList);
        lvResult.setAdapter(adapter);

        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (!productNames.get(arg2).equals("Sorry, couldn't find any matching products! Make sure the database is synced.")) {
                    String name = productNames.get(arg2);
                    Boolean vegan = isVeganList.get(arg2);
                    mainActivity.productToResult(name, vegan);
                }
            }
        });
    }

    public void handleSearch() {
        EditText etInput = (EditText) mainActivity.findViewById(R.id.search_frg_input_et);
        String input = etInput.getText().toString();
        String hint = mainActivity.getString(R.string.search_fragment_et_input_searched_hint) + input;
        etInput.setText("");
        etInput.setHint(hint);

        mainActivity.prefs.edit().putString("searchHint", hint).apply();
        mainActivity.prefs.edit().putString("searchInput", input).apply();
        mainActivity.prefs.edit().putString("searchText", "").apply();
        mainActivity.localDatabase.getProductsFromInput(input);
    }

    /** provides feedback when no products could be found */
    //TODO Change this to TextView for simplicity? (also delete the check in SearchFragment if so)
    public void noProductsFound() {
        ArrayList<String> productNames = new ArrayList<>();
        ArrayList<Boolean> isVeganList = new ArrayList<>();
        productNames.add(
                "Sorry, couldn't find any matching products! Make sure the database is synced.");
        isVeganList.add(false);
        mainActivity.searchFragment.createList(productNames, isVeganList);
    }
}
