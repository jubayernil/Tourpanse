package com.compiler.tourpanse.helper;


import android.content.Context;
import android.content.SharedPreferences;

public class SaveUserCredentialsToSharedPreference {


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String USER_ID = "userId";


    public SaveUserCredentialsToSharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("tour_mate", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUserCredentials(int userId) {
        editor.putInt(USER_ID, userId);
        editor.commit();
    }


    public int getUserCredentials() {
        int userId = sharedPreferences.getInt(USER_ID, 0);
        return userId;
    }




}
