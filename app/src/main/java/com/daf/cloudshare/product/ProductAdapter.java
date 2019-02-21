package com.daf.cloudshare.product;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;
import com.daf.cloudshare.model.ProductBean;

import java.util.List;

/**
 * Created by PP on 2019/2/20.
 */
public class ProductAdapter extends BaseQuickAdapter<ProductBean, BaseViewHolder> {
    public ProductAdapter( @Nullable List<ProductBean> data) {
        super(R.layout.adapter_product, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean item) {

        Glide.with(mContext).load(item.url).into((ImageView) helper.getView(R.id.iv));
        helper.setText(R.id.tv_name,item.name)
                .setText(R.id.tv_money,item.name)
                .setText(R.id.tv_lilv,item.name)
                .setText(R.id.bottom,item.name)
                .setText(R.id.bottom2,item.name)
                .setText(R.id.tv_body,item.name)
                .addOnClickListener(R.id.tv_apply);


    }
}
