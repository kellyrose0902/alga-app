package edu.uwp.alga.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.uwp.alga.MainActivity;
import edu.uwp.alga.R;
import edu.uwp.alga.utils.DataUtils;

/**
 * Created by Francisco on 11/25/2015.
 * Straight copy Hanh's logic...
 */
public class PoEstimateFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    Button estimateButton;
    public View rootView;
    EditText Totaltext;
    EditText Cyanotext;
    SharedPreferences DataInputLog;
    SharedPreferences.Editor editor;
    private Button farmButton;
    private Button urbanButton;
    private Button sandButton;
    private Button naturalButton;
    private Button lawnButton;

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
        initializeValue();
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

    }

    private void initializeValue() {
        // Implement
    }


    public boolean checkInput() {
        // Implement
        return false;
    }

    public void saveData() {
        // Implement
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.po4_submit_estimate:
                if (checkInput()) {
                    saveData();
                    editor.putBoolean(DataUtils.isSetPO, true);
                    editor.commit();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.farmButton:
                clickButton(farmButton);
                unClickButton(urbanButton);
                break;
            case R.id.urbanButton:
                clickButton(urbanButton);
                unClickButton(farmButton);
                break;
            case R.id.naturalButton:
                clickButton(naturalButton);
                unClickButton(lawnButton);
                unClickButton(sandButton);
                break;
            case R.id.lawnButton:
                clickButton(lawnButton);
                unClickButton(sandButton);
                unClickButton(naturalButton);
                break;
            case R.id.sandButton:
                clickButton(sandButton);
                unClickButton(lawnButton);
                unClickButton(naturalButton);
                break;

        }
    }

    public void clickButton(Button b){
        b.setBackgroundResource(R.drawable.background_primary);
        b.setTextColor(ContextCompat.getColor(getActivity(), R.color.WText));

    }

    public void unClickButton(Button b){
        b.setBackgroundResource(R.drawable.background_white);
        b.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
    }

}
