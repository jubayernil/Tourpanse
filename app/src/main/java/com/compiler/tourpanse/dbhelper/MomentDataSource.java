package com.compiler.tourpanse.dbhelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.compiler.tourpanse.helper.SaveUserCredentialsToSharedPreference;
import com.compiler.tourpanse.models.Moment;

import java.util.ArrayList;

public class MomentDataSource {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Moment moment;
    private SaveUserCredentialsToSharedPreference sfData;
    private Context context;

    public MomentDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        database.close();
    }

    public ArrayList<Moment> getAllMoment(int eventId) {
        ArrayList<Moment> moments = new ArrayList<Moment>();
        this.open();
        sfData = new SaveUserCredentialsToSharedPreference(context);
        Cursor cursor = database.rawQuery("select * from " + DatabaseHelper.TABLE_MOMENTS + " where " +
                DatabaseHelper.COL_USER_ID + " = "+ sfData.getUserCredentials() + " AND "+
                DatabaseHelper.COL_EVENT_ID + " = "+ eventId, null);
        if(cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            for(int i = 0; i<cursor.getCount(); i++) {
                int momentId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                String momentLocation = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MOMENT_LOCATION));
                String momentRemark = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MOMENT_REMARK));
                String picturePath = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE_PATH));
                String pictureCaption = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE_CAPTION));
                int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
                int momentEventId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_EVENT_ID));
                moment = new Moment(momentId, momentLocation, momentRemark, picturePath, pictureCaption, userId, momentEventId);
                cursor.moveToNext();
                moments.add(moment);
            }
        }
        cursor.close();
        this.close();
        return moments;
    }


    public boolean saveEvent(Moment moment) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_MOMENT_LOCATION, moment.getMomentLocation());
        contentValues.put(DatabaseHelper.COL_MOMENT_REMARK, moment.getMomentRemark());
        contentValues.put(DatabaseHelper.COL_PICTURE_PATH, moment.getPicturePath());
        contentValues.put(DatabaseHelper.COL_PICTURE_CAPTION, moment.getPictureCaption());
        contentValues.put(DatabaseHelper.COL_USER_ID, moment.getUserId());
        contentValues.put(DatabaseHelper.COL_EVENT_ID, moment.getEventId());
        long inserted = database.insert(DatabaseHelper.TABLE_MOMENTS, null, contentValues);
        this.close();
        if(inserted>0) {
            return true;
        } else {
            return false;
        }
    }



    public Moment findMomentById(int id) {
        this.open();
        Cursor cursor = database.query(DatabaseHelper.TABLE_MOMENTS,
                new String[]{
                        DatabaseHelper.COL_ID,
                        DatabaseHelper.COL_MOMENT_LOCATION,
                        DatabaseHelper.COL_MOMENT_REMARK,
                        DatabaseHelper.COL_PICTURE_PATH,
                        DatabaseHelper.COL_PICTURE_CAPTION,
                        DatabaseHelper.COL_USER_ID,
                        DatabaseHelper.COL_EVENT_ID}
                , DatabaseHelper.COL_ID + "="+ id, null, null, null, null);
        cursor.moveToFirst();
        int momentId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
        String momentLocation = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MOMENT_LOCATION));
        String momentRemark = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MOMENT_REMARK));
        String picturePath = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE_PATH));
        String pictureCaption = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE_CAPTION));
        int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
        int momentEventId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_EVENT_ID));
        moment = new Moment(momentId, momentLocation, momentRemark, picturePath, pictureCaption, userId, momentEventId);
        this.close();
        return  moment;
    }

    public boolean update(int id, Moment moment) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_MOMENT_LOCATION, moment.getMomentLocation());
        contentValues.put(DatabaseHelper.COL_MOMENT_REMARK, moment.getMomentRemark());
        contentValues.put(DatabaseHelper.COL_PICTURE_PATH, moment.getPicturePath());
        contentValues.put(DatabaseHelper.COL_PICTURE_CAPTION, moment.getPictureCaption());
        contentValues.put(DatabaseHelper.COL_USER_ID, moment.getUserId());
        contentValues.put(DatabaseHelper.COL_EVENT_ID, moment.getEventId());
        long updated = database.update(DatabaseHelper.TABLE_MOMENTS, contentValues, DatabaseHelper.COL_ID+"="+id, null);
        this.close();
        if(updated>0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(int id) {
        this.open();
        long deleted = database.delete(DatabaseHelper.TABLE_MOMENTS, DatabaseHelper.COL_ID+"="+id, null);
        this.close();
        if(deleted>0) {
            return true;
        } else {
            return false;
        }
    }







}
