package nl.mprog.bubbles.veganproductscanner;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 *
 * AddFragment contains the UI allowing the user to add a product that couldn't be found in the
 * database.
 * TODO Think about if this entire fragment is redundant: might just be an useless extra action.
 */

public class AddFragment extends Fragment {
    public MainActivity mainActivity;

    /** inflates xml and resets the EnterFragment input, because a new product has been scanned */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivity.prefs.edit().putString("productEnterComment", "").apply();
        mainActivity.prefs.edit().putString("productEnterName", "").apply();
        // TODO Find a way to do this differently than with a hardcoded ID, maybe with
        // enterFragment.rgVegan.findViewById(R.id.enter_fragment_vegan_dont_know_rb).getId()
        mainActivity.prefs.edit().putInt("productEnterVegan", 2131492987).apply();

        return inflater.inflate(R.layout.add_fragment, container, false);
    }
}