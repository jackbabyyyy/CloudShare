package com.daf.cloudshare.base;

/**
 * Created by PP on 2019/2/22.
 */

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;
import com.daf.cloudshare.model.ProductBean;
import com.daf.cloudshare.utils.MoneyUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class BaseProductAdapter extends BaseQuickAdapter<ProductBean.DataBean, BaseViewHolder> {
    public BaseProductAdapter(@Nullable List<ProductBean.DataBean> data) {
        super(R.layout.adapter_product, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean.DataBean item) {
        List<String> labels = new ArrayList<>();
        labels=item.getP_label();
        TagFlowLayout flowLayout= helper.getView(R.id.flow);
        flowLayout.setAdapter(new TagAdapter<String>(labels)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv,
                        flowLayout, false);
                tv.setText(s);
                if (position%2==0){
                    tv.setBackground(mContext.getDrawable(R.drawable.bg_label));
                    tv.setTextColor(mContext.getResources().getColor(R.color.lab));
                }else{
                    tv.setBackground(mContext.getDrawable(R.drawable.bg_label2));
                    tv.setTextColor(mContext.getResources().getColor(R.color.lab2));
                }
                return tv;
            }
        });



        Glide.with(mContext).load(item.getP_logo()).into((ImageView) helper.getView(R.id.iv));
        helper.setText(R.id.tv_name, item.getP_name())
                .setText(R.id.tv_money, MoneyUtils.get(item.getP_limitup()))
                .setText(R.id.tv_lilv, item.getP_rate()+"%")
                .setText(R.id.tv_rate_type, item.getP_ratetype())
                .setText(R.id.tv_over,item.getP_passrate())
                .setText(R.id.tv_body, item.getP_quantity() + "人已借款")
              ;

        ImageView hot=helper.getView(R.id.hot);
        if (item.getP_ishot().equals("1")){
            hot.setVisibility(View.VISIBLE);
        }else {
            hot.setVisibility(View.INVISIBLE);
        }






    }


}
