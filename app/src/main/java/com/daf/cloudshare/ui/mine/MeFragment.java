package com.daf.cloudshare.ui.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daf.cloudshare.BuildConfig;
import com.daf.cloudshare.R;
import com.daf.cloudshare.VersionManager;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.model.InfoBeanToc;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.ui.login.LoginActivity;
import com.daf.cloudshare.ui.mine.favorite.FavoriteFragment;
import com.daf.cloudshare.ui.mine.tool.ToolFragment;
import com.daf.cloudshare.utils.DataCleanManager;
import com.daf.cloudshare.utils.GlideManager;
import com.daf.cloudshare.utils.ImageUtils;
import com.daf.cloudshare.utils.SP;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.walkermanx.photopicker.PhotoPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by PP on 2019/5/7.
 */
public class MeFragment extends BaseFragment {

    @BindView(R.id.ivHead)
    CircleImageView mIvHead;
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.ivPartner)
    ImageView mIvPartner;
    @BindView(R.id.btnSZ)
    TextView mBtnSZ;
    @BindView(R.id.tvYJ)
    TextView mTvYJ;
    @BindView(R.id.tvKTX)
    TextView mTvKTX;
    @BindView(R.id.btnTX)
    TextView mBtnTX;
    @BindView(R.id.tvYJmonth)
    TextView mTvYJmonth;
    @BindView(R.id.tvYJmy)
    TextView mTvYJmy;
    @BindView(R.id.tvYJteam)
    TextView mTvYJteam;
    @BindView(R.id.tvYJall)
    TextView mTvYJall;
    @BindView(R.id.btnLook)
    TextView mBtnLook;
    @BindView(R.id.btnTeam)
    LinearLayout mBtnTeam;
    @BindView(R.id.btnRecruit)
    LinearLayout mBtnRecruit;
    @BindView(R.id.btnSettlement)
    LinearLayout mBtnSettlement;
    @BindView(R.id.btnCustomer)
    LinearLayout mBtnCustomer;
    @BindView(R.id.btn)
    Button mBtnOut;
    @BindView(R.id.bar)
    QMUITopBarLayout mBar;
    @BindView(R.id.btnOauth)
    LinearLayout mBtnOauth;


    private String mS;
    private String mName;
    private String mCompany;
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    private boolean mIsToc;
    private boolean mIsOauth;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void init() {
        mIsToc = VersionManager.isToc(getActivity());//是否c端
        mIsOauth = isOauth();//是否实名认证 c端
        mBar.setTitle("个人中心");
        //toc
        if (mIsToc) {
            mBtnOauth.setVisibility(View.VISIBLE);
        } else {
            mBtnOauth.setVisibility(View.GONE);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拍照功能或者裁剪功能返回

        if (null != data) {
            String path = data.getStringExtra(PhotoPicker.KEY_CAMEAR_PATH);
            upload(path);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        getMyInfoData();
    }

    @OnClick({R.id.ivHead, R.id.btnSZ, R.id.btnTX, R.id.btnLook, R.id.btnTeam, R.id.btnRecruit, R.id.btnSettlement, R.id.btnCustomer, R.id.btn
            , R.id.btnOauth, R.id.btnFavorite, R.id.btnProgress, R.id.btnPass, R.id.btnAdvice, R.id.btnCache, R.id.btnVersion})
    public void onViewClicked(View view) {


        switch (view.getId()) {
            case R.id.ivHead:
                photoPicker();
                break;
            case R.id.btnSZ:
                break;
            case R.id.btnTX:
                break;
            case R.id.btnLook:
                break;
            case R.id.btnTeam:
                if (mIsToc && mIsOauth) {
                    startFragment(new TeamFragmentToc());
                } else {
                    startFragment(new OauthFragmentToc());
                }

                break;
            case R.id.btnRecruit:
                if (mIsToc && mIsOauth) {
                    startFragment(MyQrFragment.getInstance(mS, mName, mCompany));
                } else {
                    startFragment(new OauthFragmentToc());
                }

                break;
            case R.id.btnSettlement:

                break;
            case R.id.btnCustomer:
                if (mIsToc && mIsOauth) {
                    startFragment(new KefuFragmentToc());
                } else {
                    startFragment(new OauthFragmentToc());
                }

                break;
            case R.id.btn:
                showLoginDialog();
                break;
            case R.id.btnOauth:
                startFragment(new OauthFragmentToc());
                break;
            case R.id.btnFavorite:
                startFragment(new FavoriteFragment());
                break;
            case R.id.btnProgress:
                startFragment(new ToolFragment());
                break;
            case R.id.btnPass:
                startFragment(new ModifyPassFragment());
                break;
            case R.id.btnAdvice:
                startFragment(new SubmitFragment());
                break;
            case R.id.btnCache:
                showClearDialog();
                break;
            case R.id.btnVersion:
                if (BuildConfig.APPLICATION_ID.equals("cn.dafyun.app.salesmanqxpc")) {
                    return;
                }
                startFragment(new VersionFragment());

                break;
        }
    }

    private void photoPicker() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setPreviewEnabled(false)
                .setCrop(true)
                .setCropXY(1, 1)
                .start(getActivity(), this);
    }

    private void upload(String path) {
        Luban.with(getActivity())
                .load(Uri.fromFile(new File(path)))
                .ignoreBy(50)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        String base64 = ImageUtils.file2Base64(file.getAbsolutePath());
                        Map<String, String> map = new HashMap<>();
                        map.put("avatar", base64);
                        HttpUtil.getInstance(getActivity())
                                .postForm(AppUrl.base+AppUrl.saveAvatar, map, new HttpUtil.ResultCallback() {
                                    @Override
                                    public void onError(Request request, Exception e) {

                                    }

                                    @Override
                                    public void onResponse(String response) throws IOException {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            showToast(jsonObject.getString("msg"));
                                            if (jsonObject.getString("code").equals("90003")) {
                                                //头像上传成功
                                                mS=base64;

                                                Glide.with(getActivity()).load(Uri.fromFile(new File(path))).into(mIvHead);
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();


    }



    private boolean isOauth() {
        InfoBeanToc beanToc = JSON.parseObject(SP.getInfo(getActivity()), InfoBeanToc.class);
        if (TextUtils.isEmpty(beanToc.data.i_id)) {
            return false;
        } else
            return true;

    }

    private void showClearDialog() {
        String cache = "";
        try {
            cache = DataCleanManager.getTotalCacheSize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new QMUIDialog.MessageDialogBuilder(getActivity())

                .setMessage("确定要清除" + cache + "缓存吗？")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "清除", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        DataCleanManager.clearAllCache(getActivity());
                        showToast("成功清除缓存!");

                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    private void showLoginDialog() {
        new QMUIDialog.MessageDialogBuilder(getActivity())

                .setMessage("确定要退出当前登录吗？")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "退出", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        //shanchu 登录信息
                        SP.clear(getActivity());

                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        popBackStack();
                    }
                })
                .create(mCurrentDialogStyle).show();

    }


    private void getMyInfoData() {
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.base+AppUrl.getMyInfo, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {

                        if (mIsToc){
                            loadInfoTOC(response);
                        }else{
                            loadInfo(response);
                        }


                    }
                });
    }



    private void loadInfo(String res){
        try {

            JSONObject jsonObject=new JSONObject(res);
            JSONObject data=new JSONObject(jsonObject.getString("data"));
            mS = data.getString("u_avatar");

            mName = data.getString("u_name");
            String phone=data.getString("u_tel");
            String money=data.getString("u_money");
            mCompany = data.getString("a_company");
            mTvName.setText(mName);
            mTvYJ.setText("余额: "+money);




            Glide.with(getActivity())
                    .load(ImageUtils.stringToBitmap(mS))
                    .apply(GlideManager.getGlideOptions())
                    .into(mIvHead);

            SP.put(getActivity(),"name",mName);
            SP.put(getActivity(),"phone",phone);


        } catch (JSONException e) {


        }


    }

    private void loadInfoTOC(String res){

        InfoBeanToc beanToc = JSON.parseObject(res, InfoBeanToc.class);
        mS = beanToc.data.i_logo;
        mName = beanToc.data.i_name;
        String phone = beanToc.data.i_telephone;
        mTvName.setText(mName);


        Glide.with(getActivity())
                .load(ImageUtils.stringToBitmap(mS))
                .apply(GlideManager.getGlideOptions())
                .into(mIvHead);

        SP.put(getActivity(), "name", mName);
        SP.put(getActivity(), "phone", phone);

    }






}
