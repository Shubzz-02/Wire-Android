package com.shubzz.wire;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_EMPTY = "";
    private static final String KEY = "KEY1";
    private static final String KEY_Longitude = "Longitude";
    private static final String KEY_Latitude = "Latitude";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    public void loginUser(String username, String fullName, String uq_key) {
        mEditor.putString(KEY_USERNAME, username);
        mEditor.putString(KEY_FULL_NAME, fullName);
        mEditor.putString(KEY, uq_key);
        Date date = new Date();

        long millis = date.getTime() + (24 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    public boolean isLoggedIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        return currentDate.before(expiryDate);
    }

    public void setLocation(String longi,String lati){
        mEditor.putString(KEY_Latitude, lati);
        mEditor.putString(KEY_Longitude,longi);
        mEditor.commit();
    }


    String getKey() {
        return mPreferences.getString(KEY,KEY_EMPTY);
    }

    String[] getDetails(){
        String[] arr = {mPreferences.getString(KEY_FULL_NAME,KEY_EMPTY),mPreferences.getString(KEY_USERNAME,KEY_EMPTY)
                    ,mPreferences.getString(KEY,KEY_EMPTY),mPreferences.getString(KEY_Latitude,KEY_EMPTY),mPreferences.getString(KEY_Longitude,KEY_EMPTY)};
        return arr;
    }

    public void logoutUser() {
        mEditor.clear();
        mEditor.commit();
    }

}
