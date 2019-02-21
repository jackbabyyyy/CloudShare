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

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.home.adapter.HomeAdapter;
import com.daf.cloudshare.home.model.HotPrjBean;
import com.daf.cloudshare.net.AppUrl;
import com.lzy.okgo.OkGo;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        HomeAdapter homeAdapter=new HomeAdapter(mData);
        mRecyclerView.setAdapter(homeAdapter);
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

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



    }



    private void initNew() {

    }

    private void initTop() {

    }

    private void initBanner() {
        OkGo.post(AppUrl.banner)

    }


}