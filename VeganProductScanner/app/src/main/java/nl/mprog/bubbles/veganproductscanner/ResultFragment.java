package nl.mprog.bubbles.veganproductscanner;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class ResultFragment {

    Activity mainActivity;
    TextView is_vegan, product_name;
    PageFragment fragment;

    public void onCreate(View view, PageFragment pageFragment) {
        fragment = pageFragment;
        createFloatingActionButton(view, pageFragment.getActivity());
    }

    public void createFloatingActionButton(View view, final Activity activity){
        mainActivity = activity;
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // uses Zxing to scan a barcode
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.initiateScan(IntentIntegrator.PRODUCT_CODE_TYPES);
            }
        });
    }

    public void showProductName(String productName) {
        product_name = (TextView) fragment.getActivity().findViewById(R.id.product_name);
        product_name.setText(productName);
    }

    public void showIsVegan(boolean isVegan) {
        is_vegan = (TextView) fragment.getActivity().findViewById(R.id.is_vegan);
        if (isVegan) {
            is_vegan.setText(R.string.vegan_true);
            is_vegan.setTextColor(fragment.getResources().getColor(R.color.veganGreen));
        }
        else {
            is_vegan.setText(R.string.vegan_false);
            is_vegan.setTextColor(fragment.getResources().getColor(R.color.nonVeganRed));
        }
    }
}
