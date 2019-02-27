package com.daf.cloudshare.model;

import java.util.List;

/**
 * Created by PP on 2019/2/27.
 */
public class DetailBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : {"p_id":"105","p_name":"浦发银行","p_logo":"./Public/images/Uploads/201810/2018-10-30/1540893780_1578435927.png","p_number":"JR20181030-9582","p_limitdown":"0.00","p_limitup":"0.00","p_require":"","p_condition":"","p_ratetype":"年利率","p_rate":"0.00","p_ratedown":"0.00","p_rateup":"0.00","p_deadlinedown":"0","p_deadlineup":"0","p_speed":"0","p_periods":"0","p_label":["额度高","手续简单","CPA"],"p_jumpurl":"https://www.dafyun.cn/index.php?a=sq&id=MTA2MjcjIzEwNSMjNjMwc2dk","p_directjump":"0","p_price":"0.00","p_appShow":[{"name":"项目名称","desc":"浦发银行"},{"name":"项目编号","desc":"JR20181030-9582"}]}
     */

    private String code;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * p_id : 105
         * p_name : 浦发银行
         * p_logo : ./Public/images/Uploads/201810/2018-10-30/1540893780_1578435927.png
         * p_number : JR20181030-9582
         * p_limitdown : 0.00
         * p_limitup : 0.00
         * p_require :
         * p_condition :
         * p_ratetype : 年利率
         * p_rate : 0.00
         * p_ratedown : 0.00
         * p_rateup : 0.00
         * p_deadlinedown : 0
         * p_deadlineup : 0
         * p_speed : 0
         * p_periods : 0
         * p_label : ["额度高","手续简单","CPA"]
         * p_jumpurl : https://www.dafyun.cn/index.php?a=sq&id=MTA2MjcjIzEwNSMjNjMwc2dk
         * p_directjump : 0
         * p_price : 0.00
         * p_appShow : [{"name":"项目名称","desc":"浦发银行"},{"name":"项目编号","desc":"JR20181030-9582"}]
         */

        private String p_id;
        private String p_name;
        private String p_logo;
        private String p_number;
        private String p_limitdown;
        private String p_limitup;
        private String p_require;
        private String p_condition;
        private String p_ratetype;
        private String p_rate;
        private String p_ratedown;
        private String p_rateup;
        private String p_deadlinedown;
        private String p_deadlineup;
        private String p_speed;
        private String p_periods;
        private String p_jumpurl;
        private String p_directjump;
        private String p_price;
        private List<String> p_label;
        private List<PAppShowBean> p_appShow;

        public String getP_id() {
            return p_id;
        }

        public void setP_id(String p_id) {
            this.p_id = p_id;
        }

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
        }

        public String getP_logo() {
            return p_logo;
        }

        public void setP_logo(String p_logo) {
            this.p_logo = p_logo;
        }

        public String getP_number() {
            return p_number;
        }

        public void setP_number(String p_number) {
            this.p_number = p_number;
        }

        public String getP_limitdown() {
            return p_limitdown;
        }

        public void setP_limitdown(String p_limitdown) {
            this.p_limitdown = p_limitdown;
        }

        public String getP_limitup() {
            return p_limitup;
        }

        public void setP_limitup(String p_limitup) {
            this.p_limitup = p_limitup;
        }

        public String getP_require() {
            return p_require;
        }

        public void setP_require(String p_require) {
            this.p_require = p_require;
        }

        public String getP_condition() {
            return p_condition;
        }

        public void setP_condition(String p_condition) {
            this.p_condition = p_condition;
        }

        public String getP_ratetype() {
            return p_ratetype;
        }

        public void setP_ratetype(String p_ratetype) {
            this.p_ratetype = p_ratetype;
        }

        public String getP_rate() {
            return p_rate;
        }

        public void setP_rate(String p_rate) {
            this.p_rate = p_rate;
        }

        public String getP_ratedown() {
            return p_ratedown;
        }

        public void setP_ratedown(String p_ratedown) {
            this.p_ratedown = p_ratedown;
        }

        public String getP_rateup() {
            return p_rateup;
        }

        public void setP_rateup(String p_rateup) {
            this.p_rateup = p_rateup;
        }

        public String getP_deadlinedown() {
            return p_deadlinedown;
        }

        public void setP_deadlinedown(String p_deadlinedown) {
            this.p_deadlinedown = p_deadlinedown;
        }

        public String getP_deadlineup() {
            return p_deadlineup;
        }

        public void setP_deadlineup(String p_deadlineup) {
            this.p_deadlineup = p_deadlineup;
        }

        public String getP_speed() {
            return p_speed;
        }

        public void setP_speed(String p_speed) {
            this.p_speed = p_speed;
        }

        public String getP_periods() {
            return p_periods;
        }

        public void setP_periods(String p_periods) {
            this.p_periods = p_periods;
        }

        public String getP_jumpurl() {
            return p_jumpurl;
        }

        public void setP_jumpurl(String p_jumpurl) {
            this.p_jumpurl = p_jumpurl;
        }

        public String getP_directjump() {
            return p_directjump;
        }

        public void setP_directjump(String p_directjump) {
            this.p_directjump = p_directjump;
        }

        public String getP_price() {
            return p_price;
        }

        public void setP_price(String p_price) {
            this.p_price = p_price;
        }

        public List<String> getP_label() {
            return p_label;
        }

        public void setP_label(List<String> p_label) {
            this.p_label = p_label;
        }

        public List<PAppShowBean> getP_appShow() {
            return p_appShow;
        }

        public void setP_appShow(List<PAppShowBean> p_appShow) {
            this.p_appShow = p_appShow;
        }

        public static class PAppShowBean {
            /**
             * name : 项目名称
             * desc : 浦发银行
             */

            private String name;
            private String desc;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
