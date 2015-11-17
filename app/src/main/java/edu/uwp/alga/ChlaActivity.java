package edu.uwp.alga;

/**
 * Copyright 2015 UW-Parkside, Harleen Kaur, Hanh Le, Francisco Mateo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import edu.uwp.alga.utils.ChlaSectionsPagerAdapter;

public class ChlaActivity extends AppCompatActivity {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private ChlaSectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chla);
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
        mSectionsPagerAdapter = new ChlaSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.chla_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    /**
     * The {@link Toolbar} is a generalization of {@link ActionBar} for use within application layouts.
     */
    private void setToolbar() {
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.chla_toolbar);
        mToolbar.setTitle("Set Chl a Values");
        setSupportActionBar(mToolbar);


        if (getSupportActionBar() != null) {
            // method invoked only when the actionBar is not null.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    private void setTabLayout() {
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.chla_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Make it swipeable, otherwise they will be squished together.
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }


}
