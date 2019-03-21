package com.daf.cloudshare.ui.product;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.BoringLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.AppData;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;

import com.daf.cloudshare.base.BaseProductAdapter;
import com.daf.cloudshare.model.ProductBean;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.utils.AnimationUtil;
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

import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by PP on 2019/2/19.
 */
public class ProductFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {
    private static final String TITLE = "TITLE";
    private static final String TYPE = "TYPE";
    private static final String URL = "URL";


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.index)
    TextView mIndex;
    @BindView(R.id.index2)
    TextView mIndex2;

    private List<ProductBean.DataBean> mProductBeanList = new ArrayList<>();
    private BaseProductAdapter mAdapter;
    private int mPage = 1;
    private String mTitle = "";
    private String mType = "";
    private String mUrl;
    private boolean mIsNew = false;
    private PopupWindow mPopupWindow;


    @Override
    protected boolean canDragBack() {
        return false;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product;
    }






    public static ProductFragment getInstance(String title, String type, String url) {
        // 通过bundle传递数据
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(TYPE, type);
        bundle.putString(URL, url);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void init() {


        mTitle = getArguments().getString(TITLE);
        mType = getArguments().getString(TYPE);
        mUrl = getArguments().getString(URL);

        mIsNew = getArguments().getBoolean("new");

        mTopBar.setTitle(mTitle);
        if (!mTitle.equals("产品")) {
            mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popBackStack();
                }
            });
        }


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
                String pid = ((List<ProductBean.DataBean>) adapter.getData()).get(position).getP_id();
                String name=((List<ProductBean.DataBean>) adapter.getData()).get(position).getP_name();
                startFragment(DetailFragment.getInstance(pid,name));
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
        Map<String, String> map = new HashMap<>();
        map.put("page", mPage + "");
        map.put("type", mType);
        if (mIsNew) {
            map.put("order", "3");
        }


        HttpUtil.getInstance(getActivity()).postForm(mUrl, map, new HttpUtil.ResultCallback() {
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


    @OnClick({R.id.index, R.id.index2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.index:

              //  showIndex(view);

                break;
            case R.id.index2:
                break;
        }
    }

    private void showIndex(View v){
        if (mPopupWindow.isShowing()){

            mPopupWindow.dismiss();
        }
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.index,null);
        View root = view.findViewById(R.id.root);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new IndexAdapter(AppData.getIndex()));


        mPopupWindow = new PopupWindow(root, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,false);

        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setAnimationStyle(R.style.pop);
        AnimationUtil.createAnimation(true,view,root,null);

        mPopupWindow.showAsDropDown(v);


    }



}
