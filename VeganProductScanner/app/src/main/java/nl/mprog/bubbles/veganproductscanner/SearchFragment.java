package nl.mprog.bubbles.veganproductscanner;

import android.app.Fragment;
import android.content.Context;
import android.view.View;
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

    public void passViewContext(View v, Context c) {
        view = v;
        context = c;
    }

    public void createList(final ArrayList<String> productNames,
                           final ArrayList<Boolean> isVeganList) {
        ListView search_frg_result_lv = (ListView) view.findViewById(R.id.search_frg_result_lv);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, productNames);
        search_frg_result_lv.setAdapter(adapter);

        /*for (int i = 0; i < isVeganList.size(); i++) {
            if (isVeganList.get(i)) {
                // TODO fix so listView.getChildAt(i) isn't null
                listView.getChildAt(i).setBackgroundColor(mainActivity.
                        getResources().getColor(R.color.veganGreen));
            }
            else {
                listView.getChildAt(i).setBackgroundColor(mainActivity.
                        getResources().getColor(R.color.nonVeganRed));
            }
            final String name = productNames.get(i);
            final Boolean vegan = isVeganList.get(i);
            listView.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.productToResult(name, vegan);
                }
            });
        }*/

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
