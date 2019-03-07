package com.daf.cloudshare.ui.tool;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;

import java.util.List;

/**
 * Created by PP on 2019/3/1.
 */
public class ToolAdapter extends BaseQuickAdapter<ToolListBean.DataBean, BaseViewHolder> {
    public ToolAdapter( @Nullable List<ToolListBean.DataBean> data) {
        super(R.layout.adapter_tool
                , data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ToolListBean.DataBean item) {


        Glide.with(mContext).load(item.getIcon()).into((ImageView)helper.getView(R.id.iv_pic));
        helper.setText(R.id.tv_title,item.getTitle());



    }



}
