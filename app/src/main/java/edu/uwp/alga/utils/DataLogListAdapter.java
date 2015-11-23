package edu.uwp.alga.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;

import java.util.List;

import edu.uwp.alga.R;

/**
 * Created by Kelly on 11/22/2015.
 */
public class DataLogListAdapter extends ArrayAdapter<SharedPreferences>{

    private Activity context;
    public List<SharedPreferences> DataLog;

    public DataLogListAdapter(Activity context, List<SharedPreferences> DataLog) {
        super(context, R.layout.log_list_item,DataLog);
        this.DataLog = DataLog;
        this.context = context;

    }





    public DataLogListAdapter(Context context, int resource, List<SharedPreferences> objects) {
        super(context, resource, objects);
    }

    public DataLogListAdapter(Context context, int resource, int textViewResourceId, List<SharedPreferences> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
