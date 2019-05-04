package com.daf.cloudshare.ui.mine;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daf.cloudshare.R;
import com.daf.cloudshare.VersionManager;
import com.daf.cloudshare.utils.SP;
import com.daf.cloudshare.utils.StringUtil;

import java.util.List;

/**
 * Created by PP on 2019/2/20.
 */
public class MineAdapter extends BaseQuickAdapter<String , BaseViewHolder> {



    public MineAdapter( @Nullable List<String> data) {
        super(R.layout.adapter_mine, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv,item);

        if (helper.getAdapterPosition()==getData().size()){
            helper.setImageResource(R.id.icon,R.mipmap.version);
            helper.setText(R.id.tvVersion, StringUtil.getVersion(mContext));

            if (VersionManager.isNewVerionHasLook(mContext)){
                helper.setVisible(R.id.icon,false);
            }else {
                helper.setVisible(R.id.icon,true);
            }

        }





    }


}
