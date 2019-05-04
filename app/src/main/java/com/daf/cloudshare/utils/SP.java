package com.daf.cloudshare.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by kaizen on 15/10/31.
 */
public class SP {

    /**
     * 文件名
     */
    public static String FILE_NAME = "share_data";


    /**
     * 保存数据, 根据具体的数据类型保存数据
     */
    public static void put(Context ctx, String key, Object obj) {
        SharedPreferences sp = ctx.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (obj instanceof String) {
            editor.putString(key, (String) obj);
        } else if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj);
        } else if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            editor.putFloat(key, (Float) obj);
        } else if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        } else {
            editor.putString(key, obj.toString());
        }
        editor.apply();
    }

    /**
     * 获取保存的数据,根据默认值的数据类型调用方法
     */
    @NonNull
    public static Object get(Context ctx, String key, Object defaultObj) {
        SharedPreferences sp = ctx.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultObj instanceof String) {
            return sp.getString(key, (String) defaultObj);
        } else if (defaultObj instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObj);
        } else if (defaultObj instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObj);
        } else if (defaultObj instanceof Float) {
            return sp.getFloat(key, (Float) defaultObj);
        } else if (defaultObj instanceof Long) {
            return sp.getLong(key, (Long) defaultObj);
        }
        return null;
    }

    /**
     * 删除指定key对应的值
     */
    public static void remove(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 删除所有数据
     */
    public static void clear(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 查询key是否已经存在
     */
    public static boolean contains(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有键值对
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    public static void saveToken(Context context,String token){
        SP.put(context,Const.token,token);
    }
    public static String  getToken(Context context){
        return SP.get(context,Const.token,"").toString();
    }

    public static void saveUser(Context context,String telephone,String password){
        SP.put(context,"telephone",telephone);
        SP.put(context,"password",password);
    }

    public static String[] getUser(Context context){
        String[] s=new String[2];
        s[0]=SP.get(context,"telephone","").toString();
        s[1]=SP.get(context,"password","").toString();
        return s;

    }

    public static void saveInfo(Context context,String infoString){
        SP.put(context,"info",infoString);
    }

    public static String getInfo(Context context){
        return SP.get(context,"info","").toString();
    }

}
