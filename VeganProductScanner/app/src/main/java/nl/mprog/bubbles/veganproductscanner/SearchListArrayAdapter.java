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
 * Created by Bubbles on 21/01/2016.
 */

public class SearchListArrayAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> names;
    List<Boolean> vegan;

    public SearchListArrayAdapter(Context context, @LayoutRes int resource,
                                  @IdRes int textViewResourceId, List<String> names,
                                  List<Boolean> vegan) {
        super(context, resource, textViewResourceId, names);
        this.context = context;
        this.names = names;
        this.vegan = vegan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        Boolean green = vegan.get(position);
        if (green) {
            v.setBackgroundColor(ContextCompat.getColor(context, R.color.veganGreen));
        } else {
            v.setBackgroundColor(ContextCompat.getColor(context, R.color.nonVeganRed));
        }
        return v;
    }
}
