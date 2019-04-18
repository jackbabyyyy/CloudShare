package com.daf.cloudshare.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daf.cloudshare.R;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by PP on 2019/4/3.
 */
public class HomeDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    public final ImageView mIv;


    public HomeDialog(@NonNull Context context) {
        super(context);
        mContext=context;

        setContentView(R.layout.dialog_tip);
        mIv = findViewById(R.id.iv);

        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {

        dismiss();
    }

    public void showDialog(String url){
        Window window=getWindow();
        window.setWindowAnimations(R.style.style_ad_dialog);
        window.setBackgroundDrawableResource(R.color.cl_tran);
        WindowManager.LayoutParams wl=window.getAttributes();
        wl.gravity= Gravity.CENTER;
        window.setAttributes(wl);


        Glide.with(mContext).load(url).into(mIv);






        findViewById(R.id.close).setOnClickListener(this);
        show();

    }






}
