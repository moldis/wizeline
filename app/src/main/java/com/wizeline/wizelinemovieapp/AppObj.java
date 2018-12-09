package com.wizeline.wizelinemovieapp;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

public class AppObj extends Application {

    static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        this.mContext = this;
        Fresco.initialize(this);
    }

    public static Context getContext() {
        return mContext;
    }
}
