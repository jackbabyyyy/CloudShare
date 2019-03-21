package com.daf.cloudshare.ui.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;
import com.daf.cloudshare.model.FavoriteBean;

import java.util.List;

/**
 * Created by PP on 2019/3/15.
 */
public class FavoriteHomeAdapter extends BaseQuickAdapter<FavoriteBean.DataBean, BaseViewHolder> {

    public FavoriteHomeAdapter( @Nullable List<FavoriteBean.DataBean> data) {
        super(R.layout.adapter_home_top_btn, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, FavoriteBean.DataBean item) {
        helper.setText(R.id.tv_title,item.f_name);
        Glide.with(mContext).load(item.f_logo).into((ImageView) helper.getView(R.id.iv_pic));


    }


}
