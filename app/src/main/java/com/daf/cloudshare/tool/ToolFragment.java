package com.daf.cloudshare.tool;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PP on 2019/2/19.
 */
public class ToolFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBarLayout;

    @Override
    protected View onCreateView() {


        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_tool, null);
        ButterKnife.bind(this, root);

        init();
        return root;
    }

    private void init() {



        mTopBarLayout.setTitle("工具");

    }


}
