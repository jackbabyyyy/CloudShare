package com.daf.cloudshare.ui.mine;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daf.cloudshare.BuildConfig;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.model.KefuBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.utils.ImageUtils;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by PP on 2019/4/22.
 */
public class KefuFragmentToc extends BaseFragment implements EasyPermissions.PermissionCallbacks {


    @BindView(R.id.head)
    CircleImageView mHead;
    @BindView(R.id.phone)
    ImageView mPhone;
    @BindView(R.id.wx)
    ImageView mWx;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.qr)
    ImageView mQr;
    @BindView(R.id.bar)
    QMUITopBarLayout mBar;
    private String mPhoneNumber;
    @BindView(R.id.time)
    TextView mDes;
    @BindView(R.id.des)
    TextView mDesc;
    @BindView(R.id.kefu)
    TextView mKefu;

    @BindView(R.id.root)
    View mRoot;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kefu_toc;
    }

    @Override
    protected void init() {
        if (BuildConfig.APPLICATION_ID.equals("cn.dafyun.app.salesmanzfyd")||
        BuildConfig.APPLICATION_ID.equals("cn.dafyun.app.salesmanqxpc")){
            mRoot.setVisibility(View.GONE);
        }
        mDesc.setText("专属客服会详细解答您的问题，快和她联系吧");
        mBar.setTitle("联系客服");
        mBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

        getData();

        mQr.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                saveBitmap(v,getActivity().getResources().getString(R.string.kefu));
                return false;
            }
        });

        mKefu.setText(R.string.kefu);


    }


    @OnClick({R.id.phone, R.id.wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone:
                checkPermission(mPhoneNumber);
                break;
            case R.id.wx:

                Intent intent = new Intent();

                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");

                intent.setAction(Intent.ACTION_MAIN);

                intent.addCategory(Intent.CATEGORY_LAUNCHER);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.setComponent(cmp);

                getActivity().startActivity(intent);
                break;
        }
    }

    private void getData() {
        HttpUtil.getInstance(getActivity()).postForm(AppUrl.base+AppUrl.getMyParent, null, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) throws IOException {

                KefuBean bean = JSON.parseObject(response, KefuBean.class);

                RequestOptions options = new RequestOptions()
                        .placeholder(R.mipmap.ic_photo)//图片加载出来前，显示的图片
                        .fallback(R.mipmap.ic_photo) //url为空的时候,显示的图片
                        .error(R.mipmap.ic_photo);//图片加载失败后，显示的图片

                Glide.with(getActivity())
                        .load(ImageUtils.stringToBitmap(bean.data.i_logo))
                        .apply(options)
                        .into(mHead);

                mName.setText(bean.data.i_name);
                mPhoneNumber = bean.data.i_telephone;


            }
        });

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    private void checkPermission(String phoneNumber) {
        String[] perms = {Manifest.permission.CALL_PHONE};

        if (EasyPermissions.hasPermissions(getContext(), perms)) {

            call(phoneNumber);

        } else {
            // Request one permission


            EasyPermissions.requestPermissions(this, "允许拨打电话",
                    888, perms);
        }

    }

    private void call(String phoneNumber) {
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_CALL);
        intent2.setData(Uri.parse("tel:" + phoneNumber));
        getActivity().startActivity(intent2);

    }

    public void saveBitmap(View v, String name) {


        String fileName = name + ".png";
        Bitmap bm = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        v.draw(canvas);


        String parent = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
        File f = new File(parent + "/", fileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();

            showToast("已保存至相册");

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }


}
