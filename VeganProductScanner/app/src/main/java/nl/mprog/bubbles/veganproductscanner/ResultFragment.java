package nl.mprog.bubbles.veganproductscanner;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class ResultFragment {

    Activity mainActivity;
    Button add_product_button, send_to_database;
    EditText enter_comment, enter_product_name;
    PageFragment fragment;
    RadioGroup is_vegan_radio_buttons;
    String pass_barcode;
    TextView add_product_text, is_vegan, product_barcode, product_name, thanks;

    public void onCreate(View view, PageFragment pageFragment) {
        fragment = pageFragment;
        mainActivity = pageFragment.getActivity();
        createFloatingActionButton(view, mainActivity);
    }

    public void initialise() {
        add_product_button = (Button) mainActivity.findViewById(R.id.add_product_button);
        add_product_text = (TextView) mainActivity.findViewById(R.id.add_product_text);
        enter_comment = (EditText) mainActivity.findViewById(R.id.enter_comment);
        enter_product_name = (EditText) mainActivity.findViewById(R.id.enter_product_name);
        is_vegan = (TextView) mainActivity.findViewById(R.id.is_vegan);
        is_vegan_radio_buttons =
                (RadioGroup) mainActivity.findViewById(R.id.is_vegan_radio_buttons);
        product_barcode = (TextView) mainActivity.findViewById(R.id.product_barcode);
        product_name = (TextView) mainActivity.findViewById(R.id.product_name);
        send_to_database = (Button) mainActivity.findViewById(R.id.send_to_database);
        thanks = (TextView) mainActivity.findViewById(R.id.thanks);
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

        product_name.setText(productName);

        if (isVegan) {
            is_vegan.setText(R.string.vegan_true);
            is_vegan.setTextColor(fragment.getResources().getColor(R.color.veganGreen));
        }
        else {
            is_vegan.setText(R.string.vegan_false);
            is_vegan.setTextColor(fragment.getResources().getColor(R.color.nonVeganRed));
        }
    }

    public void productNotFound(String barcode) {
        showEnterProductElements(false);
        showProductFoundElements(false);
        showProductNotFoundElements(true);
        showThanks(false);

        pass_barcode = barcode;
    }

    public void addProduct() {
        showEnterProductElements(true);
        showProductFoundElements(false);
        showProductNotFoundElements(false);
        showThanks(false);

        String barcode = pass_barcode;

        product_barcode.setText(barcode);
    }

    public void showProductFoundElements(boolean show) {
        if (show) {
            product_name.setVisibility(View.VISIBLE);
            is_vegan.setVisibility(View.VISIBLE);
        }
        else {
            product_name.setVisibility(View.INVISIBLE);
            is_vegan.setVisibility(View.INVISIBLE);
        }

    }

    public void showProductNotFoundElements(boolean show) {
        if (show) {
            add_product_button.setVisibility(View.VISIBLE);
            add_product_text.setVisibility(View.VISIBLE);
        }
        else {
            add_product_button.setVisibility(View.INVISIBLE);
            add_product_text.setVisibility(View.INVISIBLE);
        }
    }

    public void showEnterProductElements(boolean show) {
        if (show) {
            enter_comment.setVisibility(View.VISIBLE);
            enter_product_name.setVisibility(View.VISIBLE);
            is_vegan_radio_buttons.setVisibility(View.VISIBLE);
            product_barcode.setVisibility(View.VISIBLE);
            send_to_database.setVisibility(View.VISIBLE);
        }
        else {
            enter_comment.setVisibility(View.INVISIBLE);
            enter_product_name.setVisibility(View.INVISIBLE);
            is_vegan_radio_buttons.setVisibility(View.INVISIBLE);
            product_barcode.setVisibility(View.INVISIBLE);
            send_to_database.setVisibility(View.INVISIBLE);
        }
    }

    public void showThanks(boolean show) {
        if (show) {
            thanks.setVisibility(View.VISIBLE);
        }
        else {
            thanks.setVisibility(View.INVISIBLE);
        }
    }

    public void sendSubmission(MemoryManagement memoryManagement) {
        String barcode = product_barcode.getText().toString();
        String name = enter_product_name.getText().toString();
        String vegan;
        if (Integer.toString(is_vegan_radio_buttons.getCheckedRadioButtonId()).equals("2131493003")) {
            vegan = "true";
        }
        else if (Integer.toString(is_vegan_radio_buttons.getCheckedRadioButtonId()).equals("2131493004")) {
            vegan = "false";
        }
        else {
            vegan = "unknown";
        }
        String comment = enter_comment.getText().toString();

        memoryManagement.saveSubmission(barcode, name, vegan, comment);

        showEnterProductElements(false);
        showProductFoundElements(false);
        showProductNotFoundElements(false);
        showThanks(true);
    }
}
