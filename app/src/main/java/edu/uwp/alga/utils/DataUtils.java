package edu.uwp.alga.utils;

import android.widget.EditText;

/**
 * Created by Kelly on 11/17/2015.
 */
public class DataUtils {

    public static String mPreference = "input_data";

    //key for all inputs
    public static String PO = "POkey";
    public static String TempSurface = "TempSurfacekey";
    public static String TempBottom = "TempBottomkey";
    public static String LakeDepth = "LakeDepthkey";
    public static String DirectTotal = "DirectTotalChlakey";
    public static String DirectCyano = "DirectCyanoChlakey";
    public static String EstimateOxygen = "EstimateOxygenkey";
    public static String EstimateSecchi = "EstimateSecchikey";

    public static boolean hasValue(EditText editText){
        String value = editText.getText().toString();
        return (value.length()!=0);
    }
}
