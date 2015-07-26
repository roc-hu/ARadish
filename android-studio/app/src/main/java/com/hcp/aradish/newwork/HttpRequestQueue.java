package com.hcp.aradish.newwork;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by hcp on 15/6/16.
 */
public class HttpRequestQueue {
    private static Context mApplicationContext;
    private static RequestQueue mRequestQueue;

    public static void setApplicationContext(Context context) {
        mApplicationContext = context;
    }

    public synchronized static RequestQueue getRequestQueue () {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mApplicationContext);
        }
        return mRequestQueue;
    }
}
