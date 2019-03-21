package com.daf.cloudshare.model;

import java.util.List;

/**
 * Created by PP on 2019/3/15.
 */
public class FavoriteBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : [{"f_id":"1","f_name":"浦发银行","f_value":"105","f_type":"1","f_logo":"https://www.dafyun.cn/Public/images/Uploads/201810/2018-10-30/1540893780_1578435927.png","f_order":"0"}]
     */

    public String code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * f_id : 1
         * f_name : 浦发银行
         * f_value : 105
         * f_type : 1
         * f_logo : https://www.dafyun.cn/Public/images/Uploads/201810/2018-10-30/1540893780_1578435927.png
         * f_order : 0
         */

        public String f_id;
        public String f_name;
        public String f_value;
        public String f_type;
        public String f_logo;
        public String f_order;
    }
}
