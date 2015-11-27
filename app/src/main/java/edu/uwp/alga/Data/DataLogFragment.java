package edu.uwp.alga.Data;


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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import edu.uwp.alga.R;
import edu.uwp.alga.SubmitActivity;
import edu.uwp.alga.utils.DataUtils;
import edu.uwp.alga.utils.LogAdapter;

public class DataLogFragment extends Fragment{

    private static final String ARG_SECTION_NUMBER = "section_number";
    public List<String> DataLog = null; // Format: TimeStamp - Log location
    public List<String> LogHeader;
    public HashMap<String,List<String>> LogChild;
    public LogAdapter logAdapter;


    public View rootView;
    public ExpandableListView dataList;

    public DataLogFragment() {
    }


    public static DataLogFragment create(int sectionNumber) {
        DataLogFragment fragment = new DataLogFragment();
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

        rootView = inflater.inflate(R.layout.fragment_datalog, container, false);
        //Save data
        Context context = getActivity();

        getDataLogFile(context);
        if(DataLog.size()>0)
        {
            populateLogAdapter();
            logAdapter = new LogAdapter(context,LogHeader,LogChild);
            dataList = (ExpandableListView)rootView.findViewById(R.id.data_log_list);
            dataList.setAdapter(logAdapter);
            for (int i = 0;i<LogHeader.size();i++){
                dataList.expandGroup(i);
            }
            dataList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    return true;
                }
            });

            dataList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    int index = parent.getFlatListPosition(ExpandableListView
                            .getPackedPositionForChild(groupPosition, childPosition));
                    parent.setItemChecked(index, true);
                    String data = (LogChild.get(LogHeader.get(groupPosition))).get(childPosition);
                    String logLoc = data.substring(26, data.length());

                    Intent intent = new Intent(getActivity(), SubmitActivity.class);
                    intent.putExtra("LogFile",logLoc);
                    startActivity(intent);

                    return false;
                }
            });
        }





        return rootView;
    }
// get String of Timestamp + the location of the Shared Preference file
    public void getDataLogFile(Context context){
        DataLog = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            SharedPreferences LogFile = context.getSharedPreferences(DataUtils.DataLog+String.valueOf(i),
                    Context.MODE_PRIVATE);
            if (DataUtils.hasData(LogFile)){
                String timeStamp = LogFile.getString(DataUtils.TimeStamp,"");
                DataLog.add(timeStamp + " - " + DataUtils.DataLog + String.valueOf(i));
                //DataLog.add(a.substring(0,23));
            }

            sortLatest(DataLog);

        }

    }

    public void sortLatest(List<String> timeData){
        Collections.sort(timeData, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                String lhsTime = lhs.substring(0,23);
                String rhsTime = rhs.substring(0,23);

                Date lhsDate = DataUtils.convertToDate(lhsTime);
                Date rhsDate = DataUtils.convertToDate(rhsTime);
                if(lhsDate.compareTo(rhsDate)== -1){
                    return 1;
                }
                else if(lhsDate.compareTo(rhsDate)== 1){
                    return -1;
                } else return 0;
            }


        });

    }

    public void populateLogAdapter(){
        LogHeader = new ArrayList<String>();
        LogChild = new HashMap<String,List<String>>();

        String currentHeader = null;
        String firstHeader = DataLog.get(0).substring(0,14);
        currentHeader = firstHeader;
        LogHeader.add(firstHeader);

        List currentChildSet = new ArrayList<String>();
        for(int i = 0; i< DataLog.size();i++){

            if(DataLog.get(i).substring(0,14).equals(currentHeader)){
                currentChildSet.add(DataLog.get(i));
            }
            else{
                LogChild.put(currentHeader,currentChildSet);
                currentChildSet = new ArrayList<String>();
                currentHeader = DataLog.get(i).substring(0,14);
                LogHeader.add(currentHeader);
                currentChildSet.add(DataLog.get(i));
            }
        }
        //last log
        LogChild.put(currentHeader, currentChildSet);


    }








}