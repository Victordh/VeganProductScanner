package nl.mprog.bubbles.veganproductscanner;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
                String name = productNames.get(arg2);
                Boolean vegan = isVeganList.get(arg2);
                mainActivity.productToResult(name, vegan);
            }

        });
    }
}
