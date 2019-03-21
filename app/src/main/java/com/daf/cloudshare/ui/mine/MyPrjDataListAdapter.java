package com.daf.cloudshare.ui.mine;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;
import com.daf.cloudshare.model.MyPrjDataListBean;

import java.util.List;

public class MyPrjDataListAdapter extends BaseQuickAdapter<MyPrjDataListBean.DataBean, BaseViewHolder> {
    public MyPrjDataListAdapter( @Nullable List<MyPrjDataListBean.DataBean> data) {
        super(R.layout.adapter_my_prj_data_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyPrjDataListBean.DataBean item) {

        helper.setText(R.id.tvName,item.getName()+"    "+item.getMobile())
                .setText(R.id.tvCard,"身份证: "+item.getIdcard())
                .setText(R.id.tvId,"订单编号: "+item.getId());


    }
}
