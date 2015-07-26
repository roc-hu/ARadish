package com.hcp.aradish.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

/**
 * 
 * 1、Log.v 的输出颜色为黑色的，输出大于或等于VERBOSE日志级别的信息
 * 2、Log.d的输出颜色是蓝色的，输出大于或等于DEBUG日志级别的信息
 * 3、Log.i的输出为绿色，输出大于或等于INFO日志级别的信息
 * 4、Log.w的输出为橙色, 输出大于或等于WARN日志级别的信息
 * 5、Log.e的输出为红色，仅输出ERROR日志级别的信息.
 * 
 * 各个Log等级的使用
 * Verbose: 开发调试过程中一些详细信息，不应该编译进产品中，只在开发阶段使用。（参考api文档的描述：Verbose should never be compiled into anapplication except during development）
 * Debug: 用于调试的信息，编译进产品，但可以在运行时关闭。（参考api文档描述：Debug logs are compiled in but stripped atruntime）
 * Info:例如一些运行时的状态信息，这些状态信息在出现问题的时候能提供帮助。
 * Warn：警告系统出现了异常，即将出现错误。
 * Error：系统已经出现了错误。
 * 
 * Info、Warn、Error这三个等级的Log的警示作用依次提高，需要一直保留。这些信息在系统异常时能提供有价值的分析线索。
 * 
 * 
 * 日志读写到sd卡
 * <!-- 往SDCard的创建与删除文件权限 -->
 * <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
 * <!-- 往SDCard写入数据权限 -->
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 */
@SuppressLint("SimpleDateFormat") 
public final class ALog {
	private static final String TAG = "hcp_ILog";
	
	private static final String path = Environment.getExternalStorageDirectory()+"/ARadish/log/";
	
	private static volatile boolean writeDebugLogs = true;//Debug logs open
	private static volatile boolean writeLogs = true;//Print Log
	private static volatile boolean writeSDLogs = true;//write Log to sd

	private ALog() {}

	public static void writeDebugLogs(boolean writeDebugLogs) {
		ALog.writeDebugLogs = writeDebugLogs;
	}

	public static void writeLogs(boolean writeLogs) {
		ALog.writeLogs = writeLogs;
	}

	/**
	 * logging call. DEBUG
     * @param msgObj  String || Throwable
	 */
	public static void d(Object msgObj) {
		if (writeDebugLogs)
			log(Log.DEBUG, TAG, null, msgObj);
	}
	/**
	 * logging call. DEBUG
     * @param key msg key.
     * @param msgObj  String || Throwable
	 */
	public static void d(String key, Object msgObj) {
		if (writeDebugLogs)
			log(Log.DEBUG, TAG, key, msgObj);
	}
	/**
	 * logging call. DEBUG
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param key msg key.
     * @param msgObj  String || Throwable
	 */
	public static void d(String tag,String key, Object msgObj) {
		if (writeDebugLogs)
			log(Log.DEBUG, tag, key, msgObj);
	}
	

	/**
	 * logging call. INFO
     * @param msgObj  String || Throwable
	 */
	public static void i(Object msgObj) {
		log(Log.INFO, TAG, null, msgObj);
	}
	/**
	 * logging call. INFO
     * @param key msg key.
     * @param msgObj  String || Throwable
	 */
	public static void i(String key, Object msgObj) {
		log(Log.INFO, TAG, key, msgObj);
	}
	/**
	 * logging call. INFO
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param key msg key.
     * @param msgObj  String || Throwable
	 */
	public static void i(String tag,String key, Object msgObj) {
		log(Log.INFO, tag, key, msgObj);
	}
	
	/**
	 * logging call. WARN
     * @param msgObj  String || Throwable
	 */
	public static void w(Object msgObj) {
		log(Log.WARN, TAG, null, msgObj);
	}
	/**
	 * logging call. WARN
     * @param key msg key.
     * @param msgObj  String || Throwable
	 */
	public static void w(String key, Object msgObj) {
		log(Log.WARN, TAG, key, msgObj);
	}
	/**
	 * logging call. WARN
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param key msg key.
     * @param msgObj  String || Throwable
	 */
	public static void w(String tag,String key, Object msgObj) {
		log(Log.WARN, tag, key, msgObj);
	}

	/**
	 * logging call. ERROR
     * @param msgObj  String || Throwable
	 */
	public static void e(Object msgObj) {
		log(Log.ERROR, TAG, null, msgObj);
	}
	/**
	 * logging call. ERROR
     * @param key msg key.
     * @param msgObj  String || Throwable
	 */
	public static void e(String key, Object msgObj) {
		log(Log.ERROR, TAG, key, msgObj);
	}
	/**
	 * logging call. ERROR
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param key msg key.
     * @param msgObj  String || Throwable
	 */
	public static void e(String tag,String key, Object msgObj) {
		log(Log.ERROR, tag, key, msgObj);
	}

	/**
	 * 
	 *  logging call.
	 * 
	 * @param priority The priority/type（Log.VERBOSE; Log.DEBUG; Log.INFO; Log.WARN; Log.ERROR; Log.ASSERT） of this log message
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param key msg key.
     * @param msgObj  String || Throwable
     * 
     * @param msg The message you would like logged.
     * @param ex An exception to log
     * 
	 */
	public static void log(int priority,String tag, String key, Object msgObj) {
		if (!writeLogs) return;

		String log=null;//String msg, Throwable ex
		if(msgObj instanceof Throwable){
			if (msgObj!= null) {
				Throwable ex=(Throwable)msgObj;
				log = String.format("%1$s\n%2$s","["+ex.getMessage()+"]", Log.getStackTraceString(ex));
			}
		}else{
			log = msgObj.toString();
		}
		if (key == null || key.length() == 0){
			//日志格式 时间 ［key］msg
			log = String.format("%1$s%2$s",formatterLog.format(new Date())+"\t",log);
		}else{
			//日志格式 时间 ［key］msg
			log = String.format("%1$s%2$s%3$s",formatterLog.format(new Date())+"\t", "["+key+"]", log);
		}

		Log.println(priority,tag, log);
		if(writeSDLogs){
			saveLog2File(priority,log);
		}
	}
	//日志生成的时间
	private static DateFormat formatterLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 用于格式化日期,作为日志文件名的一部分
	private static DateFormat formatterPath = new SimpleDateFormat("yyyy_MM_dd");
	
	/**
	 * 保存错误信息到文件中[UTF-8]
	 * @param priority
	 * @param msg
	 */
	private static void saveLog2File(int priority,String msg) {
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(msg).append("\t[").append(priority).append("]\n");
		try {
			String fileName = formatterPath.format(new Date())+ ".txt";
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				//得到path路径下的文件 日志超过7天 清空一次
				if(dir.listFiles().length>7){
					File[] files = dir.listFiles();
					for (File file : files) {
						if (file.isFile() && file.exists()) {
					        file.delete();
					    }
					}
				}
				
				FileOutputStream fos = new FileOutputStream(path + fileName,true);
				fos.write(sbBuffer.toString().getBytes("UTF-8"));
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "an error occured while writing file...", e);
		}
	}
}
