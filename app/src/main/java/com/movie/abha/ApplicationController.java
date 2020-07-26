package com.movie.abha;

import android.app.Application;
import android.content.Context;

public class ApplicationController extends Application {
    // Google project id
    public static final String TAG = ApplicationController.class.getName();

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

}
