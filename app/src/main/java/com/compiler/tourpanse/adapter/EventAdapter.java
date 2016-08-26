package com.compiler.tourpanse.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.compiler.tourpanse.activity.AddEventExpenseMoment;
import com.compiler.tourpanse.R;
import com.compiler.tourpanse.dbhelper.EventDataSource;
import com.compiler.tourpanse.models.Event;

import java.util.ArrayList;

/**
 * Created by Anik Dey on 8/23/2016.
 */
public class EventAdapter extends ArrayAdapter<Event> {

    private Context context;
    private ArrayList<Event> events;
    private Event event;
    private Intent intent;
    private EventDataSource eventDataSource;

    public EventAdapter(Context context, ArrayList<Event> events) {
        super(context, R.layout.event_custom_layout, events);
        this.context = context;
        this.events = events;
    }


    private static class ViewHolder{
        private LinearLayout eventCustomLinearLayout;
        private TextView showEventTitle;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_custom_layout, null, false);
            viewHolder = new ViewHolder();
            viewHolder.showEventTitle = (TextView) convertView.findViewById(R.id.showEventTitle);
            viewHolder.eventCustomLinearLayout = (LinearLayout) convertView.findViewById(R.id.eventCustomLinearLayout);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.showEventTitle.setText(events.get(position).getEventLocation());


        viewHolder.eventCustomLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(context.getApplicationContext(), AddEventExpenseMoment.class);
                intent.putExtra("eventId", events.get(position).getEventId());
                context.startActivity(intent);
            }
        });


        return convertView;
    }





}
