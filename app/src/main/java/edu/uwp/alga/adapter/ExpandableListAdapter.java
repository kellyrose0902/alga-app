package edu.uwp.alga.adapter;

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
 * Created by Kelly on 11/18/2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    public List<Item> listDataHeader; // header titles
    // child data in format of header title, child title
    public HashMap<Item, List<Item>> listDataChild;
    public static class Item{
        public String title;
        public String value;
    }
    public ExpandableListAdapter(Context context, List<Item> listDataHeader,
                                 HashMap<Item, List<Item>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Item childItem = (Item) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expand_item, null);
        }

        TextView childTitle = (TextView)convertView.findViewById(R.id.item_title);
        TextView childValue = (TextView)convertView.findViewById(R.id.item_content);
        childTitle.setText(childItem.title);
        childValue.setText(childItem.value);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(listDataHeader.get(groupPosition)).size();
        //return 4;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Item GroupItem = (Item) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expand_group, null);
        }

        TextView groupTitle = (TextView)convertView.findViewById(R.id.group_title);
        TextView groupValue = (TextView)convertView.findViewById(R.id.group_content);

        groupTitle.setText(GroupItem.title);
        groupValue.setText(GroupItem.value);

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

