package nl.mprog.bubbles.veganproductscanner;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/*
 * Victor den Haan - 10118039 - vdenhaan@gmail.com
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // TODO Replace LocalDatastore with SQLite, apparently it's 20 times faster
        // http://stackoverflow.com/questions/30425087/parse-query-local-database-is-20-times-slower-then-sqlite
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String scanned_barcode = intent.getStringExtra("SCAN_RESULT");
                Toast.makeText(getApplicationContext(), scanned_barcode, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void syncDatabase(View view) {
        // checks for internet connection (needs permissions)
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if ((ni != null) && (ni.isConnected())) {
            Toast.makeText(getApplicationContext(),
                    "Internet connection detected. TODO: sync database.",
                    Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(getApplicationContext(),
                    "No internet connection detected. Could not sync database.",
                    Toast.LENGTH_LONG).show();
        }

        /*ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        query.getInBackground("createdAt", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // object will be your game score
                } else {
                    // something went wrong
                }
            }
        });*/
    }
}
