package com.hcp.aradish.newwork;

import android.content.Context;

/**
 * Created by hcp on 15/6/16.
 */
public class HttpContextWrapper {

    private Context mContext;
//    private boolean shouldCancel = false;

    /**
     * 通过context判断是否在activity销毁的时候返回http请求的结果
     *
     * @param context
     */
    public HttpContextWrapper(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("You cannot new a HttpContextWrapper on a null Context");
        }
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    /*public void setContext(Context context) {
        this.mContext = context;
    }*/

    /*public boolean isShouldCancel() {
        return shouldCancel;
    }

    public void setShouldCancel(boolean shouldCancel) {
        this.shouldCancel = shouldCancel;
    }*/
}
