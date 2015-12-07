package edu.uwp.alga.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import edu.uwp.alga.R;

/**
 * Created by Kelly on 11/27/2015.
 */
public class DataSetAdapter extends BaseExpandableListAdapter {
    public static DecimalFormat df = new DecimalFormat("#.00");
    public List<String> dataHeader; // header titles
    // child data in format of header title, child title
    public HashMap<String, List<Float>> dataChild;
    private Context context;

    public DataSetAdapter(Context context, List<String> listDataHeader,
                          HashMap<String, List<Float>> listChildData) {

        this.context = context;
        this.dataHeader = listDataHeader;
        this.dataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return dataChild.get(this.dataHeader.get(groupPosition))
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
            convertView = infalInflater.inflate(R.layout.dialog_item, null);
        }
        TextView value = (TextView) convertView.findViewById(R.id.setValue);
        value.setText(df.format((dataChild.get(dataHeader.get(groupPosition))).get(childPosition)));

        TextView index = (TextView) convertView.findViewById(R.id.setIndex);
        String indexValue = String.valueOf(childPosition * 24);
        index.setText("[ " + indexValue + " ]");

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.dataChild.get(dataHeader.get(groupPosition)).size();
        //return 4;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return dataHeader.size();
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
            convertView = infalInflater.inflate(R.layout.dialog_header, null);
        }
        TextView header = (TextView) convertView.findViewById(R.id.setHeader);
        header.setText(dataHeader.get(groupPosition));

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
