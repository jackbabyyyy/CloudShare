package com.daf.cloudshare.ui.product;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;

import com.daf.cloudshare.event.MessageFavorite;
import com.daf.cloudshare.model.DetailBean;
import com.daf.cloudshare.model.DialogBean;
import com.daf.cloudshare.model.FavoriteResBean;
import com.daf.cloudshare.model.PosterBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.ui.web.WebFragment;
import com.daf.cloudshare.utils.TipDialog;
import com.daf.cloudshare.utils.Const;
import com.daf.cloudshare.utils.MoneyUtils;
import com.daf.cloudshare.utils.StringUtil;
import com.daf.cloudshare.wx.OnResponseListener;
import com.daf.cloudshare.wx.ShareFragment;
import com.daf.cloudshare.wx.WXshare;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsReaderView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
 * Created by PP on 2019/2/27.
 * <p>
 * 产品详情页
 */
public class DetailFragment extends BaseFragment implements QbSdk.PreInitCallback, EasyPermissions.PermissionCallbacks, IWXAPIEventHandler {

    private static final String PID = "PID";
    public static final String TAG = DetailFragment.class.getSimpleName();
    @BindView(R.id.tvAmount)
    TextView mTvAmount;
    @BindView(R.id.tv_lilv)
    TextView mTvLilv;
    @BindView(R.id.tv_periods)
    TextView mTvPeriods;
    @BindView(R.id.tv_speed)
    TextView mTvSpeed;


    @BindView(R.id.favorite)
    ImageView mFavorite;
    @BindView(R.id.share)
    TextView mShare;

    @BindView(R.id.tvLook)
    TextView mTvLook;


    @BindView(R.id.login)
    TextView mLogin;


    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBarLayout;

    @BindView(R.id.tv_rate_type)
    TextView mTvRateType;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<DetailBean.DataBean.PAppShowBean> mData = new ArrayList<>();
    private DetailDesAdapter adapter;
    private DetailBean mDetailBean;

    private boolean isFlag = false;
    private String fid = "0";

    private File mFile = null;
    private TipDialog mDialog;
    private String mPoster;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail;
    }


    public static DetailFragment getInstance(String pid, String title) {
        // 通过bundle传递数据
        Bundle bundle = new Bundle();
        bundle.putString(PID, pid);
        bundle.putString("title", title);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    protected void init() {

        wxShare = new WXshare(getActivity());
        wxShare.register();
        wxShare.setListener(new OnResponseListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onFail(String message) {

            }
        });


        QbSdk.initX5Environment(getActivity(), this);

        mTopBarLayout.setTitle(getArguments().getString("title"));
        mTopBarLayout.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });


        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DetailDesAdapter(mData);
        mRecyclerView.setAdapter(adapter);


        String id = getArguments().getString(PID);
        Map<String, String> map = new HashMap<>();
        map.put("proid", id);
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.prjInfo, map, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        showDialog("获取数据出错");

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("code").equals(Const.body_success)) {
                                mDetailBean = JSON.parseObject(response, DetailBean.class);


                                fid = mDetailBean.data.p_isfavorite + "";

                                if (mDetailBean.data.p_isfavorite == 0) {
                                    isFlag = false;
                                } else {
                                    isFlag = true;
                                }
                                //
                                adapter.setNewData(mDetailBean.data.p_appShow);
                                setView(mDetailBean);

                            } else {
                                showDialog(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

        HashMap hashMap = new HashMap();
        hashMap.put("proid", id);
        HttpUtil.getInstance(getActivity()).postForm(AppUrl.getPoster, hashMap, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) throws IOException {
                mPoster = response;


            }
        });


    }

    private void goShare(String response) {
        PosterBean bean = JSON.parseObject(response, PosterBean.class);
        List<String> imglist = bean.data.imgList;
        String img = "";
        if (imglist != null) {
            if (imglist.size() != 0) {
                img = imglist.get(0);
            }
        }
        startFragment(ShareFragment.getInstance(img,bean.data.jumpUrl));

    }

    private void setView(DetailBean detailBean) {

        //title 补充
        mTopBarLayout.setTitle(detailBean.data.p_name);

        mTvAmount.setText(MoneyUtils.get(detailBean.data.p_limitdown) + "~" + MoneyUtils.get(detailBean.data.p_limitup));

        String ratedown = detailBean.data.p_ratedown;
        String rateup = detailBean.data.p_rateup;
        if (ratedown.equals(rateup)) {
            mTvLilv.setText(rateup + "%");
            mTvLilv.setTextSize(18);
        } else {
            mTvLilv.setTextSize(13);
            mTvLilv.setText(ratedown + "%~" + rateup + "%");
        }

        String perdown = detailBean.data.p_deadlinedown;
        String perup = detailBean.data.p_deadlineup;
        if (perdown.equals(perup)) {
            mTvPeriods.setText(perdown);
        } else {
            mTvPeriods.setText(perdown + "~" + perup);
        }


        //  mTvLilv.setText(detailBean.data.p_rate + "%");

        //    mTvPeriods.setText(detailBean.data.p_periods + "");
        mTvSpeed.setText("T+" + detailBean.data.p_speed);
        mTvRateType.setText(detailBean.data.p_ratetype);

        if (TextUtils.isEmpty(mDetailBean.data.p_outline)) {
            mTvLook.setVisibility(View.GONE);
        } else {
            mTvLook.setVisibility(View.VISIBLE);

        }
        if (isFlag) {
            mFavorite.setImageResource(R.mipmap.favorite);
        } else {
            mFavorite.setImageResource(R.mipmap.favorite_dark);
        }


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mDetailBean.data.p_isappadd.equals("0")) {
                    showUnableApp();
                    return;
                }
                startFragment(WebFragment.getInstance(detailBean.data.p_jumpurl));
            }
        });


    }

    private void showUnableApp() {

        mDialog = new TipDialog(getActivity());
        mDialog.showDialog();


    }


    @OnClick({R.id.favorite, R.id.share, R.id.tvLook})
    public void onViewClicked(View view) {


        if (!mDetailBean.code.equals(Const.body_success)) {
            return;
        }

        switch (view.getId()) {
            case R.id.favorite:

                onClickFavorite();
                break;

            case R.id.share:
                goShare(mPoster);
//                BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
//                View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_share, null);
//                v.findViewById(R.id.wx).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        wxShare.shareUrl(0, getActivity(), mDetailBean.data.p_jumpurl, mDetailBean.data.p_name, mDetailBean.data.p_resume + "\n立即申请");
//                        dialog.dismiss();
//                    }
//                });
//                dialog.setContentView(v);
//                dialog.show();


                break;

            case R.id.tvLook:
                checkPermission();
                break;
        }
    }

    private void onClickFavorite() {
        if (isFlag) {
            cancelFavorite(fid);
        } else {
            addToFavorite();
        }
    }


    private void preDownloadFile() {


        if (mFile != null) {
            showTbsDialog();
            return;
        }

        HttpUtil.getInstance(getActivity()).downdload(mDetailBean.data.p_outline, Environment.getExternalStorageDirectory().getAbsolutePath(),
                StringUtil.getFileName(mDetailBean.data.p_outline), new HttpUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(File file) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mFile = file;

                                showTbsDialog();

                            }
                        });


                    }

                    @Override
                    public void onDownloading(int progress) {
                        //  Log.d(TAG, "onDownloading: " + progress);

                    }

                    @Override
                    public void onDownloadFailed(Exception e) {
                        Log.d(TAG, "onDownloadFailed: " + e.getMessage());

                    }
                });

    }


    private void showTbsDialog() {


        TbsReaderView tbsReaderView = new TbsReaderView(getActivity(), new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {

            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("filePath", mFile.getAbsolutePath());
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
        boolean result = tbsReaderView.preOpen(mFile.getName().split("[.]")[1], false);
        if (result) {
            tbsReaderView.openFile(bundle);
        }


        Dialog dialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialog);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.tbs, null);
        v.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                tbsReaderView.onStop();
            }
        });
        LinearLayout rootRl = (LinearLayout) v.findViewById(R.id.rl_root);
        rootRl.addView(tbsReaderView, -1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootRl.setGravity(Gravity.BOTTOM);

        dialog.setContentView(v);
        dialog.show();

    }


    private void cancelFavorite(String id) {

        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        Log.d(TAG, "cancelFavorite: " + id);
        HttpUtil.getInstance(getActivity()).postForm(AppUrl.cancelFavorite, map, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    showToast(jsonObject.getString("msg"));
                    if (jsonObject.getString("code").equals(Const.getFavorite_cancel)) {
                        mFavorite.setImageResource(R.mipmap.favorite_dark);
                        isFlag = false;
                        //
                        postEvent();
                    } else {
                        mFavorite.setImageResource(R.mipmap.favorite);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void addToFavorite() {
        Map<String, String> map = new HashMap<>();
        map.put("name", mDetailBean.data.p_name);
        map.put("value", mDetailBean.data.p_id);
        map.put("type", "1");
        map.put("logo", mDetailBean.data.p_logo);
        map.put("order", "0");
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.addToFavorite, map, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {
                        FavoriteResBean bean = JSON.parseObject(response, FavoriteResBean.class);
                        showToast(bean.msg);
                        if (bean.code.equals(Const.favorite_ok)) {
                            mFavorite.setImageResource(R.mipmap.favorite);
                            isFlag = true;
                            //
                            postEvent();
                            fid = bean.data.f_id;
                        } else {
                            mFavorite.setImageResource(R.mipmap.favorite_dark);
                        }


                    }
                });


    }

    private void postEvent() {
        EventBus.getDefault().post(new MessageFavorite());
    }

    @Override
    public void onCoreInitFinished() {

    }

    @Override
    public void onViewInitFinished(boolean b) {

    }

    private void showDialog(String msg) {
        new QMUIDialog.MessageDialogBuilder(getActivity())

                .setTitle("提醒")
                .setMessage(msg)

                .setCanceledOnTouchOutside(false)
                .setCancelable(false)
                .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        popBackStack();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();

    }


    public void checkPermission() {

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(getContext(), perms)) {

            preDownloadFile();
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, "允许访问文件",
                    888, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted

        preDownloadFile();


    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied

        showToast("需要文件访问权限！");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }


    private WXshare wxShare;

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Intent intent = new Intent(WXshare.ACTION_SHARE_RESPONSE);
        intent.putExtra(WXshare.EXTRA_RESULT, new WXshare.Response(baseResp));
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wxShare.unregister();
    }


}
