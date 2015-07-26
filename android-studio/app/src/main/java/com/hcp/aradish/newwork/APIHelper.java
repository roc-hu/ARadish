package com.hcp.aradish.newwork;

import android.content.Context;

import com.android.volley.Request;

/**
 * Created by hcp on 15/6/16.
 */
public final class APIHelper {
    private static final APIHelper sInstance = new APIHelper();

    private APIHelper() {}

    public static APIHelper getInstance() {
        return sInstance;
    }

    public HttpRequest get(final Context context, String url) {
        HttpContextWrapper wrapper = new HttpContextWrapper(context);
        HttpRequest request = new HttpRequest(Request.Method.GET, url, null, null, wrapper);
        return request;
    }
}
