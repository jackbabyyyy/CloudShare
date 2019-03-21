package com.daf.cloudshare.ui.favorite;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;
import com.daf.cloudshare.model.FavoriteBean;

import java.util.List;

/**
 * Created by PP on 2019/3/19.
 */
public class FavoriteAdapter extends BaseQuickAdapter<FavoriteBean.DataBean, BaseViewHolder> {
    public FavoriteAdapter( @Nullable List<FavoriteBean.DataBean> data) {
        super(R.layout.adapter_favorite, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FavoriteBean.DataBean item) {
        helper.setText(R.id.name,item.f_name);
        Glide.with(mContext).load(item.f_logo).into((ImageView)(helper.getView(R.id.iv)));

    }
}
