package com.daf.cloudshare.login;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.daf.cloudshare.MainActivity;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.utils.Const;
import com.daf.cloudshare.utils.SP;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login, null);
        ButterKnife.bind(this, root);


        initBar();
        init();
        return root ;
    }

    private void init() {

        if (!TextUtils.isEmpty(SP.getUser(getActivity())[0])){
            mAccount.setText(SP.getUser(getActivity())[0]);
            mPassword.setText(SP.getUser(getActivity())[1]);
        }

        final Drawable eye=getResources().getDrawable(R.mipmap.password_open);
        final Drawable noEye=getResources().getDrawable(R.mipmap.password_close);
        final Drawable password=getResources().getDrawable(R.mipmap.password);
        password.setBounds(0,0,password.getMinimumWidth(),password.getMinimumHeight());
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

                if (event.getX() > mPassword.getWidth() - mPassword.getPaddingRight() - drawable.getIntrinsicWidth()){

                  if (canSee){
                      canSee=false;
                      mPassword.setCompoundDrawables(password,null,noEye,null);
                      mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                  }else{
                      canSee=true;
                      mPassword.setCompoundDrawables(password,null,eye,null);
                      mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                  }


                }
                return false;
            }
        });




    }

    private void initBar() {

        mTopBar.setTitle("员工登录");
        mTopBar.addRightImageButton(R.mipmap.close,R.id.topbar_right_close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


//        mTopBar.addLeftTextButton("注册",R.id.topbar_left_text_button)
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                })
//        ;




    }

    @OnClick(R.id.login)
    public void login()  {
        String account=mAccount.getText().toString();
        String password=mPassword.getText().toString();
        if (TextUtils.isEmpty(account)){
            showToast("账号为空!");
            return;
        }
        if (TextUtils.isEmpty(password)){
            showToast("密码为空!");
            return;
        }
        HashMap hashMap=new HashMap();
        hashMap.put("telephone",account);
        hashMap.put("password",password);


        HttpUtil.getInstance(getActivity()).postForm(AppUrl.login, hashMap, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String s) throws IOException {
                try {


                    JSONObject login=new JSONObject(s);
                    if (login.getString("code").equals(Const.login_success)){
                        LoginBean loginBean= JSON.parseObject(s,LoginBean.class);
                        //save token
                        SP.put(getActivity(),Const.token,loginBean.getData().getToken());
                        SP.saveUser(getActivity(),account,password);


                        startActivity(new Intent(getActivity(),MainActivity.class));
                        getActivity().finish();
                    }else{
                        showToast(login.getString("msg"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });





    }







}
