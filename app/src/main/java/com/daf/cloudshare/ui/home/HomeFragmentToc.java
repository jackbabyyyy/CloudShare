package com.daf.cloudshare.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.BuildConfig;
import com.daf.cloudshare.MainActivity;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.event.MessageFavorite;
import com.daf.cloudshare.event.MessageTabIndex;
import com.daf.cloudshare.model.BannerBean;
import com.daf.cloudshare.model.CheckBean;
import com.daf.cloudshare.model.DialogBean;
import com.daf.cloudshare.model.FavoriteBean;
import com.daf.cloudshare.model.InfoBeanToc;
import com.daf.cloudshare.model.MyPrjDataListBean;
import com.daf.cloudshare.model.ProductBean;
import com.daf.cloudshare.model.TipBean;
import com.daf.cloudshare.model.TypeBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.ui.home.adapter.HotAdapter;
import com.daf.cloudshare.ui.mine.MyQrFragment;
import com.daf.cloudshare.ui.mine.OauthFragmentToc;
import com.daf.cloudshare.ui.mine.favorite.FavoriteFragment;
import com.daf.cloudshare.ui.mine.tool.ToolFragment;
import com.daf.cloudshare.ui.product.DetailFragment;
import com.daf.cloudshare.ui.product.ProductFragment;
import com.daf.cloudshare.ui.web.WebFragment;
import com.daf.cloudshare.utils.Const;
import com.daf.cloudshare.utils.GpsUtils;
import com.daf.cloudshare.utils.HomeDialog;
import com.daf.cloudshare.utils.MyLoadMoreView;
import com.daf.cloudshare.utils.SP;
import com.daf.cloudshare.utils.StringUtil;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder;
import com.sonnyjack.widget.dragview.SonnyJackDragView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by PP on 2019/3/15.
 */
public class HomeFragmentToc extends BaseFragment implements EasyPermissions.PermissionCallbacks, View.OnClickListener {
//    @BindView(R.id.banner)
//    Banner mBanner;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
//    @BindView(R.id.flipper)
//    ViewFlipper mFlipper;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
//    @BindView(R.id.toc1)
//    View mToc1;
//    @BindView(R.id.toc2)
//    View mToc2;
//    @BindView(R.id.toc3)
//    View mToc3;
//    @BindView(R.id.toc4)
//    View mToc4;
    private boolean mPopShow=true;
    private List<ProductBean.DataBean> mData = new ArrayList<>();

    private HotAdapter mAdapter;


    private DialogBean mBean1;
    private SonnyJackDragView mSonnyJackDragView;
    private Banner mBanner;
    private ViewFlipper mFlipper;
    private View mToc1;
    private View mToc2;
    private View mToc3;
    private View mToc4;
    private String mIdcard;
    private String mHeadString;
    private String mName;
    private String mCompany = "";
    private RxPermissions mRxPermissions;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_toc;
    }

    @Override
    protected void init() {
        initRecycler();

        initBanner();
        initAd();
        initSwipe();
        initHomeDialog();
        initPop();

        mRxPermissions = new RxPermissions(getActivity());
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        update();
                        // All requested permissions are granted
                    } else {
                        // At least one permission is denied
                    }
                });

        EventBus.getDefault().register(this);
        checkPermission();

        //  定位服务是否开启
        if (!GpsUtils.isLocServiceEnable(getActivity())) {
            GpsUtils.openGPSSettings(getActivity());
            return;
        }

        getData();
        getMyInfoData();



    }
    private void getMyInfoData() {

        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.base+AppUrl.getMyInfo, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {

                        SP.saveInfo(getActivity(),response);
                        initInfo();




                    }
                });
    }

    private void initInfo() {
        InfoBeanToc beanToc=JSON.parseObject(SP.getInfo(getActivity()),InfoBeanToc.class);
        mIdcard=beanToc.data.i_idcard;
        mName=beanToc.data.i_name;
        mHeadString=beanToc.data.i_logo;
        mCompany="";
    }

    private void initPop() {
        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                imageView.setLayoutParams(params);
            }
        });
        imageView.setImageResource(R.mipmap.home_pop);
        imageView.setOnClickListener(v -> showAdDialog(mBean1));

        mSonnyJackDragView = new SonnyJackDragView.Builder()
                .setActivity(getActivity())//当前Activity，不可为空
                .setDefaultLeft(30)//初始位置左边距
                .setDefaultTop(300)//初始位置上边距
                .setNeedNearEdge(false)//拖动停止后，是否移到边沿
//                .setSize(100)//DragView大小
                .setView(imageView)//设置自定义的DragView，切记不可为空
                .build();
    }

    private void showAdDialog(DialogBean bean) {

        try {
            String url = bean.data.url;
            String type = bean.data.type;
            String icon = bean.data.icon;
            HomeDialog dialog = new HomeDialog(getActivity());
            dialog.mIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                    if (type.equals("view")) {
                        if (!TextUtils.isEmpty(url) && url.contains("http")) {
                            startFragment(WebFragment.getInstance(url));
                        }

                    } else if (type.equals("browser")) {
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    } else if (type.equals("product")) {

                        startFragment(DetailFragment.getInstance(url, ""));

                    }
                }
            });

            dialog.showDialog(icon);
        }catch (Exception e){

        }
    }

    private void initHomeDialog() {
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.base+AppUrl.getDialog, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {
                        mBean1 = JSON.parseObject(response, DialogBean.class);
                        showAdDialog(mBean1);
                    }
                });

    }

    private void update() {
        Map<String, String> map = new HashMap<>();
        map.put("appid", BuildConfig.APP_ID);
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.checkVersion, map, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("code").equals(Const.body_success)) {
                                CheckBean checkBean = JSON.parseObject(response, CheckBean.class);
                                //Integer.valueOf(checkBean.data.app_versions_android.version_id)
                                if (checkBean.data.app_versions_android.version_id==null)return;
                                if (Integer.valueOf(checkBean.data.app_versions_android.version_id) > StringUtil.getVersionCode(getActivity())) {


                                    QMUIDialogBuilder builder = new QMUIDialog.MessageDialogBuilder(getActivity())
                                            .setTitle("版本更新")
                                            .setMessage("是否更新到最新版本？")
                                            .setCanceledOnTouchOutside(false)
                                            .setCancelable(false)
                                            .addAction(0, "更新", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                                                @Override
                                                public void onClick(QMUIDialog dialog, int index) {
                                                    dialog.dismiss();
                                                    android.app.AlertDialog.Builder mBuilder = null;
                                                    android.app.AlertDialog mDialog;

                                                    View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_download, null);
                                                    ProgressBar mProgressBar1 = v.findViewById(R.id.progressBar);
                                                    mBuilder = new android.app.AlertDialog.Builder(getActivity())
                                                            .setView(v)
                                                            .setCancelable(false);
                                                    mDialog = mBuilder.show();


                                                    String url = checkBean.data.app_versions_android.android_url;

                                                    HttpUtil.getInstance(getActivity()).downdload(url, Environment.getExternalStorageDirectory().getAbsolutePath(),
                                                            StringUtil.getFileName(url), new HttpUtil.OnDownloadListener() {
                                                                @Override
                                                                public void onDownloadSuccess(File file) {
                                                                    getActivity().runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {

                                                                            installApk(file);

                                                                            mDialog.dismiss();

                                                                        }
                                                                    });


                                                                }

                                                                @Override
                                                                public void onDownloading(int progress) {

                                                                    getActivity().runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            mProgressBar1.setProgress(progress);
                                                                        }
                                                                    });


                                                                    Log.d("download", "onDownloading: " + progress);

                                                                }

                                                                @Override
                                                                public void onDownloadFailed(Exception e) {
                                                                    Log.d("download", "e: " + e.getMessage());

                                                                }
                                                            });

                                                }
                                            });


                                    if (checkBean.data.app_versions_android.level.equals("1")) {
                                        builder.addAction("取消", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    builder.create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();


                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                });

    }

    private void initSwipe() {
        mSwipe.setColorSchemeResources(R.color.app_color_blue);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                mAdapter.getData().clear();
                getData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipe.setRefreshing(false);
                    }
                }, 1500);

            }
        });
    }

    private void getData() {
        HashMap<String, String> map = new HashMap<>();

        HttpUtil.getInstance(getActivity()).postForm(AppUrl.base+AppUrl.getHotBank, null, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) throws IOException {

                ProductBean bean = JSON.parseObject(response, ProductBean.class);
                mAdapter.setNewData(bean.getData());


            }
        });

    }

    private void initRecycler() {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.head_fragment_home_toc, null);
        mBanner = v.findViewById(R.id.banner);
        mFlipper = v.findViewById(R.id.flipper);
        mToc1 = v.findViewById(R.id.toc1);
        mToc2 = v.findViewById(R.id.toc2);

        mToc3 = v.findViewById(R.id.toc3);
        mToc4 = v.findViewById(R.id.toc4);
        mToc1.setOnClickListener(this);
        mToc2.setOnClickListener(this);
        mToc3.setOnClickListener(this);
        mToc4.setOnClickListener(this);

        if (BuildConfig.APPLICATION_ID.equals("com.dafyun.app.salesman")){
            mToc3.setVisibility(View.VISIBLE);
        }else{
            mToc3.setVisibility(View.GONE);
        }

        mRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mSwipe.isRefreshing()) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {

            int space = QMUIDisplayHelper.dp2px(getActivity(), 12);

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int pos = parent.getChildAdapterPosition(view);

                //     Log.d("test", "getItemOffsets: "+pos);
                outRect.bottom = space;
                if (pos < 4 && pos > 0) {
                    outRect.top = space;
                }
                if (pos % 3 == 1) {
                    outRect.left = space;
                    outRect.right = space / 2;
                } else if (pos % 3 == 2) {
                    outRect.left = space / 2;
                    outRect.right = space / 2;
                } else if (pos % 3 == 0 && pos != 0) {
                    outRect.left = space / 2;
                    outRect.right = space;
                }


            }
        });
        mAdapter = new HotAdapter(mData);
        mRecycler.setAdapter(mAdapter);
        mAdapter.addHeaderView(v);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startFragment(DetailFragment.getInstance(mAdapter.getData().get(position).getP_id(), mAdapter.getData().get(position).getP_name()));
            }
        });





    }

    @Override
    public void onResume() {
        super.onResume();


        if (mPopShow)
            mSonnyJackDragView.getDragView().setVisibility(View.VISIBLE);

        else
            mSonnyJackDragView.getDragView().setVisibility(View.GONE);

    }


    private void initAd() {
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.base+AppUrl.getIndexTip, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String s) throws IOException {
                        try {

                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getString("code").equals(Const.body_success)) {

                                TipBean tipBean = JSON.parseObject(s, TipBean.class);

                                for (int i = 0; i < tipBean.data.size(); i++) {
                                    View flipper_item = LayoutInflater.from(getActivity()).inflate(R.layout.flipper_item, null);
                                    TextView index = flipper_item.findViewById(R.id.index);
                                    TextView index2 = flipper_item.findViewById(R.id.index2);
                                    index.setText(tipBean.data.get(i).p_name + "产品最新上架");

                                    if (TextUtils.isEmpty(tipBean.data.get(i).p_resume)) {
                                        index2.setVisibility(View.GONE);
                                    } else {
                                        index2.setVisibility(View.VISIBLE);
                                    }

                                    index2.setText(tipBean.data.get(i).p_resume);
                                    mFlipper.addView(flipper_item);
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


        mFlipper.setAutoStart(true);
        mFlipper.startFlipping();


    }

    @Override
    public void onPause() {
        super.onPause();

        mSonnyJackDragView.getDragView().setVisibility(View.GONE);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        mFlipper.stopFlipping();


    }


    private void initBanner() {
        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (i == 1) {
                    mSwipe.setEnabled(false);
                } else if (i == 2) {
                    mSwipe.setEnabled(true);
                }

            }
        });

        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.base+AppUrl.banner, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String s) throws IOException {
                        try {

                            JSONObject json = new JSONObject(s);
                            if (json.getString("code").equals(Const.body_success)) {
                                BannerBean bannerBean = JSON.parseObject(s, BannerBean.class);
                                List<BannerBean.DataBean> mDatas = bannerBean.getData();
                                List<String> imgs = new ArrayList<>();
                                for (int i = 0; i < mDatas.size(); i++) {
                                    imgs.add(mDatas.get(i).getIcon());
                                }
                                //
                                mBanner.setImages(imgs);
                                mBanner.setImageLoader(new ImageLoader() {
                                    @Override
                                    public void displayImage(Context context, Object path, ImageView imageView) {
                                        Glide.with(getActivity()).load(path).into(imageView);
                                    }
                                });
                                mBanner.setOnBannerListener(new OnBannerListener() {
                                    @Override
                                    public void OnBannerClick(int position) {

                                        //点击
                                        String type = mDatas.get(position).getType();
                                        String url = mDatas.get(position).getUrl();
                                        if (type.equals("view")) {


                                            if (!TextUtils.isEmpty(url) && url.contains("http")) {
                                                startFragment(WebFragment.getInstance(url));
                                            }

                                        } else if (type.equals("browser")) {
                                            Uri uri = Uri.parse(url);
                                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                            startActivity(intent);
                                        } else if (type.equals("product")) {

                                            startFragment(DetailFragment.getInstance(url, ""));

                                        }


                                    }
                                }).start();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


    }


    private void installApk(File file) {
        Uri uri = Uri.fromFile(file);
        Uri photoURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".file_provider", file);
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) {
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.setDataAndType(photoURI, "application/vnd.android.package-archive");
        } else {
            install.setDataAndType(uri, "application/vnd.android.package-archive");
        }


        // 执行意图进行安装
        getActivity().startActivity(install);
    }

    public void checkPermission() {

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};

        if (EasyPermissions.hasPermissions(getContext(), perms)) {

            update();

        } else {
            // Request one permission


            EasyPermissions.requestPermissions(this, "允许访问文件",
                    888, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted

        update();


    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied

        showToast("更新需要文件访问权限！");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if (getUserVisibleHint()) {//界面可见时
                mSonnyJackDragView.getDragView().setVisibility(View.VISIBLE);
            } else {
                mSonnyJackDragView.getDragView().setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.toc1:
                startFragment(new ToolFragment());
                break;
            case R.id.toc2:

                if (TextUtils.isEmpty(mIdcard)) {
                    startFragment(new OauthFragmentToc());
                } else {
                    startFragment(MyQrFragment.getInstance(mHeadString, mName, mCompany));
                }
                break;
            case R.id.toc3:
                startFragment(WebFragment.getInstance(AppUrl.toc3));
                break;
            case R.id.toc4:
                startFragment(WebFragment.getInstance(AppUrl.toc4));
                break;


        }
    }






    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageTabIndex event) {
        if (event.show) {
            mPopShow = true;
        } else
            mPopShow = false;

    }

}
