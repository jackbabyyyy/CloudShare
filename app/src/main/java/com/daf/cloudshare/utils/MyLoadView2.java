package com.daf.cloudshare.utils;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.daf.cloudshare.R;

public class MyLoadView2 extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.view_load_more2;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.normal;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.normal;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.normal;
    }
}
