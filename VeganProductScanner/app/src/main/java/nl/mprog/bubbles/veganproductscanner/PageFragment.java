package nl.mprog.bubbles.veganproductscanner;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 * Based on code at
 * https://github.com/codepath/android_guides/wiki/Google-Play-Style-Tabs-using-TabLayout
 */

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    static MainActivity mainActivity;
    ResultFragment resultFragment;
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
        }
        else if (mPage == 4) {
            view = inflater.inflate(R.layout.add_fragment, container, false);
            AddFragment addFragment = new AddFragment();
            mainActivity.setAddFragment(addFragment);
        }
        else if (mPage == 5) {
            view = inflater.inflate(R.layout.enter_fragment, container, false);
            EnterFragment enterFragment = new EnterFragment();
            mainActivity.setEnterFragment(enterFragment);
        }
        else if (mPage == 6) {
            view = inflater.inflate(R.layout.sent_fragment, container, false);
        }
        else {
            view = inflater.inflate(R.layout.result_fragment, container, false);
            resultFragment = new ResultFragment();
            resultFragment.onCreate(view, this);
            mainActivity.setResultFragment(resultFragment);
        }
        return view;
    }
}