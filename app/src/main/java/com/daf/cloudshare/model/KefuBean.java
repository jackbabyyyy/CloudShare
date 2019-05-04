package com.daf.cloudshare.model;

/**
 * Created by PP on 2019/4/22.
 */
public class KefuBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : {"i_id":"1","i_name":"1","i_telephone":"1","i_logo":"1"}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * i_id : 1
         * i_name : 1
         * i_telephone : 1
         * i_logo : 1
         */

        public String i_id;
        public String i_name;
        public String i_telephone;
        public String i_logo;
    }
}
