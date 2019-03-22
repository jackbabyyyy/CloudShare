package com.daf.cloudshare.ui.tool;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;

import com.daf.cloudshare.model.ToolListBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;


import com.daf.cloudshare.ui.web.WebFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Request;

/**
 * Created by PP on 2019/2/19.
 */
public class ToolFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBarLayout;

    private List<ToolListBean.DataBean> mDataBeans=new ArrayList<>();
    private ToolAdapter mAdapter;

    @Override
    protected boolean canDragBack() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tool;
    }

   @Override
   protected void init() {



        mTopBarLayout.setTitle("工具");
       mTopBarLayout.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               popBackStack();
           }
       });

        mAdapter = new ToolAdapter(mDataBeans);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                outRect.right= QMUIDisplayHelper.dp2px(getActivity(),3);
                outRect.top=QMUIDisplayHelper.dp2px(getActivity(),3);
                if (parent.getChildAdapterPosition(view)%3==0){
                    outRect.left=QMUIDisplayHelper.dp2px(getActivity(),3);
                }


            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                String url=((List<ToolListBean.DataBean>)adapter.getData()).get(position).getUrl();
                 startFragment(WebFragment.getInstance(url));
            }
        });

        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.toolsList, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {

                        ToolListBean toolListBean= JSON.parseObject(response,ToolListBean.class);
                        mAdapter.setNewData(toolListBean.getData());

                    }
                });

    }


}
