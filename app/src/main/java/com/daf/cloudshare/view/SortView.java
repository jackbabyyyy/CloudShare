package com.daf.cloudshare.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.AppData;
import com.daf.cloudshare.R;
import com.daf.cloudshare.utils.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PP on 2019/4/15.
 */
public class SortView {

    public void setCheckedListener(CheckedListener checkedListener) {
        mCheckedListener = checkedListener;
    }

    private CheckedListener mCheckedListener;
    public SortAdapter mSortAdapter;
    private PopupWindow mPopupWindow;
    private List<String> mSortStrings=new ArrayList<>();
    private final View mView;
    private final View mRoot;

    public SortView(Context context){


        mSortStrings= AppData.getSort();
        mView = LayoutInflater.from(context).inflate(R.layout.view_sort,null);
        mRoot = mView.findViewById(R.id.root);
        RecyclerView recyclerView= mView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mSortAdapter=new SortAdapter(mSortStrings);
        recyclerView.setAdapter(mSortAdapter);


        mPopupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,true);

        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setAnimationStyle(R.style.pop);


        mSortAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mSortAdapter.checked=position;
                mSortAdapter.setNewData(mSortStrings);
               mCheckedListener.onChecked(position);

            }
        });

    }

    public void show(View view){
        if (!mPopupWindow.isShowing()) {
            AnimationUtil.createAnimation(true,mView,mRoot,null);
            mPopupWindow.showAsDropDown(view);

        }
    }

    public void close(){
        if (mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }

    }

    public interface CheckedListener{
        void  onChecked(int pos);
    }

}
