package edu.uwp.alga.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kelly on 11/17/2015.
 */

/*
 * Handling functions related to data files
 * The data logic of the whole app implement Shared Preference, Shared Preference is used to store to current value set
 * add saved log
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
    public static String isSetPO = "isSetPo4key";
    public static String isDirect = "isDirectkey";
    public static String lux = "Luxkey";

    public static String TimeStamp = "Timekey";

    public static String DataLog = "DataLog";
    public static String current = "currentkey";


    // Check whether the input field contains data
    public static boolean hasValue(EditText editText){
        String value = editText.getText().toString();
        return (value.length()!=0);
    }
    // Return the current date and time of the system
    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM - dd - yyyy hh:mm a");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    // Convert date and time string to a Date object
    public static Date convertToDate(String currentTimeStamp){

        SimpleDateFormat df = new SimpleDateFormat("MM - dd - yyyy hh:mm a");
        Date time = null;
        try {
            time = df.parse(currentTimeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
//simple test to check if the SharedPreferences key value is available
    public static boolean hasData(SharedPreferences preferences){
        return preferences.contains(PO) && preferences.contains(TempSurface) && preferences.contains(TempBottom)
                && preferences.contains(lux) && preferences.contains(LakeDepth);
    }


    // Save the value from one SharedPreferenced to another
    public static void saveLog(Context context,String inputLog, String saveLog){

        SharedPreferences InputData = context.getSharedPreferences(inputLog, Context.MODE_PRIVATE);
        SharedPreferences LogData = context.getSharedPreferences(saveLog, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = LogData.edit();
        editor.clear().commit();

        if (InputData.contains(PO)) editor.putFloat(PO,InputData.getFloat(PO,-1f));
        if (InputData.contains(TempSurface)) editor.putFloat(TempSurface,InputData.getFloat(TempSurface,-1f));
        if (InputData.contains(TempBottom)) editor.putFloat(TempBottom,InputData.getFloat(TempBottom,-1f));
        if (InputData.contains(LakeDepth)) editor.putFloat(LakeDepth,InputData.getFloat(LakeDepth,-1f));
        if (InputData.contains(DirectTotal)) editor.putFloat(DirectTotal,InputData.getFloat(DirectTotal,-1f));
        if (InputData.contains(DirectCyano)) editor.putFloat(DirectCyano,InputData.getFloat(DirectCyano,-1f));
        if (InputData.contains(EstimateOxygen)) editor.putFloat(EstimateOxygen,InputData.getFloat(EstimateOxygen,-1f));
        if (InputData.contains(EstimateSecchi)) editor.putFloat(EstimateSecchi,InputData.getFloat(EstimateSecchi,-1f));
        if (InputData.contains(lux)) editor.putFloat(lux,InputData.getFloat(lux,-1f));
        if (InputData.contains(isDirect)) editor.putBoolean(isDirect,InputData.getBoolean(isDirect,true));
        String time = getCurrentTimeStamp();
        editor.putString(TimeStamp, time);

        editor.commit();

    }


}
