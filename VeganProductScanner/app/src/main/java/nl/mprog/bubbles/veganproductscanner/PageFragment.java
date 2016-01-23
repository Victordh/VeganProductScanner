package nl.mprog.bubbles.veganproductscanner;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 * Based on code at
 * https://github.com/codepath/android_guides/wiki/Google-Play-Style-Tabs-using-TabLayout
 */

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    static MainActivity mainActivity;
    ContainerFragment containerFragment;
    private int mPage;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (mPage == 2) {
            SearchFragment searchFragment = new SearchFragment();
            view = searchFragment.onCreateView(inflater, container, savedInstanceState);
            searchFragment.passViewContext(view, this.getContext());
            mainActivity.setSearchFragment(searchFragment);
        } else if (mPage == 3) {
            InfoFragment infoFragment = new InfoFragment();
            view = infoFragment.onCreateView(inflater, container, savedInstanceState);
        } else {
            containerFragment = new ContainerFragment();
            view = containerFragment.onCreateView(inflater, container, savedInstanceState);
            mainActivity.fillContainerFragment(mainActivity.prefs.getInt("currentContainerFragment", 0));
            mainActivity.setContainerFragment(containerFragment);
        }
        return view;
    }
}