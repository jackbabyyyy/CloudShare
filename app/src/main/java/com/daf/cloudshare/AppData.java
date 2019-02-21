package com.daf.cloudshare;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PP on 2019/2/20.
 */
public class AppData {
    public static final String[] MINE_BODY = {"我的订单", "我的账户", "我的钱包", "推荐码", "设置", "新消息通知", "关于达飞云享"};

    public static List<String> getMineBody() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < MINE_BODY.length; i++) {
            list.add(MINE_BODY[i]);
        }

        return list;


    }
}
