package com.daf.cloudshare.ui.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daf.cloudshare.R;
import com.daf.cloudshare.VersionManager;
import com.daf.cloudshare.base.BaseFragment;

import com.daf.cloudshare.model.InfoBeanToc;
import com.daf.cloudshare.model.MyQrBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.utils.ImageUtils;
import com.daf.cloudshare.utils.SP;
import com.daf.cloudshare.wx.ShareFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by PP on 2019/3/4.
 */
public class MyQrFragment extends BaseFragment {

    @BindView(R.id.iv)
    ImageView mImageView;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBarLayout;
    @BindView(R.id.ivHead)
    CircleImageView mHead;
    @BindView(R.id.tvName)
    TextView mName;

    @BindView(R.id.tvCompany)
    TextView mCompany;
    @BindView(R.id.root)
    View mroot;
    private String mName1;

    @BindView(R.id.tvDes)
    TextView mTvDes;
    @BindView(R.id.ivLeft)
    ImageView mIvLeft;
    @BindView(R.id.tvLeft)
    TextView mTvLeft;
    @BindView(R.id.ivRight)
    ImageView mIvRight;
    @BindView(R.id.tvRight)
    TextView mTvRight;
    @BindView(R.id.left)
    View mLeft;
    @BindView(R.id.right)
    View mRight;
    @BindView(R.id.btn)
    View mAd;
    private MyQrBean mMyQrBean;
    private boolean isFisrt = true;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_qr;
    }


    public static MyQrFragment getInstance(String head, String name, String company) {
        Bundle bundle = new Bundle();
        MyQrFragment fragment = new MyQrFragment();
        bundle.putString("head", head);
        bundle.putString("name", name);
        bundle.putString("company", company);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void init() {

        String head = getArguments().getString("head");
        mName1 = getArguments().getString("name");
        String company = getArguments().getString("company");
        mName.setText(mName1);
        mCompany.setText(company);

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_photo)//图片加载出来前，显示的图片
                .fallback(R.mipmap.ic_photo) //url为空的时候,显示的图片
                .error(R.mipmap.ic_photo);//图片加载失败后，显示的图片

        Glide.with(getActivity()).load(ImageUtils.stringToBitmap(head)).apply(options).into(mHead);
        mTopBarLayout.setTitle("我的二维码");
        mTopBarLayout.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.base+AppUrl.myQr, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {
                        mMyQrBean = JSON.parseObject(response, MyQrBean.class);
                        if (null != mMyQrBean.data.imgUrl)
                            ;
                        {
                            Glide.with(getActivity()).load(mMyQrBean.data.imgUrl).into(mImageView);
                        }


                    }
                });

        mroot.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showSavePop();

                return false;
            }
        });

        mLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIvLeft.setImageResource(R.mipmap.qr_pro);
                mTvLeft.setTextColor(getResources().getColor(R.color.app_color_blue));
                mIvRight.setImageResource(R.mipmap.qr_register_un);
                mTvRight.setTextColor(getResources().getColor(R.color.app_color_description));
                mTvDes.setText("扫一扫可获取产品列表");
                Glide.with(getActivity()).load(mMyQrBean.data.imgUrl).into(mImageView);
                isFisrt = true;

            }
        });

        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIvLeft.setImageResource(R.mipmap.qr_pro_un);
                mTvLeft.setTextColor(getResources().getColor(R.color.app_color_description));
                mIvRight.setImageResource(R.mipmap.qr_register);
                mTvRight.setTextColor(getResources().getColor(R.color.app_color_blue));
                mTvDes.setText("扫一扫可进行注册");
                Glide.with(getActivity()).load(mMyQrBean.data.shareUrl).into(mImageView);
                isFisrt = false;
            }
        });

        mAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShare();
            }
        });


    }

    private void goShare(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(mImageView.getMeasuredWidth(),
                        mImageView.getMeasuredHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(bitmap);
                mImageView.draw(c);
                String jump = QRCodeDecoder.syncDecodeQRCode(bitmap);
                if (isFisrt) {
                    startFragment(ShareFragment.getInstance(mMyQrBean.data.proImg, jump));
                } else {
                    startFragment(ShareFragment.getInstance(mMyQrBean.data.userImg, jump));
                }

            }
        });
    }

    private QMUIPopup mNormalPopup;

    private void showSavePop() {
        if (mNormalPopup == null) {
            mNormalPopup = new QMUIPopup(getContext(), QMUIPopup.DIRECTION_NONE);
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(mNormalPopup.generateLayoutParam(
                    QMUIDisplayHelper.dp2px(getContext(), 250),
                    WRAP_CONTENT
            ));
            textView.setLineSpacing(QMUIDisplayHelper.dp2px(getContext(), 4), 1.0f);
            int padding = QMUIDisplayHelper.dp2px(getContext(), 20);
            textView.setPadding(padding, padding, padding, padding);
            textView.setText("保存二维码图片");
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.app_color_description));

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveBitmap(mroot, mName1.replace(".", "").trim() + "_QR");
                    mNormalPopup.dismiss();
                }
            });
            mNormalPopup.setContentView(textView);
            mNormalPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                }
            });
        }

        mNormalPopup.show(mroot);
    }


    public void saveBitmap(View v, String name) {


        String fileName = name + ".png";
        Bitmap bm = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        v.draw(canvas);


        String parent =  Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;
        File f = new File(parent + "/", fileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();

            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),bm,fileName,null);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(f);
            intent.setData(uri);
            getActivity().sendBroadcast(intent);

            showToast("已保存至相册");

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}
