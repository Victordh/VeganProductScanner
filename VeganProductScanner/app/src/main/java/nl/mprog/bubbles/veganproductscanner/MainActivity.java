package nl.mprog.bubbles.veganproductscanner;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class MainActivity extends AppCompatActivity {

    //TODO Change colours/design/styles etc.

    AddFragment addFragment;
    ContainerFragment containerFragment;
    EnterFragment enterFragment;
    MemoryManagement memoryManagement;
    ResultFragment resultFragment;
    SearchFragment searchFragment;
    SharedPreferences prefs;
    String barcode;
    TabLayout main_act_tl;
    ViewPager main_act_vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        main_act_vp = (ViewPager) findViewById(R.id.main_act_vp);
        main_act_vp.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                this.getBaseContext(), this));


        // Give the TabLayout the ViewPager
        main_act_tl = (TabLayout) findViewById(R.id.main_act_tl);
        main_act_tl.setupWithViewPager(main_act_vp);

        memoryManagement = new MemoryManagement();
        memoryManagement.setMainActivity(this);

        // TODO Replace LocalDatastore with SQLite, apparently it's 20 times faster
        // http://stackoverflow.com/questions/30425087/parse-query-local-database-is-20-times-slower-then-sqlite
        //Parse.enableLocalDatastore(this);
        //Parse.initialize(this);

        prefs = getSharedPreferences("prefs", 0);

        goToFragment(prefs.getInt("currentTab", 0));

        barcode = prefs.getString("productBarcode", "");

        main_act_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                prefs.edit().putInt("currentTab", position).apply();
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String scanned_barcode = intent.getStringExtra("SCAN_RESULT");
                memoryManagement.getProductFromBarcode(scanned_barcode);
            }
        }
    }

    public void syncButtonClick(View view) {
        memoryManagement.syncLocalDatabase();
    }

    public void eraseButtonClick(View view) {
        memoryManagement.eraseLocalDatabase();
    }

    public void setResultFragment(ResultFragment fragment){
        resultFragment = fragment;
        resultFragment.mainActivity = this;
        PageFragment pageFragment = new PageFragment();
        resultFragment.onCreate(pageFragment.getContext());
    }

    public void setContainerFragment(ContainerFragment fragment) {
        containerFragment = fragment;
        containerFragment.mainActivity = this;
    }

    public void setSearchFragment(SearchFragment fragment) {
        searchFragment = fragment;
        searchFragment.mainActivity = this;
    }

    public void setEnterFragment(EnterFragment fragment) {
        enterFragment = fragment;
        enterFragment.mainActivity = this;
    }

    public void setAddFragment(AddFragment fragment) {
        addFragment = fragment;
        addFragment.mainActivity = this;
    }

    public void searchButtonClick(View view) {
        EditText search_frg_input_et = (EditText) findViewById(R.id.search_frg_input_et);
        String input = search_frg_input_et.getText().toString();
        search_frg_input_et.setText("");
        String hint = getString(R.string.search_fragment_et_input_searched_hint) + input;
        search_frg_input_et.setHint(hint);
        prefs.edit().putString("searchHint", hint).apply();
        prefs.edit().putString("searchInput", input).apply();
        prefs.edit().putString("searchText", "").apply();
        memoryManagement.getProductsFromInput(input);
    }

    public void addButtonClick(View view) {
        // goes to EnterFragment
        fillContainerFragment(2);
    }

    public void sendToDatabase(View view) {
        enterFragment.sendSubmission();
    }

    public void productToResult(String name, Boolean vegan) {
        goToFragment(0);
        fillContainerFragment(0);
        resultFragment.setProduct(name, vegan);
    }

    public void goToFragment(int number) {
        main_act_vp.setCurrentItem(number);
        //TODO Fix that old (wrong) tab title is highlighted after tab is changed via this method
    }

    public void fillContainerFragment(int number) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (number == 0) {
            ResultFragment fragment = new ResultFragment();
            setResultFragment(fragment);
            fragment.onCreate(this.getBaseContext());
            fragmentTransaction.replace(R.id.container_fragment, fragment);
        } else if (number == 2) {
            EnterFragment fragment = new EnterFragment();
            setEnterFragment(fragment);
            fragmentTransaction.replace(R.id.container_fragment, fragment);
        } else if (number == 3) {
            Fragment fragment = new SentFragment();
            fragmentTransaction.replace(R.id.container_fragment, fragment);
        } else {
            AddFragment fragment = new AddFragment();
            setAddFragment(fragment);
            fragmentTransaction.replace(R.id.container_fragment, fragment);
        }
        fragmentTransaction.commit();
        prefs.edit().putInt("currentContainerFragment", number).apply();
    }
}
