package edu.uwp.alga;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import edu.uwp.alga.utils.PoSectionsPagerAdapter;

/**
 * Created by Francisco on 11/25/2015.
 */
public class Po4Activity extends AppCompatActivity {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private PoSectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po4);
        setFragmentPagers();
        setTabLayout();
        setToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFragmentPagers() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new PoSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.po4_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    /**
     * The {@link Toolbar} is a generalization of {@link ActionBar} for use within application layouts.
     */
    private void setToolbar() {
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.po4_toolbar);
        mToolbar.setTitle("Set PO4 Values");
        setSupportActionBar(mToolbar);


        if (getSupportActionBar() != null) {
            // method invoked only when the actionBar is not null.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    private void setTabLayout() {
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.po4_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Make it swipeable, otherwise they will be squished together.
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
