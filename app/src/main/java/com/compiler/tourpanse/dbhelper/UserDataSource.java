package com.compiler.tourpanse.dbhelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.compiler.tourpanse.models.User;

import java.util.ArrayList;

public class UserDataSource {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private User user;

    public UserDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        database.close();
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<User>();
        this.open();
        Cursor cursor = database.rawQuery("select * from " + DatabaseHelper.TABLE_USERS, null);
        if(cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            for(int i = 0; i<cursor.getCount(); i++) {
                int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                String fullName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FULL_NAME));
                String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_EMAIL));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PHONE_NUMBER));
                String emergencyContactNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_EMERGENCY_PHONE_NUMBER));
                String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PASSWORD));
                User user = new User(userId, fullName, email, phoneNumber, emergencyContactNumber, username, password);
                cursor.moveToNext();
                users.add(user);
            }
        }
        cursor.close();
        this.close();
        return users;
    }


    public boolean saveUser(User user) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_FULL_NAME, user.getFullName());
        contentValues.put(DatabaseHelper.COL_EMAIL, user.getEmail());
        contentValues.put(DatabaseHelper.COL_PHONE_NUMBER, user.getPhoneNumber());
        contentValues.put(DatabaseHelper.COL_EMERGENCY_PHONE_NUMBER, user.getPhoneNumber());
        contentValues.put(DatabaseHelper.COL_USERNAME, user.getUsername());
        contentValues.put(DatabaseHelper.COL_PASSWORD, user.getPassword());

        long inserted = database.insert(DatabaseHelper.TABLE_USERS, null, contentValues);
        this.close();
        if(inserted>0) {
            return true;
        } else {
            return false;
        }
    }



    public boolean isUniqueEmail(String email) {
        boolean result = true;
        this.open();
        Cursor cursor = database.rawQuery("select * from " + DatabaseHelper.TABLE_USERS+" where "+DatabaseHelper.COL_EMAIL+"='" + email + "'", null);
        if(cursor != null && cursor.getCount()>0) {
            cursor.close();
            this.close();
            result = false;
        } else {
            cursor.close();
            this.close();
        }
        return result;
    }

    public boolean isUniqueUsername(String username) {
        boolean result = true;
        this.open();
        Cursor cursor = database.rawQuery("select * from " + DatabaseHelper.TABLE_USERS+" where "+DatabaseHelper.COL_USERNAME+"='" + username + "'", null);
        if(cursor != null && cursor.getCount()>0) {
            cursor.close();
            this.close();
            result = false;
        } else {
            cursor.close();
            this.close();
        }
        return result;
    }


    public User loginUser(String username, String password) {
        this.open();
        Cursor cursor = database.rawQuery("select * from " + DatabaseHelper.TABLE_USERS+" where " + DatabaseHelper.COL_USERNAME+"='" + username + "' AND "+DatabaseHelper.COL_PASSWORD+"='" + password + "' limit 1", null);
        if(cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            for(int i = 0; i<cursor.getCount(); i++) {
                int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                String fullName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FULL_NAME));
                String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_EMAIL));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PHONE_NUMBER));
                String emergencyContactNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_EMERGENCY_PHONE_NUMBER));
                String userName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME));
                String userPassword = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PASSWORD));
                user = new User(userId, fullName, email, phoneNumber, emergencyContactNumber, userName, userPassword);
                cursor.moveToNext();
                cursor.close();
                this.close();
            }
            return user;
        } else {
            cursor.close();
            this.close();
        }
        return user;
    }





    public User findUserById(int id) {
        this.open();
        Cursor cursor = database.query(DatabaseHelper.TABLE_USERS, new String[]{
                DatabaseHelper.COL_ID,
                DatabaseHelper.COL_FULL_NAME,
                DatabaseHelper.COL_EMAIL,
                DatabaseHelper.COL_PHONE_NUMBER,
                DatabaseHelper.COL_EMERGENCY_PHONE_NUMBER,
                DatabaseHelper.COL_USERNAME,
                DatabaseHelper.COL_PASSWORD
        }, DatabaseHelper.COL_ID + "="+ id, null, null, null, null);
        cursor.moveToFirst();
        int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
        String fullName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FULL_NAME));
        String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_EMAIL));
        String phoneNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PHONE_NUMBER));
        String emergencyContactNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_EMERGENCY_PHONE_NUMBER));
        String userName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME));
        String userPassword = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PASSWORD));
        user = new User(userId, fullName, email, phoneNumber, emergencyContactNumber, userName, userPassword);
        this.close();
        return  user;
    }

    public boolean update(int id, User user) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_FULL_NAME, user.getFullName());
        contentValues.put(DatabaseHelper.COL_EMERGENCY_PHONE_NUMBER, user.getPhoneNumber());
        long updated = database.update(DatabaseHelper.TABLE_USERS, contentValues, DatabaseHelper.COL_ID+"="+id, null);
        this.close();
        if(updated>0) {
            return true;
        } else {
            return false;
        }
    }
//
//    public boolean delete(int id) {
//        this.open();
//        long deleted = database.delete(DatabaseHelper.TABLE_CATEGORIES, DatabaseHelper.COL_ID+"="+id, null);
//        this.close();
//        if(deleted>0) {
//            return true;
//        } else {
//            return false;
//        }
//    }

}
