package com.example.olportal.activities.drawer;

/**
 * Created by kravchenko on 12/08/16.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olportal.R;

import java.util.HashMap;
import java.util.List;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader; // header titles
    private HashMap<String, List<Integer>> listImageId;
    // child data in format of header title, child title
    private HashMap<String, List<String>> listDataChild;

    public MyExpandableListAdapter(Context context, List<String> listDataHeader,
                                   HashMap<String, List<String>> listChildData, HashMap<String, List<Integer>> listImageId) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
        this.listImageId = listImageId;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    public Object getChildIconId(int groupPosition, int childPosititon) {
        return this.listImageId.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        int childIconId = (int) getChildIconId(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.drawer_list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        txtListChild.setText(childText);
        ImageView iconListChild = (ImageView) convertView.findViewById(R.id.expandableListIcon);
        iconListChild.setImageDrawable(context.getResources().getDrawable(childIconId));

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
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
            convertView = infalInflater.inflate(R.layout.drawer_list_group, null);
        }

        String headerTitle = (String) getGroup(groupPosition);
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.groupTitle);
        lblListHeader.setText(headerTitle);
        //изменение цета заголовка и индикатора группы при сворачивании/разворачивании
        ImageView groupIndicator = (ImageView) convertView.findViewById(R.id.group_indicator);
        if (isExpanded) {
            lblListHeader.setTextColor(Color.WHITE);
            groupIndicator.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_down_white));
        } else {
            lblListHeader.setTextColor(context.getResources().getColor(R.color.grey_text));
            groupIndicator.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_down_grey));
        }

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

