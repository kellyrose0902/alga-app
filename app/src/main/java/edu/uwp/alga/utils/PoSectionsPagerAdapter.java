package edu.uwp.alga.utils;

/**
 * Created by Francisco on 11/25/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import edu.uwp.alga.calculator.PoDirectFragment;
import edu.uwp.alga.calculator.PoEstimateFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class PoSectionsPagerAdapter extends FragmentPagerAdapter {

    /**
     * {@inheritDoc}
     */
    public PoSectionsPagerAdapter(FragmentManager fm) {
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
                return PoDirectFragment.create(position++);
            case 1:
                return PoEstimateFragment.create(position++);
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
