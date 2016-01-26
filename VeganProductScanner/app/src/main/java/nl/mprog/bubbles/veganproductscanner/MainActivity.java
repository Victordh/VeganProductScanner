package nl.mprog.bubbles.veganproductscanner;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 * MainActivity contains all fragments and their managers. It also redirects button clicks to the
 * correct fragment.
 */

public class MainActivity extends AppCompatActivity {
    //TODO Change colours/design/styles etc.

    public ContainerFragment containerFragment;
    public MemoryManagement memoryManagement;
    public ResultFragment resultFragment;
    public SearchFragment searchFragment;
    public SharedPreferences prefs;
    public String barcode;

    private EnterFragment enterFragment;
    private ViewPager viewPager;

    // sets up ViewPager and TabLayout, loads previously opened fragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("prefs", 0);
        barcode = prefs.getString("productBarcode", "");
        memoryManagement = new MemoryManagement();
        memoryManagement.mainActivity = this;

        setUpViewPagerAndTabLayout();
        addListenerVp();
        goToFragment(prefs.getInt("currentTab", 0));
    }

    // sets ViewPager's PagerAdapter so that it can display items, and gives ViewPager to TabLayout
    private void setUpViewPagerAndTabLayout() {
        viewPager = (ViewPager) findViewById(R.id.main_act_vp);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                this.getBaseContext(), this));

        TabLayout tl = (TabLayout) findViewById(R.id.main_act_tl);
        tl.setupWithViewPager(viewPager);
    }

    private void addListenerVp() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                prefs.edit().putInt("currentTab", position).apply();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void fillContainerFragment(int n) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (n == 0) {
            resultFragment = new ResultFragment();
            resultFragment.mainActivity = this;
            ft.replace(R.id.container_fragment, resultFragment);
        } else if (n == 2) {
            enterFragment = new EnterFragment();
            enterFragment.mainActivity = this;
            ft.replace(R.id.container_fragment, enterFragment);
        } else if (n == 3) {
            SentFragment sentFragment = new SentFragment();
            ft.replace(R.id.container_fragment, sentFragment);
        } else {
            AddFragment addFragment = new AddFragment();
            addFragment.mainActivity = this;
            ft.replace(R.id.container_fragment, addFragment);
        }
        ft.commit();
        prefs.edit().putInt("currentContainerFragment", n).apply();
    }

    public void goToFragment(int n) {
        viewPager.setCurrentItem(n);
        //TODO Fix that old (wrong) tab title is highlighted after tab is changed via this method
    }

    // receives scanned barcode from Zxing (IntentResult.java) and hands it to MemoryManagement
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                memoryManagement.getProductFromBarcode(intent.getStringExtra("SCAN_RESULT"));
            }
        }
    }

    // goes to ContainerFragment, fills it with ResultFragment
    public void productToResult(String name, Boolean vegan) {
        goToFragment(0);
        fillContainerFragment(0);
        resultFragment.setProduct(name, vegan);
    }

    // goes to EnterFragment
    public void addButtonClick(View view) {
        fillContainerFragment(2);
    }

    public void eraseButtonClick(View view) {
        memoryManagement.eraseLocalDatabase();
    }

    public void searchButtonClick(View view) {
        searchFragment.handleSearch();
    }

    public void sendButtonClick(View view) {
        enterFragment.sendSubmission();
    }

    public void syncButtonClick(View view) {
        memoryManagement.syncLocalDatabase();
    }
}