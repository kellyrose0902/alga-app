package edu.uwp.alga.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import edu.uwp.alga.R;

/**
 * Created by Kelly on 11/16/2015.
 */
public class ResultUtils {


    public static ArrayList<LineDataSet> getDataSet(Context context, ArrayList<Float> totalDataSet, ArrayList<Float> cyanoDataSet) {

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        if (totalDataSet!= null) {
            //Load Total Chla dataset
            ArrayList<Entry> valueSet1 = new ArrayList<>();
            for (int i = 0; i < totalDataSet.size(); i++) {
                Entry value = new Entry(totalDataSet.get(i), i);

                Log.d("DataTotal", totalDataSet.get(i).toString());
                valueSet1.add(value);
            }
            LineDataSet LineDataSet1 = new LineDataSet(valueSet1, "Total Chla");
            LineDataSet1.setColor(ContextCompat.getColor(context, R.color.WgraphTotal));
            LineDataSet1.setDrawCircles(false);
            LineDataSet1.setCircleColor(ContextCompat.getColor(context, R.color.WgraphTotal));
            LineDataSet1.setLineWidth(5f);
            //LineDataSet1.setLineWidth(0.6f);
            //LineDataSet1.setCubicIntensity(0.05f);

            dataSets.add(LineDataSet1);
        }
        if (cyanoDataSet!= null) {
            // Load Cyano Chla dataset
            ArrayList<Entry> valueSet2 = new ArrayList<>();
            for (int i = 0; i < cyanoDataSet.size(); i++) {
                Entry value = new Entry(cyanoDataSet.get(i), i);
                valueSet2.add(value);
            }
            LineDataSet LineDataSet2 = new LineDataSet(valueSet2, "Cyano Chla");

            LineDataSet2.setColor(ContextCompat.getColor(context, R.color.WgraphCyano));
            LineDataSet2.setDrawCircles(false);
            LineDataSet2.setCircleColor(ContextCompat.getColor(context, R.color.WgraphCyano));
            LineDataSet2.setLineWidth(5f);
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
