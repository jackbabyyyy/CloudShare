package com.daf.cloudshare.ui.product;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.JsonReader;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;

import com.daf.cloudshare.model.DetailBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.ui.web.WebFragment;
import com.daf.cloudshare.utils.Const;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by PP on 2019/2/27.
 * <p>
 * 产品详情页
 */
public class DetailFragment extends BaseFragment {

    private static final String PID = "PID";
    @BindView(R.id.tvAmount)
    TextView mTvAmount;
    @BindView(R.id.tv_lilv)
    TextView mTvLilv;
    @BindView(R.id.tv_periods)
    TextView mTvPeriods;
    @BindView(R.id.tv_speed)
    TextView mTvSpeed;
    @BindView(R.id.tv_range)
    TextView mTvRange;
    @BindView(R.id.tv_label)
    TextView mTvLabel;

    @BindView(R.id.favorite)
    ImageView mFavorite;
    @BindView(R.id.share)
    View mShare;


    @BindView(R.id.login)
    QMUIRoundButton mLogin;

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBarLayout;


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<DetailBean.DataBean.PAppShowBean> mData = new ArrayList<>();
    private DetailDesAdapter adapter;
    private DetailBean mDetailBean;

    private boolean isFlag=false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail;
    }



    public static DetailFragment getInstance(String pid,String title) {
        // 通过bundle传递数据
        Bundle bundle = new Bundle();
        bundle.putString(PID, pid);
        bundle.putString("title",title);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        return fragment;

    }


    @Override
    protected void init() {

        mTopBarLayout.setTitle(getArguments().getString("title")+"产品");
        mTopBarLayout.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DetailDesAdapter(mData);
        mRecyclerView.setAdapter(adapter);


        String id = getArguments().getString(PID);
        Map<String, String> map = new HashMap<>();
        map.put("proid", id);
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.prjInfo, map, new HttpUtil.ResultCallback() {

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("code").equals(Const.body_success)) {
                                mDetailBean = JSON.parseObject(response, DetailBean.class);

                                if (mDetailBean.data.p_isfavorite==0){
                                    isFlag=false;
                                }else{
                                    isFlag=true;
                                }
                                //
                                adapter.setNewData(mDetailBean.data.p_appShow);
                                setView(mDetailBean);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });


    }

    private void setView(DetailBean detailBean) {

        mTvAmount.setText(detailBean.data.p_limitdown + "~" + detailBean.data.p_limitup);
        mTvLilv.setText(detailBean.data.p_rate);
        mTvPeriods.setText(detailBean.data.p_periods);
        mTvSpeed.setText(detailBean.data.p_speed);
        if(isFlag){
            mFavorite.setImageResource(R.mipmap.favorite);
        }else{
            mFavorite.setImageResource(R.mipmap.favorite_dark);
        }

        mTvRange.setText("额度范围：" + detailBean.data.p_limitdown+ "-" + detailBean.data.p_limitup + "万元");

        for (int i = 0; i < detailBean.data.p_label.size(); i++) {
            mTvLabel.append(detailBean.data.p_label.get(i) + "、");
        }


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startFragment(WebFragment.getInstance(detailBean.data.p_jumpurl));

            }
        });


    }




    @OnClick({R.id.favorite, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.favorite:

                if (isFlag){
                    cancelFavorite(mDetailBean.data.p_isfavorite+"");

                }else{
                    addToFavorite();
                }


                break;
            case R.id.share:


                break;
        }
    }

    private void cancelFavorite(String id){

        Map<String,String > map=new HashMap<>();
        map.put("id",id);
        HttpUtil.getInstance(getActivity()).postForm(AppUrl.cancelFavorite, map, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) throws IOException {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    showToast(jsonObject.getString("msg"));
                    if (jsonObject.getString("code").equals(Const.getFavorite_cancel)){
                        mFavorite.setImageResource(R.mipmap.favorite_dark);
                        isFlag=false;
                    }else{
                        mFavorite.setImageResource(R.mipmap.favorite);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void addToFavorite(){
        Map<String ,String > map=new HashMap<>();
        map.put("name",mDetailBean.data.p_name);
        map.put("value",mDetailBean.data.p_id);
        map.put("type","1");
        map.put("logo",mDetailBean.data.p_logo);
        map.put("order","0");
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.addToFavorite, map, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            showToast(jsonObject.getString("msg"));
                            if (jsonObject.getString("code").equals(Const.favorite_ok)){
                                mFavorite.setImageResource(R.mipmap.favorite);
                                isFlag=true;
                            }else{
                                mFavorite.setImageResource(R.mipmap.favorite_dark);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


    }
}
