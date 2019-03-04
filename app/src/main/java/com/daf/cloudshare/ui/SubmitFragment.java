package com.daf.cloudshare.ui;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by PP on 2019/3/4.
 */
public class SubmitFragment extends BaseFragment {
    @BindView(R.id.text)
    EditText mText;
    @BindView(R.id.btn_commit)
    Button mBtnCommit;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopbar;
    @BindView(R.id.title)
    EditText mTitle;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_submit, null);
        ButterKnife.bind(this, root);
        init();
        return root;
    }

    private void init() {

        mTopbar.setTitle("投诉建议");
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });


    }


    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        String title=mTitle.getText().toString();
        String content=mText.getText().toString();
        if (TextUtils.isEmpty(title)){
            showToast("请填写意见或问题标题");
            return;
        }
        if (TextUtils.isEmpty(content)){
            showToast("请具体描述详细意见或问题");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("title",title);
        map.put("content",content);
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.addAdvice, map, new HttpUtil.ResultCallback() {
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


}
