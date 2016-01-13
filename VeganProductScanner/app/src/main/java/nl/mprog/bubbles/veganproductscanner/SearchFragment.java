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

    public void createList(ArrayList<String> productNames, ArrayList<Boolean> isVeganList) {
        ListView listView = (ListView) view.findViewById(R.id.search_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, productNames);
        listView.setAdapter(adapter);

        // TODO Fix that each item has the correct colour (depending on isVegan)
        /*for (int i = 0; i < isVeganList.size(); i++) {
            if (isVeganList.get(i)) {
                listView.getChildAt(i).setBackgroundColor(
                        this.context.getResources().getColor(R.color.veganGreen));
            }
            else {
                listView.getChildAt(i).setBackgroundColor(
                        this.context.getResources().getColor(R.color.nonVeganRed));
            }
        }*/
    }
}
