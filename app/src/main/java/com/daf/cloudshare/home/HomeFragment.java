/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.daf.cloudshare.home;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.home.adapter.HomeAdapter;
import com.daf.cloudshare.home.adapter.NewPrjAdapter;
import com.daf.cloudshare.home.adapter.TopBtnAdapter;
import com.daf.cloudshare.home.model.BannerBean;
import com.daf.cloudshare.home.model.HotPrjBean;
import com.daf.cloudshare.home.model.NewPrjBean;
import com.daf.cloudshare.home.model.TopBtnBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.utils.Const;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author cginechen
 * @date 2016-10-19
 */

public class HomeFragment extends BaseFragment {
    private final static String TAG = HomeFragment.class.getSimpleName();


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;



    private List<HotPrjBean.DataBean> mData=new ArrayList<>();
    private Banner mBanner;
    private RecyclerView mTop;
    private RecyclerView mNew;
    private HomeAdapter homeAdapter;


    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, root);

        init();
        return root ;
    }

    private void init() {



        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        homeAdapter = new HomeAdapter(mData);
        mRecyclerView.setAdapter(homeAdapter);
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        OkGo.post(AppUrl.hotPrj)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            if (jsonObject.getString("code").equals(Const.body_success)){
                                HotPrjBean hotPrjBean=JSON.parseObject(s,HotPrjBean.class);
                                homeAdapter.setNewData(hotPrjBean.getData());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


        initHead();



    }

    private void initHead() {
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.head_home,null);
        mBanner=v.findViewById(R.id.banner);
        mTop=v.findViewById(R.id.recycle_top);
        mNew=v.findViewById(R.id.recycle_new);

        initBanner();
        initTop();
        initNew();

        homeAdapter.addHeaderView(v);



    }



    private void initNew() {
        mNew.setLayoutManager(new GridLayoutManager(getActivity(),4));
        List<NewPrjBean.DataBean> data=new ArrayList<>();
        final NewPrjAdapter adapter=new NewPrjAdapter(data);
        mNew.setAdapter(adapter);
        OkGo.post(AppUrl.newPrj)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            if (jsonObject.getString("code").equals(Const.body_success)){
                                NewPrjBean newPrjBean=JSON.parseObject(s,NewPrjBean.class);
                                adapter.setNewData(newPrjBean.getData().subList(0,4));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });



    }

    private void initTop() {
        mTop.setLayoutManager(new GridLayoutManager(getActivity(),3));
        List<TopBtnBean.DataBean> data=new ArrayList<>();
        final TopBtnAdapter topBtnAdapter=new TopBtnAdapter(data);
        mTop.setAdapter(topBtnAdapter);
        OkGo.post(AppUrl.topBtn)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            if (jsonObject.getString("code").equals(Const.body_success)){
                                TopBtnBean topBtnBean=JSON.parseObject(s,TopBtnBean.class);
                                topBtnAdapter.setNewData(topBtnBean.getData());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });




    }

    private void initBanner() {


        OkGo.post(AppUrl.banner)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject json=new JSONObject(s);
                            if (json.getString("code").equals(Const.body_success)){
                                BannerBean bannerBean= JSON.parseObject(s,BannerBean.class);
                                List<BannerBean.DataBean> mDatas= bannerBean.getData();
                                List<String> imgs=new ArrayList<>();
                                for (int i=0;i<mDatas.size();i++){
                                    imgs.add(mDatas.get(i).getIcon());
                                }
                                //
                                mBanner.setImages(imgs);
                                mBanner.setImageLoader(new ImageLoader() {
                                    @Override
                                    public void displayImage(Context context, Object path, ImageView imageView) {
                                        Glide.with(getActivity()).load(path).into(imageView);
                                    }
                                });
                                mBanner.setOnBannerListener(new OnBannerListener() {
                                    @Override
                                    public void OnBannerClick(int position) {
                                        //点击
                                    }
                                }).start();



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

    }


}