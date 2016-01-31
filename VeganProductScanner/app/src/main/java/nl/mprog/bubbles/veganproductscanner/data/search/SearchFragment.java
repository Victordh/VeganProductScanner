package nl.mprog.bubbles.veganproductscanner.data.search;

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

import nl.mprog.bubbles.veganproductscanner.MainActivity;
import nl.mprog.bubbles.veganproductscanner.R;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 *
 * SearchFragment contains the UI elements that allow for manually searching products.
 * TODO Fix auto-focus EditText and removing keyboard when moving to another view
 */

public class SearchFragment extends Fragment {
    private Context context;
    private EditText etInput;
    private View view;

    public MainActivity mainActivity;

    /** inflates xml, passes view so ListView can use it later, passes context so
     *  SearchListArrayAdapter can use it later */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);
        context = container.getContext();
        return view;
    }

    /** adds TextWatcher to input EditText */
    @Override
    public void onStart() {
        super.onStart();
        addTextWatcherEtInput();
    }

    /** initialises etInput, loads text and hint in etInput and items in ListView from
     *  SharedPreferences */
    public void loadFromPrefs() {
        etInput = (EditText) mainActivity.findViewById(R.id.search_fragment_input_et);
        etInput.setText(mainActivity.prefs.getString("searchText", ""));
        etInput.setHint(mainActivity.prefs.getString("searchHint",
                mainActivity.getString(R.string.search_fragment_et_input_hint)));
        mainActivity.localDatabase.getProductsFromInput(mainActivity.prefs.getString(
                "searchInput", ""));
    }

    /** adds TextWatcher that saves the changed text in the input EditText to SharedPreferences */
    private void addTextWatcherEtInput() {
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
    }

    /** updates text and hint in etInput, saves text, hint and input in SharedPreferences, starts
     * searching for products via localDatabase */
    public void handleSearch() {
        String input = etInput.getText().toString();
        String hint = mainActivity.getString(R.string.search_fragment_et_input_searched_hint);
        // prevents etInput from taking up the entire screen if someone only entered whitespace
        if (input.matches("\\s+")) {
            hint = hint + "";
        } else {
            hint = hint + input;
        }
        etInput.setText("");
        etInput.setHint(hint);

        mainActivity.prefs.edit().putString("searchHint", hint).apply();
        mainActivity.prefs.edit().putString("searchInput", input).apply();
        mainActivity.prefs.edit().putString("searchText", "").apply();
        mainActivity.localDatabase.getProductsFromInput(input);
    }

    /** creates ListView with items based on products found and passed via nameList and veganList,
     * makes each item clickable, if any products found, redirecting to ResultFragment */
    public void createList(final ArrayList<String> nameList, final ArrayList<Boolean> veganList) {
        ListView lvResult = (ListView) view.findViewById(R.id.search_fragment_result_lv);
        ArrayAdapter<String> adapter = new SearchListArrayAdapter(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, nameList, veganList);
        lvResult.setAdapter(adapter);
        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO Save product to SharedPreferences onClick (shows previous product if going to Scanner and going back)
                //Hmm, problem is probably not this, since the product does get saved, maybe force reload of
                //ResultFragment when going back from Scanner?
                if (!nameList.get(position).equals(context.getString(
                        R.string.search_fragment_no_products))) {
                    mainActivity.productToResult(nameList.get(position), veganList.get(position));
                }
            }
        });
    }

    /** provides feedback when no products could be found */
    //TODO Change this to TextView for simplicity? (also delete the check in createList onClickListener if so)
    public void noProductsFound() {
        ArrayList<String> productNames = new ArrayList<>();
        ArrayList<Boolean> isVeganList = new ArrayList<>();
        productNames.add(context.getString(R.string.search_fragment_no_products));
        isVeganList.add(false);
        mainActivity.searchFragment.createList(productNames, isVeganList);
    }
}