package com.hcp.aradish.utils;

import com.hcp.aradish.constants.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

/**
 * Created by hcp on 15/6/16.
 */
public class AHkitCache {
    /** 缓存路径 **/
    static final String DIRPATH= Constant.CACHE_ROOT_DIRPATH;
    /** 缓存版本号 */
    static final int VERSION = 0;
    /** 缓存有效时间 */
    static final long EXPIR_TIME = 365 * 24 * 60 * 60 * 1000;
    /**
     * 缓存序列号对象
     * @param key
     * @param value
     */
    public synchronized static void put(String key, Serializable value) {
        ObjectOutputStream oos = null;
        FileOutputStream out = null;
        try {
            File file = new File(DIRPATH, key.hashCode() + "");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            out = new FileOutputStream(file);
            //缓存数据[版本号(int),缓存时间(long),缓存有效时间(long),缓存对象]
            oos = new ObjectOutputStream(out);
            oos.writeInt(VERSION);//版本号
            oos.writeLong(System.currentTimeMillis());//缓存时间
            oos.writeLong(EXPIR_TIME);//缓存有效时间
            oos.writeObject(value);//缓存序列化对象
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                    oos.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 取得 缓存对象
     * @param key
     * @param <T>
     * @return
     */
    public static <T extends Object> T getValue(String key) {
        return (T) get(key);
    }
    /**
     * 取得 缓存对象
     * @param key
     * @return
     */
    public static Object get(String key) {
        try {
            File file = new File(DIRPATH, key.hashCode() + "");
            if (!file.exists()) {
                return null;
            }
            //缓存数据[版本号(int),缓存时间(long),缓存有效时间(long),缓存对象]
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            int version = ois.readInt();//缓存版本号
            if (VERSION != version) {// 版本号不符，表示这个缓存已经没用了
            }
            long writeTime = ois.readLong();//缓存时间
            long expiredTime = ois.readLong();//缓存有效时间
            if (System.currentTimeMillis() - writeTime > expiredTime) {// 缓存超过有效期
            }
            return ois.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 移除 缓存对象
     * @param key
     */
    public synchronized static void remove(String key) {
        File file = new File(DIRPATH, key.hashCode() + "");
        if (file.exists()) {
            file.delete();
        }
    }
    interface Converter<F,T>{
        T convert(F from);
    }

    Converter<String,Integer> converter=new Converter<String, Integer>() {
        @Override
        public Integer convert(String from) {
            return null;
        }
    };
}
