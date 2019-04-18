package com.daf.cloudshare.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by PP on 2019/4/17.
 */
public class MyLinearLayoutManager extends LinearLayoutManager {
    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 纵向
     *
     * @param dy
     * @param recycler
     * @param state
     *
     * @return
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i = 0;
        try {
            i = super.scrollVerticallyBy(dy, recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 横向
     *
     * @param dx
     * @param recycler
     * @param state
     *
     * @return
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i = 0;
        try {
            super.scrollHorizontallyBy(dx, recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return i;
    }
}

