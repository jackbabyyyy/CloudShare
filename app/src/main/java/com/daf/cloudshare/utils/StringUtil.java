package com.daf.cloudshare.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class StringUtil {
    public static boolean checkPhone(String phone) {
        if (!phone.startsWith("1") || phone.length() != 11) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String content) {
        if (content.trim().equals("")) {
            return true;
        }
        return false;
    }



    public static String sublimitPhone(String phone) {
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length());
    }


    public static String getFileName(String url){

        int start=url.lastIndexOf("/");
        int end=url.lastIndexOf(".");
        if(start!=-1 && end!=-1){
            return url.substring(start+1,url.length());
        }else{
            return null;
        }


    }

    public static int getVersionCode(Context context){

        int versioncode = 1;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);

            versioncode = pi.versionCode;

        } catch (Exception e) {
            // Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }

    public static String getVersion(Context context){

        String versioncode = "1.0.0";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);

            versioncode = pi.versionName;

        } catch (Exception e) {
            // Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }


}
