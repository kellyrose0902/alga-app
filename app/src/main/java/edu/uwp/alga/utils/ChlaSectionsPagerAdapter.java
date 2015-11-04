package edu.uwp.alga.utils;

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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import edu.uwp.alga.calculator.ChlaDirectFragment;
import edu.uwp.alga.calculator.ChlaEstimateFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ChlaSectionsPagerAdapter extends FragmentPagerAdapter {

    /**
     * {@inheritDoc}
     */
    public ChlaSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Called to instatiate the fragment for the given page.
     *
     * @param position Position in the tab view.
     * @return A {@link Fragment} based on the position
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ChlaDirectFragment.create(position++);
            case 1:
                return ChlaEstimateFragment.create(position++);
            default:
                return PlaceholderFragment.newInstance(position++);
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    /**
     * Sets the title of the fragment view.
     *
     * @param position Position in the tab view
     * @return The title of the fragment.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Direct";
            case 1:
                return "Estimate";
        }
        return null;
    }
}