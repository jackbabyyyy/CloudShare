package com.daf.cloudshare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.daf.cloudshare.base.BaseActivity;
import com.daf.cloudshare.ui.login.LoginActivity;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        QMUIDisplayHelper.setFullScreen(this);

    }
    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToLogin();
            }
        }, 1500);
    }

    public void jumpToLogin() {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
    }


}
