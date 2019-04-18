package com.daf.cloudshare.ui.favorite;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.model.FavoriteBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.ui.product.DetailFragment;
import com.daf.cloudshare.utils.Const;
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
import okhttp3.Request;

/**
 * Created by PP on 2019/3/18.
 */
public class FavoriteFragment extends BaseFragment {


    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.bar)
    QMUITopBarLayout mBar;
    private List<FavoriteBean.DataBean> mData=new ArrayList<>();
    private FavoriteAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void init() {
        mBar.setTitle("我的收藏");
        mBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });


        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int space=QMUIDisplayHelper.dp2px(getActivity(),16);
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                if (parent.getChildAdapterPosition(view)<3){
                    outRect.top=space;
                }
                outRect.bottom= space;
                if (parent.getChildAdapterPosition(view)%3==1){
                    outRect.left=space;
                    outRect.right=space;

                }
                if (parent.getChildAdapterPosition(view)%3==0){
                    outRect.left=space*2;
                }
                if (parent.getChildAdapterPosition(view)%3==2){
                    outRect.right=space*2;
                }


            }
        });

        mAdapter = new FavoriteAdapter(mData);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String id=((List<FavoriteBean.DataBean>)adapter.getData()).get(position).f_value;
                String name=((List<FavoriteBean.DataBean>)adapter.getData()).get(position).f_name;
                startFragment(DetailFragment.getInstance(id,name));


            }
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

                mAdapter.show=true;
                mAdapter.setNewData(mData);
                mBar.addRightTextButton("完成",R.id.topbar_right_about_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBar.removeAllRightViews();
                        mAdapter.show=false;
                        mAdapter.setNewData(mAdapter.getData());

                    }
                });

                return false;
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {


                cancelFavorite(mData.get(position).f_id,position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);



    }

    private void getFavorite(){
        HttpUtil.getInstance(getActivity()).postForm(AppUrl.getFavorite, null, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) throws IOException {
                FavoriteBean bean= JSON.parseObject(response,FavoriteBean.class);
                mData=bean.data;
                mAdapter.setNewData(bean.data);


            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getFavorite();
    }

    private void cancelFavorite(String id,int pos) {

        Map<String, String> map = new HashMap<>();
        map.put("id", id);

        HttpUtil.getInstance(getActivity()).postForm(AppUrl.cancelFavorite, map, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    showToast(jsonObject.getString("msg"));
                    if (jsonObject.getString("code").equals(Const.getFavorite_cancel)) {

                        mAdapter.remove(pos);

                        //
                      //  postEvent();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
