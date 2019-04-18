package com.daf.cloudshare.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by PP on 2019/4/17.
 */
public class MySwipeRefreshLayout extends SwipeRefreshLayout {
    private float mStartX=0;
    private float mStartY=0;
    private boolean mIsvpDrag;
    private final int mTouchSlop;
    public MySwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop=ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        int action= event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mStartX=event.getX();
                mStartY=event.getY();
                mIsvpDrag=false;
                break;

                case MotionEvent.ACTION_MOVE:
                    if (mIsvpDrag){
                        return  false;
                    }
                    float endY=event.getY();
                    float ednX=event.getX();
                    float distanceX=Math.abs(ednX-mStartX);
                    float distanceY=Math.abs(endY-mStartY);
                    if (distanceX>mTouchSlop&&distanceX>distanceY){
                        mIsvpDrag=true;
                        return false;
                    }
                    break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsvpDrag=false;
                break;
                default:
                    break;
        }
        return super.onInterceptHoverEvent(event);
    }
}
