package nl.mprog.bubbles.veganproductscanner;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class ResultFragment {

    Activity mainActivity;
    Button result_frg_add_btn, result_frg_send_data_btn;
    Context context;
    EditText result_frg_enter_comment_e, result_frg_enter_name;
    PageFragment fragment;
    RadioGroup result_frg_is_vegan_rg;
    String product_barcode;
    TextView result_frg_add_tv, result_frg_is_vegan_tv, result_frg_barcode_tv, result_frg_name_tv,
            result_frg_thanks_tv;

    public void onCreate(View view, PageFragment pageFragment) {
        context = pageFragment.getContext();
        fragment = pageFragment;
        mainActivity = pageFragment.getActivity();
        createFloatingActionButton(view, mainActivity);
    }

    public void initialise() {
        result_frg_add_btn = (Button) mainActivity.findViewById(R.id.result_frg_add_btn);
        result_frg_add_tv = (TextView) mainActivity.findViewById(R.id.result_frg_add_tv);
        result_frg_enter_comment_e =
                (EditText) mainActivity.findViewById(R.id.result_frg_enter_comment_et);
        result_frg_enter_name = (EditText) mainActivity.findViewById(R.id.result_frg_enter_name);
        result_frg_is_vegan_tv = (TextView) mainActivity.findViewById(R.id.result_frg_is_vegan_tv);
        result_frg_is_vegan_rg =
                (RadioGroup) mainActivity.findViewById(R.id.result_frg_is_vegan_rg);
        result_frg_barcode_tv = (TextView) mainActivity.findViewById(R.id.result_frg_barcode_tv);
        result_frg_name_tv = (TextView) mainActivity.findViewById(R.id.result_frg_name_tv);
        result_frg_send_data_btn =
                (Button) mainActivity.findViewById(R.id.result_frg_send_data_btn);
        result_frg_thanks_tv = (TextView) mainActivity.findViewById(R.id.result_frg_thanks_tv);
    }

    private void createFloatingActionButton(View view, final Activity activity){
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

    public void productFound(String productName, boolean isVegan) {
        showEnterProductElements(false);
        showProductFoundElements(true);
        showProductNotFoundElements(false);
        showThanks(false);

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

    public void productNotFound(String barcode) {
        showEnterProductElements(false);
        showProductFoundElements(false);
        showProductNotFoundElements(true);
        showThanks(false);

        product_barcode = barcode;
    }

    public void addProduct() {
        showEnterProductElements(true);
        showProductFoundElements(false);
        showProductNotFoundElements(false);
        showThanks(false);

        String barcode = product_barcode;

        result_frg_barcode_tv.setText(barcode);
    }

    public void showProductFoundElements(boolean show) {
        if (show) {
            result_frg_name_tv.setVisibility(View.VISIBLE);
            result_frg_is_vegan_tv.setVisibility(View.VISIBLE);
        }
        else {
            result_frg_name_tv.setVisibility(View.INVISIBLE);
            result_frg_is_vegan_tv.setVisibility(View.INVISIBLE);
        }

    }

    public void showProductNotFoundElements(boolean show) {
        if (show) {
            result_frg_add_btn.setVisibility(View.VISIBLE);
            result_frg_add_tv.setVisibility(View.VISIBLE);
        }
        else {
            result_frg_add_btn.setVisibility(View.INVISIBLE);
            result_frg_add_tv.setVisibility(View.INVISIBLE);
        }
    }

    public void showEnterProductElements(boolean show) {
        if (show) {
            result_frg_enter_comment_e.setVisibility(View.VISIBLE);
            result_frg_enter_name.setVisibility(View.VISIBLE);
            result_frg_is_vegan_rg.setVisibility(View.VISIBLE);
            result_frg_barcode_tv.setVisibility(View.VISIBLE);
            result_frg_send_data_btn.setVisibility(View.VISIBLE);
        }
        else {
            result_frg_enter_comment_e.setVisibility(View.INVISIBLE);
            result_frg_enter_name.setVisibility(View.INVISIBLE);
            result_frg_is_vegan_rg.setVisibility(View.INVISIBLE);
            result_frg_barcode_tv.setVisibility(View.INVISIBLE);
            result_frg_send_data_btn.setVisibility(View.INVISIBLE);
        }
    }

    public void showThanks(boolean show) {
        if (show) {
            result_frg_thanks_tv.setVisibility(View.VISIBLE);
        }
        else {
            result_frg_thanks_tv.setVisibility(View.INVISIBLE);
        }
    }

    public void sendSubmission(MemoryManagement memoryManagement) {
        String barcode = result_frg_barcode_tv.getText().toString();
        String name = result_frg_enter_name.getText().toString();
        String vegan;
        if (Integer.toString(result_frg_is_vegan_rg.getCheckedRadioButtonId()).equals(
                "2131493003")) {
            vegan = "true";
        }
        else if (Integer.toString(result_frg_is_vegan_rg.getCheckedRadioButtonId()).equals(
                "2131493004")) {
            vegan = "false";
        }
        else {
            vegan = "unknown";
        }
        String comment = result_frg_enter_comment_e.getText().toString();

        memoryManagement.saveSubmission(barcode, name, vegan, comment);

        showEnterProductElements(false);
        showProductFoundElements(false);
        showProductNotFoundElements(false);
        showThanks(true);
    }
}
