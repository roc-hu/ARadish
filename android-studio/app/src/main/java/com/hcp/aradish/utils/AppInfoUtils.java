package com.hcp.aradish.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import com.hcp.aradish.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hcp on 15/7/2.
 */
public class AppInfoUtils {
    private static final String TAG = "hcp_AppInfoUtils";

    public static void println() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nProduct: " + android.os.Build.PRODUCT);
        builder.append(";\nCPU_ABI: " + android.os.Build.CPU_ABI);
        builder.append(";\nTAGS: " + android.os.Build.TAGS);
        builder.append(";\nVERSION_CODES.BASE: " + android.os.Build.VERSION_CODES.BASE);
        builder.append(";\nMODEL: " + android.os.Build.MODEL);// 手机型号
        builder.append(";\nSDK: " + android.os.Build.VERSION.SDK);
        builder.append(";\nVERSION.RELEASE: " + android.os.Build.VERSION.RELEASE);
        builder.append(";\nDEVICE: " + android.os.Build.DEVICE);
        builder.append(";\nDISPLAY: " + android.os.Build.DISPLAY);
        builder.append(";\nBRAND: " + android.os.Build.BRAND);
        ;//手机品牌
        builder.append(";\nBOARD: " + android.os.Build.BOARD);
        builder.append(";\nFINGERPRINT: " + android.os.Build.FINGERPRINT);
        builder.append(";\nID: " + android.os.Build.ID);
        builder.append(";\nMANUFACTURER: " + android.os.Build.MANUFACTURER);
        builder.append(";\nUSER: " + android.os.Build.USER);

        //获取手机号码
        TelephonyManager tm = (TelephonyManager) App.instance.getSystemService(Context.TELEPHONY_SERVICE);
        builder.append(";\nDeviceid: " + tm.getDeviceId());//获取智能设备唯一编号
        builder.append(";\nLine1Number: " + tm.getLine1Number());//手机号码，有的可得，有的不可得
        builder.append(";\nSimSerialNumber: " + tm.getSimSerialNumber());//获得SIM卡的序号
        builder.append(";\nSubscriberId: " + tm.getSubscriberId());//得到用户Id

        builder.append(";\nMac地址: " + getMacAddress());
        builder.append(";\n系统总内存: " + getTotalMemory());
        builder.append(";\n当前可用内存: " + getAvailMemory());
        builder.append(";\n手机CPU信息: " + getCpuInfo());

        builder.append(";\nUMENG_CHANNEL:"+getManiFestMetaData("UMENG_CHANNEL"));
        //返回当前程序版本名
        try {
            // ---get the package info---
            PackageManager pm = App.instance.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(App.instance.getPackageName(), 0);
            builder.append(";\nApp->versionName: " + pi.versionName);
            builder.append(";\nApp->versionCode: " + pi.versionCode);
        } catch (Exception e) {
        }

        Log.i(TAG, builder.toString());
        AppInfoUtils.printSDCardInfo();
        AppInfoUtils.printSystemInfo();
    }

    /**
     * 取得AndroidManifest.xml中meta-data的值
     *
     * @param name meta-data的name
     * @return
     */
    public static String getManiFestMetaData(String name) {
        String value = null;
        try {
            ApplicationInfo appInfo = App.instance.getPackageManager().getApplicationInfo(
                    App.instance.getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = null;
            if (null != appInfo)
                metaData = appInfo.metaData;
            if (null != metaData) {
                value = metaData.getString(name);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取当前android可用内存大小
     *
     * @return
     */
    public static String getAvailMemory() {
        ActivityManager am = (ActivityManager) App.instance.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(App.instance, mi.availMem);// 将获取的内存大小规格化
    }

    /**
     * .获取手机MAC地址
     * 只有手机开启wifi才能获取到mac地址
     */
    public static String getMacAddress() {
        String result = "";
        WifiManager wifiManager = (WifiManager) App.instance.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();
        return result;
    }

    /**
     * 获得系统总内存
     * MemTotal: 所有可用RAM大小。
     * MemFree: LowFree与HighFree的总和，被系统留着未使用的内存。
     * Buffers: 用来给文件做缓冲大小。
     * Cached: 被高速缓冲存储器（cache memory）用的内存的大小（等于diskcache minus SwapCache）。
     * SwapCached:被高速缓冲存储器（cache memory）用的交换空间的大小。已经被交换出来的内存，仍然被存放在swapfile中，用来在需要的时候很快的被替换而不需要再次打开I/O端口。
     * Active: 在活跃使用中的缓冲或高速缓冲存储器页面文件的大小，除非非常必要，否则不会被移作他用。
     * Inactive: 在不经常使用中的缓冲或高速缓冲存储器页面文件的大小，可能被用于其他途径。
     * SwapTotal: 交换空间的总大小。
     * SwapFree: 未被使用交换空间的大小。
     * Dirty: 等待被写回到磁盘的内存大小。
     * Writeback: 正在被写回到磁盘的内存大小。
     * AnonPages：未映射页的内存大小。
     * Mapped: 设备和文件等映射的大小。
     * Slab: 内核数据结构缓存的大小，可以减少申请和释放内存带来的消耗。
     * SReclaimable:可收回Slab的大小。
     * SUnreclaim：不可收回Slab的大小（SUnreclaim+SReclaimable＝Slab）。
     * PageTables：管理内存分页页面的索引表的大小。
     * NFS_Unstable:不稳定页表的大小。
     */
    public static String getTotalMemory() {
        StringBuilder builder = new StringBuilder();
        builder.append("TotalMemory_Start...");
        String str1 = "/proc/meminfo";// 系统内存信息文件

        Pattern pattern = Pattern.compile(":(.*)kB");
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader);
            int c;
            while ((c = localBufferedReader.read()) != -1) {
                String s = localBufferedReader.readLine();
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    long is = Long.valueOf(matcher.group(1).trim());
                    String strIs = Formatter.formatFileSize(App.instance, is * 1024);
                    s = s + "\t[" + strIs + "]";
                }
                //读取meminfo第一行，系统总内存大小
                builder.append("\n").append(s);
            }
            localBufferedReader.close();
        } catch (IOException e) {
        }
        builder.append("\nEnd");
        Log.i(TAG, builder.toString());
        return null;// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 手机CPU信息
     */
    public static String getCpuInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("CpuInfo_Start...");
        String str1 = "/proc/cpuinfo";

        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr);

            int c;
            while ((c = localBufferedReader.read()) != -1) {
                String s = localBufferedReader.readLine();
                builder.append("\n").append(s);
            }

            localBufferedReader.close();
        } catch (IOException e) {
        }
        builder.append("\nEnd");
        Log.i(TAG, builder.toString());
        return null;
    }

    /**
     * StatFs获取的都是以block为单位的，这里我解释一下block的概念：
     * 1.硬件上的 block size, 应该是"sector size"，linux的扇区大小是512byte
     * 2.有文件系统的分区的block size, 是"block size"，大小不一，可以用工具查看
     * 3.没有文件系统的分区的block size，也叫“block size”，大小指的是1024 byte
     * 4.Kernel buffer cache 的block size, 就是"block size"，大部分PC是1024
     * 5.磁盘分区的"cylinder size"，用fdisk 可以查看。
     * 我们这里的block size是第二种情况，一般SD卡都是fat32的文件系统，block size是4096.
     * 这样就可以知道手机的内部存储空间和sd卡存储空间的总大小和可用大小了。
     **/
    public static void printSDCardInfo() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            printlnDiskInfo("SDCardInfo", sf);
        } else {
            Log.i(TAG, "未找到SDCard");
        }
    }

    public static void printSystemInfo() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        printlnDiskInfo("SystemInfo", sf);
    }

    private static void printlnDiskInfo(String diskType, StatFs sf) {
        long blockSize, blockCount, availCount;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = sf.getBlockSizeLong();
            blockCount = sf.getBlockCountLong();
            availCount = sf.getAvailableBlocksLong();
        } else {
            blockSize = sf.getBlockSize();
            blockCount = sf.getBlockCount();
            availCount = sf.getAvailableBlocks();
        }

        StringBuilder builder = new StringBuilder();
        builder.append(diskType);
        builder.append(":[" + "block大小:").append(blockSize).append(";\t");
        builder.append("block数目:").append(blockCount).append(";\t");
//        builder.append("总大小:").append(blockSize * blockCount / 1024).append("KB;\t");
        builder.append("总大小:").append(Formatter.formatFileSize(App.instance, blockSize * blockCount)).append("\t");
        builder.append("可用的block数目:").append(availCount).append(";\t");
//        builder.append("剩余空间:").append(availCount * blockSize / 1024).append("KB]");
        builder.append("剩余空间:").append(Formatter.formatFileSize(App.instance, availCount * blockSize)).append("]");

        Log.i(TAG, builder.toString());
    }

}
