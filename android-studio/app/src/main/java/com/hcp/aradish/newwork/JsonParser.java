package com.hcp.aradish.newwork;

import android.util.Log;

import com.google.gson.Gson;

/**
 * json数据解析器，负责所有网络返回数据的解析
 * Created by hcp on 15/7/9.
 */
public class JsonParser {
    private static final String TAG = "hcp_JsonParser";

    /**
     * 把json字符串转化成Java bean对象
     *
     * @param jsonString
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T> T fromJson(final String jsonString, final Class<T> classOfT) {
        try {
            return new Gson().fromJson(jsonString, classOfT);
        } catch (Exception e) {
            Log.e(TAG, "json can not convert to " + classOfT.getName() + " " + e.getMessage());
            return null;
        }
    }
}
