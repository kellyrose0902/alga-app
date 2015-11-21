package edu.uwp.alga.utils;

import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    public static String isSetChla = "isSetChlakey";
    public static String isDirect = "isDirectkey";
    public static String lux = "Luxkey";
    public static boolean hasValue(EditText editText){
        String value = editText.getText().toString();
        return (value.length()!=0);
    }

    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
