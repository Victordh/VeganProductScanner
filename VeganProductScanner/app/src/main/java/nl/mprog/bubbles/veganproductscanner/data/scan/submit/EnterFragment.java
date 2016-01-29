package nl.mprog.bubbles.veganproductscanner.data.scan.submit;

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

import nl.mprog.bubbles.veganproductscanner.MainActivity;
import nl.mprog.bubbles.veganproductscanner.R;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 *
 * EnterFragment contains the UI allowing the user to enter the details of a scanned product that
 * couldn't be found. It also handles the calls to OnlineDatabase for sending this information to
 * the Parse database.
 */

public class EnterFragment extends Fragment {
    public MainActivity mainActivity;

    private EditText etComment, etName;
    private RadioGroup rgVegan;

    /** inflates xml */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.enter_fragment, container, false);
    }

    /** loads the saved user input from SharedPreferences, adds TextWatchers and Listener */
    @Override
    public void onStart() {
        super.onStart();
        setBarcode();

        addTextWatcherEtComment();
        etComment.setText(mainActivity.prefs.getString("productEnterComment", ""));

        addTextWatcherEtName();
        etName.setText(mainActivity.prefs.getString("productEnterName", ""));

        addListenerRgVegan();
        rgVegan.check(mainActivity.prefs.getInt("productEnterVegan",
                rgVegan.getCheckedRadioButtonId()));
    }

    /** adds TextWatcher that saves the changed text in the comment EditText to SharedPreferences */
    private void addTextWatcherEtComment() {
        etComment = (EditText) mainActivity.findViewById(R.id.enter_fragment_comment_et);
        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mainActivity.prefs.edit().putString("productEnterComment",
                            s.toString()).apply();
                }
            }
        });
    }

    /** adds TextWatcher that saves the changed text in the name EditText to SharedPreferences */
    private void addTextWatcherEtName() {
        etName = (EditText) mainActivity.findViewById(R.id.enter_fragment_name_et);
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mainActivity.prefs.edit().putString("productEnterName", s.toString()).apply();
                }
            }
        });
    }

    /** adds Listener that saves the checked RadioButton to SharedPreferences */
    private void addListenerRgVegan() {
        rgVegan = (RadioGroup) mainActivity.findViewById(R.id.enter_fragment_vegan_rg);
        rgVegan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mainActivity.prefs.edit().putInt("productEnterVegan", checkedId).apply();
            }
        });
    }

    /** updates TextView text */
    private void setBarcode() {
        TextView tvBarcode = (TextView) mainActivity.findViewById(R.id.enter_fragment_barcode_tv);
        tvBarcode.setText(mainActivity.barcode);
    }

    /** collects details entered by user and calls OnlineDatabase function to send it to the Parse
     * database, also fills ContainerFragment with SentFragment */
    public void sendSubmission() {
        String name = etName.getText().toString();
        String vegan;
        if (rgVegan.getCheckedRadioButtonId() == rgVegan.findViewById(
                R.id.enter_fragment_vegan_yes_rb).getId()) {
            vegan = "true";
        } else if (rgVegan.getCheckedRadioButtonId() == rgVegan.findViewById(
                R.id.enter_fragment_vegan_no_rb).getId()) {
            vegan = "false";
        } else {
            vegan = "unknown";
        }
        String comment = etComment.getText().toString();

        mainActivity.onlineDatabase.saveSubmission(mainActivity.barcode, name, vegan, comment);
        mainActivity.fillContainerFragment(3);
    }
}