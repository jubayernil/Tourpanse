package com.compiler.tourpanse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.compiler.tourpanse.R;
import com.compiler.tourpanse.pojoplace.Result;

import java.util.ArrayList;

/**
 * Created by User on 8/26/2016.
 */
public class PlaceListAdapter extends ArrayAdapter<Result>{
    private Context context;
    private ArrayList<Result> results;

    public PlaceListAdapter(Context context, ArrayList<Result> results) {
        super(context, R.layout.place_list_row ,results);
        this.context = context;
        this.results = results;
    }

    private class ViewHolder{
        TextView placeNameTv, placeAddressTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.place_list_row, null);

            viewHolder.placeNameTv = (TextView) convertView.findViewById(R.id.placeNameTv);
            viewHolder.placeAddressTv = (TextView) convertView.findViewById(R.id.placeAddressTv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            viewHolder.placeNameTv.setText(""+results.get(position).getName());
            viewHolder.placeAddressTv.setText(""+results.get(position).getVicinity());


        return convertView;
    }
}
