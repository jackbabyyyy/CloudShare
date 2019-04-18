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

        helper.setText(R.id.tvName,item.name)
                .setText(R.id.tvPhone,item.mobile)
                .setText(R.id.tvCard,"身份证: "+item.idcard)
                .setText(R.id.tvId,"订单编号: "+item.id)
        .setText(R.id.tvTime,"交易时间："+item.time)
        .setText(R.id.tvStatus,item.d_status);



    }
}
