package com.daf.cloudshare.mine;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daf.cloudshare.AppData;
import com.daf.cloudshare.LoginActivity;
import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.ui.ModifyPassFragment;
import com.daf.cloudshare.ui.MyPrjFragment;
import com.daf.cloudshare.ui.MyQrFragment;
import com.daf.cloudshare.ui.SubmitFragment;
import com.daf.cloudshare.utils.SP;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PP on 2019/2/19.
 */
public class MineFragment extends BaseFragment {
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, root);

        init();
        return root ;
    }

    private void init() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MineAdapter adapter=new MineAdapter(AppData.getMineBody());
        View head=LayoutInflater.from(getActivity()).inflate(R.layout.head_mine,null);
        adapter.addHeaderView(head);
        View foot=LayoutInflater.from(getActivity()).inflate(R.layout.foot_mine,null);
        foot.findViewById(R.id.login_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLoginDialog();
            }
        });
        adapter.addFooterView(foot);

        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:

                        startFragment(new MyPrjFragment());
                        break;
                    case 1:

                        startFragment(new MyQrFragment());

                        break;

                    case 2:
                        startFragment(new ModifyPassFragment());
                        break;

                    case 3:
                        startFragment(new SubmitFragment());
                        break;

                }
            }
        });

    }

    private void showLoginDialog() {
        new QMUIDialog.MessageDialogBuilder(getActivity())

                .setMessage("确定要退出当前登录吗？")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0,"退出", QMUIDialogAction.ACTION_PROP_NEGATIVE,new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        //shanchu 登录信息
                        SP.clear(getActivity());

                       startActivity(new Intent(getActivity(), LoginActivity.class));
                       popBackStack();
                    }
                })
                .create(mCurrentDialogStyle).show();

    }
}
