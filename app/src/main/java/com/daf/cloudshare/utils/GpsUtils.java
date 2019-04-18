package com.daf.cloudshare.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.daf.cloudshare.R;

/**
 * Created by PP on 2019/4/17.
 */
public class GpsUtils {

    /**
     * 跳转GPS设置
     */
    public static void openGPSSettings(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("当前应用需要打开定位功能。\\n\\n请点击\\\"设置\\\"-\\\"定位服务\\\"-打开定位功能。")
                .setNegativeButton("取消", null)
                .setPositiveButton("设置",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //跳转GPS设置界面
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                context.startActivity(intent);
                            }
                        })
                .setCancelable(false)
                .show();
    }

    public static boolean isLocServiceEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

}
