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

public class InfoFragment extends Fragment {

    //TODO Replace placeholder with more/other information
    //TODO Maybe remove 'empty database'?
    //TODO Alter/remove Toasts

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_fragment, container, false);
    }
}
