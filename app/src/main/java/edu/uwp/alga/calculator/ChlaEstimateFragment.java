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

public class ChlaEstimateFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public View rootView;
    Button estimateButton;
    EditText Sechitext;
    EditText Oxygentext;
    SharedPreferences DataInputLog;
    SharedPreferences.Editor editor;
    public ChlaEstimateFragment() {}

    /**
     * Returns a new instance of this fragment for the given section number.
     *
     * @param sectionNumber Section number for this fragment
     * @return new instance of {@link CalculatorFragment}
     */
    public static ChlaEstimateFragment create(int sectionNumber) {
        ChlaEstimateFragment fragment = new ChlaEstimateFragment();
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
        rootView = inflater.inflate(R.layout.fragment_chla_estimate, container, false);

        Context context = getActivity();
        DataInputLog = context.getSharedPreferences(DataUtils.mPreference,
                Context.MODE_PRIVATE);
        editor = DataInputLog.edit();

        estimateButton = (Button)rootView.findViewById(R.id.submit_estimate);
        estimateButton.setOnClickListener(this);
        Oxygentext = (EditText)rootView.findViewById(R.id.Oxygen_input);
        Sechitext = (EditText)rootView.findViewById(R.id.Secchi_input);
        //initializeValue();
        return rootView;
    }
    public void initializeValue(){
        if(DataInputLog.contains(DataUtils.EstimateOxygen)){
            Oxygentext.setText(String.valueOf(DataInputLog.getFloat(DataUtils.EstimateOxygen, 0f)));
        }

        if(DataInputLog.contains(DataUtils.EstimateSecchi)){
            Sechitext.setText(String.valueOf(DataInputLog.getFloat(DataUtils.EstimateSecchi, 0f)));
        }
    }
    public boolean checkInput(){
        if(!(DataUtils.hasValue(Sechitext))){
            Toast.makeText(getActivity(), "Please input secchi depth", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void saveData(){
        if (DataUtils.hasValue(Sechitext)){
            editor.putFloat(DataUtils.EstimateSecchi,Float.valueOf(Sechitext.getText().toString()));
        }
        if (DataUtils.hasValue(Oxygentext)){
            editor.putFloat(DataUtils.EstimateOxygen,Float.valueOf(Oxygentext.getText().toString()));
        }
        editor.apply();
        Log.e("Preference", "Secchi Depth: " + String.valueOf(DataInputLog.getFloat(DataUtils.EstimateSecchi, -1.0f)));
        Log.e("Preference","Oxygen: "+String.valueOf(DataInputLog.getFloat(DataUtils.EstimateOxygen, -1.0f)));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_estimate:
                if (checkInput()){
                    saveData();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }

                break;
        }
    }
}
