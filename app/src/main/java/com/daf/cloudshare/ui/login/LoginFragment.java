package com.daf.cloudshare.ui.login;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.daf.cloudshare.BuildConfig;
import com.daf.cloudshare.MainActivity;
import com.daf.cloudshare.MyApplication;
import com.daf.cloudshare.R;
import com.daf.cloudshare.VersionManager;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.model.LoginBean;
import com.daf.cloudshare.model.LoginBeanToc;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.utils.Const;
import com.daf.cloudshare.utils.GradientView;
import com.daf.cloudshare.utils.SP;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by PP on 2019/2/19.
 */
public class LoginFragment extends BaseFragment {
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.account)
    EditText mAccount;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.login)
    QMUIRoundButton mLogin;

    @BindView(R.id.forget)
    TextView mForget;

    private boolean canSee;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }


    @Override
    protected void init() {


        mTopBar.setTitle("用户登录");
        mAccount.setHint("请输入您的手机号");
        mPassword.setHint("请输入密码");

        mTopBar.addRightImageButton(R.mipmap.close, R.id.topbar_right_close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        if (!TextUtils.isEmpty(SP.getUser(getActivity())[0])) {
            mAccount.setText(SP.getUser(getActivity())[0]);
            mPassword.setText(SP.getUser(getActivity())[1]);
        }

        final Drawable eye = getResources().getDrawable(R.mipmap.password_open);
        final Drawable noEye = getResources().getDrawable(R.mipmap.password_close);
        final Drawable password = getResources().getDrawable(R.mipmap.password);
        password.setBounds(0, 0, password.getMinimumWidth(), password.getMinimumHeight());
        eye.setBounds(0, 0, eye.getMinimumWidth(), eye.getMinimumHeight());
        noEye.setBounds(0, 0, eye.getMinimumWidth(), eye.getMinimumHeight());
        mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        mPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable drawable = mPassword.getCompoundDrawables()[2];
                if (drawable == null)
                    return false;

                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;

                if (event.getX() > mPassword.getWidth() - mPassword.getPaddingRight() - drawable.getIntrinsicWidth()) {

                    if (canSee) {
                        canSee = false;
                        mPassword.setCompoundDrawables(password, null, noEye, null);
                        mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    } else {
                        canSee = true;
                        mPassword.setCompoundDrawables(password, null, eye, null);
                        mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }


                }
                return false;
            }
        });

    }


    private void login(){
        String account = mAccount.getText().toString();
        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(account)) {
            showToast("账号为空!");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showToast("密码为空!");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("telephone", account);
        hashMap.put("password", password);
   //     hashMap.put("appid", BuildConfig.APP_ID);

        HttpUtil.getInstance(getActivity()).postForm(AppUrl.login, hashMap, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String s) throws IOException {
                try {
                    JSONObject json = new JSONObject(s);
                    //登陆失败
                    if (!json.getString("code").equals(Const.login_success)) {
                        showToast(json.getString("msg"));
                        return;
                    }
                    //登陆成功
                    JSONObject jsonObject=json.getJSONObject("data");
                    String userType = jsonObject.optString("user_type");
                    Log.d("chenzhiyuan", "onResponse: "+userType);
                    if (userType.equals("idle")) {
                        //版本置成toc版
                        VersionManager.setToc(getActivity(),true);
                        LoginBeanToc beanToc = JSON.parseObject(s, LoginBeanToc.class);
                        SP.saveToken(getActivity(), beanToc.data.token);

                    } else {
                        //版本为正常版
                        VersionManager.setToc(getActivity(),false);
                        LoginBean loginBean = JSON.parseObject(s, LoginBean.class);
                        SP.put(getActivity(), Const.token, loginBean.getData().getToken());
                    }



                    SP.saveUser(getContext(), account, password);
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
    @OnClick({R.id.login,R.id.forget})
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.login:
                login();
                break;
            case R.id.forget:
                startFragment(new ForgetPassFragment());
                break;
        }



    }

    @Override
    protected boolean canDragBack() {
        return false;
    }
}
