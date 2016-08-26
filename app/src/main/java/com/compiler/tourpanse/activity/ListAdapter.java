package com.compiler.tourpanse.activity;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.compiler.tourpanse.R;

import java.util.ArrayList;

/**
 * Created by User on 8/21/2016.
 */
public class ListAdapter implements ExpandableListAdapter {
    private Context context;
    private ArrayList<Group> groups;

    public ListAdapter(Context context, ArrayList<Group> groups) {
        this.context = context;
        this.groups = groups;
    }



    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int i) {
        ArrayList<Child> chList = groups.get(i)
                .getItems();

        return chList.size();
    }

    @Override
    public Object getGroup(int i) {
        return groups.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        ArrayList<Child> chList = groups.get(i)
                .getItems();
        return chList.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        Group group = (Group) getGroup(i);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.group_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.group_name);
        tv.setText(group.getName());
        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        Child child = (Child) getChild(i,
                i1);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.child_item, null);
        }
        //TextView tv = (TextView) view.findViewById(R.id.country_name);

        LinearLayout childOneLayout = (LinearLayout) view.findViewById(R.id.childOneLayout);
        LinearLayout childTwoLayout = (LinearLayout) view.findViewById(R.id.childTwoLayout);
        LinearLayout childThreeLayout = (LinearLayout) view.findViewById(R.id.childThreeLayout);
        //tv.setText(child.getName().toString());
        childOneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RegistrationActivity.class);
                context.startActivity(intent);
            }
        });

        childTwoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        });

        childThreeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RegistrationActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int i) {

    }

    @Override
    public void onGroupCollapsed(int i) {

    }

    @Override
    public long getCombinedChildId(long l, long l1) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long l) {
        return 0;
    }
}
