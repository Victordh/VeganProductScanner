package nl.mprog.bubbles.veganproductscanner;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class SearchFragment {
    public void createList(View view, Context context) {
        ListView listView = (ListView) view.findViewById(R.id.search_list);

        String[] values = new String[] { "This is a list",
                "of different products",
                "that in some way",
                "match the search query.",
                "The list item will have",
                "either a red or green background,",
                "depending on whether the product",
                "is vegan or not.",
                "This text will be the names",
                "of the products."
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
    }
}
