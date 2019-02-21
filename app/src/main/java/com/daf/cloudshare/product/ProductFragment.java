package com.daf.cloudshare.product;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.model.ProductBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PP on 2019/2/19.
 */
public class ProductFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<ProductBean> mProductBeanList=new ArrayList<>();


    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_product, null);
        ButterKnife.bind(this, root);
        init();
        return root ;
    }

    private void init() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ProductAdapter adapter=new ProductAdapter(mProductBeanList);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mRecyclerView.setAdapter(adapter);

    }




}
