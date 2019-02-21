package com.daf.cloudshare.home.model;

import java.util.List;

/**
 * Created by PP on 2019/2/21.
 */
public class NewPrjBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : [{"p_id":"54","p_logo":"./Public/images/Uploads/201810/2018-10-09/1539073790_1159937403.jpeg","p_name":"富德-车抵贷"},{"p_id":"182","p_logo":"./Public/images/Uploads/201901/2019-01-16/1547604625_1523059227.png","p_name":"云享\u2014天德车抵贷"},{"p_id":"35","p_logo":"./Public/images/Uploads/201810/2018-10-09/1539074882_1342471597.jpg","p_name":"华安-安居贷"}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * p_id : 54
         * p_logo : ./Public/images/Uploads/201810/2018-10-09/1539073790_1159937403.jpeg
         * p_name : 富德-车抵贷
         */

        private String p_id;
        private String p_logo;
        private String p_name;

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
    }
}
