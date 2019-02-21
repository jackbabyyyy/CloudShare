package com.daf.cloudshare.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;
import com.daf.cloudshare.home.model.HotPrjBean;

import java.util.List;

/**
 * Created by PP on 2019/2/21.
 */
public class HomeAdapter extends BaseQuickAdapter<HotPrjBean.DataBean, BaseViewHolder> {
    public HomeAdapter( @Nullable List<HotPrjBean.DataBean> data) {
        super(R.layout.adapter_product, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotPrjBean.DataBean item) {
        Glide.with(mContext).load(item.getP_logo()).into((ImageView) helper.getView(R.id.iv));
        helper.setText(R.id.tv_name,item.getP_name())
                .setText(R.id.tv_money,item.getP_name())
                .setText(R.id.tv_lilv,item.getP_name())
                .setText(R.id.bottom,item.getP_name())
                .setText(R.id.bottom2,item.getP_name())
                .setText(R.id.tv_body,item.getP_name())
                .addOnClickListener(R.id.tv_apply);

    }
}
