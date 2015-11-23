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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.utils.Utils;

import edu.uwp.alga.ChlaActivity;
import edu.uwp.alga.R;
import edu.uwp.alga.SubmitActivity;
import edu.uwp.alga.utils.DataUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalculatorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends Fragment implements View.OnClickListener, SensorEventListener{
    public View rootView;
    Button setChlbutton;
    Button submitData;
    EditText POtext;
    EditText TempSurtext;
    EditText TempBottext;
    EditText Depthtext;
    Float lux;

    SensorManager sensorManager;
    Sensor lightSensor;
    /**
     * The fragment argument representing the section number for this
     * fragment. Static because it's shared across all instances of
     * this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    SharedPreferences DataInputLog;
    SharedPreferences.Editor editor;

    SharedPreferences SaveLog;
    SharedPreferences stateData;
    private int dataPtr;

    public CalculatorFragment() {}

    /**
     * Returns a new instance of this fragment for the given section number.
     *
     * @param sectionNumber Section number for this fragment
     * @return new instance of {@link CalculatorFragment}
     */
    public static CalculatorFragment newInstance(int sectionNumber) {
        CalculatorFragment fragment = new CalculatorFragment();
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
        rootView = inflater.inflate(R.layout.fragment_calculator, container, false);

        Context context = getActivity();
        getLux();

        //Manage data resource to save log and current input
        stateData = PreferenceManager.getDefaultSharedPreferences(context);
        dataPtr = stateData.getInt(DataUtils.current, 0);
        SaveLog = context.getSharedPreferences(DataUtils.DataLog+String.valueOf(dataPtr),Context.MODE_PRIVATE);
        DataInputLog = context.getSharedPreferences(DataUtils.mPreference,
                Context.MODE_PRIVATE);
        editor = DataInputLog.edit();

        initializeViewId(context);
        initializeValue();
        return rootView;
    }

    private void initializeViewId(Context context) {
        setChlbutton = (Button)rootView.findViewById(R.id.buttonChla);
        setChlbutton.setOnClickListener(this);

        submitData = (Button)rootView.findViewById(R.id.SubmitAll);
        submitData.setOnClickListener(this);
        POtext = (EditText)rootView.findViewById(R.id.po_edit);
        TempSurtext = (EditText)rootView.findViewById(R.id.temp_surface_edit);
        TempBottext = (EditText) rootView.findViewById(R.id.temp_bottom_edit);
        Depthtext = (EditText) rootView.findViewById(R.id.lake_depth_edit);

        if(DataInputLog.getBoolean(DataUtils.isSetChla, false)){
            setChlbutton.setBackgroundResource(R.drawable.set_button_xml);
            setChlbutton.setText(context.getResources().getString(R.string.tick));

            Log.e("Fragment", "setbutton");

        }
    }

    private void initializeValue(){
            if(DataInputLog.contains(DataUtils.PO)){
                POtext.setText(String.valueOf(DataInputLog.getFloat(DataUtils.PO,0f)));
            }
            if(DataInputLog.contains(DataUtils.TempSurface)){
                TempSurtext.setText(String.valueOf(DataInputLog.getFloat(DataUtils.TempSurface,0f)));
            }
            if(DataInputLog.contains(DataUtils.TempBottom)){
                TempBottext.setText(String.valueOf(DataInputLog.getFloat(DataUtils.TempBottom,0f)));
            }
            if (DataInputLog.contains(DataUtils.LakeDepth)){
                Depthtext.setText(String.valueOf(DataInputLog.getFloat(DataUtils.LakeDepth,0f)));
            }
        }

    public void getLux() {
        sensorManager
                = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        lightSensor
                = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            lux = 12000f;
            Log.e("Light", String.valueOf(lux));
        } else {
            lux = lightSensor.getMaximumRange(); // first calibration
            sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonChla:
                saveData();
                Intent intentChl = new Intent(getActivity(), ChlaActivity.class);
                startActivity(intentChl);
                break;
            case R.id.SubmitAll:

                if (checkInput()){
                    editor.putFloat(DataUtils.lux, lux);
                    SaveLog.edit().clear().commit();
                    saveData();
                    DataUtils.saveLog(DataInputLog,SaveLog);
                    dataPtr = dataPtr + 1; // increment log pointer
                    if(dataPtr == 10) dataPtr = 0; // reset log to store only 10 logs => change this to achieve more
                    Log.e("Pointer",String.valueOf(dataPtr));
                    stateData.edit().putInt(DataUtils.current,dataPtr).apply();
                    Intent intentData = new Intent(getActivity(), SubmitActivity.class);
                    startActivity(intentData);
                }
                else {
                    Toast.makeText(getActivity(),"Please input more data",Toast.LENGTH_SHORT).show();
                }
                //test clear

                break;


        }
    }

    public boolean checkInput(){
        if (!DataUtils.hasValue(POtext)||!DataUtils.hasValue(TempSurtext)||!DataUtils.hasValue(TempBottext)||!DataUtils.hasValue(Depthtext)){
            return false;
        }
        else{
            Float value;
            value = Float.valueOf(POtext.getText().toString());
            if (value<0.0001 || value>7){
                Toast.makeText(getActivity(),"Please input PO4 concentation between 0.0001 and 7",Toast.LENGTH_SHORT).show();
                return false;
            }

            value = Float.valueOf(TempSurtext.getText().toString());
            Float surtempVal = value;
            if (value<0 || value>40){
                Toast.makeText(getActivity(),"Please input Surface between 0 and 40",Toast.LENGTH_SHORT).show();
                return false;
            }
            value = Float.valueOf(TempBottext.getText().toString());
            Float bottempVal = value;
            if (value<0 || value>40){
                Toast.makeText(getActivity(),"Please input Surface between 0 and 40",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(bottempVal>surtempVal){
                Toast.makeText(getActivity(),"Bottom temperature cannot be greater that surface temperature",Toast.LENGTH_SHORT).show();
                return false;
            }

            value = Float.valueOf(Depthtext.getText().toString());
            if (value<=0 || value>5){
                Toast.makeText(getActivity(),"Algal bloom will not happen if lake depth > 5",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!DataInputLog.getBoolean(DataUtils.isSetChla,false)){
            Toast.makeText(getActivity(),"Please set value for Chla",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void saveData(){

        if(DataUtils.hasValue(POtext))
        editor.putFloat(DataUtils.PO,Float.valueOf(POtext.getText().toString()));

        if(DataUtils.hasValue(TempSurtext))
        editor.putFloat(DataUtils.TempSurface,Float.valueOf(TempSurtext.getText().toString()));

        if(DataUtils.hasValue(TempBottext))
        editor.putFloat(DataUtils.TempBottom,Float.valueOf(TempBottext.getText().toString()));

        if(DataUtils.hasValue(Depthtext))
        editor.putFloat(DataUtils.LakeDepth,Float.valueOf(Depthtext.getText().toString()));

        editor.apply();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            lux = event.values[0];
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
