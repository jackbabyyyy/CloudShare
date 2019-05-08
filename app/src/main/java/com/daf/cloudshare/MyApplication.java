package com.daf.cloudshare;

import android.app.Application;
import android.content.res.Resources;

import com.bumptech.glide.Glide;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.utils.Const;
import com.daf.cloudshare.utils.SP;

import com.mob.MobSDK;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

/**
 * Created by PP on 2019/2/18.
 */
public class MyApplication extends Application {



    public static String BASEURL="https://www.winsharecn.cn/api.php?";//默认
    @Override
    public void onCreate() {
        super.onCreate();

        QMUISwipeBackActivityManager.init(this);



        MobSDK.init(this);

   //     CrashReport.initCrashReport(getApplicationContext(), "bddf3d2e48", true);


         BASEURL=SP.get(this,"baseurl","https://www.winsharecn.cn/api.php?").toString();

    }


}


