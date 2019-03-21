package com.daf.cloudshare.ui.product;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.AppData;
import com.daf.cloudshare.R;

import java.util.List;

/**
 * Created by PP on 2019/3/18.
 */
public class IndexAdapter extends BaseQuickAdapter<AppData.IndexBean , BaseViewHolder> {


    public IndexAdapter( @Nullable List<AppData.IndexBean> data) {
        super(R.layout.adapter_index, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppData.IndexBean item) {
        helper.setText(R.id.name,item.index);

    }
}
