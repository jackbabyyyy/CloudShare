package com.daf.cloudshare.model;



import java.util.List;

/**
 * Created by PP on 2019/2/20.
 */
public class ProductBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : [{"p_id":"105","p_logo":"https://www.dafyun.cn/Public/images/Uploads/201810/2018-10-30/1540893780_1578435927.png","p_name":"浦发银行","p_label":["额度高","手续简单","CPA"],"p_ratetype":"年利率","p_rate":"0.00","p_ratedown":"0.00","p_rateup":"0.00","p_limitdown":"0.00","p_limitup":"0.00","p_quantity":"1376"},{"p_id":"109","p_logo":"https://www.dafyun.cn/Public/images/Uploads/201810/2018-10-30/1540894135_1987923855.png","p_name":"民生银行","p_label":["额度高","手续简单","CPA"],"p_ratetype":"年利率","p_rate":"0.00","p_ratedown":"0.00","p_rateup":"0.00","p_limitdown":"0.00","p_limitup":"0.00","p_quantity":"925"}]
     */

    private String code;
    private String msg;
    private List<ProductBean.DataBean> data;

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

    public List<ProductBean.DataBean> getData() {
        return data;
    }

    public void setData(List<ProductBean.DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * p_id : 105
         * p_logo : https://www.dafyun.cn/Public/images/Uploads/201810/2018-10-30/1540893780_1578435927.png
         * p_name : 浦发银行
         * p_label : ["额度高","手续简单","CPA"]
         * p_ratetype : 年利率
         * p_rate : 0.00
         * p_ratedown : 0.00
         * p_rateup : 0.00
         * p_limitdown : 0.00
         * p_limitup : 0.00
         * p_quantity : 1376
         */

        private String p_id;
        private String p_logo;
        private String p_name;
        private String p_ratetype;
        private String p_rate;
        private String p_ratedown;
        private String p_rateup;
        private String p_limitdown;
        private String p_limitup;
        private String p_quantity;
        private List<String> p_label;

        public String getP_id() {
            return p_id;
        }

        public void setP_id(String p_id) {
            this.p_id = p_id;
        }

        public String getP_logo() {
            return p_logo;
        }

        public void setP_logo(String p_logo) {
            this.p_logo = p_logo;
        }

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
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

        public String getP_quantity() {
            return p_quantity;
        }

        public void setP_quantity(String p_quantity) {
            this.p_quantity = p_quantity;
        }

        public List<String> getP_label() {
            return p_label;
        }

        public void setP_label(List<String> p_label) {
            this.p_label = p_label;
        }
    }
}
