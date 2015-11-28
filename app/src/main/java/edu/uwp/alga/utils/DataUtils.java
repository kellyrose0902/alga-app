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


    public static boolean hasValue(EditText editText){
        String value = editText.getText().toString();
        return (value.length()!=0);
    }

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
//simple test to check if the data is available

    public static boolean hasData(SharedPreferences preferences){
        if(preferences.contains(PO) && preferences.contains(TempSurface) && preferences.contains(TempBottom)
                && preferences.contains(lux) && preferences.contains(LakeDepth)){
            return true;
        }
        return false;
    }

    /*public static void saveLog(SharedPreferences InputData, SharedPreferences LogData){
        LogData.edit().clear().commit();
        if (InputData.contains(PO)) LogData.edit().putFloat(PO,LogData.getFloat(PO,-1f));
        if (InputData.contains(TempSurface)) LogData.edit().putFloat(TempSurface,InputData.getFloat(TempSurface,-1f));
        if (InputData.contains(TempBottom)) LogData.edit().putFloat(TempBottom,InputData.getFloat(TempBottom,-1f));
        if (InputData.contains(LakeDepth)) LogData.edit().putFloat(LakeDepth,InputData.getFloat(LakeDepth,-1f));
        if (InputData.contains(DirectTotal)) LogData.edit().putFloat(DirectTotal,InputData.getFloat(DirectTotal,-1f));
        if (InputData.contains(DirectCyano)) LogData.edit().putFloat(DirectCyano,InputData.getFloat(DirectCyano,-1f));
        if (InputData.contains(EstimateOxygen)) LogData.edit().putFloat(EstimateOxygen,InputData.getFloat(EstimateOxygen,-1f));
        if (InputData.contains(EstimateSecchi)) LogData.edit().putFloat(EstimateSecchi,InputData.getFloat(EstimateSecchi,-1f));
        if (InputData.contains(lux)) LogData.edit().putFloat(lux,InputData.getFloat(lux,-1f));
        if (InputData.contains(isDirect)) LogData.edit().putBoolean(lux,InputData.getBoolean(isDirect,true));
        String time = getCurrentTimeStamp();
        LogData.edit().putString(TimeStamp,time);

        Log.e("DataLog","get time "+ time);
        LogData.edit().commit();

    }*/

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
