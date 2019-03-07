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

package com.daf.cloudshare.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;

import com.daf.cloudshare.base.BaseProductAdapter;
import com.daf.cloudshare.ui.home.adapter.NewPrjAdapter;
import com.daf.cloudshare.ui.home.adapter.TopBtnAdapter;
import com.daf.cloudshare.ui.home.model.BannerBean;

import com.daf.cloudshare.ui.home.model.TopBtnBean;
import com.daf.cloudshare.model.ProductBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.ui.product.ProductFragment;
import com.daf.cloudshare.ui.DetailFragment;
import com.daf.cloudshare.ui.WebFragment;
import com.daf.cloudshare.utils.Const;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * @author cginechen
 * @date 2016-10-19
 */

public class HomeFragment extends BaseFragment {
    private final static String TAG = HomeFragment.class.getSimpleName();


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    private List<ProductBean.DataBean> mData=new ArrayList<>();
    private Banner mBanner;
    private RecyclerView mTop;
    private RecyclerView mNew;
    private BaseProductAdapter homeAdapter;


    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, root);


        init();
       // initHead(root);
        return root ;
    }

    private void init() {







        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        homeAdapter = new BaseProductAdapter(mData);

        mRecyclerView.setAdapter(homeAdapter);
        homeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                String pid=((List<ProductBean.DataBean>)adapter.getData()).get(position).getP_id();
                startFragment(DetailFragment.getInstance(pid));

            }
        });




        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.hotPrj, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String s) throws IOException {
                        try {

                            Log.d(TAG, "onResponse: "+s);
                            JSONObject jsonObject=new JSONObject(s);
                            if (jsonObject.getString("code").equals(Const.body_success)){
                                ProductBean hotPrjBean=JSON.parseObject(s,ProductBean.class);
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

        TextView newMore=v.findViewById(R.id.tvMore);
        TextView hotMore=v.findViewById(R.id.tvMore2);

        newMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putBoolean("new",true);
                bundle.putString("TITLE","最新上架");
                bundle.putString("TYPE","");
                bundle.putString("URL",AppUrl.newPrj);
                ProductFragment productFragment= new ProductFragment();
                productFragment.setArguments(bundle);

                startFragment(productFragment);

            }
        });
        hotMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(ProductFragment.getInstance("热门推荐","",AppUrl.hotPrj));


            }
        });

        initBanner();
        initTop();
        initNew();

       homeAdapter.addHeaderView(v);



    }



    private void initNew() {
        mNew.setLayoutManager(new GridLayoutManager(getActivity(),4));
        List<ProductBean.DataBean> data=new ArrayList<>();
        final NewPrjAdapter adapter=new NewPrjAdapter(data);
        mNew.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                List<ProductBean.DataBean> list=adapter.getData();
                startFragment(DetailFragment.getInstance(list.get(position).getP_id()));

            }
        });
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.newPrj, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String s) throws IOException {
                        try {

                            JSONObject jsonObject=new JSONObject(s);
                            if (jsonObject.getString("code").equals(Const.body_success)){
                                ProductBean newPrjBean=JSON.parseObject(s,ProductBean.class);
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
        topBtnAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                String type=((List<TopBtnBean.DataBean>)adapter.getData()).get(position).getUrl().getType()+"";


                String title=((List<TopBtnBean.DataBean>)adapter.getData()).get(position).getTitle();

                startFragment(ProductFragment.getInstance(title,type,AppUrl.prjList));


            }
        });



        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.topBtn, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {


                    }

                    @Override
                    public void onResponse(String s) throws IOException {
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


        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.banner, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String s) throws IOException {
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
                                        String url=mDatas.get(position).getUrl();
                                        if (!TextUtils.isEmpty(url)){
                                            startFragment(WebFragment.getInstance(url));
                                        }


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