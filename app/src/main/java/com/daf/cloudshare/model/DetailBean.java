package com.daf.cloudshare.model;

import java.util.List;

/**
 * Created by PP on 2019/2/27.
 */
public class DetailBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : {"p_id":"116","p_name":"拍拍贷","p_logo":"./Public/images/Uploads/201811/2018-11-01/1541061769_2083634270.png","p_number":"JR20181101-7811","p_limitdown":"1.00","p_limitup":"3.00","p_require":"","p_condition":"简单、快速、借得到","p_ratetype":"月利率","p_rate":"2.00","p_ratedown":"0.78","p_rateup":"2.00","p_deadlinedown":"12","p_deadlineup":"36","p_speed":"0","p_periods":"0","p_label":["额度高","手续简单"],"p_jumpurl":"https://www.dafyun.cn/index.php?a=sq&id=MTA2MjcjIzExNiMjMzYxc2dk","p_jumpurlnum":"1","p_appdirectjump":"0","p_price":"0.00","p_resume":"","p_appjs":"","p_appShow":[{"name":"项目名称","desc":"拍拍贷"},{"name":"项目编号","desc":"JR20181101-7811"},{"name":"项目介绍","desc":"简单、快速、借得到"}],"p_quantity":"434","p_passrate":2.53,"p_isfavorite":1}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * p_id : 116
         * p_name : 拍拍贷
         * p_logo : ./Public/images/Uploads/201811/2018-11-01/1541061769_2083634270.png
         * p_number : JR20181101-7811
         * p_limitdown : 1.00
         * p_limitup : 3.00
         * p_require :
         * p_condition : 简单、快速、借得到
         * p_ratetype : 月利率
         * p_rate : 2.00
         * p_ratedown : 0.78
         * p_rateup : 2.00
         * p_deadlinedown : 12
         * p_deadlineup : 36
         * p_speed : 0
         * p_periods : 0
         * p_label : ["额度高","手续简单"]
         * p_jumpurl : https://www.dafyun.cn/index.php?a=sq&id=MTA2MjcjIzExNiMjMzYxc2dk
         * p_jumpurlnum : 1
         * p_appdirectjump : 0
         * p_price : 0.00
         * p_resume :
         * p_appjs :
         * p_appShow : [{"name":"项目名称","desc":"拍拍贷"},{"name":"项目编号","desc":"JR20181101-7811"},{"name":"项目介绍","desc":"简单、快速、借得到"}]
         * p_quantity : 434
         * p_passrate : 2.53
         * p_isfavorite : 1
         */

        public String p_id;
        public String p_name;
        public String p_logo;
        public String p_number;
        public String p_limitdown;
        public String p_limitup;
        public String p_require;
        public String p_condition;
        public String p_ratetype;
        public String p_rate;
        public String p_ratedown;
        public String p_rateup;
        public String p_deadlinedown;
        public String p_deadlineup;
        public String p_speed;
        public String p_periods;
        public String p_jumpurl;
        public String p_jumpurlnum;
        public String p_appdirectjump;
        public String p_price;
        public String p_resume;
        public String p_appjs;
        public String p_quantity;
        public double p_passrate;
        public int p_isfavorite;
        public List<String> p_label;
        public List<PAppShowBean> p_appShow;

        public static class PAppShowBean {
            /**
             * name : 项目名称
             * desc : 拍拍贷
             */

            public String name;
            public String desc;
        }
    }
}
