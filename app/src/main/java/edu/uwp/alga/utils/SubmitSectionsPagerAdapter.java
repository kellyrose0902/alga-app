package edu.uwp.alga.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import edu.uwp.alga.results.DataViewFragment;
import edu.uwp.alga.results.GraphViewFragment;

public class SubmitSectionsPagerAdapter extends FragmentPagerAdapter {

    public SubmitSectionsPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
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
                return DataViewFragment.create(position++);
            case 1:
                return (GraphViewFragment)GraphViewFragment.create(position++);
            default:
                return PlaceholderFragment.newInstance(position++);
        }
    }

    @Override
    public int getCount() {
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
                return "Data";
            case 1:
                return "Graph";
        }
        return null;
    }
}
