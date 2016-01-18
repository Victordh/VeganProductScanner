package nl.mprog.bubbles.veganproductscanner;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.parse.Parse;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class MainActivity extends AppCompatActivity {

    AddFragment addFragment;
    EnterFragment enterFragment;
    MemoryManagement memoryManagement;
    ResultFragment resultFragment;
    SearchFragment searchFragment;
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
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String scanned_barcode = intent.getStringExtra("SCAN_RESULT");
                memoryManagement.getProductFromBarcode(scanned_barcode, resultFragment);
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
    }

    public void setSearchFragment(SearchFragment fragment){
        searchFragment = fragment;
        searchFragment.mainActivity = this;
    }

    public void setEnterFragment(EnterFragment fragment){
        enterFragment = fragment;
        enterFragment.mainActivity = this;
    }

    public void setAddFragment(AddFragment fragment){
        addFragment = fragment;
        addFragment.mainActivity = this;
    }

    public void searchButtonClick(View view) {
        EditText search_frg_input_et = (EditText) findViewById(R.id.search_frg_input_et);
        String input = search_frg_input_et.getText().toString();
        memoryManagement.getProductsFromInput(input, searchFragment, this);
    }

    public void addButtonClick(View view) {
        // goes to EnterFragment
        setFragment(4);
        enterFragment.setBarcode();
    }

    public void sendToDatabase(View view) {
        enterFragment.sendSubmission();
    }

    public void productToResult(String name, Boolean vegan) {
        setFragment(0);
        resultFragment.productFound(name, vegan);
    }

    public void setFragment(int number) {
        main_act_vp.setCurrentItem(number);
    }
}
