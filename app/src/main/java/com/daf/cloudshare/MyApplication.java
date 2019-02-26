package com.daf.cloudshare;

import android.app.Application;
import android.content.res.Resources;

import com.daf.cloudshare.utils.Const;
import com.daf.cloudshare.utils.SP;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

/**
 * Created by PP on 2019/2/18.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.init(this);


        OkGo.getInstance().debug("daf");





        QMUISwipeBackActivityManager.init(this);

    }







}


