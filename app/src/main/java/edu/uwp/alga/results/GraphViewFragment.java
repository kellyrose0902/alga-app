package edu.uwp.alga.results;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import edu.uwp.alga.utils.GraphUtils;

public class GraphViewFragment extends Fragment {
    LineChart chart;
    private static final String ARG_SECTION_NUMBER = "section_number";


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
        float cyano = 9f;
        float p04 =  0.7f;
        float surtemp = 24f;
        float bottemp = 21.5f;
        float depth = 4f;
        float lux = 12000f;

        AlgalDataCal mCyano = new AlgalDataCal(-1f,cyano,p04,surtemp,bottemp,depth,lux);
        AlgalDataCal mTotal = new AlgalDataCal(cyano,-1,p04,surtemp,bottemp,depth,lux);



        LineData lineData = new LineData(GraphUtils.getXAxisValues(), GraphUtils.getDataSet(mTotal.getTotalChlaDataSet(), mCyano.getCyanoChlaDataSet()));
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
        return view;


    }
}
