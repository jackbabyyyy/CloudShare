package com.daf.cloudshare.ui.order;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.model.MyPrjDataListBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.utils.Const;
import com.daf.cloudshare.utils.MyLoadMoreView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Request;

public class MyPrjDataListFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    private static final String PID = "PID";
    private static final String TITLE = "TITLE";
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;

    private List<MyPrjDataListBean.DataBean> mDatas=new ArrayList<>();
    private MyPrjDataListAdapter mAdapter;
    private int mPage=1;
    private String mPid;

    public static MyPrjDataListFragment getInstance(String pid,String title){
        // 通过bundle传递数据
        Bundle bundle = new Bundle();
        bundle.putString(PID, pid);
        bundle.putString(TITLE,title);
        MyPrjDataListFragment fragment = new MyPrjDataListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_prj;
    }

    private void initSwipe() {
        mSwipe.setColorSchemeResources(R.color.app_color_blue);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mPage=1;
                mAdapter.getData().clear();
                getData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipe.setRefreshing(false);
                    }
                },1500);

            }
        });
    }

    @Override
    protected void init() {
        String title=getArguments().getString(TITLE);
        mTopBar.setTitle(title+"订单");
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

        mPid = getArguments().getString(PID);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MyPrjDataListAdapter(mDatas);
        mAdapter.setOnLoadMoreListener(this,mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setLoadMoreView(new MyLoadMoreView());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });


        getData();

        initSwipe();





    }

    @Override
    public void onLoadMoreRequested() {
        mPage++;
        getData();

    }

    private void getData(){

        Map<String ,String> map=new HashMap<>();
        map.put("page",mPage+"");
        map.put("proid",mPid);

        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.base+AppUrl.myPrjDataList, map, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("code").equals(Const.body_success)) {
                                MyPrjDataListBean myPrjBean = JSON.parseObject(response, MyPrjDataListBean.class);



                                if (myPrjBean.data.size() != 0) {
                                    mAdapter.addData(myPrjBean.data);
                                    mAdapter.loadMoreComplete();
                                } else {
                                    mAdapter.loadMoreEnd();
                                }

                            } else {
                                mAdapter.loadMoreEnd();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

}
