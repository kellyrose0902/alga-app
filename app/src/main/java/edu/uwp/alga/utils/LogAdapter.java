package edu.uwp.alga.utils;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Kelly on 11/23/2015.
 */
public class LogAdapter extends ArrayAdapter<DataUtils.LogHolder> {
    public LogAdapter(Context context, int resource, List<DataUtils.LogHolder> objects) {
        super(context, resource, objects);
    }
}
