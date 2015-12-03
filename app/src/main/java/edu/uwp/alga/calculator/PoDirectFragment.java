package edu.uwp.alga.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import edu.uwp.alga.MainActivity;
import edu.uwp.alga.R;
import edu.uwp.alga.utils.DataUtils;

/**
 * Created by Francisco on 11/25/2015.
 * Straight copy/paste Hanh's logic...
 */
public class PoDirectFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    Button directButton;
    public View rootView;
    EditText po4DirectTotal;
    SharedPreferences DataInputLog;
    SharedPreferences.Editor editor;
    ImageView background;
    public PoDirectFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section number.
     *
     * @param sectionNumber Section number for this fragment
     * @return new instance of {@link CalculatorFragment}
     */
    public static PoDirectFragment create(int sectionNumber) {
        PoDirectFragment fragment = new PoDirectFragment();
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
        rootView = inflater.inflate(R.layout.fragment_po4_direct, container, false);
        //Save data
        Context context = getActivity();
        DataInputLog = context.getSharedPreferences(DataUtils.mPreference,
                Context.MODE_PRIVATE);
        editor = DataInputLog.edit();

        po4DirectTotal = (EditText) rootView.findViewById(R.id.po4_direct_total);
        directButton = (Button) rootView.findViewById(R.id.po4_submit_direct);
        directButton.setOnClickListener(this);
        background = (ImageView)rootView.findViewById(R.id.po4_direct_BG);
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
        if(DataInputLog.contains(DataUtils.PO)){
            po4DirectTotal.setText(String.valueOf(DataInputLog.getFloat(DataUtils.PO, 0f)));
            po4DirectTotal.setSelection(po4DirectTotal.getText().length());
        }
    }


    public boolean checkInput(){
        if(!(DataUtils.hasValue(po4DirectTotal))) {
            Toast.makeText(getActivity(), "You did not enter any value.", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            Float value;
            value = Float.valueOf(po4DirectTotal.getText().toString());
            if (value<0.0001 || value>7){
                Toast.makeText(getActivity(),"Please input PO4 concentation between 0.0001 and 7",Toast.LENGTH_SHORT).show();
                return false;
        }



        // TODO: Logic for boundary check.

        return true;
    }
    }



    public void saveData(){
        //editor.clear();
        //editor.commit();
        if (DataUtils.hasValue(po4DirectTotal)){
            editor.putFloat(DataUtils.PO,Float.valueOf(po4DirectTotal.getText().toString()));
        }

        editor.apply();
        Log.e("Preference", "Total PO4: " + String.valueOf(DataInputLog.getFloat(DataUtils.PO, -1.0f)));
    }


    // TODO: Check over savining data correctly Hanh
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.po4_submit_direct:
                if(checkInput()){
                    saveData();
                    editor.putBoolean(DataUtils.isSetPO, true);
                    editor.commit();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }

                break;
        }
    }


}
