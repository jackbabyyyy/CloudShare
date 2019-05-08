package com.daf.cloudshare.ui.mine;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.model.TeamBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;

import com.daf.cloudshare.utils.MyLoadView2;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

import okhttp3.Request;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by PP on 2019/4/22.
 */
public class TeamFragmentToc extends BaseFragment implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.team)
    TextView mTeam;
    @BindView(R.id.add)
    TextView mAdd;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.bar)
    QMUITopBarLayout mBar;


    private List<TeamBean.DataBean> mDataBeans=new ArrayList<>();
    private TeamAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_team_toc;
    }

    @Override
    protected void init() {
        mBar.setTitle("团队列表");
        mBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TeamAdapter(mDataBeans);
        mRecycler.setAdapter(mAdapter);
        mRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom= QMUIDisplayHelper.dp2px(getActivity(),20);
            }
        });
        mAdapter.setLoadMoreView(new MyLoadView2());
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.wx:

                        Intent intent =new Intent();

                        ComponentName cmp =new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");

                        intent.setAction(Intent.ACTION_MAIN);

                        intent.addCategory(Intent.CATEGORY_LAUNCHER);

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.setComponent(cmp);

                        getActivity().startActivity(intent);


                        break;
                    case R.id.phone:

                        checkPermission(position);

                        break;

                }
            }
        });

        getData();

    }

    private void call(int pos){
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_CALL);
        intent2.setData(Uri.parse("tel:"+mAdapter.getData().get(pos).i_telephone));
        getActivity().startActivity(intent2);

    }

    private void checkPermission(int pos) {
        String[] perms = {Manifest.permission.CALL_PHONE};

        if (EasyPermissions.hasPermissions(getContext(), perms)) {

            call(pos);

        } else {
            // Request one permission


            EasyPermissions.requestPermissions(this, "允许拨打电话",
                    888, perms);
        }

    }

    private void getData(){
        HttpUtil.getInstance(getActivity()).postForm(AppUrl.base+AppUrl.getMyTeam, null, new HttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) throws IOException {

                TeamBean bean= JSON.parseObject(response,TeamBean.class);
                mAdapter.setNewData(bean.data);

                if (null==bean.data){
                    mTeam.setText("0");
                    mAdd.setText("0");
                    return;
                }

                mTeam.setText(bean.data.size()+"");

                //
                int total=0;
                int month = Integer.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
                for (int i=0;i<bean.data.size();i++){
                    int m=Integer.valueOf(bean.data.get(i).i_time.split("-")[1]);
                    if (month==m){
                        total++;
                    }
                }
                mAdd.setText(total+"");



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
}
