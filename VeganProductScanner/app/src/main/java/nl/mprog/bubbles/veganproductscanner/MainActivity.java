package nl.mprog.bubbles.veganproductscanner;

import android.app.ActionBar;
import android.app.Fragment;
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

    TabLayout tabLayout;
    ViewPager viewPager;

    MemoryManagement memoryManagement;
    ResultFragment resultFragment;
    SearchFragment searchFragment;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bundle = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this, this));

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        memoryManagement = new MemoryManagement();

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
        memoryManagement.syncLocalDatabase(this);
    }

    public void eraseButtonClick(View view) {
        memoryManagement.eraseLocalDatabase(this);
    }

    public void setResultFragment(ResultFragment fragment){
        resultFragment = fragment;
    }

    public void setSearchFragment(SearchFragment fragment){
        searchFragment = fragment;
    }

    public void searchButtonClick(View view) {
        EditText userInput = (EditText) findViewById(R.id.search_edittext);
        String input = userInput.getText().toString();
        memoryManagement.getProductsFromInput(input, searchFragment, this);
    }

    public void addProductButtonClick(View view) {
        resultFragment.addProduct();
    }

    public void sendToDatabase(View view) {
        resultFragment.sendSubmission(memoryManagement);
    }

    public void productToResult(String name, Boolean vegan) {
        viewPager.setCurrentItem(0);
        resultFragment.productFound(name, vegan);
    }
}
