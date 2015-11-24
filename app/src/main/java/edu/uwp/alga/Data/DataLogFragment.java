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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.uwp.alga.R;
import edu.uwp.alga.utils.DataUtils;

public class DataLogFragment extends Fragment{

    private static final String ARG_SECTION_NUMBER = "section_number";
    public List<String> DataLog = null;


    public View rootView;
    public ListView dataList;

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
        populateDataLogFile(context);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,DataLog);
        dataList = (ListView)rootView.findViewById(R.id.data_list);
        dataList.setAdapter(adapter);



        return rootView;
    }

    public void populateDataLogFile(Context context){
        DataLog = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            SharedPreferences LogFile = context.getSharedPreferences(DataUtils.DataLog+String.valueOf(i),
                    Context.MODE_PRIVATE);
            if (DataUtils.hasData(LogFile)){
                DataLog.add(DataUtils.DataLog+String.valueOf(i));

            }


        }
        int size = DataLog.size();
        Log.e("DataLog", String.valueOf(size));

    }








}