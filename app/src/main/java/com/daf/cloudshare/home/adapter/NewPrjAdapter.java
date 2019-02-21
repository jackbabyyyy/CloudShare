package com.daf.cloudshare.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;
import com.daf.cloudshare.home.model.NewPrjBean;
import com.daf.cloudshare.home.model.TopBtnBean;

import java.util.List;

/**
 * Created by PP on 2019/2/21.
 */
public class NewPrjAdapter extends BaseQuickAdapter<NewPrjBean.DataBean, BaseViewHolder> {

    public NewPrjAdapter(@Nullable List<NewPrjBean.DataBean> data) {
        super(R.layout.adapter_home_new, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewPrjBean.DataBean item) {
        Glide.with(mContext).load(item.getP_logo()).into((ImageView)helper.getView(R.id.iv_pic));
        helper.setText(R.id.tv_title,item.getP_name());

    }
}
