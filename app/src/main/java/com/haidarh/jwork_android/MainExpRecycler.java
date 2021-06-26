package com.haidarh.jwork_android;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainExpRecycler extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Recruiter> listRecruiter;
    private HashMap<Recruiter, ArrayList<Job>> childMapping;

    public MainExpRecycler(Context context, ArrayList<Recruiter> listRecruiter, HashMap<Recruiter, ArrayList<Job>> childMapping){
        this.context = context;
        this.listRecruiter = listRecruiter;
        this.childMapping = childMapping;
    }

    @Override
    public int getGroupCount() {
        return childMapping.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childMapping.get(listRecruiter.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listRecruiter.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childMapping.get(listRecruiter.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final Recruiter headerTitle = (Recruiter) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater)
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item, null);
        }

        TextView ListHeader = (TextView) convertView.findViewById(R.id.tv_title);
        ListHeader.setTypeface(null, Typeface.BOLD);
        ListHeader.setText(headerTitle.getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Job childText = (Job) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater)
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_item, null);
        }
        TextView txtListChild = (TextView) convertView.findViewById(R.id.tv_title);
        String s = childText.getName() + ", " + childText.getCategory();
        txtListChild.setText(s);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
