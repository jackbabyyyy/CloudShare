package com.daf.cloudshare.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.daf.cloudshare.R;
import com.daf.cloudshare.model.DialogBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.ui.product.DetailFragment;
import com.daf.cloudshare.ui.web.WebFragment;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by PP on 2019/4/3.
 */
public class TipDialog extends Dialog implements View.OnClickListener {
    private Context mContext;


    public TipDialog(@NonNull Context context) {
        super(context);
        mContext=context;

        setContentView(R.layout.dialog_ad);

        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {

        dismiss();
    }

    public void showDialog(){
        Window window=getWindow();
        window.setWindowAnimations(R.style.style_ad_dialog);
        window.setBackgroundDrawableResource(R.color.cl_tran);
        WindowManager.LayoutParams wl=window.getAttributes();
        wl.gravity= Gravity.CENTER;
        window.setAttributes(wl);







        findViewById(R.id.close).setOnClickListener(this);
        show();

    }





}
