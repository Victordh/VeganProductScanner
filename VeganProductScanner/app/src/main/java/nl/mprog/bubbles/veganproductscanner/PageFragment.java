package nl.mprog.bubbles.veganproductscanner;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 * Based on code at
 * https://github.com/codepath/android_guides/wiki/Google-Play-Style-Tabs-using-TabLayout
 */

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    static MainActivity mainActivity;
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
            view = inflater.inflate(R.layout.search_fragment, container, false);
            SearchFragment searchFragment = new SearchFragment();
            searchFragment.passViewContext(view, this.getContext());
            mainActivity.setSearchFragment(searchFragment);
        }
        else if (mPage == 3) {
            view = inflater.inflate(R.layout.info_fragment, container, false);
            InfoFragment infoFragment = new InfoFragment();
        }
        else {
            view = inflater.inflate(R.layout.result_fragment, container, false);
            ResultFragment resultFragment = new ResultFragment();
            resultFragment.onCreate(view, this);
            mainActivity.setResultFragment(resultFragment);
        }
        return view;
    }
}