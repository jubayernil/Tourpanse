package com.compiler.tourpanse.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.compiler.tourpanse.R;
import com.compiler.tourpanse.dbhelper.MomentDataSource;
import com.compiler.tourpanse.models.Expense;
import com.compiler.tourpanse.models.Moment;

import java.util.ArrayList;

/**
 * Created by Anik Dey on 8/24/2016.
 */
public class MomentAdapter extends ArrayAdapter<Moment> {
    private Context context;
    private ArrayList<Moment> moments;
    private Moment moment;
    private Intent intent;
    private MomentDataSource momentDatasource;

    public MomentAdapter(Context context, ArrayList<Moment> moments) {
        super(context, R.layout.list_of_moments_custom_list_view, moments);
        this.context = context;
        this.moments = moments;
    }

    private static class ViewHolder{
        private TextView showMomentLocationTV;
        private TextView showMomentImageTV;
        private Button showMomentDetail;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_of_moments_custom_list_view, null, false);
            viewHolder = new ViewHolder();
            viewHolder.showMomentLocationTV = (TextView) convertView.findViewById(R.id.showMomentLocationTV);
            viewHolder.showMomentImageTV = (TextView) convertView.findViewById(R.id.showMomentImageTV);
            viewHolder.showMomentDetail = (Button) convertView.findViewById(R.id.showMomentDetail);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.showMomentLocationTV.setText(moments.get(position).getMomentLocation());
        viewHolder.showMomentImageTV.setText(""+moments.get(position).getPicturePath());
        return convertView;
    }


}
