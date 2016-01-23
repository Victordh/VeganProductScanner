package nl.mprog.bubbles.veganproductscanner;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class EnterFragment extends Fragment {

    EditText enter_frg_comment_et, enter_frg_name_et;
    MainActivity mainActivity;
    RadioGroup enter_frg_vegan_rg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.enter_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        setBarcode();
        enter_frg_comment_et =
                (EditText) mainActivity.findViewById(R.id.enter_frg_comment_et);
        enter_frg_comment_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mainActivity.prefs.edit().putString("productEnterComment", s.toString()).apply();
                }
            }
        });
        enter_frg_comment_et.setText(mainActivity.prefs.getString("productEnterComment", ""));

        enter_frg_name_et =
                (EditText) mainActivity.findViewById(R.id.enter_frg_name_et);
        enter_frg_name_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mainActivity.prefs.edit().putString("productEnterName", s.toString()).apply();
                }
            }
        });
        enter_frg_name_et.setText(mainActivity.prefs.getString("productEnterName", ""));

        enter_frg_vegan_rg =
                (RadioGroup) mainActivity.findViewById(R.id.enter_frg_vegan_rg);
        enter_frg_vegan_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mainActivity.prefs.edit().putInt("productEnterVegan", checkedId).apply();
            }
        });
        enter_frg_vegan_rg.check(mainActivity.prefs.getInt("productEnterVegan",
                enter_frg_vegan_rg.getCheckedRadioButtonId()));
    }

    public void setBarcode() {
        TextView enter_frg_barcode_tv =
                (TextView) mainActivity.findViewById(R.id.enter_frg_barcode_tv);
        enter_frg_barcode_tv.setText(mainActivity.barcode);
    }

    public void sendSubmission() {
        String name = enter_frg_name_et.getText().toString();
        String vegan;
        if (Integer.toString(enter_frg_vegan_rg.getCheckedRadioButtonId()).equals(
                "2131492989")) {
            vegan = "true";
        }
        else if (Integer.toString(enter_frg_vegan_rg.getCheckedRadioButtonId()).equals(
                "2131492990")) {
            vegan = "false";
        }
        else {
            vegan = Integer.toString(enter_frg_vegan_rg.getCheckedRadioButtonId());
            //vegan = "unknown";
        }
        String comment = enter_frg_comment_et.getText().toString();

        mainActivity.memoryManagement.saveSubmission(mainActivity.barcode, name, vegan, comment);

        // goes to SentFragment
        mainActivity.fillContainerFragment(3);
    }
}
