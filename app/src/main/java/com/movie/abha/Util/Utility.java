package com.movie.abha.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class Utility {

    public static Context appContext;
    private static String SHARE_MOVIE = "SHARE_MOVIE";

    // for username string preferences
    public static void setSharedPreference(Context context, String name, String value) {
        appContext = context;
        SharedPreferences settings = context.getSharedPreferences(SHARE_MOVIE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(name, value);
        editor.commit();
    }


    public static String getSharedPreferences(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(SHARE_MOVIE, 0);
        return settings.getString(name, "");
    }

    public static int getIngerSharedPreferences(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(SHARE_MOVIE, 0);
        return settings.getInt(name, 0);
    }


    public static void setSharedPreferenceBoolean(Context context, String name, boolean value) {
        appContext = context;
        SharedPreferences settings = context.getSharedPreferences(SHARE_MOVIE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static boolean getSharedPreferencesBoolean(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(SHARE_MOVIE, 0);
        return settings.getBoolean(name, false);
    }

}

