package com.daf.cloudshare.product;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.base.BaseProductAdapter;
import com.daf.cloudshare.model.ProductBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.ui.DetailFragment;
import com.daf.cloudshare.ui.MyPrjDataListFragment;
import com.daf.cloudshare.utils.Const;
import com.daf.cloudshare.utils.SP;

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
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by PP on 2019/2/19.
 */
public class ProductFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;


    private List<ProductBean.DataBean> mProductBeanList = new ArrayList<>();
    private BaseProductAdapter mAdapter;
    private int mPage = 1;


    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_product, null);
        ButterKnife.bind(this, root);
        init();
        return root;
    }

    public static ProductFragment getInstance(String pid, String title){
        // 通过bundle传递数据
        Bundle bundle = new Bundle();
//        bundle.putString(PID, pid);
//        bundle.putString(TITLE,title);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    private void init() {

        mTopBar.setTitle("产品");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new BaseProductAdapter(mProductBeanList);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String pid=((List<ProductBean.DataBean>)adapter.getData()).get(position).getP_id();
                startFragment(DetailFragment.getInstance(pid));
            }
        });


        getData();

    }


    @Override
    public void onLoadMoreRequested() {


        mPage++;
        getData();


    }

    private void getData() {
        Map<String,String> map=new HashMap<>();
        map.put("page",mPage+"");
        HttpUtil.getInstance(getActivity()).postForm(AppUrl.prjList, map, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String s) throws IOException {
                try {

                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("code").equals(Const.body_success)) {
                        ProductBean productBean = JSON.parseObject(s, ProductBean.class);
                        if (productBean.getData().size() != 0) {
                            mAdapter.addData(productBean.getData());
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
