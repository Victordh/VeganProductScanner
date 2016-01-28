package nl.mprog.bubbles.veganproductscanner;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 *
 * SearchListArrayAdapter is a custom Adapter that allows for individual background colours of
 * items in a list.
 */

public class SearchListArrayAdapter extends ArrayAdapter<String> {
    final List<Boolean> veganList;
    final List<String> nameList;

    /** saves the lists containing names and if the products are vegan or not*/
    public SearchListArrayAdapter(Context context, @LayoutRes int resource,
                                  @IdRes int textViewResourceId, List<String> names,
                                  List<Boolean> vegan) {
        super(context, resource, textViewResourceId, names);
        nameList = names;
        veganList = vegan;
    }

    /** changes background colour of list item depending on if a product is vegan or not */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (veganList.get(position)) {
            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.veganGreen));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.nonVeganRed));
        }
        return view;
    }
}