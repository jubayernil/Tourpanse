package com.compiler.tourpanse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.compiler.tourpanse.R;
import com.compiler.tourpanse.pojo.WeatherList;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by User on 8/27/2016.
 */
public class ForecastListAdapter extends ArrayAdapter<WeatherList> {

    Date date;
    private Context context;
    private ArrayList<WeatherList> weatherListsArray;

    public ForecastListAdapter(Context context, ArrayList<WeatherList> weatherLists) {
        super(context, R.layout.forcast_list_row, weatherLists);
        this.context = context;
        this.weatherListsArray = weatherLists;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        date = new Date(weatherListsArray.get(i).getDt() * 1000);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.forcast_list_row, null);

            viewHolder.dayName = (TextView) convertView.findViewById(R.id.dayNameTvListRow);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.iconListRow);
            viewHolder.maxTemp = (TextView) convertView.findViewById(R.id.tempHighTvListRow);
            viewHolder.minTemp = (TextView) convertView.findViewById(R.id.tempLowTvListRow);
            viewHolder.simpleDateFormat = new SimpleDateFormat("EEEE");

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+6"));
        String currentTime = viewHolder.simpleDateFormat.format(new Date(weatherListsArray.get(i).getDt() * 1000));


        viewHolder.dayName.setText(currentTime);

        Picasso.with(context).load(weatherListsArray.get(i).getWeather().get(0).getIcon()).into(viewHolder.icon);
//
//        viewHolder.icon.setImageResource(R.drawable.cloud_icon);
        viewHolder.maxTemp.setText("" + String.valueOf((int) Math.ceil(weatherListsArray.get(i).getTemp().getMax()))+(char) 0x00B0+"C");
        viewHolder.minTemp.setText("" + String.valueOf((int) Math.ceil(weatherListsArray.get(i).getTemp().getMin()))+(char) 0x00B0+"C");

        return convertView;
    }

    private class ViewHolder{
        TextView dayName;
        ImageView icon;
        TextView maxTemp;
        TextView minTemp;
        SimpleDateFormat simpleDateFormat;
    }
}
