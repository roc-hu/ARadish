package com.hcp.aradish.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 格式化时间
 * Created by hcp on 15/7/3.
 */
public class FormatDate {
    /** yyyy年MM月dd日 HH时mm分ss秒 **/
    public static final String longDatetime_zh="yyyy年MM月dd日 HH时mm分ss秒";
    /** yyyy-MM-dd HH:mm:ss **/
    public static final String longDatetime="yyyy-MM-dd HH:mm:ss";
    /** yyyy年MM月dd日 E周 **/
    public static final String longDateWeek_zh="yyyy年MM月dd日 E";
    /** yyyy年MM月dd日 **/
    public static final String shortDate_zh="yyyy年MM月dd日";
    /** yyyy-MM-dd **/
    public static final String shortDate="yyyy-MM-dd";
    /** yyyyMMddHHmmss **/
    public static final String longDateTimeStr="yyyyMMddHHmmss";
    /** yyyyMMdd **/
    public static final String shortDateStr="yyyyMMdd";
    /** MM月dd日 HH:mm **/
    public static final String shortMdHmStr="MM月dd日 HH:mm";

    /**
     * 格式字符串为Date类型
     * @param date 日期字符串
     * @param dateFormat 日期字符串的格式
     * @return java.util.Date
     * @throws ParseException
     */
    public static Date Format(String date,String dateFormat) throws ParseException{
        return new SimpleDateFormat(dateFormat).parse(date);
    }
    /**
     * 格式化日期
     * @param retFormat 需要返回的格式 例如：yyyy年MM月dd日 E
     * @param date 日期
     *
     * @return String
     */
    public static String Format(String retFormat,Date date){
        return new SimpleDateFormat(retFormat).format(date);
    }
    /**
     * 格式化日期
     * @param retFormat 需要返回的格式 例：yyyy年MM月dd日 E
     * @param date 日期	例: 20131122
     * @param dateFormat 日期的格式	例:yyyyMMdd
     *
     * @return String
     * @throws ParseException
     */
    public static String Format(String retFormat,String date,String dateFormat) throws ParseException{
        Date dateTime=new SimpleDateFormat(dateFormat).parse(date);
        return  new SimpleDateFormat(retFormat).format(dateTime);
    }
    /**
     * 判断当前时间是否在指定时间之间
     * @param starDate star 开始时间
     * @param endDate end 结束时间
     * @return boolean
     * @throws ParseException
     */
    public static boolean isNowTimeBetween(String starDate,String endDate,String dateFormat) throws ParseException{
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
        Date sdate = sdf.parse(starDate);
        Date edate=  sdf.parse(endDate);
        Date date=new Date();
        if(date.after(sdate) && date.before(edate)) {
            return true;
        }
        return false;
    }
    /**
     * 判断当前时间是否在指定时间之间
     * @param starDate star 开始时间
     * @param endDate end 结束时间
     * @return boolean
     * @throws ParseException
     */
    public static boolean isNowTimeBetween(Date starDate,Date endDate,String dateFormat) throws ParseException{
        Date date=new Date();
        if(date.after(starDate) && date.before(endDate)) {
            return true;
        }
        return false;
    }
    /**
     * 计算两个日期之间相差的天数
     * @param minDate minDate 较小的时间
     * @param maxDate maxDate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(String minDate,String maxDate,String dateFormat) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
        Calendar cal = Calendar.getInstance();

        cal.setTime(sdf.parse(minDate));
        long time1 = cal.getTimeInMillis();

        cal.setTime(sdf.parse(maxDate));
        long time2 = cal.getTimeInMillis();

        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }
    /**
     * 计算两个日期之间相差的天数
     * @param minDate minDate 较小的时间
     * @param maxDate maxDate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date minDate,Date maxDate,String dateFormat) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
        Calendar cal = Calendar.getInstance();

        cal.setTime(minDate);
        long time1 = cal.getTimeInMillis();


        cal.setTime(maxDate);
        long time2 = cal.getTimeInMillis();

        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }
    /**
     * 通过日期获取 星期几
     * 如果是当天则返回 今天
     * @param date
     * @return
     */
    public static String getWeekZh(Date date){
        String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar c = Calendar.getInstance(Locale.getDefault());
        c.setTime(date);
//		return new SimpleDateFormat("E").format(c.getTime());
        int intWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if(intWeek>6)
            return "";
        if(new SimpleDateFormat(shortDate).format(new Date()).equals(new SimpleDateFormat(shortDate).format(date))){
            return "今天";
        }
        return weekDaysName[intWeek];
    }

    /**
     * 转换中文对应的时段
     * @param date
     * @return
     */
    public static String convertNowHour2CN(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hourString = sdf.format(date);
        int hour = Integer.parseInt(hourString);
        if(hour < 6) {
            return "凌晨";
        }else if(hour >= 6 && hour < 12) {
            return "早上";
        }else if(hour == 12) {
            return "中午";
        }else if(hour > 12 && hour <=18) {
            return "下午";
        }else if(hour >=19) {
            return "晚上";
        }
        return "";
    }

    /**
     * 剩余秒数转换
     * @param time
     * @return
     */
    public static String convertSecond2Day(int time) {
        int day = time/86400;
        int hour = (time - 86400*day)/3600;
        int min = (time - 86400*day - 3600*hour)/60;
        int sec = (time - 86400*day - 3600*hour - 60*min);
        StringBuilder sb = new StringBuilder();
        sb.append(day);
        sb.append("天");
        sb.append(hour);
        sb.append("时");
        sb.append(min);
        sb.append("分");
        sb.append(sec);
        sb.append("秒");
        return sb.toString();
    }
}
