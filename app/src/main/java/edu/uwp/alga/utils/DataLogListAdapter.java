package edu.uwp.alga.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.uwp.alga.R;

/**
 * Created by Kelly on 11/22/2015.
 */
public class DataLogListAdapter extends ArrayAdapter<SharedPreferences>{

    private final Activity context;
    public List<String> DataLog;

    public DataLogListAdapter(Activity context, List<String> DataLog) {
        super(context, R.layout.data_list_item);
        this.DataLog = DataLog;
        this.context = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.e("DataLog","get view");
        if(convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.data_list_item,null);

        }
        String logLocation = DataLog.get(position);
        SharedPreferences currentLog = context.getSharedPreferences(logLocation, Context.MODE_PRIVATE);
        String timeStamp = currentLog.getString(DataUtils.TimeStamp, "");
        TextView timeField = (TextView)convertView.findViewById(R.id.time);
        timeField.setText(timeStamp);

        return  convertView;

    }


}
