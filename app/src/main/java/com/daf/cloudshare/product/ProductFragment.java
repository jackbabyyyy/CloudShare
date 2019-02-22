package com.daf.cloudshare.product;

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
import com.daf.cloudshare.utils.Const;
import com.daf.cloudshare.utils.SP;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
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


        getData();

    }


    @Override
    public void onLoadMoreRequested() {


        mPage++;
        getData();


    }

    private void getData() {
        OkGo.post(AppUrl.prjList)
                .headers("token", SP.getToken(getActivity()))
                .params("page", mPage)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
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
