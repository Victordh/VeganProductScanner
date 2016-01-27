package nl.mprog.bubbles.veganproductscanner;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 *
 * ResultFragment contains views that show if a single product is vegan or not.
 */

public class ResultFragment extends Fragment {
    //TODO Change design, add picture?

    private Boolean is_vegan;
    private String product_name;

    public MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.result_fragment, container, false);
    }

    // loads
    @Override
    public void onStart() {
        super.onStart();
        product_name = mainActivity.prefs.getString("productName", "");
        is_vegan = mainActivity.prefs.getBoolean("productIsVegan", false);
        // needs currentTab check to prevent crash after turning off the screen in InfoFragment
        if (!product_name.equals("") && mainActivity.prefs.getInt("currentTab", 2) != 2) {
            productFound(product_name, is_vegan);
            mainActivity.findViewById(R.id.fab).bringToFront();
        }
    }

    public void setProduct(String name, boolean vegan) {
        product_name = name;
        is_vegan = vegan;
        mainActivity.prefs.edit().putString("productName", product_name).apply();
        mainActivity.prefs.edit().putBoolean("productIsVegan", is_vegan).apply();
    }

    public void productFound(String productName, boolean isVegan) {
        TextView result_frg_is_vegan_tv =
                (TextView) mainActivity.findViewById(R.id.result_frg_is_vegan_tv);
        TextView result_frg_name_tv =
                (TextView) mainActivity.findViewById(R.id.result_frg_name_tv);

        result_frg_name_tv.setText(productName);

        if (isVegan) {
            result_frg_is_vegan_tv.setText(R.string.result_fragment_tv_vegan_true);
            if (this.getView() != null) {
                this.getView().setBackgroundColor(ContextCompat.getColor(
                        getActivity().getApplicationContext(), R.color.veganGreen));
            }
        }
        else {
            result_frg_is_vegan_tv.setText(R.string.result_fragment_tv_vegan_false);
            if (this.getView() != null) {
                this.getView().setBackgroundColor(ContextCompat.getColor(
                        getActivity().getApplicationContext(), R.color.nonVeganRed));
            }
        }
        mainActivity.findViewById(R.id.fab).bringToFront();
    }
}
