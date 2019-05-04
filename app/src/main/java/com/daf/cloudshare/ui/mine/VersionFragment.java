package com.daf.cloudshare.ui.mine;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.daf.cloudshare.BuildConfig;
import com.daf.cloudshare.R;
import com.daf.cloudshare.VersionManager;
import com.daf.cloudshare.base.BaseFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import org.w3c.dom.Text;

import butterknife.BindView;

/**
 * Created by PP on 2019/4/11.
 */
public class VersionFragment extends BaseFragment {


    @BindView(R.id.bar)
    QMUITopBarLayout mBar;
    @BindView(R.id.version)
    TextView mTvVersion;
    @BindView(R.id.content)
    TextView mTvContent;
    @BindView(R.id.title)
    TextView mTextView;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_version;
    }

    @Override
    protected void init() {
        mTextView.setText("("+BuildConfig.VERSION_NAME+")"+"优化概述");
        mTvVersion.setText("版本号："+ BuildConfig.VERSION_NAME);
        mBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });
        mBar.setTitle("版本介绍");

        if(VersionManager.isToc(getActivity())){
            mTvContent.setText(R.string.version_c);
        }else{
            mTvContent.setText(R.string.version_b);
        }

        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
