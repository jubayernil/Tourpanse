package com.compiler.tourpanse.dbhelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.compiler.tourpanse.helper.SaveUserCredentialsToSharedPreference;
import com.compiler.tourpanse.models.Event;

import java.util.ArrayList;

public class EventDataSource {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Event event;
    private SaveUserCredentialsToSharedPreference sfData;
    private Context context;

    public EventDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        database.close();
    }

    public ArrayList<Event> getAllEvent() {
        ArrayList<Event> events = new ArrayList<Event>();
        this.open();
        sfData = new SaveUserCredentialsToSharedPreference(context);
        Cursor cursor = database.rawQuery("select * from " + DatabaseHelper.TABLE_EVENTS + " where " + DatabaseHelper.COL_USER_ID + " = "+ sfData.getUserCredentials(), null);
        if(cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            for(int i = 0; i<cursor.getCount(); i++) {
                int eventId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                String eventLocation = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_EVENT_LOCATION));
                String travelStartingDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TRAVEL_STARTING_DATE));
                String travelDuration = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TRAVEL_DURATION));
                String estimatedBudget = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ESTIMATED_BUDGET));
                int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
                event = new Event(eventId, eventLocation, travelStartingDate, travelDuration, estimatedBudget, userId);
                cursor.moveToNext();
                events.add(event);
            }
        }
        cursor.close();
        this.close();
        return events;
    }


    public boolean saveEvent(Event event) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_EVENT_LOCATION, event.getEventLocation());
        contentValues.put(DatabaseHelper.COL_TRAVEL_STARTING_DATE, event.getTravelStartingDate());
        contentValues.put(DatabaseHelper.COL_TRAVEL_DURATION, event.getTravelDuration());
        contentValues.put(DatabaseHelper.COL_ESTIMATED_BUDGET, event.getEstimatedBudget());
        contentValues.put(DatabaseHelper.COL_USER_ID, event.getUserId());
        long inserted = database.insert(DatabaseHelper.TABLE_EVENTS, null, contentValues);
        this.close();
        if(inserted>0) {
            return true;
        } else {
            return false;
        }
    }



    public Event findEventById(int id) {
        this.open();
        Cursor cursor = database.query(DatabaseHelper.TABLE_EVENTS, new String[]{DatabaseHelper.COL_ID,DatabaseHelper.COL_EVENT_LOCATION,
                DatabaseHelper.COL_TRAVEL_STARTING_DATE, DatabaseHelper.COL_TRAVEL_DURATION, DatabaseHelper.COL_ESTIMATED_BUDGET,
                DatabaseHelper.COL_USER_ID}, DatabaseHelper.COL_ID + "="+ id, null, null, null, null);
        cursor.moveToFirst();
        int eventId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
        String eventLocation = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_EVENT_LOCATION));
        String travelStartingDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TRAVEL_STARTING_DATE));
        String travelDuration = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TRAVEL_DURATION));
        String estimatedBudget = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ESTIMATED_BUDGET));
        int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
        event = new Event(eventId, eventLocation, travelStartingDate, travelDuration, estimatedBudget, userId);
        this.close();
        return  event;
    }

    public boolean update(int id, Event event) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_EVENT_LOCATION, event.getEventLocation());
        contentValues.put(DatabaseHelper.COL_TRAVEL_STARTING_DATE, event.getTravelStartingDate());
        contentValues.put(DatabaseHelper.COL_TRAVEL_DURATION, event.getTravelDuration());
        contentValues.put(DatabaseHelper.COL_ESTIMATED_BUDGET, event.getEstimatedBudget());
        contentValues.put(DatabaseHelper.COL_USER_ID, event.getUserId());
        long updated = database.update(DatabaseHelper.TABLE_EVENTS, contentValues, DatabaseHelper.COL_ID+"="+id, null);
        this.close();
        if(updated>0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(int id) {
        this.open();
        long deleted = database.delete(DatabaseHelper.TABLE_EVENTS, DatabaseHelper.COL_ID+"="+id, null);
        this.close();
        if(deleted>0) {
            return true;
        } else {
            return false;
        }
    }


}
