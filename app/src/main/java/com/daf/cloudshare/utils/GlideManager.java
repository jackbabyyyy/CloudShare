package com.daf.cloudshare.utils;

import android.graphics.Path;
import android.support.annotation.NonNull;

import com.bumptech.glide.request.RequestOptions;
import com.daf.cloudshare.R;

/**
 * Created by PP on 2019/5/8.
 */
public class GlideManager {


    public static RequestOptions getGlideOptions(){
        RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.ic_photo)//图片加载出来前，显示的图片
            .fallback(R.mipmap.ic_photo) //url为空的时候,显示的图片
            .error(R.mipmap.ic_photo);
        return options;
    }






}
