package com.daf.cloudshare;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PP on 2019/2/20.
 */
public class AppData {
    public static final String[] MINE_BODY = {"我的订单", "我的二维码", "修改密码", "投诉建议"};

    public static List<String> getMineBody() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < MINE_BODY.length; i++) {
            list.add(MINE_BODY[i]);
        }

        return list;


    }
}
