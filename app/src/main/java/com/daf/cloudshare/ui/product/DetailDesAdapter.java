package com.daf.cloudshare.ui.product;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;
import com.daf.cloudshare.model.DetailBean;

import java.util.List;

public class DetailDesAdapter extends BaseQuickAdapter<DetailBean.DataBean.PAppShowBean, BaseViewHolder> {
    public DetailDesAdapter( @Nullable List<DetailBean.DataBean.PAppShowBean> data) {
        super(R.layout.adapter_detail_des, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailBean.DataBean.PAppShowBean item) {
        helper.setText(R.id.tv,item.name+":  "+item.desc);


    }
}
