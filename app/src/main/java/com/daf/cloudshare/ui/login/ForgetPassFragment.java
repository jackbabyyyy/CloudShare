package com.daf.cloudshare.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.daf.cloudshare.R;
import com.daf.cloudshare.VersionManager;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.model.AccountBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;

/**
 * Created by PP on 2019/4/24.
 */
public class ForgetPassFragment extends BaseFragment {
    @BindView(R.id.tel)
    EditText mTel;
    @BindView(R.id.sms)
    EditText mSms;
    @BindView(R.id.getSms)
    TextView mGetSms;
    @BindView(R.id.pass)
    EditText mPass;
    @BindView(R.id.pass2)
    EditText mPass2;
    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.bar)
    QMUITopBarLayout mBar;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forget
                ;
    }

    @Override
    protected void init() {
        mBar.setTitle("忘记密码");
        mBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

    }



    @OnClick({R.id.getSms, R.id.btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getSms:
                break;
            case R.id.btn:
                break;
        }
    }

    private void getPass(String phone){
        HashMap<String,String> map=new HashMap<>();
        map.put("telephone",phone);
        HttpUtil.getInstance(getActivity()).postForm(AppUrl.base+AppUrl.backPass, map, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) throws IOException {

                AccountBean bean= JSON.parseObject(response,AccountBean.class);
                if (bean.data.user_type.equals("idle")){
                    VersionManager.setToc(getActivity(),true);
                }
            }
        });

    }

    private void getPass2(String phone){
        HashMap<String,String> map=new HashMap<>();
        map.put("telephone",phone);
        HttpUtil.getInstance(getActivity()).postForm(AppUrl.base+AppUrl.backPass2, map, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) throws IOException {

            }
        });
    }

    private void getPass3(String phone,String code,String newpass,String repass){

        HashMap<String,String> map=new HashMap<>();
        map.put("telephone",phone);
        map.put("code",code);
        map.put("newPwd",newpass);
        map.put("rePwd",repass);
        HttpUtil.getInstance(getActivity()).postForm(AppUrl.backPass2, map, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }
            @Override
            public void onResponse(String response) throws IOException {

            }
        });
    }
}
