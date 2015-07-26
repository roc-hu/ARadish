package com.hcp.aradish.utils;

import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串的格式化
 * Created by hcp on 15/7/3.
 */
public class FormatStr {
    /**
     *
     * formatPhoneNum
     * 格式化手机号 加俩空格
     * @param phonenum
     * @return
     *String
     * @exception
     * @since  1.0.0
     */
    public static String formatPhoneNum(String phonenum){
        StringBuffer num = new StringBuffer(phonenum);
        num.insert(3, " ");
        num.insert(8, " ");
        return num.toString();
    }
    /**
     * 判断str 是否为null或者""
     * @param str
     * @return
     */
    public static boolean isNotNull(String str){
        if(str==null||"".equals(str))
            return false;
        else
            return true;

    }
    /**
     * if( str==null) return "";
     * else return str
     * @param str
     * @return
     */
    public static String retEmpty(String str){
        return str==null?"":str;
    }
    /**
     * 判断是否支持英文名字  例： Jack/Lin 返回true 其他false
     * @return
     */
    public static boolean isEnName(String enName){
        return enName.matches("^[a-zA-z]+/[A-Za-z]+$");
    }
    /**
     * 判断字符串是否为手机号码
     * @param phoneNumber 手机号码
     * @return  true为正确，false 不符合规则
     */
    public static boolean isMobileNumber(String phoneNumber) {
        Pattern p = Pattern.compile("(^1[34578]\\d{9}$)");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 判断字符串是否为银行卡卡号
     * @param cardNum 银行卡卡号
     * @return  true为正确，false 不符合银行卡卡号规则
     */
    public static boolean isBankCardNum(String cardNum){
        //		 return aCardNum.matches("^(?!123456)[0-9]{16}$|^(?!123456)[0-9]{18,19}$") ?
        //	              !aCardNum.matches("^([0-9])\\1{15}$|^([0-9])\\1{17,18}$") : false;
        cardNum = clearSpaces(cardNum);
        if(cardNum.length() == 16 || cardNum.length() == 18  || cardNum.length() == 19 || cardNum.length() == 23){
            return true;
        }
        return false;
    }
    /**
     * 判断身份证输入是否合法
     *
     * @param IDNumber
     * @return
     */
    public static boolean isIDCardLegal(String IDNumber) {
        if (IDNumber == null)
            return false;
        // /判断是否为15位或18为
        boolean result = false;
        if(IDNumber.length() == 15 || IDNumber.length() == 18) result = true;// /判断身份证的位数是否合法
        if (!result)
            return result;

        String numStr = IDNumber.substring(0, IDNumber.length() - 1);// /判断身份证除了最后一位外，其它的是否都是数字
        result = isNumeric(numStr);
        if (!result)
            return result;

        String lastNumber = IDNumber.substring(IDNumber.length() - 1);// /判断身份证最后一位是否是0-9，或者是x(包括中文和英文)
        result = isNumeric(lastNumber);
        if (!result) {
            if (lastNumber.equals("x") || lastNumber.equals("X")) {
                result = true;
            }
        }
        if (!result)
            return result;

        // 判断月份是否合法
        if (result) {
            int year, month, date;
            if (IDNumber.length() == 15) {
                year = Integer.parseInt(IDNumber.substring(6, 8));
                month = Integer.parseInt(IDNumber.substring(8, 10));
                date = Integer.parseInt(IDNumber.substring(10, 12));
            } else {
                year = Integer.parseInt(IDNumber.substring(6, 10));
                month = Integer.parseInt(IDNumber.substring(10, 12));
                date = Integer.parseInt(IDNumber.substring(12, 14));
            }
            switch (month) {
                case 2:
                    result = (date >= 1)
                            && (year % 4 == 0 ? date <= 29 : date <= 28);
                    break;
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    result = (date >= 1) && (date <= 31);
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    result = (date >= 1) && (date <= 31);
                    break;
                default:
                    result = false;
                    break;
            }
        }
        return result;
    }
    /**
     * 检查字符串是否为纯数字
     * @param str 检查字符
     * @return true为正确，false 不符合规则
     */
    public static boolean isNumeric(String str) {
        return str.matches("[0-9]+");
    }
    /**
     * 校验银行卡卡号
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char  bit = getBankCardCheckCode(cardId.substring( 0 , cardId.length() -  1 ));
        if (bit ==  'N' ){
            return   false ;
        }
        return  cardId.charAt(cardId.length() -  1 ) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    public  static  char getBankCardCheckCode(String nonCheckCodeCardId){
        if (nonCheckCodeCardId ==  null  || nonCheckCodeCardId.trim().length() ==  0
                || !nonCheckCodeCardId.matches("\\d+" )) {
            //如果传的不是数据返回N
            return 'N' ;
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum =  0 ;
        for ( int  i = chs.length -  1 , j =  0 ; i >=  0 ; i--, j++) {
            int  k = chs[i] -  '0' ;
            if (j %  2  ==  0 ) {
                k *= 2 ;
                k = k / 10  + k %  10 ;
            }
            luhmSum += k;
        }
        return  (luhmSum %  10  ==  0 ) ?  '0'  : ( char )(( 10  - luhmSum %  10 ) +  '0' );
    }
    /**
     * 去除字符串中空格
     * @param str 处理字符
     * @return 处理结果
     */
    public static String clearSpaces(String str) {
        StringTokenizer aStringTok = new StringTokenizer(str, " ", false);
        String aResult = "";
        while (aStringTok.hasMoreElements()) {
            aResult += aStringTok.nextElement();
        }
        return aResult;
    }
    /**
     * 格式化金额
     * @param money	金额
     * @return		格式化后的金额
     */
    public static String formatMoney(String money) {
        if (isNotNull(money)) {
            DecimalFormat df = new DecimalFormat("#0.00");
            double b = Double.parseDouble(money) / 100;
            String c = df.format(b);
            return "" + c;
        }else{
            return "";
        }
    }

    /**
     * 判断是否有中文
     *
     * @param strName
     * @return
     */
    public static final boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 判断是否有中文
     *
     * @param c
     * @return
     */
    private static final boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
    /**
     * trim 格式化字符串 去掉字符串前面及后面的多个空格
     * @param s
     * @return String
     * @exception
     * @since  1.0.0
     */
    public static String trim(String s) {
        int i = s.length();// 字符串最后一个字符的位置
        int j = 0;// 字符串第一个字符
        int k = 0;// 中间变量
        char[] arrayOfChar = s.toCharArray();// 将字符串转换成字符数组
        while ((j < i) && (arrayOfChar[(k + j)] <= ' '))
            ++j;// 确定字符串前面的空格数
        while ((j < i) && (arrayOfChar[(k + i - 1)] <= ' '))
            --i;// 确定字符串后面的空格数
        return (((j > 0) || (i < s.length())) ? s.substring(j, i) : s);// 返回去除空格后的字符串
    }
}
