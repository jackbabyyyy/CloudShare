package com.daf.cloudshare.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.R;
import com.daf.cloudshare.utils.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PP on 2019/4/15.
 */
public class FilterView {

    private final PopupWindow mPopupWindow;
    private final View mView;
    private final View mRoot;
    private final RecyclerView mRecyclerView;
    private final RecyclerView mRecyclerView2;
    private final FilterAdapter mAdapter;
    private final FilterAdapter mAdapter2;

    public String type="";
    public String money_min="";
    public String money_max="";
    public String month="";


    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }


    private ClickListener mClickListener;

    public FilterView(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.view_filter,null);
        mRoot = mView.findViewById(R.id.root);
        mPopupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,true);

        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setAnimationStyle(R.style.pop);
        mPopupWindow.setClippingEnabled(false);

        mRecyclerView = mView.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context,3));
        List<FilterItem > test=new ArrayList<>();

        mAdapter = new FilterAdapter(test);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter2 = new FilterAdapter(test);
        mRecyclerView2 = mView.findViewById(R.id.recycler2);
        mRecyclerView2.setLayoutManager(new GridLayoutManager(context,3));
        mRecyclerView2.setAdapter(mAdapter2);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                mAdapter.checked=position;
                mAdapter.setNewData(mAdapter.getData());
                type=mAdapter.getData().get(position).value;
            }
        });

        mAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapter2.checked=position;
                mAdapter2.setNewData(mAdapter2.getData());
                month=mAdapter2.getData().get(position).value;
            }
        });

        EditText min=(EditText) mView.findViewById(R.id.min);
        EditText max=(EditText) mView.findViewById(R.id.max);

        SegmentControlView segmentControlView=mView.findViewById(R.id.segment);
        segmentControlView.setOnSegmentChangedListener(new SegmentControlView.OnSegmentChangedListener() {
            @Override
            public void onSegmentChanged(int newSelectedIndex) {
                if (newSelectedIndex==0){
                    mAdapter.checked=-1;
                    mAdapter2.checked=-1;
                    mAdapter.setNewData(mAdapter.getData());
                    mAdapter2.setNewData(mAdapter2.getData());
                    min.setText("");
                    max.setText("");
                    type="";
                    month="";

                }else if(newSelectedIndex==1){
                    money_min=min.getText().toString().trim()+"";
                    money_max=max.getText().toString().trim()+"";
                    mClickListener.onClick(type,money_min,money_max,month);


                }
            }
        });

        mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });


    }

    public void show(View view){
        if (!mPopupWindow.isShowing()) {
        //    AnimationUtil.createAnimation(true,mView,mRoot,null);
            mPopupWindow.showAsDropDown(view);

        }
    }



    public void close(){
        if (mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }

    }

    public void setData(List<FilterItem> head,List<FilterItem> bot){
        mAdapter.setNewData(head);
        mAdapter2.setNewData(bot);

    }

    public interface ClickListener{
         void onClick(String type, String min, String max, String month);
    }



}
