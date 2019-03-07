package com.daf.cloudshare;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.ui.home.HomeFragment;
import com.daf.cloudshare.ui.mine.MineFragment;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.ui.product.ProductFragment;
import com.daf.cloudshare.ui.tool.ToolFragment;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIPagerAdapter;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PP on 2019/2/19.
 */
public class MainFragment extends BaseFragment {
    private final static String TAG = HomeFragment.class.getSimpleName();


    @BindView(R.id.pager)
    QMUIViewPager mViewPager;
    @BindView(R.id.tabs)
    QMUITabSegment mTabSegment;



    @Override
    protected View onCreateView() {
        FrameLayout layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main, null);
        ButterKnife.bind(this, layout);
        initTabs();
        initPagers();




        return layout;
    }




    private void initTabs() {
        int normalColor = QMUIResHelper.getAttrColor(getActivity(), R.attr.qmui_config_color_gray_6);
        int selectColor = QMUIResHelper.getAttrColor(getActivity(), R.attr.qmui_config_color_blue);
        mTabSegment.setDefaultNormalColor(normalColor);
        mTabSegment.setDefaultSelectedColor(selectColor);
//        mTabSegment.setDefaultTabIconPosition(QMUITabSegment.ICON_POSITION_BOTTOM);




        QMUITabSegment.Tab tab=new QMUITabSegment.Tab(getActivity().getDrawable(R.mipmap.tab),
                getActivity().getDrawable(R.mipmap.tab_),"首页",false);
        QMUITabSegment.Tab tab2=new QMUITabSegment.Tab(getActivity().getDrawable(R.mipmap.tab2),
                getActivity().getDrawable(R.mipmap.tab2_),"产品",false);
        QMUITabSegment.Tab tab3=new QMUITabSegment.Tab(getActivity().getDrawable(R.mipmap.tab3),
                getActivity().getDrawable(R.mipmap.tab3_),"工具",false);
        QMUITabSegment.Tab tab4=new QMUITabSegment.Tab(getActivity().getDrawable(R.mipmap.tab4),
                getActivity().getDrawable(R.mipmap.tab4_),"我的",false);




        // 如果你的 icon 显示大小和实际大小不吻合:
        // 通过 tab.setTabIconBounds 重新设置 bounds
//        int iconShowSize = QMUIDisplayHelper.dp2px(getContext(), 20);
//        component.setTabIconBounds(0, 0, iconShowSize, iconShowSize);
        mTabSegment.addTab(tab)
                .addTab(tab2)
                .addTab(tab3)
                .addTab(tab4);

    }

    private void initPagers() {
        mViewPager.setSwipeable(false);
        QMUIPagerAdapter adapter = new QMUIPagerAdapter() {
            private FragmentTransaction mCurrentTransaction;
            private Fragment mCurrentPrimaryItem = null;

            @Override
            protected Object hydrate(ViewGroup container, int position) {
                switch (position) {
                    case 0:
                        return new HomeFragment();
                    case 1:
                        return  ProductFragment.getInstance("产品","", AppUrl.prjList);
                    case 2:
                        return new ToolFragment();
                    case 3:
                        return new MineFragment();
                    default:
                        return new HomeFragment();

                }
            }
            @Override
            public void startUpdate(ViewGroup container) {
                if (container.getId() == View.NO_ID) {
                    throw new IllegalStateException("ViewPager with adapter " + this
                            + " requires a view id");
                }
            }
            @Override
            public void finishUpdate(ViewGroup container) {
                if (mCurrentTransaction != null) {
                    mCurrentTransaction.commitNowAllowingStateLoss();
                    mCurrentTransaction = null;
                }
            }

            @Override
            protected void populate(ViewGroup container, Object item, int position) {
                String name = makeFragmentName(container.getId(), position);
                if (mCurrentTransaction == null) {
                    mCurrentTransaction = getChildFragmentManager()
                            .beginTransaction();
                }
                Fragment fragment = getChildFragmentManager().findFragmentByTag(name);
                if (fragment != null) {
                    mCurrentTransaction.attach(fragment);
                } else {
                    fragment = (Fragment) item;
                    mCurrentTransaction.add(container.getId(), fragment, name);
                }
                if (fragment != mCurrentPrimaryItem) {
                    fragment.setMenuVisibility(false);
                    fragment.setUserVisibleHint(false);
                }
            }

            @Override
            protected void destroy(ViewGroup container, int position, Object object) {
                if (mCurrentTransaction == null) {
                    mCurrentTransaction = getChildFragmentManager()
                            .beginTransaction();
                }
                mCurrentTransaction.detach((Fragment) object);
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == ((Fragment) o).getView();
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                Fragment fragment = (Fragment) object;
                if (fragment != mCurrentPrimaryItem) {
                    if (mCurrentPrimaryItem != null) {
                        mCurrentPrimaryItem.setMenuVisibility(false);
                        mCurrentPrimaryItem.setUserVisibleHint(false);
                    }
                    if (fragment != null) {
                        fragment.setMenuVisibility(true);
                        fragment.setUserVisibleHint(true);
                    }
                    mCurrentPrimaryItem = fragment;
                }
            }

            private String makeFragmentName(int viewId, long id) {
                return "QDFitSystemWindowViewPagerFragment:" + viewId + ":" + id;
            }
        };


        mViewPager.setAdapter(adapter);

        mTabSegment.setupWithViewPager(mViewPager,false);



    }


    @Override
    protected boolean canDragBack() {
        return false;
    }
}
