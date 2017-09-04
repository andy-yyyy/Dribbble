package com.andy.dribbble.common_utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by andy on 2017/1/15.
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
