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

import edu.uwp.alga.MainActivity;
import edu.uwp.alga.R;
import edu.uwp.alga.utils.DataUtils;
import edu.uwp.alga.utils.HelpUtils;

public class ChlaDirectFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public View rootView;
    Button directButton;
    EditText Totaltext;
    EditText Cyanotext;
    ImageView background;
    ImageButton helpDirect;
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
        helpDirect = (ImageButton) rootView.findViewById(R.id.help_direct_chla);
        helpDirect.setOnClickListener(this);
        background = (ImageView)rootView.findViewById(R.id.chla_direct_BG);
        background.setImageBitmap(getBackground());
        initializeValue();
        return rootView;
    }
    private Bitmap getBackground(){

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        Log.e("Background",String.valueOf(bitmap.getWidth()));
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        bitmap =Bitmap.createScaledBitmap(bitmap, width, height, true);
        Log.e("Background", String.valueOf(bitmap.getWidth()));

        return bitmap;
    }
    private void initializeValue() {
        if(DataInputLog.contains(DataUtils.DirectTotal)){
            Totaltext.setText(String.valueOf(DataInputLog.getFloat(DataUtils.DirectTotal, 0f)));
            Totaltext.setSelection(Totaltext.getText().length());
        }

        if(DataInputLog.contains(DataUtils.DirectCyano)){
            Cyanotext.setText(String.valueOf(DataInputLog.getFloat(DataUtils.DirectCyano, 0f)));
            Cyanotext.setSelection(Cyanotext.getText().length());
        }
    }


    public boolean checkInput(){
        if(!(DataUtils.hasValue(Totaltext)||DataUtils.hasValue(Cyanotext))){
            HelpUtils.makeToast(getActivity(), "Input at least 1 field");
            return false;
        }
        if(DataUtils.hasValue(Totaltext)){
            Float value = Float.valueOf(Totaltext.getText().toString().replace(',','.'));
            if (value < 0 || value > 300){
                HelpUtils.makeToast(getActivity(), "Please input Total Chl a value between 0 and 300");
                return false;
            }
        }
        if(DataUtils.hasValue(Cyanotext)) {
            Float value = Float.valueOf(Cyanotext.getText().toString().replace(',', '.'));
            if (value < 0 || value > 300) {
                HelpUtils.makeToast(getActivity(), "Please input Cyano Chl a value between 0 and 300");
                return false;
            }
        }
        return true;
    }



    public void saveData(){
        //editor.clear();
        //editor.commit();
        if (DataUtils.hasValue(Totaltext)){
            editor.putFloat(DataUtils.DirectTotal,Float.valueOf(Totaltext.getText().toString().replace(',', '.')));
        }
        else editor.remove(DataUtils.DirectTotal);
        if (DataUtils.hasValue(Cyanotext)){
            editor.putFloat(DataUtils.DirectCyano,Float.valueOf(Cyanotext.getText().toString().replace(',','.')));
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
                    deletePreviousData();
                    saveData();
                    editor.putBoolean(DataUtils.isSetChla, true);
                    editor.putBoolean(DataUtils.isDirect,true);
                    editor.commit();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.help_direct_chla:
                showDialog("direct");
        }
    }

    private void deletePreviousData() {
        editor.remove(DataUtils.EstimateSecchi);
        editor.remove(DataUtils.EstimateOxygen);
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