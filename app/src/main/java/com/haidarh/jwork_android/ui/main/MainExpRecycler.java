package com.haidarh.jwork_android.ui.main;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.haidarh.jwork_android.R;
import com.haidarh.jwork_android.classes.Job;
import com.haidarh.jwork_android.classes.Recruiter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Main Expandable Recycler,
 * Adapter untuk data pekerjaan dan recruiter dari main activity <br>
 *
 * @author Haidar Hanif
 */
public class MainExpRecycler extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Recruiter> listRecruiter;
    private HashMap<Recruiter, ArrayList<Job>> childMapping;

    /**
     * Instantiates a new Main exp recycler.
     *
     * @param context       the context
     * @param listRecruiter the list recruiter
     * @param childMapping  the child mapping
     */
    public MainExpRecycler(Context context, ArrayList<Recruiter> listRecruiter, HashMap<Recruiter, ArrayList<Job>> childMapping){
        this.context = context;
        this.listRecruiter = listRecruiter;
        this.childMapping = childMapping;
    }

    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        return listRecruiter.size();
    }

    /**
     * Gets the number of children in a specified group.
     *
     * @param groupPosition the position of the group for which the children
     *            count should be returned
     * @return the children count in the specified group
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return childMapping.get(listRecruiter.get(groupPosition)).size();
    }

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    @Override
    public Object getGroup(int groupPosition) {
        return listRecruiter.get(groupPosition);
    }

    /**
     * Gets the data associated with the given child within the given group.
     *
     * @param groupPosition the position of the group that the child resides in
     * @param childPosition the position of the child with respect to other
     *            children in the group
     * @return the data of the child
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childMapping.get(listRecruiter.get(groupPosition)).get(childPosition);
    }

    /**
     * Gets the ID for the group at the given position. This group ID must be
     * unique across groups.
     *
     * @param groupPosition the position of the group for which the ID is wanted
     * @return the ID associated with the group
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Gets the ID for the given child within the given group. This ID must be
     * unique across all children within the group.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group for which
     *            the ID is wanted
     * @return the ID associated with the child
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * Indicates whether the child and group IDs are stable across changes to the
     * underlying data.
     *
     * @return whether or not the same ID always refers to the same object
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * menampilkan group view yaitu recruiter ke layout group_item
     *
     * @param groupPosition posisi group
     * @param isExpanded kondisi expand (tidak digunakan)
     * @param convertView view untuk inflate ke group item
     * @param parent parent
     * @return view dengan data yang sesuai
     */
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

    /**
     * menampilkan group view yaitu recruiter ke layout child item
     *
     * @param groupPosition posisi group
     * @param isLastChild kondisi last child (tidak digunakan)
     * @param convertView view untuk inflate ke group item
     * @param parent parent
     * @return view dengan data yang sesuai
     */
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

    /**
     * Whether the child at the specified position is selectable.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group
     * @return whether the child is selectable.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
