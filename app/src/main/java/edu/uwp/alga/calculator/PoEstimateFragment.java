package edu.uwp.alga.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;

import edu.uwp.alga.MainActivity;
import edu.uwp.alga.R;
import edu.uwp.alga.utils.DataUtils;

/**
 * Created by Francisco on 11/25/2015.
 * Straight copy Hanh's logic...
 */
public class PoEstimateFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static DecimalFormat df = new DecimalFormat("0.0000");
    public View rootView;
    Button estimateButton;
    EditText Totaltext;
    EditText Cyanotext;
    SharedPreferences DataInputLog;
    SharedPreferences.Editor editor;
    ImageView background;
    private Button farmButton;
    private Button urbanButton;
    private Button sandButton;
    private Button naturalButton;
    private Button lawnButton;
    private SeekBar plantSeek;
    private SeekBar bloomSeek;
    private TextView po4Val;
    private float location = 0.065f;
    private float landVegetation = 0.002f;
    private float waterVegetation = 0.034f;
    private float recurringBlooms = 0.002f;
    private float scaleBloom = (0.065f-0.002f)/100;
    private float scalePlant = (0.065f-0.034f)/100;


    /**
     * Default empty constructor.
     */
    public PoEstimateFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section number.
     *
     * @param sectionNumber Section number for this fragment
     * @return new instance of {@link CalculatorFragment}
     */
    public static PoEstimateFragment create(int sectionNumber) {
        PoEstimateFragment fragment = new PoEstimateFragment();
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
        rootView = inflater.inflate(R.layout.fragment_po4_estimate, container, false);
        //Save data
        Context context = getActivity();
        DataInputLog = context.getSharedPreferences(DataUtils.mPreference,
                Context.MODE_PRIVATE);
        editor = DataInputLog.edit();
        initializeViewId();
        background = (ImageView)rootView.findViewById(R.id.po4_estimate_BG);
        background.setImageBitmap(getBackground());
        calculatePO4();
        bloomSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                recurringBlooms = 0.002f + (progress * scaleBloom);
                calculatePO4();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        plantSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waterVegetation = 0.034f + progress * scalePlant;
                calculatePO4();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return rootView;
    }

    private void initializeViewId() {
        estimateButton = (Button) rootView.findViewById(R.id.po4_submit_estimate);
        estimateButton.setOnClickListener(this);

        farmButton = (Button)rootView.findViewById(R.id.farmButton);
        farmButton.setOnClickListener(this);
        urbanButton = (Button)rootView.findViewById(R.id.urbanButton);
        urbanButton.setOnClickListener(this);
        naturalButton = (Button)rootView.findViewById(R.id.naturalButton);
        naturalButton.setOnClickListener(this);
        lawnButton = (Button)rootView.findViewById(R.id.lawnButton);
        lawnButton.setOnClickListener(this);
        sandButton = (Button)rootView.findViewById(R.id.sandButton);
        sandButton.setOnClickListener(this);
        po4Val = (TextView)rootView.findViewById(R.id.po4_estimate_val);
        plantSeek = (SeekBar)rootView.findViewById(R.id.plant_seekbar);
        bloomSeek = (SeekBar)rootView.findViewById(R.id.bloom_seekbar);
    }





    public void saveData() {
        editor.putFloat(DataUtils.PO,Float.valueOf(po4Val.getText().toString()));
        editor.apply();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.po4_submit_estimate:
                saveData();
                editor.putBoolean(DataUtils.isSetPO, true);
                editor.commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.farmButton:
                clickButton(farmButton);
                unClickButton(urbanButton);
                location = 0.065f;
                calculatePO4();
                break;
            case R.id.urbanButton:
                clickButton(urbanButton);
                unClickButton(farmButton);
                location = 0.034f;
                calculatePO4();
                break;
            case R.id.naturalButton:
                clickButton(naturalButton);
                unClickButton(lawnButton);
                unClickButton(sandButton);
                landVegetation = 0.002f;
                calculatePO4();
                break;
            case R.id.lawnButton:
                clickButton(lawnButton);
                unClickButton(sandButton);
                unClickButton(naturalButton);
                landVegetation = 0.065f;
                calculatePO4();
                break;
            case R.id.sandButton:
                clickButton(sandButton);
                unClickButton(lawnButton);
                unClickButton(naturalButton);
                landVegetation = 0.002f;
                calculatePO4();
                break;

        }
    }
    private Bitmap getBackground(){

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        Log.e("Background", String.valueOf(bitmap.getWidth()));
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        bitmap =Bitmap.createScaledBitmap(bitmap, width, height, true);
        Log.e("Background",String.valueOf(bitmap.getWidth()));

        return bitmap;
    }
    public void clickButton(Button b){
        b.setBackgroundResource(R.drawable.background_primary);
        b.setTextColor(ContextCompat.getColor(getActivity(), R.color.WText));

    }

    public void unClickButton(Button b){
        b.setBackgroundResource(R.drawable.background_white);
        b.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
    }

    public void calculatePO4(){
        float value = 0.1f*location + 0.4f*landVegetation + 0.1f*waterVegetation + 0.4f*recurringBlooms;
        float finalVal = 44.2222f* value - 0.36f;
        po4Val.setText(df.format(finalVal));
    }



}
