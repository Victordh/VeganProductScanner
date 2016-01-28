package nl.mprog.bubbles.veganproductscanner;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.mprog.bubbles.veganproductscanner.data.InfoFragment;
import nl.mprog.bubbles.veganproductscanner.data.scan.ContainerFragment;
import nl.mprog.bubbles.veganproductscanner.data.search.SearchFragment;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 * Based on code at
 * https://github.com/codepath/android_guides/wiki/Google-Play-Style-Tabs-using-TabLayout
 *
 * PageFragment handles inflating the fragments for each tab.
 */

public class PageFragment extends Fragment {
    private int mPage;
    private MainActivity mainActivity;

    /** creates a new PageFragment with certain tab number, hands it MainActivity */
    public static PageFragment newInstance(int page, MainActivity activity) {
        PageFragment fragment = new PageFragment();
        fragment.mPage = page;
        fragment.mainActivity = activity;
        return fragment;
    }

    /** returns the correct fragment as view */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (mPage == 2) {
            view = createSearchFragment(inflater, container, savedInstanceState);
        } else if (mPage == 3) {
            view = createInfoFragment(inflater, container, savedInstanceState);
        } else {
            view = createContainerFragment(inflater, container, savedInstanceState);
        }
        return view;
    }

    /** makes sure views in SearchFragment are loaded from SharedPreferences */
    @Override
    public void onStart() {
        super.onStart();
        if (mPage == 2) {
            mainActivity.searchFragment.loadFromPrefs();
        }
    }

    /** creates double connection between ContainerFragment and MainActivity, fills
     * ContainerFragment with correct Fragment from SharedPreferences, returns inflated view */
    private View createContainerFragment(LayoutInflater inflater, ViewGroup container,
                                         Bundle savedInstanceState) {
        mainActivity.containerFragment = new ContainerFragment();
        mainActivity.containerFragment.mainActivity = mainActivity;
        View view = mainActivity.containerFragment.onCreateView(inflater, container,
                savedInstanceState);
        mainActivity.fillContainerFragment(mainActivity.prefs.getInt("currentContainerFragment",
                0));
        return view;
    }

    /** creates double connection between InfoFragment and MainActivity, returns inflated view */
    private View createInfoFragment(LayoutInflater inflater, ViewGroup container,
                                    Bundle savedInstanceState) {
        mainActivity.infoFragment = new InfoFragment();
        mainActivity.infoFragment.mainActivity = mainActivity;
        return mainActivity.infoFragment.onCreateView(inflater, container, savedInstanceState);
    }

    /** creates double connection between SearchFragment and MainActivity, returns inflated view */
    private View createSearchFragment(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState) {
        mainActivity.searchFragment = new SearchFragment();
        mainActivity.searchFragment.mainActivity = mainActivity;
        return mainActivity.searchFragment.onCreateView(inflater, container,
                savedInstanceState);
    }
}