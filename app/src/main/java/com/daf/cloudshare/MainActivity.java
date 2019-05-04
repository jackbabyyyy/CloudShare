package com.daf.cloudshare;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.daf.cloudshare.base.BaseFragment;

import com.daf.cloudshare.base.BaseFragmentActivity;
import com.daf.cloudshare.model.InfoBeanToc;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.utils.SP;

import java.io.IOException;

import okhttp3.Request;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected int getContextViewId() {
        return R.id.app;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        if (savedInstanceState == null) {
            BaseFragment fragment =new MainFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }



    }














}
