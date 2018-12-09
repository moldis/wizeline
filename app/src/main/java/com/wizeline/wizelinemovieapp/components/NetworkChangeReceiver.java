package com.wizeline.wizelinemovieapp.components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.wizeline.wizelinemovieapp.AppObj;

import org.greenrobot.eventbus.EventBus;


public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        checkRemotely();
    }

    public static void checkRemotely() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> EventBus.getDefault().post(checkInternet(AppObj.getContext())));
    }

    static boolean checkInternet(Context context) {
        ServiceManager serviceManager = new ServiceManager(context);
        if (serviceManager.isNetworkAvailable()) {
            return true;
        } else {
            return false;
        }
    }

}