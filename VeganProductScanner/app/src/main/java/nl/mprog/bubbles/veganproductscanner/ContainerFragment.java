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
 */

public class ContainerFragment extends Fragment {

    MainActivity mainActivity;
    PageFragment fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.container_fragment, container, false);
        createFloatingActionButton(view);
        return view;
    }

    private void createFloatingActionButton(View view){
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // uses Zxing to scan a barcode
                //IntentIntegrator integrator = new IntentIntegrator(mainActivity);
                //integrator.initiateScan(IntentIntegrator.PRODUCT_CODE_TYPES);
                productNotFound("123456789999");
            }
        });
    }

    public void productNotFound(String product_barcode) {
        //Go to AddFragment, send barcode too
        mainActivity.barcode = product_barcode;
        mainActivity.fillFragmentContainer(1);
    }
}
