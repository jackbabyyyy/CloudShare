package com.daf.cloudshare.ui.mine;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.AppData;
import com.daf.cloudshare.BuildConfig;
import com.daf.cloudshare.R;
import com.daf.cloudshare.VersionManager;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.event.MessageNewTip;
import com.daf.cloudshare.model.InfoBeanToc;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.ui.login.LoginActivity;
import com.daf.cloudshare.ui.mine.favorite.FavoriteFragment;
import com.daf.cloudshare.ui.mine.tool.ToolFragment;
import com.daf.cloudshare.utils.DataCleanManager;
import com.daf.cloudshare.utils.ImageUtils;
import com.daf.cloudshare.utils.SP;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.walkermanx.photopicker.PhotoPicker;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by PP on 2019/2/19.
 */
public class MineFragmentToc extends BaseFragment {

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.bar)
    QMUITopBarLayout mBar;
    private CircleImageView mHeadView;
    private TextView mTvName;
    private TextView mTvMoney;
    private String mS;
    private String mName;
    private TextView mTvCompany;
    private String mCompany;
    private MineAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }


    @Override
    protected void init() {
        mBar.setTitle("个人中心");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MineAdapter(AppData.getMineBodyTOC());
        View head = LayoutInflater.from(getActivity()).inflate(R.layout.head_mine, null);
        mTvName = head.findViewById(R.id.tvName);
        mTvMoney = head.findViewById(R.id.tvMoney);
        mHeadView = head.findViewById(R.id.head);
        mTvCompany = head.findViewById(R.id.tvCompany);
        mHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                photoPicker();


            }
        });
        mAdapter.addHeaderView(head);
        View foot = LayoutInflater.from(getActivity()).inflate(R.layout.foot_mine, null);
        foot.findViewById(R.id.login_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });
        mAdapter.addFooterView(foot);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                InfoBeanToc beanToc = JSON.parseObject(SP.getInfo(getActivity()), InfoBeanToc.class);
                String idcard = beanToc.data.i_idcard;
                switch (position) {
                    case 3:
                        startFragment(new FavoriteFragment());
                        break;
                    case 4:
                        startFragment(new ToolFragment());
                        break;
                    case 6:

                        if (checkOauth()) {
                            startFragment(new KefuFragmentToc());
                        } else {
                            startFragment(new OauthFragmentToc());
                        }

                        break;
                    case 0:

                        if (TextUtils.isEmpty(idcard)) {
                            startFragment(new OauthFragmentToc());
                        } else {
                            startFragment(new TeamFragmentToc());
                        }

                        break;
                    case 2:

                        startFragment(new OauthFragmentToc());
                        break;
                    case 1:
                        if (TextUtils.isEmpty(idcard)) {
                            startFragment(new OauthFragmentToc());
                        } else {
                            startFragment(MyQrFragment.getInstance(mS, mName, mCompany));
                        }

                        break;
                    case 5:
                        startFragment(new ModifyPassFragment());
                        break;
                    case 7:
                        startFragment(new SubmitFragment());
                        break;
                    case 8:
                        showClearDialog();
                        break;
                    case 9:
                        if (BuildConfig.APPLICATION_ID.equals("cn.dafyun.app.salesmanqxpc")){
                            return;
                        }
                        EventBus.getDefault().post(new MessageNewTip());
                        VersionManager.setNewVersionHasLook(getActivity());
                        mAdapter.notifyItemChanged(mAdapter.getData().size());
                        startFragment(new VersionFragment());
                        break;
                }
            }
        });


        getMyInfoData();

    }

    private boolean checkOauth() {
        InfoBeanToc beanToc = JSON.parseObject(SP.getInfo(getActivity()), InfoBeanToc.class);
        if (TextUtils.isEmpty(beanToc.data.i_id)) {
            return false;
        } else
            return true;

    }

    private void photoPicker() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setPreviewEnabled(false)
                .setCrop(true)
                .setCropXY(1, 1)
                .start(getActivity(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拍照功能或者裁剪功能返回

        if (null != data) {
            String path = data.getStringExtra(PhotoPicker.KEY_CAMEAR_PATH);
            Glide.with(getActivity()).load(Uri.fromFile(new File(path))).into(mHeadView);


            upload(path);
        }


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
                                .postForm(AppUrl.saveAvatar, map, new HttpUtil.ResultCallback() {
                                    @Override
                                    public void onError(Request request, Exception e) {

                                    }

                                    @Override
                                    public void onResponse(String response) throws IOException {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            showToast(jsonObject.getString("msg"));
                                            if (jsonObject.getString("code").equals("90003")) {
                                                freshMyInfo();
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

    private void getMyInfoData() {
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.getMyInfo, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {

                        InfoBeanToc beanToc = JSON.parseObject(response, InfoBeanToc.class);

                        mS = beanToc.data.i_logo;
                        mName = beanToc.data.i_name;
                        String phone = beanToc.data.i_telephone;
                        mTvName.setText(mName);
//                        mTvMoney.setText("余额: "+money);
//                        mTvCompany.setText(mCompany);
                        RequestOptions options = new RequestOptions()
                                .placeholder(R.mipmap.ic_photo)//图片加载出来前，显示的图片
                                .fallback(R.mipmap.ic_photo) //url为空的时候,显示的图片
                                .error(R.mipmap.ic_photo);//图片加载失败后，显示的图片

                        Glide.with(getActivity())
                                .load(ImageUtils.stringToBitmap(mS))
                                .apply(options)
                                .into(mHeadView);

                        SP.put(getActivity(), "name", mName);
                        SP.put(getActivity(), "phone", phone);

                    }
                });
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

    @Override
    protected boolean canDragBack() {
        return false;
    }


    private void freshMyInfo() {
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.getMyInfo, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {
                        InfoBeanToc beanToc = JSON.parseObject(response, InfoBeanToc.class);
                        mS = beanToc.data.i_logo;

                    }
                });
    }


}
