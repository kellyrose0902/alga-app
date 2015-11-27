package edu.uwp.alga.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import edu.uwp.alga.R;

/**
 * Created by Kelly on 11/26/2015.
 */
public class LogAdapter extends BaseExpandableListAdapter {
    private Context context;
    public List<String> logHeader; // header titles
    // child data in format of header title, child title
    public HashMap<String, List<String>> logChild;

    public LogAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {

        this.context = context;
        this.logHeader = listDataHeader;
        this.logChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return logChild.get(this.logHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.log_list_item, null);
        }
        TextView childTime = (TextView)convertView.findViewById(R.id.log_hour);
        String hour = (logChild.get(logHeader.get(groupPosition))).get(childPosition).substring(16,23);
        childTime.setText(hour);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.logChild.get(logHeader.get(groupPosition)).size();
        //return 4;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return logHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return logHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.log_list_header, null);
        }
        TextView headerText = (TextView)convertView.findViewById(R.id.log_date);
        String headerDate = logHeader.get(groupPosition);
        headerText.setText(headerDate);


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
