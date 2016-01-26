package nl.mprog.bubbles.veganproductscanner;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 * ContainerFragment is a container for fragments (Result, Add, Enter and Sent). The Floating Action
 * Button is part of ContainerFragment, so it's visible regardless of which fragment is filling it.
 */

public class ContainerFragment extends Fragment {
    public MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.container_fragment, container, false);
        createFloatingActionButton(view);
        return view;
    }

    // gets the fragment that was filling ContainerFragment from SharedPreferences
    @Override
    public void onStart() {
        super.onStart();
        mainActivity.fillContainerFragment(mainActivity.prefs.getInt("currentContainerFragment",
                0));
    }

    private void createFloatingActionButton(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // uses Zxing to scan a barcode (IntentIntegrator.java)
                IntentIntegrator integrator = new IntentIntegrator(mainActivity);
                integrator.initiateScan(IntentIntegrator.PRODUCT_CODE_TYPES);
            }
        });
    }

    // fills ContainerFragment with AddFragment and saves barcode, if product couldn't be found
    public void productNotFound(String barcode) {
        mainActivity.prefs.edit().putString("productBarcode", barcode).apply();
        mainActivity.barcode = barcode;
        mainActivity.fillContainerFragment(1);
    }
}