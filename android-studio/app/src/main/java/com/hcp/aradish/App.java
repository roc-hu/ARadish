package com.hcp.aradish;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.hcp.aradish.newwork.HttpRequestQueue;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by hcp on 15/6/16.
 */
public class App extends Application {
    public static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LeakCanary.install(instance);
        HttpRequestQueue.setApplicationContext(this);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }
}
