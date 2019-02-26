package com.daf.cloudshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.base.BaseFragmentActivity;
import com.daf.cloudshare.login.LoginFragment;
import com.daf.cloudshare.utils.Const;
import com.daf.cloudshare.utils.SP;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

public class LoginActivity extends BaseFragmentActivity {

    @Override
    protected int getContextViewId() {
        return R.id.app_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //判断是否登录
        if (!TextUtils.isEmpty(SP.get(this,Const.token,"").toString())){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }

        BaseFragment fragment =new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }



}
