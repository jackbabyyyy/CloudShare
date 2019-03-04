package com.daf.cloudshare.utils;

import android.content.Context;

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

}
