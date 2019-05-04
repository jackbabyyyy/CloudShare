package com.daf.cloudshare.ui.home.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;
import com.daf.cloudshare.model.ProductBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by PP on 2019/4/22.
 */
public class HotAdapter extends BaseQuickAdapter<ProductBean.DataBean, BaseViewHolder> {
    public HotAdapter(@Nullable List<ProductBean.DataBean> data) {
        super(R.layout.adapter_hot, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean.DataBean item) {
        helper.setText(R.id.name, item.getP_name())
                .setText(R.id.des, item.getP_resume())
;
        Glide.with(mContext).load(item.getP_logo()).into((CircleImageView)helper.getView(R.id.head));

        List<String>  labels=item.getP_label();

        if (null==labels){
            return;
        }
        if (labels.size()>2){
            labels=labels.subList(0,2);
        }

        TagFlowLayout flowLayout= helper.getView(R.id.flow);
        flowLayout.setAdapter(new TagAdapter<String>(labels)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv,
                        flowLayout, false);
                tv.setText(s);

                    tv.setBackgroundColor(mContext.getResources().getColor(R.color.toc_home_label_bg));
                    tv.setTextColor(mContext.getResources().getColor(R.color.toc_home_label));

                return tv;
            }
        });


    }



}
