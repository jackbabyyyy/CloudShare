package com.daf.cloudshare.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.daf.cloudshare.R;

/**
 * Created by PP on 2019/4/19.
 */
public class MyDragView extends AbastractDragFloatActionButton {
    public MyDragView(Context context) {
        super(context);
    }

    public MyDragView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dragview;
    }

    @Override
    public void renderView(View view) {

    }
}
