package com.daf.cloudshare.ui.home;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;

import com.daf.cloudshare.BuildConfig;
import com.daf.cloudshare.MainActivity;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.event.MessageFavorite;
import com.daf.cloudshare.model.CheckBean;
import com.daf.cloudshare.model.DialogBean;
import com.daf.cloudshare.model.FavoriteBean;

import com.daf.cloudshare.model.TipBean;
import com.daf.cloudshare.model.TypeBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.ui.login.LoginActivity;
import com.daf.cloudshare.ui.product.DetailFragment;
import com.daf.cloudshare.ui.favorite.FavoriteFragment;
import com.daf.cloudshare.ui.web.WebFragment;
import com.daf.cloudshare.model.BannerBean;
import com.daf.cloudshare.ui.product.ProductFragment;
import com.daf.cloudshare.utils.Const;
import com.daf.cloudshare.utils.GpsUtils;
import com.daf.cloudshare.utils.HomeDialog;
import com.daf.cloudshare.utils.SP;
import com.daf.cloudshare.utils.StringUtil;
import com.daf.cloudshare.view.DragView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder;
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

import static com.daf.cloudshare.net.AppUrl.checkVersion;

/**
 * Created by PP on 2019/3/15.
 */
public class HomeFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.tv_sub)
    TextView mTvSub;
    @BindView(R.id.root)
    RelativeLayout mRoot;
    @BindView(R.id.iv2)
    ImageView mIv2;
    @BindView(R.id.tv2)
    TextView mTv2;
    @BindView(R.id.tv_sub2)
    TextView mTvSub2;
    @BindView(R.id.root2)
    RelativeLayout mRoot2;
    @BindView(R.id.iv3)
    ImageView mIv3;
    @BindView(R.id.tv3)
    TextView mTv3;
    @BindView(R.id.tv_sub3)
    TextView mTvSub3;
    @BindView(R.id.root3)
    RelativeLayout mRoot3;
    @BindView(R.id.iv4)
    ImageView mIv4;
    @BindView(R.id.tv4)
    TextView mTv4;
    @BindView(R.id.tv_sub4)
    TextView mTvSub4;
    @BindView(R.id.root4)
    RelativeLayout mRoot4;
    @BindView(R.id.tvMore)
    TextView mTvMore;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.flipper)
    ViewFlipper mFlipper;

    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.head)
    View mHead;
    @BindView(R.id.pop)
    DragView mPop;


    private List<FavoriteBean.DataBean> mData = new ArrayList<>();
    private TypeBean mBean;
    private FavoriteHomeAdapter mAdapter;


    private AlertDialog.Builder mBuilder;
    private DialogBean mBean1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home2;
    }

    @Override
    protected void init() {
        initBanner();
        initAd();
        initType();
        initRecycler();
        initSwipe();
        initHomeDialog();

        EventBus.getDefault().register(this);

        checkPermission();

        //  定位服务是否开启
        if (!GpsUtils.isLocServiceEnable(getActivity())) {
           GpsUtils.openGPSSettings(getActivity());
            return;
        }



    }
    private void showAdDialog(DialogBean bean){
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
    }

    private void initHomeDialog() {
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.getDialog, null, new HttpUtil.ResultCallback() {
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

    private void initRecycler() {


        mHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(ProductFragment.getInstance("产品列表", "", AppUrl.prjList));
            }
        });
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mAdapter = new FavoriteHomeAdapter(mData);
        mRecycler.setAdapter(mAdapter);



    //    getData();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                String id = ((List<FavoriteBean.DataBean>) adapter.getData()).get(position).f_value;
                String name = ((List<FavoriteBean.DataBean>) adapter.getData()).get(position).f_name;
                startFragment(DetailFragment.getInstance(id, name));

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.getTopFavorite, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {


                    }

                    @Override
                    public void onResponse(String response) throws IOException {


                        FavoriteBean bean = JSON.parseObject(response, FavoriteBean.class);


                        if (bean.data.size() == 0) {
                            mTvMore.setVisibility(View.GONE);
                            mHead.setVisibility(View.VISIBLE);
//                            mAdapter.addHeaderView(mHeadView);
                        } else {
                            mTvMore.setVisibility(View.VISIBLE);
//                            mAdapter.removeHeaderView(mHeadView);
                            mHead.setVisibility(View.GONE);
                        }
                        mAdapter.setNewData(bean.data);


                    }
                });

    }

    private void initType() {
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.getProType, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {
                        mBean = JSON.parseObject(response, TypeBean.class);
                        mTv.setText(mBean.data.get(0).t_name);
                        mTvSub.setText(mBean.data.get(0).t_subtitle);
                        mTv2.setText(mBean.data.get(1).t_name);
                        mTvSub2.setText(mBean.data.get(1).t_subtitle);
                        mTv3.setText(mBean.data.get(2).t_name);
                        mTvSub3.setText(mBean.data.get(2).t_subtitle);
                        mTv4.setText(mBean.data.get(3).t_name);
                        mTvSub4.setText(mBean.data.get(3).t_subtitle);
                        Glide.with(getActivity()).load(mBean.data.get(0).t_logo).into(mIv);
                        Glide.with(getActivity()).load(mBean.data.get(1).t_logo).into(mIv2);
                        Glide.with(getActivity()).load(mBean.data.get(2).t_logo).into(mIv3);
                        Glide.with(getActivity()).load(mBean.data.get(3).t_logo).into(mIv4);

                    }
                });


    }

    private void initAd() {
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.getIndexTip, null, new HttpUtil.ResultCallback() {
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
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        mFlipper.stopFlipping();

    }

    @OnClick({R.id.root, R.id.root2, R.id.root3, R.id.root4, R.id.tvMore,R.id.pop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.root:
                startFragment(ProductFragment.getInstance(mBean.data.get(0).t_name, mBean.data.get(0).t_id, AppUrl.prjList));
                break;
            case R.id.root2:
                startFragment(ProductFragment.getInstance(mBean.data.get(1).t_name, mBean.data.get(1).t_id, AppUrl.prjList));
                break;
            case R.id.root3:
                startFragment(ProductFragment.getInstance(mBean.data.get(2).t_name, mBean.data.get(2).t_id, AppUrl.prjList));
                break;
            case R.id.root4:
                startFragment(ProductFragment.getInstance(mBean.data.get(3).t_name, mBean.data.get(3).t_id, AppUrl.prjList));
                break;
            case R.id.tvMore:
                startFragment(new FavoriteFragment());
                break;
            case R.id.pop:
                showAdDialog(mBean1);
                break;
        }
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
                if (i==1){
                    mSwipe.setEnabled(false);
                }else if (i==2){
                    mSwipe.setEnabled(true);
                }

            }
        });

        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.banner, null, new HttpUtil.ResultCallback() {
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageFavorite event) {
        getData();

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

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION};

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
}
