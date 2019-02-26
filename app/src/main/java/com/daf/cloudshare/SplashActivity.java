package com.daf.cloudshare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.daf.cloudshare.base.BaseActivity;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;


import okhttp3.Call;
import okhttp3.Response;

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
        }, 2000);
    }

    public void jumpToLogin() {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
    }


}
