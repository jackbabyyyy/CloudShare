package com.daf.cloudshare.ui.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;

import com.daf.cloudshare.model.MyQrBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.utils.ImageUtils;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.io.IOException;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;

/**
 * Created by PP on 2019/3/4.
 */
public class MyQrFragment extends BaseFragment {

    @BindView(R.id.iv)
    ImageView mImageView;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBarLayout;

    @BindView(R.id.ivHead)
    CircleImageView mHead;
    @BindView(R.id.tvName)
    TextView mName;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_qr;
    }



    public static MyQrFragment getInstance(String head,String name){
        Bundle bundle=new Bundle();
        MyQrFragment fragment=new MyQrFragment();
        bundle.putString("head",head);
        bundle.putString("name",name);
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Override
    protected void init() {

        String head=getArguments().getString("head");
        String name=getArguments().getString("name");
        mName.setText(name);
        Glide.with(getActivity()).load(ImageUtils.stringToBitmap(head)).into(mHead);
        mTopBarLayout.setTitle("我的二维码");
        mTopBarLayout.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.myQr, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {
                        MyQrBean myQrBean= JSON.parseObject(response,MyQrBean.class);
                        Glide.with(getActivity()).load(myQrBean.getData().getImgUrl()).into(mImageView);

                    }
                });



    }
}
