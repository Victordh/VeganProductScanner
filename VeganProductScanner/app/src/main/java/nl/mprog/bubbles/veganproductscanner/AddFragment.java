package nl.mprog.bubbles.veganproductscanner;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class AddFragment extends Fragment {

    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity.prefs.edit().putString("productEnterComment", "").apply();
        mainActivity.prefs.edit().putString("productEnterName", "").apply();
        mainActivity.prefs.edit().putInt("productEnterVegan", 2131492987).apply();

        return inflater.inflate(R.layout.add_fragment, container, false);
    }
}
