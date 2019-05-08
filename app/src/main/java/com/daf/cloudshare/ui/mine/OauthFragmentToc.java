package com.daf.cloudshare.ui.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.model.InfoBeanToc;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.utils.SP;
import com.daf.cloudshare.utils.StringUtil;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;

/**
 * Created by PP on 2019/4/22.
 */
public class OauthFragmentToc extends BaseFragment {
    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.pass)
    EditText mPass;
    @BindView(R.id.check)
    Button mCheck;
    @BindView(R.id.bar)
    QMUITopBarLayout mBar;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_oauth_toc;
    }

    @Override
    protected void init() {
        mBar.setTitle("实名认证");
        mBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });


        mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =mName.getText().toString().trim();
                String pass=mPass.getText().toString().trim();
                if (TextUtils.isEmpty(name)||TextUtils.isEmpty(pass)){

                    showToast("请填写完整");

                }else{
                    getData(name,pass);
                }

            }
        });


        InfoBeanToc beanToc= JSON.parseObject(SP.getInfo(getActivity()),InfoBeanToc.class);

        if (!TextUtils.isEmpty(beanToc.data.i_idcard)){

            mName.setText(beanToc.data.i_name);
            mPass.setText(StringUtil.sublimitIdCard(beanToc.data.i_idcard));

            mName.setEnabled(false);
            mPass.setEnabled(false);
            mName.setFocusable(false);
            mName.setFocusableInTouchMode(false);
            mPass.setFocusableInTouchMode(false);
            mPass.setFocusable(false);

            mCheck.setVisibility(View.INVISIBLE);
        }

    }


    private void getData(String name,String pass){

        HashMap<String,String> map=new HashMap<>();
        map.put("name",name);
        map.put("idcard",pass);
        HttpUtil.getInstance(getActivity()).postForm(AppUrl.base+AppUrl.changeInfo, map, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) throws IOException {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    showToast(jsonObject.getString("msg"));
                    if (jsonObject.getString("code").equals("90003")){
                        //修改本地 认证状态
                        InfoBeanToc beanToc=JSON.parseObject(SP.getInfo(getActivity()),InfoBeanToc.class);
                        beanToc.data.i_idcard=pass;
                        beanToc.data.i_name=name;
                        SP.saveInfo(getActivity(),JSON.toJSONString(beanToc));

                        popBackStack();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }


}


