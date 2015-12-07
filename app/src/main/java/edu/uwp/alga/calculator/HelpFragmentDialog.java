package edu.uwp.alga.calculator;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import edu.uwp.alga.R;

/**
 * Created by Kelly on 11/30/2015.
 */
public class HelpFragmentDialog extends DialogFragment {


    ImageView mainImage;
    int xDim, yDim;        //stores ImageView dimensions
    private View rootView;
    private String help_type = "";
    public HelpFragmentDialog() {
        // Empty constructor required for DialogFragment
    }

    public void setType(String type) {
        this.help_type = type;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (help_type.equals("surface_temp")) {
            rootView = inflater.inflate(R.layout.dialog_help_sur, container);
        } else if (help_type.equals("bottom_temp")) {
            rootView = inflater.inflate(R.layout.dialog_help_bot, container);
        } else if (help_type.equals("secchi")) {
            rootView = inflater.inflate(R.layout.dialog_help_secchi, container);
        } else if (help_type.equals("oxygen")) {
            rootView = inflater.inflate(R.layout.dialog_help_do, container);
        } else if (help_type.equals("lux")) {
            rootView = inflater.inflate(R.layout.dialog_help_lux, container);
        } else if (help_type.equals("depth")) {
            rootView = inflater.inflate(R.layout.dialog_help_depth, container);
        } else if (help_type.equals("chla")) {
            rootView = inflater.inflate(R.layout.dialog_help_chla, container);
        } else if (help_type.equals("direct")) {
            rootView = inflater.inflate(R.layout.dialog_help_direct, container);
        } else if (help_type.equals("po")) {
            rootView = inflater.inflate(R.layout.dialog_help_po, container);
        } else {
            rootView = inflater.inflate(R.layout.dialog_help_podirect, container);
        }

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //mainImage = (ImageView)view.findViewById(R.id.help_image1);
        //mainImage.setImageBitmap(BitmapHandler.decodeSampledBitmapFromResource(getResources(), R.drawable.dialog_surtemp, xDim, yDim));

        return rootView;
    }


    @Override
    public void onResume()
    {

        super.onResume();
        /*if (!help_type.equals("depth") && !help_type.equals("po")) {
            DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;
            Window window = getDialog().getWindow();
            window.setLayout(width - (width / 20), height - (height / 10));
            window.setGravity(Gravity.NO_GRAVITY);
            //xDim=mainImage.getWidth();
            //yDim=mainImage.getHeight();

        }*/

    }

}
