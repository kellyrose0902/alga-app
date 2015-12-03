package edu.uwp.alga.calculator;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import edu.uwp.alga.R;

/**
 * Created by Kelly on 11/30/2015.
 */
public class HelpFragmentDialog extends DialogFragment {



    public HelpFragmentDialog() {
        // Empty constructor required for DialogFragment
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_help_po, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);





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
        window.setLayout(width - (width / 20), height - (height / 10));
        window.setGravity(Gravity.NO_GRAVITY);
    }
}
