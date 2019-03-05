package com.daf.cloudshare.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.model.DetailBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.utils.Const;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by PP on 2019/2/27.
 *
 * 产品详情页
 */
public class DetailFragment extends BaseFragment {

    private static final String PID = "PID";
    @BindView(R.id.tvAmount)
    TextView mTvAmount;
    @BindView(R.id.tv_lilv)
    TextView mTvLilv;
    @BindView(R.id.tv_periods)
    TextView mTvPeriods;
    @BindView(R.id.tv_speed)
    TextView mTvSpeed;
    @BindView(R.id.tv_range)
    TextView mTvRange;
    @BindView(R.id.tv_label)
    TextView mTvLabel;


    @BindView(R.id.login)
    QMUIRoundButton mLogin;

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBarLayout;


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<DetailBean.DataBean.PAppShowBean> mData=new ArrayList<>();
    private DetailDesAdapter adapter;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_detail, null);
        ButterKnife.bind(this, root);
        init();


        return root;
    }


    public static DetailFragment getInstance(String pid) {
        // 通过bundle传递数据
        Bundle bundle = new Bundle();
        bundle.putString(PID, pid);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        return fragment;

    }


    private void init() {

        mTopBarLayout.setTitle("产品详情");
        mTopBarLayout.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DetailDesAdapter(mData);
        mRecyclerView.setAdapter(adapter);




       String  id = getArguments().getString(PID);
        Map<String, String> map = new HashMap<>();
        map.put("proid", id);
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.prjInfo, map, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("code").equals(Const.body_success)) {
                                DetailBean detailBean = JSON.parseObject(response, DetailBean.class);
                                //

                                adapter.setNewData(detailBean.getData().getP_appShow());
                                setView(detailBean);




                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });


    }

    private void setView(DetailBean detailBean) {

        mTvAmount.setText(detailBean.getData().getP_limitup());
        mTvLilv.setText(detailBean.getData().getP_rate());
        mTvPeriods.setText(detailBean.getData().getP_periods());
        mTvSpeed.setText(detailBean.getData().getP_speed());

        mTvRange.setText("额度范围："+detailBean.getData().getP_limitdown()+"-"+detailBean.getData().getP_limitup()+"万元");

        for (int i=0;i<detailBean.getData().getP_label().size();i++){
            mTvLabel.append(detailBean.getData().getP_label().get(i)+"、");
        }


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              startFragment(WebFragment.getInstance(detailBean.getData().getP_jumpurl()));

            }
        });



    }


}
