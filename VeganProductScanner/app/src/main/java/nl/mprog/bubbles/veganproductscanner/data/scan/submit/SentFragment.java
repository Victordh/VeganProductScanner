package nl.mprog.bubbles.veganproductscanner.data.scan.submit;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.mprog.bubbles.veganproductscanner.R;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 *
 * SentFragment contains the view giving feedback to the user after they submit a product.
 * TODO Think about if this fragment is redundant and could be replaced by a Toast or something
 */

public class SentFragment extends Fragment {
    /** inflates xml */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sent_fragment, container, false);
    }
}
