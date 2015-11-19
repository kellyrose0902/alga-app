package edu.uwp.alga.calculator;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.uwp.alga.MainActivity;
import edu.uwp.alga.R;
import edu.uwp.alga.utils.DataUtils;

public class ChlaDirectFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    Button directButton;
    public View rootView;
    EditText Totaltext;
    EditText Cyanotext;
    SharedPreferences DataInputLog;
    SharedPreferences.Editor editor;
    public ChlaDirectFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section number.
     *
     * @param sectionNumber Section number for this fragment
     * @return new instance of {@link CalculatorFragment}
     */
    public static ChlaDirectFragment create(int sectionNumber) {
        ChlaDirectFragment fragment = new ChlaDirectFragment();
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
        rootView = inflater.inflate(R.layout.fragment_chla_direct, container, false);
        //Save data
        Context context = getActivity();
        DataInputLog = context.getSharedPreferences(DataUtils.mPreference,
                Context.MODE_PRIVATE);
        editor = DataInputLog.edit();

        Totaltext = (EditText)rootView.findViewById(R.id.direct_total);
        Cyanotext = (EditText)rootView.findViewById(R.id.direct_cyano);
        directButton = (Button)rootView.findViewById(R.id.submit_direct);
        directButton.setOnClickListener(this);
        initializeValue();
        return rootView;
    }

    private void initializeValue() {
        if(DataInputLog.contains(DataUtils.DirectTotal)){
            Totaltext.setText(String.valueOf(DataInputLog.getFloat(DataUtils.DirectTotal, 0f)));
        }

        if(DataInputLog.contains(DataUtils.DirectCyano)){
            Cyanotext.setText(String.valueOf(DataInputLog.getFloat(DataUtils.DirectCyano, 0f)));
        }
    }


    public boolean checkInput(){
        if(!(DataUtils.hasValue(Totaltext)||DataUtils.hasValue(Cyanotext))){
            Toast.makeText(getActivity(),"Input at least 1 field",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(DataUtils.hasValue(Totaltext)){
            Float value = Float.valueOf(Totaltext.getText().toString());
            if (value < 0 || value > 300){
                Toast.makeText(getActivity(),"Please input Total Chl a value between 0 and 300",Toast.LENGTH_SHORT);
                return false;
            }
        }
        if(DataUtils.hasValue(Cyanotext)) {
            Float value = Float.valueOf(Cyanotext.getText().toString());
            if (value < 0 || value > 300) {
                Toast.makeText(getActivity(), "Please input Cyano Chl a value between 0 and 300", Toast.LENGTH_SHORT);
                return false;
            }
        }
        return true;
    }



    public void saveData(){
        //editor.clear();
        //editor.commit();
        if (DataUtils.hasValue(Totaltext)){
            editor.putFloat(DataUtils.DirectTotal,Float.valueOf(Totaltext.getText().toString()));
        }
        else editor.remove(DataUtils.DirectTotal);
        if (DataUtils.hasValue(Cyanotext)){
            editor.putFloat(DataUtils.DirectCyano,Float.valueOf(Cyanotext.getText().toString()));
        }
        else editor.remove(DataUtils.DirectCyano);
        editor.apply();
        Log.e("Preference", "Total Chla: " + String.valueOf(DataInputLog.getFloat(DataUtils.DirectTotal, -1.0f)));
        Log.e("Preference","Cyano Chla: "+String.valueOf(DataInputLog.getFloat(DataUtils.DirectCyano, -1.0f)));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_direct:
                if(checkInput()){
                    saveData();
                    editor.putBoolean(DataUtils.isSetChla, true);
                    editor.putBoolean(DataUtils.isDirect,true);
                    editor.commit();
                    deleteEstimateData();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }

                break;
        }
    }

    private void deleteEstimateData() {
        editor.remove(DataUtils.EstimateSecchi);
        editor.remove(DataUtils.EstimateOxygen);
        editor.apply();
    }
}