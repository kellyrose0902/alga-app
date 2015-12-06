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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    ImageView background;
    SharedPreferences DataInputLog;
    SharedPreferences.Editor editor;
    ImageButton helpSecchi;
    ImageButton helpOxygen;
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
        helpSecchi = (ImageButton) rootView.findViewById(R.id.help_secchi);
        helpSecchi.setOnClickListener(this);
        helpOxygen = (ImageButton) rootView.findViewById(R.id.help_oxygen);
        helpOxygen.setOnClickListener(this);
        Oxygentext = (EditText)rootView.findViewById(R.id.Oxygen_input);
        Sechitext = (EditText)rootView.findViewById(R.id.Secchi_input);
        initializeValue();
        background = (ImageView)rootView.findViewById(R.id.chla_estimate_BG);
        background.setImageBitmap(getBackground());
        return rootView;
    }
    public void initializeValue(){
        if(DataInputLog.contains(DataUtils.EstimateOxygen)){
            Oxygentext.setText(String.valueOf(DataInputLog.getFloat(DataUtils.EstimateOxygen, 0f)));
            Oxygentext.setSelection(Oxygentext.getText().length());
        }

        if(DataInputLog.contains(DataUtils.EstimateSecchi)){
            Sechitext.setText(String.valueOf(DataInputLog.getFloat(DataUtils.EstimateSecchi, 0f)));
            Sechitext.setSelection(Sechitext.getText().length());
        }
    }
    private Bitmap getBackground(){

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        Log.e("Background",String.valueOf(bitmap.getWidth()));
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        bitmap =Bitmap.createScaledBitmap(bitmap, width, height, true);
        Log.e("Background",String.valueOf(bitmap.getWidth()));

        return bitmap;
    }
    //Sanitize data, check if enough fields have been input
    public boolean checkInput(){
        if(!(DataUtils.hasValue(Sechitext))){
            Toast.makeText(getActivity(), "Please input secchi depth", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            Float value = Float.valueOf(Sechitext.getText().toString());
            if (value <= 0 || value > 1) {
                Toast.makeText(getActivity(), "Please input Secchi Depth value between 0 and 1", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if(DataUtils.hasValue(Oxygentext)) {
            Float value = Float.valueOf(Oxygentext.getText().toString());
            if (value < 1 || value > 100) {
                Toast.makeText(getActivity(), "Please input Oxygen Dissolved value between 1 and 100", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
            return true;

    }

    //Save all the input data to the shared preference
    public void saveData(){
        if (DataUtils.hasValue(Sechitext)){
            editor.putFloat(DataUtils.EstimateSecchi, Float.valueOf(Sechitext.getText().toString()));
        }
        else editor.remove(DataUtils.EstimateSecchi);
        if (DataUtils.hasValue(Oxygentext)){
            editor.putFloat(DataUtils.EstimateOxygen,Float.valueOf(Oxygentext.getText().toString()));
        }
        else editor.remove(DataUtils.EstimateOxygen);
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
                    editor.putBoolean(DataUtils.isSetChla, true);
                    editor.putBoolean(DataUtils.isDirect,false);
                    editor.commit();
                    deleteDirectData();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.help_secchi:
                showDialog("secchi");
                break;
            case R.id.help_oxygen:
                showDialog("oxygen");
        }
    }

    private void deleteDirectData() {
        editor.remove(DataUtils.DirectTotal);
        editor.remove(DataUtils.DirectCyano);
        editor.apply();
    }

    public void showDialog(String type) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        HelpFragmentDialog helpFragmentDialog = new HelpFragmentDialog();
        helpFragmentDialog.setType(type);
        helpFragmentDialog.show(fm, "Fragment");
    }
}
