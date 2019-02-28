package com.daf.cloudshare.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.model.MyPrjBean;
import com.daf.cloudshare.model.ProductBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.utils.Const;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * 我的 订单列表页
 */
public class MyPrjFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBarLayout;

    private int PAGE=1;

    private List<MyPrjBean.DataBean> mDatas=new ArrayList<>();
    private MyPrjAdapter mAdapter;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_prj, null);
        ButterKnife.bind(this, root);
        init();


        return root;
    }

    private void init() {

        mTopBarLayout.setTitle("我的订单");
        mTopBarLayout.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MyPrjAdapter(mDatas);
        mAdapter.setOnLoadMoreListener(this,mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String pid=((List<MyPrjBean.DataBean>)adapter.getData()).get(position).getP_id();
                startFragment(MyPrjDataListFragment.getInstance(pid));
            }
        });

        getData();

    }


    @Override
    public void onLoadMoreRequested() {
        PAGE++;
        getData();
    }

    private void getData(){
        Map<String ,String> map=new HashMap<>();
        map.put("page",PAGE+"");
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.myPrj, map, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("code").equals(Const.body_success)) {
                                MyPrjBean myPrjBean = JSON.parseObject(response, MyPrjBean.class);
                                if (myPrjBean.getData().size() != 0) {
                                    mAdapter.addData(myPrjBean.getData());
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
