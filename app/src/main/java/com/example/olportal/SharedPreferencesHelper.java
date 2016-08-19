package com.example.olportal;

import android.content.SharedPreferences;

/**
 * Created by kravchenko on 19/08/16.
 */
public class SharedPreferencesHelper {
    public static String USER_PREFERENCES = "user";
    public static String ACCESS_TOKEN = "accessToken";
    private SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }
}
