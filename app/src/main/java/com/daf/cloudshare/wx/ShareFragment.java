package com.daf.cloudshare.wx;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.utils.SP;
import com.daf.cloudshare.utils.StringUtil;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.youth.banner.Banner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by PP on 2019/4/13.
 */
public class ShareFragment extends BaseFragment implements IWXAPIEventHandler {

    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.pyq)
    LinearLayout mPyq;
    @BindView(R.id.wx)
    LinearLayout mWx;
    @BindView(R.id.bc)
    LinearLayout mBc;
    @BindView(R.id.fz)
    LinearLayout mFz;
    @BindView(R.id.bar)
    QMUITopBarLayout mBar;

    @BindView(R.id.qrcode)
    ImageView mQrcode;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.shareView)
    View mShareView;
    private WXshare mWXshare;
    private String mImgUrl;
    private String mJumpUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_share;
    }

    public static ShareFragment getInstance(String imgurl,String jumpuel) {
        Bundle bundle = new Bundle();
        bundle.putString("img", imgurl);
        bundle.putString("url",jumpuel);
        ShareFragment fragment = new ShareFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void init() {

        mImgUrl = getArguments().getString("img");
        mJumpUrl = getArguments().getString("url");

        if (TextUtils.isEmpty(mJumpUrl)){
            mJumpUrl=mImgUrl;
        }
        Glide.with(getActivity()).load(mImgUrl).into(mIv);
        mBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });
        mWXshare = new WXshare(getActivity());
        mWXshare.register();
        mWXshare.setListener(new OnResponseListener() {
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

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap= QRCodeEncoder.syncEncodeQRCode(mJumpUrl, QMUIDisplayHelper.dp2px(getActivity(),60));
                Glide.with(getActivity()).load(bitmap).into(mQrcode);
            }
        });

        mName.setText("姓名："+SP.get(getActivity(),"name","").toString());
        mPhone.setText("联系方式："+SP.get(getActivity(),"phone","").toString());



    }
    @OnClick({R.id.pyq, R.id.wx, R.id.bc, R.id.fz})
    public void onViewClicked(View view) {
        Bitmap bitmap = Bitmap.createBitmap(mShareView.getMeasuredWidth(),
                mShareView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        mShareView.draw(c);

        switch (view.getId()) {
            case R.id.pyq:
                mWXshare.sharePicture(bitmap, 1);
                break;
            case R.id.wx:
                mWXshare.sharePicture(bitmap, 0);
                break;
            case R.id.bc:
                saveImg(bitmap);
                break;
            case R.id.fz:

                CopyToClipboard(getActivity(), mJumpUrl);
                break;
        }
    }


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
        mWXshare.unregister();
    }

    public void CopyToClipboard(Context context, String text) {
        ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        clip.setText(text); // 复制
        showToast("已复制链接");
    }

    public void saveImg(Bitmap bm){
        String fileName = StringUtil.getFileName(mImgUrl) + ".png";


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
