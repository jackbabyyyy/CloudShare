package com.daf.cloudshare.base;

import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

/**
 * Created by PP on 2019/2/18.
 */
public class BaseActivity extends QMUIActivity {

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(this, 100);
    }




}
