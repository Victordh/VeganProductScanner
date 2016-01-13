package nl.mprog.bubbles.veganproductscanner;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class SearchFragment {
    View view;
    Context context;

    public void passViewContext(View v, Context c) {
        view = v;
        context = c;
    }

    public void createList(ArrayList<String> productNames) {
        ListView listView = (ListView) view.findViewById(R.id.search_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, productNames);
        listView.setAdapter(adapter);
    }
}
