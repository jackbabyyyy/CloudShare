package com.daf.cloudshare.ui.product;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.BoringLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.AppData;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;

import com.daf.cloudshare.base.BaseProductAdapter;
import com.daf.cloudshare.model.ProductBean;
import com.daf.cloudshare.model.SetBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.utils.AnimationUtil;
import com.daf.cloudshare.utils.Const;
import com.daf.cloudshare.utils.MyLoadView2;
import com.daf.cloudshare.view.FilterView;
import com.daf.cloudshare.view.MyLinearLayoutManager;
import com.daf.cloudshare.view.SortView;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
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
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.search)
    EditText mSearch;

    private List<ProductBean.DataBean> mProductBeanList = new ArrayList<>();
    private BaseProductAdapter mAdapter;
    private int mPage = 1;
    private String mTitle = "";
    private String mType = "";
    private String mUrl;
    private boolean mIsNew = false;

    private SortView mSortView;
    private FilterView mFilterView;
    private SetBean mSetBean;
    private String f_month = "";
    private String f_money = "";
    private String f_order = "";
    private int mLoadmore_type=1;


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

    private void initSwipe() {
        mSwipe.setColorSchemeResources(R.color.app_color_blue);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mAdapter.getData().clear();
                getData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipe.setRefreshing(false);
                    }
                }, 1500);

            }
        });
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

        mTopBar.addRightImageButton(R.mipmap.search, R.id.topbar_right_about_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearch.isShown()) {
                    String keyword = mSearch.getText().toString().trim() + "";
                    mProductBeanList.clear();

                    mAdapter.setNewData(mProductBeanList);
                    getSearch(keyword);
                    mTopBar.setTitle(mTitle);
                    mSearch.setVisibility(View.GONE);
                    mSearch.setText("");
                } else {
                    mTopBar.setTitle("");
                    mSearch.setVisibility(View.VISIBLE);
                    //
                    mSearch.setFocusable(true);
                    mSearch.setFocusableInTouchMode(true);
                    mSearch.requestFocus();
                }

            }
        });


        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = mSearch.getText().toString().trim() + "";
                    mProductBeanList.clear();
                    mAdapter.setNewData(mProductBeanList);
                    getSearch(keyword);
                    mTopBar.setTitle(mTitle);
                    mSearch.setVisibility(View.GONE);
                    mSearch.setText("");
                }
                return false;
            }
        });


        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mSwipe.isRefreshing()) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = QMUIDisplayHelper.dp2px(getActivity(), 16);
                }
            }
        });
        mAdapter = new BaseProductAdapter(mProductBeanList);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String pid = ((List<ProductBean.DataBean>) adapter.getData()).get(position).getP_id();
                String name = ((List<ProductBean.DataBean>) adapter.getData()).get(position).getP_name();
                startFragment(DetailFragment.getInstance(pid, name));

            }
        });

        mAdapter.setLoadMoreView(new MyLoadView2());

        mRecyclerView.setAdapter(mAdapter);

        mSortView = new SortView(getActivity());
        mSortView.setCheckedListener(new SortView.CheckedListener() {
            @Override
            public void onChecked(int pos) {
                mPage = 1;
                f_order = pos + 1 + "";
//                f_money="";
//                f_month="";
//                mType="";
                mProductBeanList.clear();
                mAdapter.setNewData(mProductBeanList);
                getData();
                mSortView.close();
            }
        });
        mFilterView = new FilterView(getActivity());
        mFilterView.setClickListener(new FilterView.ClickListener() {
            @Override
            public void onClick(String type, String min, String max, String month) {
                mPage = 1;
                if (TextUtils.isEmpty(min) || TextUtils.isEmpty(max)) {


                } else {
                    if (Double.valueOf(min) == Double.valueOf(max)) {
                        f_money = min;
                    } else {
                        f_money = min + "-" + max;
                    }
                }
                f_month = month;
                mType = type;

                mProductBeanList.clear();
                mAdapter.setNewData(mProductBeanList);
                getData();
                mFilterView.close();


            }
        });


        getData();
        getFilter();
        initSwipe();
    }


    private void getFilter() {
        HttpUtil.getInstance(getActivity()).postForm(AppUrl.getSearchConfig, null, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) throws IOException {
                mSetBean = JSON.parseObject(response, SetBean.class);

                mFilterView.setData(mSetBean.data.top.select, mSetBean.data.fot.select);


            }
        });
    }


    @Override
    public void onLoadMoreRequested() {

        if (mLoadmore_type==-1){
            mLoadmore_type=1;
            return;
        }
        mPage++;
        getData();



    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", mPage + "");
        map.put("protype", mType);

        //
        map.put("month", f_month);
        map.put("money", f_money);
        map.put("order", f_order);


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
                mSortView.show(mIndex);
                break;
            case R.id.index2:
               // startFragment(new FilterFragment());
                mFilterView.show(mIndex2);
                break;
        }
    }


    private void getSearch(String keyword) {


        closeKeyBorad();

        if (TextUtils.isEmpty(keyword)) {
            mPage = 1;
            getData();
            return;
        }


        HashMap<String, String> map = new HashMap<>();
        map.put("keyword", keyword);
        HttpUtil.getInstance(getActivity()).postForm(AppUrl.searchProject, map, new HttpUtil.ResultCallback() {
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

                            mLoadmore_type = -1;
                            mAdapter.setNewData(productBean.getData());
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void closeKeyBorad() {
        View view=getActivity().getWindow().getDecorView();
        if (view!=null) {
            InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
