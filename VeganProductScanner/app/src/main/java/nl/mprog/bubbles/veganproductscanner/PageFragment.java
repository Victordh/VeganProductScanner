package nl.mprog.bubbles.veganproductscanner;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 * Based on code at
 * https://github.com/codepath/android_guides/wiki/Google-Play-Style-Tabs-using-TabLayout
 *
 * PageFragment handles inflating the fragments for each tab.
 */

public class PageFragment extends Fragment {
    private int mPage;
    private static MainActivity mainActivity;

    public static final String ARG_PAGE = "ARG_PAGE";
    //TODO mPage redundant, switch with ARG_PAGE?

    public static PageFragment newInstance(int page, MainActivity activity) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        mainActivity = activity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    /** inflates fragments and  makes sure they can be found via MainActivity*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (mPage == 2) {
            mainActivity.searchFragment = new SearchFragment();
            mainActivity.searchFragment.mainActivity = mainActivity;
            view = mainActivity.searchFragment.onCreateView(inflater, container,
                    savedInstanceState);
        } else if (mPage == 3) {
            mainActivity.infoFragment = new InfoFragment();
            mainActivity.infoFragment.mainActivity = mainActivity;
            view = mainActivity.infoFragment.onCreateView(inflater, container, savedInstanceState);
        } else {
            mainActivity.containerFragment = new ContainerFragment();
            view = mainActivity.containerFragment.onCreateView(inflater, container,
                    savedInstanceState);
            mainActivity.fillContainerFragment(mainActivity.prefs.getInt("currentContainerFragment",
                    0));
        }
        return view;
    }
}