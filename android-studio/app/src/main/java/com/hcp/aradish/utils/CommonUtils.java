package com.hcp.aradish.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;

/**
 * 工具包
 * Created by hcp on 15/7/3.
 */
public class CommonUtils {
    private static long lastClickTime;
    /**
     * 防止控件被重复点击
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
    /**
     * 判断一个应用在运行
     *
     * @param context
     *            上需要一个权限的： <uses-permission
     *            android:name="android.permission.GET_TASKS" />
     *
     * @param MY_PKG_NAME
     *            应用包名 例："cn.com.argorse.sec.user"
     * @return 在运行返回true，否则，反之
     */
    public static boolean isAppRunning(Context context, String MY_PKG_NAME) {
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(MY_PKG_NAME)
                    || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                isAppRunning = true;
                break;
            }
        }
        return isAppRunning;
    }

    /**
     * 判断当前应用程序处于前台还是后台1
     *
     * @param context
     *            上需要一个权限的： <uses-permission
     *            android:name="android.permission.GET_TASKS" />
     *
     * @return 在前台运行 返回true，否则，反之
     */
    public static boolean isAppRunningTop1(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getPackageName().equals(context.getPackageName())) {// context.getPackageName()
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前应用程序处于前台还是后台 2
     *
     * @param context
     *
     * @param MY_PKG_NAME
     *            应用包名 例："cn.com.argorse.sec.user"
     * @return 在前台运行 返回true，否则，反之
     */
//	public static boolean isAppRunningTop2(Context context) {
//		ActivityManager activityManager = (ActivityManager) context
//				.getSystemService(Context.ACTIVITY_SERVICE);
//		List<RunningAppProcessInfo> appProcesses = activityManager
//				.getRunningAppProcesses();
//		for (RunningAppProcessInfo appProcess : appProcesses) {
//			if (appProcess.processName.equals(context.getPackageName())) {
//				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
//					return true;
//				} else {
//					return false;
//				}
//			}
//		}
//		return false;
//	}
}
