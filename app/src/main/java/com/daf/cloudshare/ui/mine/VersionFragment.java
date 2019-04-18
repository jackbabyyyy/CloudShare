package com.daf.cloudshare.ui.mine;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.daf.cloudshare.BuildConfig;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import org.w3c.dom.Text;

import butterknife.BindView;

/**
 * Created by PP on 2019/4/11.
 */
public class VersionFragment extends BaseFragment {

    private static final String content="1、版本更新增加更新详情；\n" +
            "\n"+
            "2、增加城市定位选择功能；\n" +
            "\n"+
            "3、产品列表增加搜索、筛选、排序功能；\n" +
            "\n"+
            "4、产品列表增加热门产品推荐，并有标识；\n" +
            "\n"+
            "5、我的收藏增加取消功能；\n" +
            "\n"+
            "6、产品分享功能优化；\n" +
            "\n"+

            "7、我的二维码增加分享功能；";
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

        mTvContent.setText(content);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
