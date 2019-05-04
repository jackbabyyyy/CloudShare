package com.daf.cloudshare.ui.mine;

import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;
import com.daf.cloudshare.model.TeamBean;
import com.daf.cloudshare.utils.ImageUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by PP on 2019/4/22.
 */
public class TeamAdapter extends BaseQuickAdapter<TeamBean.DataBean, BaseViewHolder> {
    public TeamAdapter(@Nullable List<TeamBean.DataBean> data) {
        super(R.layout.adapter_team, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamBean.DataBean item) {
        CircleImageView head=helper.getView(R.id.head);
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_photo)//图片加载出来前，显示的图片
                .fallback( R.mipmap.ic_photo) //url为空的时候,显示的图片
                .error(R.mipmap.ic_photo);//图片加载失败后，显示的图片

        Glide.with(mContext)
                .load(ImageUtils.stringToBitmap(item.i_logo))
                .apply(options)
                .into(head);

        helper.setText(R.id.name,item.i_name)
                .setText(R.id.time,"加入时间:"+item.i_time)
        .addOnClickListener(R.id.wx)
        .addOnClickListener(R.id.phone);


    }
}
