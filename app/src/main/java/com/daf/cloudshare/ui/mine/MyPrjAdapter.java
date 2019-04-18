package com.daf.cloudshare.ui.mine;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;
import com.daf.cloudshare.model.MyPrjBean;

import java.util.ArrayList;
import java.util.List;

public class MyPrjAdapter extends BaseQuickAdapter<MyPrjBean.DataBean, BaseViewHolder> {
    public MyPrjAdapter( @Nullable List<MyPrjBean.DataBean> data) {
        super(R.layout.adapter_my_prj, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyPrjBean.DataBean item) {
        Glide.with(mContext).load(item.getP_logo()).into((ImageView) helper.getView(R.id.imageView));
        helper.setText(R.id.tvName,item.getP_name())
                .setText(R.id.tvBody,item.getP_quantity()+"人已报单");


        List<String>  labels=item.getP_label();

        TextView tvLabel=helper.getView(R.id.tvLabel);
        tvLabel.setText("");
        if (labels!=null){
            for (int i=0;i<labels.size();i++){
                tvLabel.append(labels.get(i)+" ");
            }
        }


    }
}
