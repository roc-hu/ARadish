package com.hcp.aradish.utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.hcp.aradish.App;

/**
 * Created by hcp on 15/5/28.
 */
public final class DensityUtils {

    private static final String TAG = "hcp_DensityUtils";

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
        final float scale = App.instance.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
        final float scale = App.instance.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     */
    public static int px2sp(float pxValue) {
//        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        float fontScale = App.instance.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px
     */
    public static int sp2px(float spValue) {
//        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        float fontScale = App.instance.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) App.instance.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) App.instance.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获取屏幕状态栏的高度
     * @return
     */
    public static int getStatusHeight() {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = App.instance.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
    /**
     * 获取当前屏幕截图，包含状态栏
     */
    public static Bitmap snapShotWithStatusBar(Activity activity){
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity){
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }
    /**
     * 获取屏幕宽度
     */
//    public static int getScreenW(Activity mActivity) {
//        DisplayMetrics dm = new DisplayMetrics();
//        dm = mActivity.getResources().getDisplayMetrics();
//        int w = dm.widthPixels;
//        // int w = aty.getWindowManager().getDefaultDisplay().getWidth();
//        return w;
//    }

    /**
     * 获取屏幕高度
     */
//    public static int getScreenH(Activity mActivity) {
//        DisplayMetrics dm = new DisplayMetrics();
//        dm = mActivity.getResources().getDisplayMetrics();
//        int h = dm.heightPixels;
//        // int h = aty.getWindowManager().getDefaultDisplay().getHeight();
//        return h;
//    }

    /**
     * 获取屏幕密度 比
     */
    public static float getDensity() {
        WindowManager wm = (WindowManager) App.instance.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.density;
    }

    /**
     * 获取屏幕密度
     */
    public static int getDensityDpi() {
        WindowManager wm = (WindowManager) App.instance.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.densityDpi;
    }

    public static void println() {
        StringBuilder builder = new StringBuilder();
        builder.append("屏幕[width:").append(getScreenWidth()).append(";\theight:");
        builder.append(getScreenHeight()).append(";\tStatusHeight:");
        builder.append(getStatusHeight()).append(";\tDensity:");
        builder.append(getDensity()).append(";\tDensityDpi:");
        builder.append(getDensityDpi()).append("]");
        Log.i(TAG, builder.toString());
    }
}

