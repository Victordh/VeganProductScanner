package nl.mprog.bubbles.veganproductscanner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 * Based on code at
 * https://github.com/codepath/android_guides/wiki/Google-Play-Style-Tabs-using-TabLayout
 *
 * TabsFragmentPagerAdapter links the Tabs to the Fragments. The user can tap the tabs to move to
 * their corresponding fragments, but sliding also works because of the linked
 * TabLayout/FragmentManager which is taking care of in the onCreate of MainActivity.
 * TODO Change tabTitles to Icons with text
 * TODO Change tabTitle of first tab depending on which fragment is loaded in ContainerFragment
 * TODO Add a settings tab with language, colour, database download, picture?
 * TODO Maybe add a history tab with last x products (is this useful? talk to people)
 */

class TabsFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private final MainActivity mainActivity;

    /** passes MainActivity for getItem to use */
    public TabsFragmentPagerAdapter(FragmentManager fm , MainActivity activity) {
        super(fm);
        mainActivity = activity;
    }

    /** specifies the amount of tabs */
    @Override
    public int getCount() {
        return 3;
    }

    /** determines the fragment corresponding to each tab */
    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1, mainActivity);
    }

    /** generates the title for each tab */
    @Override
    public CharSequence getPageTitle(int position) {
        String tabTitles[] = new String[] { "Result", "Search", "Info"};
        return tabTitles[position];
    }
}
