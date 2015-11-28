package edu.uwp.alga.results;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import edu.uwp.alga.R;

/**
 * Created by Kelly on 11/27/2015.
 */
public class DataSetDialog extends DialogFragment {

    private EditText mEditText;
    public String text = null;


    public DataSetDialog() {
        // Empty constructor required for DialogFragment
    }

    public void setData(String text){
        this.text = text;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_dataset, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        if(text!=null){
            mEditText.setText(text);
        }


        return view;
    }

    @Override
    public void onResume()
    {

        super.onResume();
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        Window window = getDialog().getWindow();
        window.setLayout(width-50, height-300);
        window.setGravity(Gravity.NO_GRAVITY);
    }
}
