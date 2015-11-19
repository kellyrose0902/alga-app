package edu.uwp.alga.results;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import edu.uwp.alga.R;
import edu.uwp.alga.utils.DataUtils;
import edu.uwp.alga.utils.GraphUtils;

public class GraphViewFragment extends Fragment {
    LineChart chart;
    private static final String ARG_SECTION_NUMBER = "section_number";
    SharedPreferences DataInputLog;
    SharedPreferences.Editor editor;
    public Context context;
    public GraphViewFragment() { }

    public static Fragment create(int sectionNumber) {
        GraphViewFragment fragment = new GraphViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_graph_view, container, false);
        chart = (LineChart)view.findViewById(R.id.chart);
        context = getActivity();
        DataInputLog = context.getSharedPreferences(DataUtils.mPreference,
                Context.MODE_PRIVATE);
        editor = DataInputLog.edit();
        //Use the current value of the input, should change later to get view from datalog
        populateChart(DataInputLog);
        return view;


    }
    public float getLux(){
        float lux;

        SensorManager sensorManager
                = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor
                = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightSensor == null){
            lux = 12000f;
            Log.e("Light",String.valueOf(lux));
        }else {
            lux = lightSensor.getMaximumRange();
            Log.e("Light",String.valueOf(lux));
        }
        return lux;
    }
    private void populateChart(SharedPreferences DataLog) {
        ArrayList<Float> totalDataSet = null;
        ArrayList<Float> cyanoDataSet = null;
        AlgalDataCal myAlgalData;

        float oxygen;
        float secchi;
        float total;
        float cyano;

        SharedPreferences.Editor LogEditor = DataLog.edit();

        float p04 = DataLog.getFloat(DataUtils.PO,0.0001f);
        float surtemp = DataLog.getFloat(DataUtils.TempSurface, 0f);
        float bottemp = DataLog.getFloat(DataUtils.TempBottom,4f);
        float depth = DataLog.getFloat(DataUtils.LakeDepth,0);
        float lux = getLux();

        boolean isDirect = DataLog.getBoolean(DataUtils.isDirect,true);
        if (isDirect){
            total = DataLog.getFloat(DataUtils.DirectTotal,-1);
            cyano = DataLog.getFloat(DataUtils.DirectTotal,-1);
            myAlgalData = new AlgalDataCal(total,cyano,p04,surtemp,bottemp,depth,lux);
            if (total!=1){
                totalDataSet = myAlgalData.getTotalChlaDataSet();
            }
            if(cyano!=-1){
                cyanoDataSet = myAlgalData.getCyanoChlaDataSet();
            }
        }
        else {
            oxygen = DataLog.getFloat(DataUtils.EstimateOxygen,-1);
            secchi = DataLog.getFloat(DataUtils.EstimateSecchi,0.1f);
            myAlgalData = new AlgalDataCal(secchi,oxygen,p04,surtemp,bottemp,depth,lux,true);
            if(oxygen!=-1){
                totalDataSet = myAlgalData.getTotalChlaDataSet();
            }
            cyanoDataSet = myAlgalData.getCyanoChlaDataSet();
        }


        //float p04 =  0.7f;
        //float surtemp = 24f;
        //float bottemp = 21.5f;
       // float depth = 4f;
        //float cyano = 9f;




        LineData lineData = new LineData(GraphUtils.getXAxisValues(), GraphUtils.getDataSet(totalDataSet, cyanoDataSet));
        chart.setData(lineData);
        YAxis yAxis = chart.getAxisLeft();
        yAxis.setAxisMaxValue(300f);
        YAxis yAxis1 = chart.getAxisRight();
        LimitLine ll = new LimitLine(40f, "WHO Risk Limit for HAB: 40 µg");
        ll.setTextSize(10f);
        ll.setTextColor(Color.RED);
        yAxis.addLimitLine(ll);
        yAxis1.setEnabled(false);
        chart.setDescription("My Chart");
        ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) chart.getData()
                .getDataSets();


        chart.animateXY(2000, 2000);
        chart.invalidate();
    }
}
