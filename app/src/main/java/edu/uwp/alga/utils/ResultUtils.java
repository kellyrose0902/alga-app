package edu.uwp.alga.utils;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/**
 * Created by Kelly on 11/16/2015.
 */
public class ResultUtils {

    public static ArrayList<LineDataSet> getDataSet(ArrayList<Float> totalDataSet, ArrayList<Float>cyanoDataSet){

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        if (totalDataSet!= null) {
            //Load Total Chla dataset
            ArrayList<Entry> valueSet1 = new ArrayList<>();
            for (int i = 0; i < totalDataSet.size(); i++) {
                Entry value = new Entry(totalDataSet.get(i), i);

                Log.d("Data", totalDataSet.get(i).toString());
                valueSet1.add(value);
            }
            LineDataSet LineDataSet1 = new LineDataSet(valueSet1, "Total Chla");
            LineDataSet1.setColor(Color.GREEN);
            LineDataSet1.setColor(Color.GREEN);
            LineDataSet1.setCircleColor(Color.GREEN);
            LineDataSet1.setLineWidth(0f);
            //LineDataSet1.setLineWidth(0.6f);
            //LineDataSet1.setCubicIntensity(0.05f);

            dataSets.add(LineDataSet1);
        }
        if (cyanoDataSet!= null) {
            // Load Cyano Chla dataset
            ArrayList<Entry> valueSet2 = new ArrayList<>();
            for (int i = 0; i < cyanoDataSet.size(); i++) {
                Entry value = new Entry(cyanoDataSet.get(i), i);

                Log.d("Data", cyanoDataSet.get(i).toString());
                valueSet2.add(value);
            }
            LineDataSet LineDataSet2 = new LineDataSet(valueSet2, "Cyano Chla");
            dataSets.add(LineDataSet2);
        }


        return dataSets;
    }
    public static ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i <= 401; i++)
        {
            xAxis.add(String.valueOf(i));
        }

        return xAxis;
    }


}
