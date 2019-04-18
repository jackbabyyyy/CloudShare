package com.daf.cloudshare.view;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;

import java.util.List;

/**
 * Created by PP on 2019/4/15.
 */
class SortAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public int checked=-1;
    public SortAdapter( @Nullable List<String> data) {
        super(R.layout.adapter_sort, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv,item);
        ImageView iv=helper.getView(R.id.iv);

        if (helper.getAdapterPosition()==checked){
            iv.setVisibility(View.VISIBLE);
        }else{
            iv.setVisibility(View.INVISIBLE);
        }

        if (helper.getAdapterPosition()==getData().size()-1){
            helper.getView(R.id.divider).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.divider).setVisibility(View.VISIBLE);
        }



    }
}
