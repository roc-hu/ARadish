package com.hcp.aradish.utils;


import android.os.Environment;
import android.util.Log;

import com.hcp.aradish.App;

import java.io.File;
import java.math.BigDecimal;

/**
 * 本应用数据清除管理器
 * Created by hcp on 15/6/30.
 */
public class CacheManager {
    /**
     * 本应用内部文件夹位置
     **/
    static final String app_path = "/data/data/" + App.instance.getPackageName();

    /**
     * shared_prefs[/data/data/com.xxx.xxx/shared_prefs下的内容
     **/
    static final String app_shared_prefs = app_path + "/shared_prefs";
    /**
     * databases[/data/data/com.xxx.xxx/databases下的所有数据库]
     **/
    static final String app_databases = app_path + "/databases";

    /**
     * 系统默认webView路径
     **/
    static final String app_webview = app_path + "/app_webview";
    /**
     * 系统默认webView缓存路径
     **/
    static final String[] app_webview_cache = {app_path + "/app_webview/Cache",
            app_path + "/cache/webviewCacheChromium",
            app_path + "/cache/webviewCacheChromiumStaging",
            app_path + "/cache/webviewCache"};

    /**
     * 系统默认webView数据库路径
     **/
    static final String app_webview_db[] = {app_path + "/databases/webview.db",
            app_path + "/databases/webview.db-shm",
            app_path + "/databases/webview.db-wal",
            app_path + "/databases/webviewCache.db"};
    /**
     * 系统默认webView Cookies路径
     **/
    static final String[] app_webview_cookies = {app_path + "/databases/webviewCookiesChromium.db",
            app_path + "/databases/webviewCookiesChromiumPrivate.db",
            app_path + "/cache/Cookies", app_path + "/cache/Cookies-journal",
            app_path + "/cache/Web Data", app_path + "/cache/Web Data-journal"};

    /**
     * 清除外部cache下的内容
     * /mnt/sdcard/android/data/com.xxx.xxx/cache
     * context.getExternalCacheDir()
     */
    public static void deleteExternalCache() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteDir(App.instance.getExternalCacheDir());
        }
    }

    /**
     * 清除本应用内部缓存
     * /data/data/com.xxx.xxx/cache
     * context.getCacheDir()
     */
    public static void deleteInternalCache() {
        deleteDir(App.instance.getCacheDir());
    }

    /**
     * 按名字清除本应用数据库
     *
     * @param dbName
     */
    public static void deleteDatabaseByName(String dbName) {
        App.instance.deleteDatabase(dbName);
    }

    /**
     * 清除本应用内webview缓存
     * /data/data/com.xxx.xxx/app_webview/Cache
     */
    public static void deleteWebViewCache() {
        for (String strPath : app_webview_cache) {
            deleteDir(new File(strPath));
        }
        for (String strPath : app_webview_db) {
            deleteDir(new File(strPath));
        }
    }

    /**
     * 清除本应用内webview缓存，Cookies，Web Data等数据
     * /data/data/com.xxx.xxx/app_webview
     */
    public static void deleteWebView() {
        deleteWebViewCache();
        deleteDir(new File(app_webview));
    }

    /**
     * 清除app相关所有缓存
     */
    public static void cleanAppCache() {
        deleteInternalCache();
        deleteExternalCache();
        deleteWebViewCache();
    }

    /**
     * 清除app相关所有数据
     */
    public static void cleanAppAllData() {
        deleteWebView();
        deleteExternalCache();//清除外部缓存
        deleteInternalCache();
        //files[/data/data/com.xxx.xxx/files下的内容- 目录，一般放一些长时间保存的数据]
        deleteDir(App.instance.getFilesDir());
        deleteDir(new File(app_shared_prefs));//shared_prefs下的内容
        deleteDir(new File(app_databases));//所有数据库
    }

    /**
     * 删除文件夹
     * context.getCacheDir()
     * context.getExternalCacheDir()
     *
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 获取所有缓存大小 包括应用 WebView，内部缓存，外部缓存大小
     *
     * @return
     */
    public static long getAllCacheSize() {
        long size = 0;
        size = getInternalCacheSize() + getExternalCacheSize() + getWebViewCacheSize();
        return size;
    }

    /**
     * 获取内部缓存大小
     *
     * @return
     */
    public static long getInternalCacheSize() {
        long size = 0;
        try {
            size = getFolderSize(App.instance.getCacheDir());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 获取WebView缓存大小
     *
     * @return
     */
    public static long getWebViewCacheSize() {
        long size = 0;
        for (String strPath : app_webview_cache) {
            size = size + getFolderOrFileSize(strPath);
        }
        for (String strPath : app_webview_db) {
            size = size + getFolderOrFileSize(strPath);
        }
        return size;
    }

    /**
     * 获取外部缓存大小
     *
     * @return
     */
    public static long getExternalCacheSize() {
        long size = 0;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            try {
                size = getFolderSize(App.instance.getExternalCacheDir());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return size;
    }

    /**
     * 获得文件大小
     *
     * @param path
     * @return
     */
    public static long getFolderOrFileSize(String path) {
        long size = 0;
        File file = null;
        try {
            file = new File(path);
            if (file.exists()) {
                if (file.isDirectory()) {
                    size = getFolderSize(file);
                } else {
                    size = file.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("hcp_cache", "[getFolderOrFileSize]" + file.getAbsolutePath() + "->" + size);
        return size;
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("hcp_cache", "[getFolderSize]" + file.getAbsolutePath() + "->" + size);
        return size;
    }

    /**
     * 获取缓存大小 单位Byte、KB、MB、GB、TB
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String getCacheSize(File file) throws Exception {
        return getFormatSize(getFolderSize(file));
    }

    /**
     * 格式化 单位Byte、KB、MB、GB、TB
     *  此方法同系统 Formatter.formatFileSize(App.instance, long xx)方法
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    public static void getAllDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            Log.e("hcp_cache", "Dir->Directory:" + dir.getAbsolutePath());
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                getAllDir(new File(dir, children[i]));
            }
        }
        Log.e("hcp_cache", "Dir->File:" + dir.getAbsolutePath());
    }
}
