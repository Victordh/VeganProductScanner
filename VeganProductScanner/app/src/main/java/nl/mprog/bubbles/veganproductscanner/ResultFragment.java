package nl.mprog.bubbles.veganproductscanner;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class ResultFragment extends Fragment {

    //TODO Change V/X to background colour and add picture?

    Boolean from_search = false, is_vegan;
    Context context;
    MainActivity mainActivity;
    String product_name;

    public void onCreate(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.result_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (from_search) {
            productFound(product_name, is_vegan);
        }
    }

    public void setProduct(String name, boolean vegan) {
        product_name = name;
        is_vegan = vegan;
    }

    public void productFound(String productName, boolean isVegan) {
        TextView result_frg_is_vegan_tv =
                (TextView) mainActivity.findViewById(R.id.result_frg_is_vegan_tv);
        TextView result_frg_name_tv =
                (TextView) mainActivity.findViewById(R.id.result_frg_name_tv);

        result_frg_name_tv.setText(productName);

        if (isVegan) {
            result_frg_is_vegan_tv.setText(R.string.result_frg_is_vegan_tv_true);
            result_frg_is_vegan_tv.setTextColor(ContextCompat.getColor(
                    context, R.color.veganGreen));
        }
        else {
            result_frg_is_vegan_tv.setText(R.string.result_frg_is_vegan_tv_false);
            result_frg_is_vegan_tv.setTextColor(ContextCompat.getColor(
                    context, R.color.nonVeganRed));
        }
    }
}
