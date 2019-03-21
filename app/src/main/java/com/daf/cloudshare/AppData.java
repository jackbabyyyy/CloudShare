package com.daf.cloudshare;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PP on 2019/2/20.
 */
public class AppData {
    public static final String[] MINE_BODY = {"我的收藏","我的订单", "我的二维码", "修改密码", "投诉建议","清除缓存"};

    public static List<String> getMineBody() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < MINE_BODY.length; i++) {
            list.add(MINE_BODY[i]);
        }

        return list;


    }



    public static class IndexBean{
        public String index;
        public String id;
    }


    public static final String[] INDEX={"实时通过率","可申请最高额度","已申请人数","成功放款笔数"};
    public static List<IndexBean> getIndex(){
        List<IndexBean> list=new ArrayList<>();

        for (int i=0;i<4;i++){
            IndexBean bean=new IndexBean();
            bean.index=INDEX[i];
            list.add(bean);

        }
        return list;
    }




}
