package edu.uwp.alga.results;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.uwp.alga.R;
import edu.uwp.alga.utils.ExpandableListAdapter;

public class DataViewFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public View rootView;
    ExpandableListAdapter adapter;
    List<ExpandableListAdapter.Item> listDataHeader;
    HashMap<ExpandableListAdapter.Item, List<ExpandableListAdapter.Item>> listDataChild;
    Context context;


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
        rootView = inflater.inflate(R.layout.fragment_data_view, container, false);
        context = getActivity();
        ExpandableListView ChlaList = (ExpandableListView)rootView.findViewById(R.id.Elist);
        populateAdapter();
        adapter = new ExpandableListAdapter(context,listDataHeader, listDataChild);
        ChlaList.setAdapter(adapter);
        return rootView;
    }
    public void populateAdapter(){
        listDataHeader = new ArrayList<ExpandableListAdapter.Item>();
        listDataChild = new HashMap<ExpandableListAdapter.Item,List<ExpandableListAdapter.Item>>();


        //get headers
        ExpandableListAdapter.Item itemHeader1= new ExpandableListAdapter.Item();
        itemHeader1.title = "Total Chl a";
        itemHeader1.value = "60";

        ExpandableListAdapter.Item itemHeader2= new ExpandableListAdapter.Item();
        itemHeader2.title = "Total Chl a";
        itemHeader2.value = "60";

        //getChild1
        List<ExpandableListAdapter.Item> set1 = new ArrayList<ExpandableListAdapter.Item>();

        ExpandableListAdapter.Item itemChild1a = new ExpandableListAdapter.Item();
        itemChild1a.title = "r0";
        itemChild1a.value = "0.8";

        ExpandableListAdapter.Item itemChild1b = new ExpandableListAdapter.Item();
        itemChild1b.title = "n0";
        itemChild1b.value = "430";

        ExpandableListAdapter.Item itemChild1c = new ExpandableListAdapter.Item();
        itemChild1c.title = "k";
        itemChild1c.value = "1.8";

        set1.add(itemChild1a);
        set1.add(itemChild1b);
        set1.add(itemChild1c);

        //get child 2

        List<ExpandableListAdapter.Item> set2 = new ArrayList<ExpandableListAdapter.Item>();
        ExpandableListAdapter.Item itemChild2a = new ExpandableListAdapter.Item();
        itemChild2a.title = "r0";
        itemChild2a.value = "0.8";

        ExpandableListAdapter.Item itemChild2b = new ExpandableListAdapter.Item();
        itemChild2b.title = "n0";
        itemChild2b.value = "430";

        ExpandableListAdapter.Item itemChild2c = new ExpandableListAdapter.Item();
        itemChild2c.title = "k";
        itemChild2c.value = "1.8";

        set2.add(itemChild2a);
        set2.add(itemChild2b);
        set2.add(itemChild2c);

        listDataHeader.add(itemHeader1);
        listDataHeader.add(itemHeader2);

        listDataChild.put(listDataHeader.get(0),set1);
        listDataChild.put(listDataHeader.get(1),set2);
    }
}
