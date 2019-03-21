package com.daf.cloudshare.base;

/**
 * Created by PP on 2019/2/22.
 */

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;
import com.daf.cloudshare.model.ProductBean;

import java.util.List;

public class BaseProductAdapter extends BaseQuickAdapter<ProductBean.DataBean, BaseViewHolder> {
    public BaseProductAdapter(@Nullable List<ProductBean.DataBean> data) {
        super(R.layout.adapter_product, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean.DataBean item) {
        List<String> labels = item.getP_label();
        String label = "";
        String label2 = "";
        String label3 = "";


        if(labels!=null) {


            if (labels.size() == 1) {
                label = labels.get(0);
            }
            if (labels.size() == 2) {
                label = labels.get(0);
                label2 = labels.get(1);
            }
            if (labels.size() == 3) {
                label = labels.get(0);
                label2 = labels.get(1);
                label3 = labels.get(2);
            }
        }

        Glide.with(mContext).load(item.getP_logo()).into((ImageView) helper.getView(R.id.iv));
        helper.setText(R.id.tv_name, item.getP_name())
                .setText(R.id.tv_money, item.getP_limitdown() + "-" + item.getP_limitup())
                .setText(R.id.tv_lilv, item.getP_rate())
                .setText(R.id.tv_rate_type, item.getP_ratetype())
                .setText(R.id.bottom, label)
                .setText(R.id.bottom2, label2)
                .setText(R.id.tv_over,item.getP_passrate())
                .setText(R.id.tv_body, item.getP_quantity() + "人已借款")
                .addOnClickListener(R.id.tv_apply);

        String text=((TextView)helper.getView(R.id.bottom)).getText().toString();
        String text2=((TextView)helper.getView(R.id.bottom2)).getText().toString();
        if(TextUtils.isEmpty(text)){
            helper.getView(R.id.bottom).setVisibility(View.INVISIBLE);
        }
        if (TextUtils.isEmpty(text2)){
            helper.getView(R.id.bottom2).setVisibility(View.INVISIBLE);

        }









    }


}
