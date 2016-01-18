package nl.mprog.bubbles.veganproductscanner;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class ResultFragment {

    MainActivity mainActivity;
    PageFragment fragment;
    String barcode;

    public void onCreate(View view, PageFragment pageFragment) {
        fragment = pageFragment;
        createFloatingActionButton(view);
    }

    private void createFloatingActionButton(View view){
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // uses Zxing to scan a barcode
                IntentIntegrator integrator = new IntentIntegrator(fragment.getActivity());
                integrator.initiateScan(IntentIntegrator.PRODUCT_CODE_TYPES);
                //productNotFound("123456789999");
            }
        });
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
                    fragment.getContext(), R.color.veganGreen));
        }
        else {
            result_frg_is_vegan_tv.setText(R.string.result_frg_is_vegan_tv_false);
            result_frg_is_vegan_tv.setTextColor(ContextCompat.getColor(
                    fragment.getContext(), R.color.nonVeganRed));
        }
    }

    public void productNotFound(String product_barcode) {
        //Go to AddFragment, send barcode too
        barcode = product_barcode;
        mainActivity.setFragment(3);
    }
}
