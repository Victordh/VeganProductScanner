package nl.mprog.bubbles.veganproductscanner;

import android.app.Fragment;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class EnterFragment extends Fragment {

    MainActivity mainActivity;

    public void setBarcode() {
        TextView enter_frg_barcode_tv =
                (TextView) mainActivity.findViewById(R.id.enter_frg_barcode_tv);
        enter_frg_barcode_tv.setText(mainActivity.resultFragment.barcode);
    }

    public void sendSubmission() {
        EditText enter_frg_comment_et =
                (EditText) mainActivity.findViewById(R.id.enter_frg_comment_et);
        EditText enter_frg_name_et =
                (EditText) mainActivity.findViewById(R.id.enter_frg_name_et);
        RadioGroup enter_frg_vegan_rg =
                (RadioGroup) mainActivity.findViewById(R.id.enter_frg_vegan_rg);

        String name = enter_frg_name_et.getText().toString();
        String vegan;
        if (Integer.toString(enter_frg_vegan_rg.getCheckedRadioButtonId()).equals(
                "2131493003")) {
            vegan = "true";
        }
        else if (Integer.toString(enter_frg_vegan_rg.getCheckedRadioButtonId()).equals(
                "2131493004")) {
            vegan = "false";
        }
        else {
            vegan = "unknown";
        }
        String comment = enter_frg_comment_et.getText().toString();

        mainActivity.memoryManagement.saveSubmission(mainActivity.resultFragment.barcode,
                name, vegan, comment);

        // goes to SentFragment
        mainActivity.setFragment(5);
    }
}
