package com.daf.cloudshare.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.model.MyQrBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by PP on 2019/3/4.
 */
public class MyQrFragment extends BaseFragment {

    @BindView(R.id.iv)
    ImageView mImageView;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBarLayout;
    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_qr, null);
        ButterKnife.bind(this, root);
        init();
        return root;
    }

    private void init() {
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
