package com.daf.cloudshare.model;

/**
 * Created by PP on 2019/4/24.
 */
public class AccountBean {

    /**
     * code : 80001
     * msg : 获取数据成功
     * data : {"user_type":"idle"}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * user_type : idle
         */

        public String user_type;
    }
}
