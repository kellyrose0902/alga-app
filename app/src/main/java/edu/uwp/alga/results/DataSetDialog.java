package edu.uwp.alga.results;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.List;

import edu.uwp.alga.R;
import edu.uwp.alga.adapter.DataSetAdapter;

/**
 * Created by Kelly on 11/27/2015.
 */
public class DataSetDialog extends DialogFragment {

    private List<String> totalDataHeader;
    private HashMap<String, List<Float>> totalData;
    private ExpandableListView setList;
    private DataSetAdapter adapter;


    public DataSetDialog() {
        // Empty constructor required for DialogFragment
    }

    public void setData(List<String> totalDataHeader, HashMap<String, List<Float>> totalData) {
        this.totalData = totalData;
        this.totalDataHeader = totalDataHeader;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_dataset, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setList = (ExpandableListView) view.findViewById(R.id.listDataset);
        if (totalData != null && totalDataHeader != null) {
            adapter = new DataSetAdapter(getActivity(), totalDataHeader, totalData);
            setList.setAdapter(adapter);
            setList.expandGroup(0);
            if (totalData.size() == 2) {
                setList.expandGroup(1);
            }

            setList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    return true;
                }
            });

        }

        return view;
    }

    @Override
    public void onResume()
    {

        super.onResume();
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        Window window = getDialog().getWindow();
        window.setLayout(width - (width / 20), height - (height / 10));
        window.setGravity(Gravity.NO_GRAVITY);
    }
}