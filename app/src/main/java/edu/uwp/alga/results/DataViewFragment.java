package edu.uwp.alga.results;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.uwp.alga.R;
import edu.uwp.alga.utils.DataUtils;
import edu.uwp.alga.utils.ExpandableListAdapter;

public class DataViewFragment extends Fragment implements View.OnClickListener{
    private HashMap<String,List<Float>> totalData;
    private static final String ARG_SECTION_NUMBER = "section_number";
    public View rootView;
    ExpandableListAdapter adapter;
    List<ExpandableListAdapter.Item> listDataHeader;
    HashMap<ExpandableListAdapter.Item, List<ExpandableListAdapter.Item>> listDataChild;
    Context context;
    Button dataset;
    SharedPreferences DataInputLog;
    ExpandableListView ChlaList;
    TextView tempdifText;
    TextView pavText;
    TextView luxText;
    ArrayList<Float> totalDataSet;
    ArrayList<Float> cyanoDataSet;
    SharedPreferences Logfile;

    ArrayList<Float> totalCriticalSet;
    ArrayList<Float> cyanoCriticalSet;


    public DataViewFragment() {
    }

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
        dataset = (Button) rootView.findViewById(R.id.data_set);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/bariol_regular.otf");
        dataset.setTypeface(tf);


        DataInputLog = context.getSharedPreferences(DataUtils.mPreference,
                Context.MODE_PRIVATE);
        luxText = (TextView)rootView.findViewById(R.id.data_lux);
        tempdifText = (TextView)rootView.findViewById(R.id.data_tempdif);
        pavText = (TextView)rootView.findViewById(R.id.data_pav);
        //Use the current value of the input, should change later to get view from datalog
        Intent intent = getActivity().getIntent();
        String logLoc = intent.getStringExtra("LogFile");
        if(logLoc==null){
            populateData(DataInputLog);
        }
        else {
            Logfile = context.getSharedPreferences(logLoc,
                    Context.MODE_PRIVATE);
            populateData(Logfile);
            //populateData(DataInputLog);
        }

        dataset.setOnClickListener(this);
        ChlaList = (ExpandableListView) rootView.findViewById(R.id.Elist);
        adapter = new ExpandableListAdapter(context, listDataHeader, listDataChild);
        ChlaList.setAdapter(adapter);
        if (listDataHeader.size()==1){
            ChlaList.expandGroup(0);
        }

        return rootView;
    }

    public void populateData(SharedPreferences DataLog) {
        totalDataSet = null;
        cyanoDataSet = null;
        AlgalDataCal myAlgalData;

        float oxygen;
        float secchi;
        float total;
        float cyano;


        float p04 = DataLog.getFloat(DataUtils.PO, 0.0001f);
        float surtemp = DataLog.getFloat(DataUtils.TempSurface, 0f);
        float bottemp = DataLog.getFloat(DataUtils.TempBottom, 4f);
        float depth = DataLog.getFloat(DataUtils.LakeDepth, 0f);
        float lux = DataLog.getFloat(DataUtils.lux,12000f);

        boolean isDirect = DataLog.getBoolean(DataUtils.isDirect, true);
        if (isDirect) {
            total = DataLog.getFloat(DataUtils.DirectTotal, -1);
            cyano = DataLog.getFloat(DataUtils.DirectCyano, -1);
            myAlgalData = new AlgalDataCal(total, cyano, p04, surtemp, bottemp, depth, lux);
            if (total != -1) {
                totalDataSet = myAlgalData.getTotalChlaDataSet();
            }
            if (cyano != -1) {
                cyanoDataSet = myAlgalData.getCyanoChlaDataSet();
            }
        } else {
            oxygen = DataLog.getFloat(DataUtils.EstimateOxygen, -1);
            secchi = DataLog.getFloat(DataUtils.EstimateSecchi, 0.1f);
            myAlgalData = new AlgalDataCal(secchi, oxygen, p04, surtemp, bottemp, depth, lux, true);
            if (oxygen != -1) {
                totalDataSet = myAlgalData.getTotalChlaDataSet();
            }
            cyanoDataSet = myAlgalData.getCyanoChlaDataSet();
        }

        Float tempdif = surtemp - bottemp;
        tempdifText.setText(String.valueOf(tempdif));
        Float pav = p04/depth;
        pavText.setText(String.valueOf(pav));
        luxText.setText(String.valueOf(lux));

        populateAdapter(myAlgalData);



    }
    public void setDataSet(){
        totalData = new HashMap<String,List<Float>>();
        int point = 0;
        totalCriticalSet = null;
        cyanoCriticalSet = null;


        if(totalDataSet!=null){
            totalCriticalSet = new ArrayList<Float>();
            for(int q = 0;q<=400;q++){
                if(point==q){
                    totalCriticalSet.add(totalDataSet.get(q));
                    point = point + 24;

                }
            }
            totalData.put("Total Chl a",totalCriticalSet);
        }
        if(cyanoDataSet!=null){
            cyanoCriticalSet = new ArrayList<Float>();
            for(int q = 0;q<=400;q++){
                if(point==q){
                    cyanoCriticalSet.add(cyanoDataSet.get(q));
                    point = point + 24;

                }
            }
            totalData.put("Cyano Chl a",cyanoCriticalSet);

        }
    }


    public void populateAdapter(AlgalDataCal mAlgalDataCal) {
        listDataHeader = new ArrayList<ExpandableListAdapter.Item>();
        listDataChild = new HashMap<ExpandableListAdapter.Item, List<ExpandableListAdapter.Item>>();

        List<ExpandableListAdapter.Item> set1;
        List<ExpandableListAdapter.Item> set2;

        if(totalDataSet!=null){
            ExpandableListAdapter.Item itemHeader1 = new ExpandableListAdapter.Item();
            itemHeader1.title = "Total Chl a";
            itemHeader1.value = String.valueOf(mAlgalDataCal.total_chla);
            listDataHeader.add(itemHeader1);

            set1 = new ArrayList<ExpandableListAdapter.Item>();

            ExpandableListAdapter.Item itemChild1a = new ExpandableListAdapter.Item();
            itemChild1a.title = "n0";
            itemChild1a.value = String.valueOf(AlgalDataCal.N0_MIN);

            ExpandableListAdapter.Item itemChild1b = new ExpandableListAdapter.Item();
            itemChild1b.title = "r0";
            itemChild1b.value = String.valueOf(mAlgalDataCal.getR01());

            ExpandableListAdapter.Item itemChild1c = new ExpandableListAdapter.Item();
            itemChild1c.title = "k";
            itemChild1c.value = String.valueOf(mAlgalDataCal.getK1());

            set1.add(itemChild1a);
            set1.add(itemChild1b);
            set1.add(itemChild1c);
            listDataChild.put(listDataHeader.get(0), set1);
        }
        //get headers
        if (cyanoDataSet!=null){
            ExpandableListAdapter.Item itemHeader2 = new ExpandableListAdapter.Item();
            itemHeader2.title = "Cyano Chl a";
            itemHeader2.value = String.valueOf(mAlgalDataCal.cyano_chla);
            listDataHeader.add(itemHeader2);

            set2 = new ArrayList<ExpandableListAdapter.Item>();
            ExpandableListAdapter.Item itemChild2a = new ExpandableListAdapter.Item();
            itemChild2a.title = "n0";
            itemChild2a.value = String.valueOf(AlgalDataCal.N0_MIN);

            ExpandableListAdapter.Item itemChild2b = new ExpandableListAdapter.Item();
            itemChild2b.title = "r0";
            itemChild2b.value = String.valueOf(mAlgalDataCal.getR02());

            ExpandableListAdapter.Item itemChild2c = new ExpandableListAdapter.Item();
            itemChild2c.title = "k";
            itemChild2c.value = String.valueOf(mAlgalDataCal.getK2());

            set2.add(itemChild2a);
            set2.add(itemChild2b);
            set2.add(itemChild2c);

            if(totalDataSet==null){
                listDataChild.put(listDataHeader.get(0), set2);
            }else listDataChild.put(listDataHeader.get(1), set2);

        }

    }

   /* public ExpandableListView.OnGroupClickListener groupClickListener() {
        ExpandableListView.OnGroupClickListener listener = new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                ImageView indicator = (ImageView) v.findViewById(R.id.indicator);
                if (parent.isGroupExpanded(groupPosition)) {
                    indicator.setImageResource(R.drawable.group_collapsed);
                } else indicator.setImageResource(R.drawable.group_indicator);
                return true;
            }
        };
        return listener;
    }*/


    public void getDataSet(View v){
        setDataSet();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.data_set:
                setDataSet();
                showDialog();
                break;

        }
    }


    public void showDialog(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DataSetDialog editNameDialog = new DataSetDialog();
        editNameDialog.setData("Kelly");
        editNameDialog.show(fm,"Fragment");
    }

}