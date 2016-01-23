package nl.mprog.bubbles.veganproductscanner;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class SearchFragment extends Fragment {

    MainActivity mainActivity;
    Context context;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toast.makeText(mainActivity.getApplicationContext(), "DO YOU GET HERE MATE", Toast.LENGTH_SHORT).show();

        EditText search_frg_input_et = (EditText) mainActivity.findViewById(R.id.search_frg_input_et);
        search_frg_input_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mainActivity.prefs.edit().putString("searchText", s.toString()).apply();
                }
            }
        });
        search_frg_input_et.setText(mainActivity.prefs.getString("searchText", ""));
        search_frg_input_et.setHint(mainActivity.prefs.getString("searchHint", mainActivity.getString(R.string.search_frg_input_et_hint)));

        mainActivity.memoryManagement.getProductsFromInput(mainActivity.prefs.getString("searchInput", "naturel"));
    }

    public void passViewContext(View v, Context c) {
        view = v;
        context = c;
    }

    public void createList(final ArrayList<String> productNames,
                           final ArrayList<Boolean> isVeganList) {
        ListView search_frg_result_lv = (ListView) view.findViewById(R.id.search_frg_result_lv);

        ArrayAdapter<String> adapter = new SearchListArrayAdapter(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, productNames, isVeganList);

        search_frg_result_lv.setAdapter(adapter);

        search_frg_result_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (!productNames.get(arg2).equals("No products found")) {
                    String name = productNames.get(arg2);
                    Boolean vegan = isVeganList.get(arg2);
                    mainActivity.productToResult(name, vegan);
                }
            }

        });
    }
}
