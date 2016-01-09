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
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import edu.uwp.alga.ChlaActivity;
import edu.uwp.alga.Po4Activity;
import edu.uwp.alga.R;
import edu.uwp.alga.SubmitActivity;
import edu.uwp.alga.utils.DataUtils;
import edu.uwp.alga.utils.HelpUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * interface
 * to handle interaction events.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/*
* This class handling taking user input from the inteface
*
*/
public class CalculatorFragment extends Fragment implements View.OnClickListener, SensorEventListener{
    /**
     * The fragment argument representing the section number for this
     * fragment. Static because it's shared across all instances of
     * this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int RECORD_NUMBER = 10;
    public View rootView;
    Button setChlbutton;
    Button setPObutton;
    Button submitData;
    EditText TempSurtext;
    EditText TempBottext;
    EditText Depthtext;
    EditText LuxText;
    ImageButton helpPO;
    ImageButton helpSur;
    ImageButton helpBot;
    ImageButton helpLux;
    ImageButton helpDepth;
    ImageButton helpChla;
    Float lux;
    ImageView background;
    SensorManager sensorManager;
    Sensor lightSensor;
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

        background = (ImageView)rootView.findViewById(R.id.calculatorBG);


        //Manage data resource to save log and current input
        stateData = PreferenceManager.getDefaultSharedPreferences(context);
        dataPtr = stateData.getInt(DataUtils.current, 0);
        SaveLog = context.getSharedPreferences(DataUtils.DataLog + String.valueOf(dataPtr), Context.MODE_PRIVATE);




        getLux();

        DataInputLog = context.getSharedPreferences(DataUtils.mPreference,
                Context.MODE_PRIVATE);

        editor = DataInputLog.edit();

        initializeViewId(context);
        initializeValue();
        return rootView;
    }
    // Inititialize all of the views
    private void initializeViewId(Context context) {
        setChlbutton = (Button)rootView.findViewById(R.id.buttonChla);
        setChlbutton.setOnClickListener(this);

        setPObutton = (Button)rootView.findViewById(R.id.buttonpo4);
        setPObutton.setOnClickListener(this);

        submitData = (Button)rootView.findViewById(R.id.SubmitAll);
        submitData.setOnClickListener(this);

        TempSurtext = (EditText)rootView.findViewById(R.id.temp_surface_edit);
        TempBottext = (EditText) rootView.findViewById(R.id.temp_bottom_edit);
        Depthtext = (EditText) rootView.findViewById(R.id.lake_depth_edit);
        LuxText = (EditText)rootView.findViewById(R.id.luxtext);
        if(DataInputLog.getBoolean(DataUtils.isSetChla, false)){
            setChlbutton.setBackgroundResource(R.drawable.background_primary);
            setChlbutton.setText(context.getResources().getString(R.string.tick));

        }

        if(DataInputLog.getBoolean(DataUtils.isSetPO, false)){
            setPObutton.setBackgroundResource(R.drawable.background_primary);
            setPObutton.setText(context.getResources().getString(R.string.tick));

        }

        helpPO = (ImageButton)rootView.findViewById(R.id.help_po);
        helpPO.setOnClickListener(this);

        helpSur = (ImageButton)rootView.findViewById(R.id.help_sur);
        helpSur.setOnClickListener(this);

        helpBot = (ImageButton)rootView.findViewById(R.id.help_bot);
        helpBot.setOnClickListener(this);

        helpDepth = (ImageButton)rootView.findViewById(R.id.help_depth);
        helpDepth.setOnClickListener(this);

        helpLux = (ImageButton)rootView.findViewById(R.id.help_lux);
        helpLux.setOnClickListener(this);

        helpChla = (ImageButton)rootView.findViewById(R.id.help_chla);
        helpChla.setOnClickListener(this);
    }
    // Inititialize value for the Edittext views, so that user go to another view like "Set PO4 values" all of the previous data will be saved
    private void initializeValue(){

            if(DataInputLog.contains(DataUtils.TempSurface)){
                TempSurtext.setText(String.valueOf(DataInputLog.getFloat(DataUtils.TempSurface,0f)));
                TempSurtext.setSelection(TempSurtext.getText().length());
            }
            if(DataInputLog.contains(DataUtils.TempBottom)){
                TempBottext.setText(String.valueOf(DataInputLog.getFloat(DataUtils.TempBottom,0f)));
                TempBottext.setSelection(TempBottext.getText().length());
            }
            if (DataInputLog.contains(DataUtils.LakeDepth)){
                Depthtext.setText(String.valueOf(DataInputLog.getFloat(DataUtils.LakeDepth, 0f)));
                Depthtext.setSelection(Depthtext.getText().length());
            }
        if (DataInputLog.contains(DataUtils.lux)) {
            LuxText.setText((String.valueOf(DataInputLog.getFloat(DataUtils.lux, 12000))));
            LuxText.setSelection(LuxText.getText().length());
        }
        }

    // Get light intensity from the device light sensor
    public void getLux() {
        sensorManager
                = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        lightSensor
                = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            lux = 12000f;
            Log.e("Light", String.valueOf(lux));
        } else {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }


    }
    // Handling actions for all of the button clicks
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonpo4:
                saveData();
                Intent intentPO = new Intent(getActivity(), Po4Activity.class);
                startActivity(intentPO);
                break;
            case R.id.buttonChla:
                saveData();
                Intent intentChl = new Intent(getActivity(), ChlaActivity.class);
                startActivity(intentChl);
                break;
            case R.id.SubmitAll:

                if (checkInput()){

                    saveData();
                    if (!DataUtils.hasValue(LuxText)) {
                        editor.putFloat(DataUtils.lux, lux);
                    }
                    DataUtils.saveLog(getActivity(),DataUtils.mPreference,DataUtils.DataLog+String.valueOf(dataPtr));

                    dataPtr = dataPtr + 1; // increment log pointer
                    if (dataPtr == RECORD_NUMBER)
                        dataPtr = 0; // reset log to store only 10 logs => change this to achieve more
                    Log.e("Pointer", String.valueOf(dataPtr));
                    stateData.edit().putInt(DataUtils.current,dataPtr).apply();
                    Intent intentData = new Intent(getActivity(), SubmitActivity.class);
                    intentData.putExtra("newData", true);
                    startActivity(intentData);
                }

                //test clear

                break;
            case R.id.help_po:
                showDialog("po");
                break;

            case R.id.help_sur:
                showDialog("surface_temp");
                break;
            case R.id.help_bot:
                showDialog("bottom_temp");
                break;
            case R.id.help_depth:
                showDialog("depth");
                break;
            case R.id.help_lux:
                showDialog("lux");
                break;
            case R.id.help_chla:
                showDialog("chla");
                break;
        }
    }

    public void showDialog(String type){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        HelpFragmentDialog helpFragmentDialog = new HelpFragmentDialog();
        helpFragmentDialog.setType(type);
        helpFragmentDialog.show(fm,"Fragment");
    }

    public boolean checkInput(){
        if (!DataUtils.hasValue(TempSurtext)||!DataUtils.hasValue(TempBottext)||!DataUtils.hasValue(Depthtext)){
            HelpUtils.makeToast(getActivity(),"Please input more data");
            return false;
        }
        else{

            Float value;

            value = Float.valueOf(TempSurtext.getText().toString());
            Float surtempVal = value;
            if (value<0 || value>40){
                HelpUtils.makeToast(getActivity(), "Please input Surface between 0 and 40");
                return false;
            }
            value = Float.valueOf(TempBottext.getText().toString());
            Float bottempVal = value;
            if (value<0 || value>40){
                HelpUtils.makeToast(getActivity(), "Please input Surface between 0 and 40");
                return false;
            }

            if(bottempVal>surtempVal){
                HelpUtils.makeToast(getActivity(), "Bottom temperature cannot be greater that surface temperature");
                return false;
            }

            value = Float.valueOf(Depthtext.getText().toString());
            if (value<=0 || value>5){
                HelpUtils.makeToast(getActivity(), "Algal bloom will not happen if lake depth > 5");
                return false;
            }
        }
        if (!DataInputLog.getBoolean(DataUtils.isSetChla,false)){
            HelpUtils.makeToast(getActivity(), "Please set value for Chla");
            return false;
        }

        if (!DataInputLog.getBoolean(DataUtils.isSetPO, false)){
            HelpUtils.makeToast(getActivity(), "Please set value for Chla");
            return false;
        }

         return true;
    }

    public void saveData(){

        if(DataUtils.hasValue(TempSurtext))
        editor.putFloat(DataUtils.TempSurface,Float.valueOf(TempSurtext.getText().toString()));

        if(DataUtils.hasValue(TempBottext))
        editor.putFloat(DataUtils.TempBottom,Float.valueOf(TempBottext.getText().toString()));

        if(DataUtils.hasValue(Depthtext))
        editor.putFloat(DataUtils.LakeDepth,Float.valueOf(Depthtext.getText().toString()));

        if(DataUtils.hasValue(LuxText)){
            editor.putFloat(DataUtils.lux,Float.valueOf(LuxText.getText().toString()));
        }

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
