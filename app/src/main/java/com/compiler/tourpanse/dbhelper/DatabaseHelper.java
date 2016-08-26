package com.compiler.tourpanse.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{

    static final String DATABASE_NAME = "tourpanse_database";
    static final int DATABASE_VERSION = 1;

    // TABLE SETUP FOR USERS
    static final String TABLE_USERS="users";
    static final String COL_ID="id";
    static final String COL_FULL_NAME="full_name";
    static final String COL_EMAIL="email";
    static final String COL_PHONE_NUMBER="phone_number";
    static final String COL_EMERGENCY_PHONE_NUMBER="emergency_phone_number";
    static final String COL_USERNAME="username";
    static final String COL_PASSWORD="password";
    private static final String CREATE_TABLE_USERS = "create table "+TABLE_USERS
            +"( " + COL_ID + " integer primary key, " +
                    COL_FULL_NAME +" text, " +
                    COL_EMAIL +" text, " +
                    COL_PHONE_NUMBER +" text, " +
                    COL_EMERGENCY_PHONE_NUMBER +" text, " +
                    COL_USERNAME +" text, " +
                    COL_PASSWORD+
                    " text);";


    // TABLE SETUP FOR EVENTS
    static final String TABLE_EVENTS = "events";
    static final String COL_EVENT_LOCATION = "event_location";
    static final String COL_TRAVEL_STARTING_DATE = "travel_starting_date";
    static final String COL_TRAVEL_DURATION = "travel_duration";
    static final String COL_ESTIMATED_BUDGET = "estimated_budget";
    static final String COL_USER_ID = "user_id";


    private static final String CREATE_TABLE_EVENTS = "create table " + TABLE_EVENTS + " ("+
            COL_ID + " integer primary key, " +
            COL_EVENT_LOCATION + " text, " +
            COL_TRAVEL_STARTING_DATE + " text, " +
            COL_TRAVEL_DURATION + " text, " +
            COL_ESTIMATED_BUDGET + " text, " +
            COL_USER_ID + " text);";

    // TABLE SETUP FOR EXPENSES
    static final String TABLE_EXPENSES = "expenses";
    static final String COL_EXPENSE_PURPOSE = "expense_purpose";
    static final String COL_AMOUNT = "amount";
    static final String COL_EVENT_ID = "event_id";

    private static final String CREATE_TABLE_EXPENSES = "create table " + TABLE_EXPENSES + " ("+
            COL_ID + " integer primary key, " +
            COL_EXPENSE_PURPOSE + " text, " +
            COL_AMOUNT + " text, " +
            COL_EVENT_ID + " text, " +
            COL_USER_ID + " text);";

    // TABLE SETUP FOR MOMENTS
    static final String TABLE_MOMENTS = "moments";
    static final String COL_MOMENT_LOCATION = "expense_purpose";
    static final String COL_MOMENT_REMARK = "amount";
    static final String COL_PICTURE_PATH = "picture_path";
    static final String COL_PICTURE_CAPTION = "caption";


    private static final String CREATE_TABLE_MOMENTS = "create table " + TABLE_MOMENTS + " ("+
            COL_ID + " integer primary key, " +
            COL_MOMENT_LOCATION + " text, " +
            COL_PICTURE_PATH + " text, " +
            COL_PICTURE_CAPTION + " text, " +
            COL_MOMENT_REMARK + " text, " +
            COL_EVENT_ID + " text, " +
            COL_USER_ID + " text);";

    // TABLE SETUP FOR GALLERIES
//    static final String TABLE_GALLERIES = "galleries";
//
//    static final String COL_MOMENT_ID = "moment_id";
//    private static final String CREATE_TABLE_GALLERIES = "create table " + TABLE_GALLERIES + " ("+
//            COL_ID + " integer primary key, " +
//            COL_PICTURE_PATH + " text, " +
//            COL_MOMENT_ID + " text, " +
//            COL_EVENT_ID + " text, " +
//            COL_USER_ID + " text);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_EVENTS);
        db.execSQL(CREATE_TABLE_EXPENSES);
        db.execSQL(CREATE_TABLE_MOMENTS);
     //   db.execSQL(CREATE_TABLE_GALLERIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_USERS);
        db.execSQL("drop table if exists " + TABLE_EVENTS);
        db.execSQL("drop table if exists " + TABLE_EXPENSES);
        db.execSQL("drop table if exists " + TABLE_MOMENTS);
       // db.execSQL("drop table if exists " + TABLE_GALLERIES);
        onCreate(db);
    }
}
