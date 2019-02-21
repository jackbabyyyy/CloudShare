package com.daf.cloudshare.tool;

import android.view.LayoutInflater;
import android.view.View;

import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by PP on 2019/2/19.
 */
public class ToolFragment extends BaseFragment {
    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_tool, null);
        ButterKnife.bind(this, root);
        return root ;
    }
}
