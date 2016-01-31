package nl.mprog.bubbles.veganproductscanner.data.scan;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nl.mprog.bubbles.veganproductscanner.MainActivity;
import nl.mprog.bubbles.veganproductscanner.R;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 *
 * ResultFragment contains views that show if a single product is vegan or not.
 * TODO Change design, add picture?
 * TODO Add 'Something wrong?' button for feedback (vegan status is wrong, typos, etc.)
 */

public class ResultFragment extends Fragment {
    private Boolean vegan;
    private String name;

    public MainActivity mainActivity;

    /** inflates xml */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.result_fragment, container, false);
    }

    /** loads last product from SharedPreferences */
    @Override
    public void onStart() {
        super.onStart();
        name = mainActivity.prefs.getString("productName", "");
        vegan = mainActivity.prefs.getBoolean("productIsVegan", false);
        // needs currentTab-check to prevent crash after turning off the screen in InfoFragment
        if (!name.equals("") && mainActivity.prefs.getInt("currentTab", 2) != 2) {
            productFound(name, vegan);
        }
    }

    /** updates product variables */
    public void setProduct(String name, boolean vegan) {
        this.name = name;
        this.vegan = vegan;
        mainActivity.prefs.edit().putString("productName", this.name).apply();
        mainActivity.prefs.edit().putBoolean("productIsVegan", this.vegan).apply();
    }

    /** updates views and background according to product variables */
    public void productFound(String name, boolean vegan) {
        TextView tvVegan = (TextView) mainActivity.findViewById(R.id.result_fragment_is_vegan_tv);
        TextView tvName = (TextView) mainActivity.findViewById(R.id.result_fragment_name_tv);
        tvName.setText(name);
        if (vegan) {
            tvVegan.setText(R.string.result_fragment_tv_vegan_true);
            if (this.getView() != null) {
                this.getView().setBackgroundColor(ContextCompat.getColor(
                        getActivity().getApplicationContext(), R.color.veganGreen));
            }
        } else {
            tvVegan.setText(R.string.result_fragment_tv_vegan_false);
            if (this.getView() != null) {
                this.getView().setBackgroundColor(ContextCompat.getColor(
                        getActivity().getApplicationContext(), R.color.nonVeganRed));
            }
        }
        mainActivity.findViewById(R.id.container_fragment_fab).bringToFront();
    }
}