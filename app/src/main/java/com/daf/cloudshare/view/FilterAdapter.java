package com.daf.cloudshare.view;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;

import java.util.List;

/**
 * Created by PP on 2019/4/15.
 */
public class FilterAdapter extends BaseQuickAdapter<FilterItem, BaseViewHolder> {

    public int checked=-1;
    public FilterAdapter( @Nullable List<FilterItem> data) {
        super(R.layout.adapter_filter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FilterItem item) {
        helper.setText(R.id.tv,item.name);
        TextView tv=helper.getView(R.id.tv);

        if (helper.getAdapterPosition()==checked){
            tv.setBackgroundResource(R.drawable.edit_bg_check);
            tv.setTextColor(Color.WHITE);
        }else {
            tv.setBackgroundResource(R.drawable.edit_bg);
            tv.setTextColor(Color.BLACK);
        }


    }
}
