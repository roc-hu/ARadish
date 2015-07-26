package com.hcp.aradish.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hcp.aradish.App;

import java.io.Serializable;

/**
 * Created by hcp on 15/7/6.
 */
public class SharedPrefsUtils {

    private SharedPrefsUtils() {/*Do not new me*/}

    private static SharedPreferences getPreferences(String shared_prefs_name) {
        SharedPreferences preferences;
        if (TextUtils.isEmpty(shared_prefs_name)) {
            preferences = PreferenceManager.getDefaultSharedPreferences(App.instance);
        } else {
            preferences = App.instance.getSharedPreferences(shared_prefs_name, Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public static boolean put(String key, Object value) {
        return put(null, key, value);
    }

    public static boolean put(String shared_prefs_name, String key, Object value) {
        SharedPreferences.Editor editor = getPreferences(shared_prefs_name).edit();
        if (value instanceof Boolean) {
            editor.putBoolean(key, Boolean.parseBoolean(String.valueOf(value)));
        } else if (value instanceof Float) {
            editor.putFloat(key, Float.parseFloat(String.valueOf(value)));
        } else if (value instanceof Integer) {
            editor.putInt(key, Integer.parseInt(String.valueOf(value)));
        } else if (value instanceof Long) {
            editor.putLong(key, Long.parseLong(String.valueOf(value)));
        } else if (value instanceof String) {
            editor.putString(key, String.valueOf(value));
        } else if (value instanceof Serializable) {
            editor.putString(key, new Gson().toJson(value));
        } else {
            throw new IllegalArgumentException("SharedPrefsUtils.class put method, value must be boolean/float/int/long/String/Serializable type");
        }
        return editor.commit();
    }

    public static boolean remove(String key) {
        return remove(null, key);
    }

    public static boolean remove(String shared_prefs_name, String key) {
        SharedPreferences.Editor editor = getPreferences(shared_prefs_name).edit();
        editor.remove(key);
        return editor.commit();
    }

    public static boolean get(String key, boolean defValue) {
        return get(null, key, defValue);
    }

    public static boolean get(String shared_prefs_name, String key, boolean defValue) {
        return getPreferences(shared_prefs_name).getBoolean(key, defValue);
    }

    public static float get(String key, float defValue) {
        return get(null, key, defValue);
    }

    public static float get(String shared_prefs_name, String key, float defValue) {
        return getPreferences(shared_prefs_name).getFloat(key, defValue);
    }

    public static int get(String key, int defValue) {
        return get(null, key, defValue);
    }

    public static int get(String shared_prefs_name, String key, int defValue) {
        return getPreferences(shared_prefs_name).getInt(key, defValue);
    }

    public static long get(String key, long defValue) {
        return get(null, key, defValue);
    }

    public static long get(String shared_prefs_name, String key, long defValue) {
        return getPreferences(shared_prefs_name).getLong(key, defValue);
    }

    public static String get(String key) {
        return getPreferences(null).getString(key, null);
    }

    public static String get(String shared_prefs_name, String key) {
        return getPreferences(shared_prefs_name).getString(key, null);
    }

    public static <T extends Object> T get(String key, Class<T> classOfT) {
        return get(null, key, classOfT);
    }

    public static <T extends Object> T get(String shared_prefs_name, String key, Class<T> classOfT) {
        String retString = getPreferences(shared_prefs_name).getString(key, null);
        if (!TextUtils.isEmpty(retString)) {
            return new Gson().fromJson(retString, classOfT);
        }
        return null;
    }
}
