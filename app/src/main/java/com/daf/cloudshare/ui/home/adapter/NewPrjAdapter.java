package com.daf.cloudshare.ui.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;

import com.daf.cloudshare.model.ProductBean;


import java.util.List;

/**
 * Created by PP on 2019/2/21.
 */
public class NewPrjAdapter extends BaseQuickAdapter<ProductBean.DataBean, BaseViewHolder> {

    public NewPrjAdapter(@Nullable List<ProductBean.DataBean> data) {
        super(R.layout.adapter_home_new, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean.DataBean item) {
        Glide.with(mContext).load(item.getP_logo()).into((ImageView)helper.getView(R.id.iv_pic));
        helper.setText(R.id.tv_title,item.getP_name());

    }
}
