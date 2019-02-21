package com.daf.cloudshare.mine;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;

import java.util.List;

/**
 * Created by PP on 2019/2/20.
 */
public class MineAdapter extends BaseQuickAdapter<String , BaseViewHolder> {
    public MineAdapter( @Nullable List<String> data) {
        super(R.layout.adapter_mine, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv,item);

    }


}
