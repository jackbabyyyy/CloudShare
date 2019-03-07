package com.daf.cloudshare.ui.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;
import com.daf.cloudshare.ui.home.model.TopBtnBean;

import java.util.List;

/**
 * Created by PP on 2019/2/21.
 */
public class TopBtnAdapter extends BaseQuickAdapter<TopBtnBean.DataBean, BaseViewHolder> {

    public TopBtnAdapter( @Nullable List<TopBtnBean.DataBean> data) {
        super(R.layout.adapter_home_top_btn, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopBtnBean.DataBean item) {
        Glide.with(mContext).load(item.getIcon()).into((ImageView)helper.getView(R.id.iv_pic));
        helper.setText(R.id.tv_title,item.getTitle());

    }
}
