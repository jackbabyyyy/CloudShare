package com.daf.cloudshare.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.AppData;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.ui.MyPrjFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PP on 2019/2/19.
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, root);

        init();
        return root ;
    }

    private void init() {
        mTopBar.setTitle("我的");


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MineAdapter adapter=new MineAdapter(AppData.getMineBody());
        View head=LayoutInflater.from(getActivity()).inflate(R.layout.head_mine,null);
        adapter.addHeaderView(head);
        View foot=LayoutInflater.from(getActivity()).inflate(R.layout.foot_mine,null);
        adapter.addFooterView(foot);

        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:

                        startFragment(new MyPrjFragment());
                        break;
                    case 1:
                        break;

                }
            }
        });

    }
}
