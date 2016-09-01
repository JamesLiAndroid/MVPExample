package com.example.lsy_android.mvpmodel.application;

import android.app.Application;

/**
 * Created by jyj-lsy on 9/1/16 in zsl-tech.
 */
public class MyApplication extends Application{

    private static MyApplication instance;
    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
