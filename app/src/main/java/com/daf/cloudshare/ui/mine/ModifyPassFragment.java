package com.daf.cloudshare.ui.mine;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;

import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.utils.StringUtil;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by PP on 2019/3/4.
 */
public class ModifyPassFragment extends BaseFragment {
    @BindView(R.id.et_pwd_old)
    EditText mEtPwdOld;
    @BindView(R.id.et_pwd_new)
    EditText mEtPwdNew;
    @BindView(R.id.et_pwd_again)
    EditText mEtPwdAgain;
    @BindView(R.id.btn_commit)
    Button mBtnCommit;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopbar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_modify_pass;
    }



    @Override
    protected void init() {

        mTopbar.setTitle("修改密码");
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });


    }


   
    @OnClick(R.id.btn_commit)
    public void onViewClicked() {

        if (StringUtil.isEmpty(mEtPwdOld.getText().toString())) {
           showToast("请填写原密码");
            return;
        }
        if (mEtPwdOld.getText().toString().trim().length() < 6) {
            showToast("请填写至少6-16位原密码");
            return;
        }
        if (StringUtil.isEmpty(mEtPwdNew.getText().toString())) {
            showToast("请填写新密码");
            return;
        }
        if (mEtPwdNew.getText().toString().trim().length() < 6) {
            showToast("请填写至少6-16位新密码");
            return;
        }
        if (StringUtil.isEmpty(mEtPwdAgain.getText().toString())) {
            showToast("请填写至少6-16位新密码");
            return;
        }
        if (mEtPwdAgain.getText().toString().trim().length() < 6) {
            showToast("请填写至少6-16位新密码");
            return;
        }
        if (!mEtPwdNew.getText().toString().equals(mEtPwdAgain.getText().toString())) {
            showToast("两次密码不一致");
            return;
        }
        if (mEtPwdOld.getText().toString().equals(mEtPwdAgain.getText().toString())) {
            showToast("新密码不可与旧密码一致");
            return;
        }

        Map<String ,String > map=new HashMap<>();

        map.put("oldpwd",mEtPwdOld.getText().toString().trim());
        map.put("newpwd",mEtPwdNew.getText().toString().trim());
        map.put("repwd",mEtPwdAgain.getText().toString().trim());
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.modifyPass, map, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            showToast(jsonObject.getString("msg"));
                            popBackStack();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
        
        
    }
}
