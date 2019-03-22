package com.daf.cloudshare.utils;

import java.text.DecimalFormat;

/**
 * Created by PP on 2019/3/22.
 */
public class MoneyUtils {
    public static String  get(String value){
        return new DecimalFormat("#").format(Double.valueOf(value)*10000)+"";
    }
}
