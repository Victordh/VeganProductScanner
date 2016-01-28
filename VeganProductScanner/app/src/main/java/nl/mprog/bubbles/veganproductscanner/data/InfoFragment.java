package nl.mprog.bubbles.veganproductscanner.data;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import nl.mprog.bubbles.veganproductscanner.MainActivity;
import nl.mprog.bubbles.veganproductscanner.R;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 *
 * InfoFragment contains the UI allowing the user to sync their local database with Parse.
 */

public class InfoFragment extends Fragment {
    public MainActivity mainActivity;
    //TODO Add more stuff (FAQ, feedback, donation?, tutorial?) to this fragment.

    /** inflates xml */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_fragment, container, false);
    }

    //TODO Change colour of disabled buttons

    /** enables or disables the sync button, to prevent unnecessary calls to the online database */
    public void enableSyncButton(boolean enable) {
        Button syncButton = (Button) mainActivity.findViewById(R.id.info_frg_sync_btn);
        syncButton.setClickable(enable);
        if (enable) {
            syncButton.setText(R.string.info_fragment_btn_sync);
        } else {
            syncButton.setText(R.string.info_fragment_btn_synced);
        }
    }

    /** enables or disables the clear button, to prevent unnecessary calls to the local database */
    public void enableClearButton(boolean enable) {
        Button eraseButton = (Button) mainActivity.findViewById(R.id.info_frg_clear_btn);
        eraseButton.setClickable(enable);
        if (enable) {
            eraseButton.setText(R.string.info_fragment_btn_clear);
        } else {
            eraseButton.setText(R.string.info_fragment_btn_cleared);
        }
    }
}