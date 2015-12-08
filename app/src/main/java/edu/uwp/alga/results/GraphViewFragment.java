package edu.uwp.alga.results;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collections;

import edu.uwp.alga.R;
import edu.uwp.alga.utils.DataUtils;
import edu.uwp.alga.utils.ResultUtils;

public class GraphViewFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public Context context;
    LineChart chart;
    SharedPreferences DataInputLog;
    SharedPreferences Logfile;
    Float maxYAxisValue = 40f;

    public GraphViewFragment() {
    }

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
        chart = (LineChart) view.findViewById(R.id.chart);
        context = getActivity();
        DataInputLog = context.getSharedPreferences(DataUtils.mPreference,
                Context.MODE_PRIVATE);



        Intent intent = getActivity().getIntent();
        String logLoc = intent.getStringExtra("LogFile");
        if(logLoc==null){
            populateChart(DataInputLog);
        }
        else {
            Logfile = context.getSharedPreferences(logLoc,
                    Context.MODE_PRIVATE);
            populateChart(Logfile);

        }

        return view;


    }


    private void populateChart(SharedPreferences DataLog) {
        ArrayList<Float> totalDataSet = null;
        ArrayList<Float> cyanoDataSet = null;
        AlgalDataCal myAlgalData;

        float oxygen;
        float secchi;
        float total;
        float cyano;



        float p04 = DataLog.getFloat(DataUtils.PO, 0.0001f);
        float surtemp = DataLog.getFloat(DataUtils.TempSurface, 0f);
        float bottemp = DataLog.getFloat(DataUtils.TempBottom, 4f);
        float depth = DataLog.getFloat(DataUtils.LakeDepth, 0);
        float lux = DataLog.getFloat(DataUtils.lux,12000f);

        boolean isDirect = DataLog.getBoolean(DataUtils.isDirect, true);
        if (isDirect) {
            total = DataLog.getFloat(DataUtils.DirectTotal, -1);
            cyano = DataLog.getFloat(DataUtils.DirectCyano, -1);
            myAlgalData = new AlgalDataCal(total, cyano, p04, surtemp, bottemp, depth, lux);
            if (total != -1) {
                totalDataSet = myAlgalData.getTotalChlaDataSet();

            }
            if (cyano != -1) {
                cyanoDataSet = myAlgalData.getCyanoChlaDataSet();
            }
        } else {
            oxygen = DataLog.getFloat(DataUtils.EstimateOxygen, -1);
            secchi = DataLog.getFloat(DataUtils.EstimateSecchi, 0.1f);
            myAlgalData = new AlgalDataCal(secchi, oxygen, p04, surtemp, bottemp, depth, lux, true);
            if (oxygen != -1) {
                totalDataSet = myAlgalData.getTotalChlaDataSet();
            }
            cyanoDataSet = myAlgalData.getCyanoChlaDataSet();
        }

        //find max value to set Y axis
        if(totalDataSet!=null){
            maxYAxisValue = Collections.max(totalDataSet);
        }
        else if (cyanoDataSet!=null){
            if (Collections.max(cyanoDataSet)>maxYAxisValue)
                maxYAxisValue = Collections.max(cyanoDataSet);
        }


        //float p04 =  0.7f;
        //float surtemp = 24f;
        //float bottemp = 21.5f;
        // float depth = 4f;
        //float cyano = 9f;


        LineData lineData = new LineData(ResultUtils.getXAxisValues(), ResultUtils.getDataSet(context, totalDataSet, cyanoDataSet));
        chart.setData(lineData);
        YAxis yAxis = chart.getAxisLeft();
        if (maxYAxisValue < 40f){
            yAxis.setAxisMaxValue(45f);
        }
        else yAxis.setAxisMaxValue(maxYAxisValue+20f);

        YAxis yAxis1 = chart.getAxisRight();
        LimitLine ll = new LimitLine(40f, "WHO Risk Limit for HAB: 40 µg");
        ll.setLineWidth(5f);
        ll.setTextSize(10f);
        ll.setTextColor(ContextCompat.getColor(context, R.color.WgraphLimit));
        ll.setLineColor(ContextCompat.getColor(context, R.color.WgraphLimit));
        yAxis.addLimitLine(ll);
        yAxis1.setEnabled(false);
        chart.setDescription("Algal Growth Prediction");
        ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) chart.getData()
                .getDataSets();


        chart.animateXY(2000, 2000);
        chart.invalidate();
        chart.setGridBackgroundColor(ContextCompat.getColor(context, R.color.WgraphBG));
    }


}
