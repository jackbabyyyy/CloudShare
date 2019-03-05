package com.daf.cloudshare.mine;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.AppData;
import com.daf.cloudshare.LoginActivity;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.ui.ModifyPassFragment;
import com.daf.cloudshare.ui.MyPrjFragment;
import com.daf.cloudshare.ui.MyQrFragment;
import com.daf.cloudshare.ui.SubmitFragment;
import com.daf.cloudshare.utils.DataCleanManager;
import com.daf.cloudshare.utils.ImageUtils;
import com.daf.cloudshare.utils.SP;
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
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by PP on 2019/2/19.
 */
public class MineFragment extends BaseFragment {
    private static final int REQUEST_CODE_CHOOSE = 999;
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private CircleImageView mHeadView;


    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, root);

        init();
        return root ;
    }

    private void init() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MineAdapter adapter=new MineAdapter(AppData.getMineBody());
        View head=LayoutInflater.from(getActivity()).inflate(R.layout.head_mine,null);
        mHeadView = head.findViewById(R.id.head);
        mHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




              photoPicker();


            }
        });
        adapter.addHeaderView(head);
        View foot=LayoutInflater.from(getActivity()).inflate(R.layout.foot_mine,null);
        foot.findViewById(R.id.login_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLoginDialog();
            }
        });
        adapter.addFooterView(foot);

        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:

                        startFragment(new MyPrjFragment());
                        break;
                    case 1:

                        startFragment(new MyQrFragment());

                        break;

                    case 2:
                        startFragment(new ModifyPassFragment());
                        break;

                    case 3:
                        startFragment(new SubmitFragment());
                        break;
                    case 4:
                        showClearDialog();
                        break;

                }
            }
        });

        getMyInfoData();

    }

    private void showClearDialog() {
        String cache="";
        try {
            cache=DataCleanManager.getTotalCacheSize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new QMUIDialog.MessageDialogBuilder(getActivity())

                .setMessage("确定要清除"+cache+"缓存吗？")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0,"清除", QMUIDialogAction.ACTION_PROP_NEGATIVE,new QMUIDialogAction.ActionListener() {
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
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONObject data=new JSONObject(jsonObject.getString("data"));
                            String s=data.getString("u_avatar");

                            Glide.with(getActivity()).load(ImageUtils.stringToBitmap(s)).into(mHeadView);


                        } catch (JSONException e) {


                        }

                    }
                });
    }

    private void photoPicker() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setPreviewEnabled(false)
                .setCrop(true)
                .setCropXY(1, 1)
                .start(getActivity(),this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拍照功能或者裁剪功能返回

        if (null!=data){
            String path=data.getStringExtra(PhotoPicker.KEY_CAMEAR_PATH);
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
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件


                        String base64=ImageUtils.file2Base64(file.getAbsolutePath());


                        Map<String,String> map=new HashMap<>();
                        map.put("avatar",base64 );
                        HttpUtil.getInstance(getActivity())
                                .postForm(AppUrl.saveAvatar, map, new HttpUtil.ResultCallback() {
                                    @Override
                                    public void onError(Request request, Exception e) {

                                    }

                                    @Override
                                    public void onResponse(String response) throws IOException {
                                        try {
                                            JSONObject jsonObject=new JSONObject(response);
                                            showToast(jsonObject.getString("msg"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });


                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();








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
                .addAction(0,"退出", QMUIDialogAction.ACTION_PROP_NEGATIVE,new QMUIDialogAction.ActionListener() {
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
        return  false;
    }




}
