package edu.uwp.alga.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uwp.alga.R;

public class DataViewFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public DataViewFragment() { }

    public static Fragment create(int sectionNumber) {
        DataViewFragment fragment = new DataViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data_view, container, false);
    }
}
