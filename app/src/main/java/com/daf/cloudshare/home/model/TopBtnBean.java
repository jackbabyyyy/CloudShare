package com.daf.cloudshare.home.model;

import java.util.List;

/**
 * Created by PP on 2019/2/20.
 */
public class TopBtnBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : [{"title":"信用卡","icon":"https://www.dafyun.cn/Public/images/app/m1.png","type":"view","url":""},{"title":"现金贷","icon":"https://www.dafyun.cn/Public/images/app/m2.png","type":"view","url":""},{"title":"信用贷","icon":"https://www.dafyun.cn/Public/images/app/m3.png","type":"view","url":""},{"title":"车房抵","icon":"https://www.dafyun.cn/Public/images/app/m4.png","type":"view","url":""},{"title":"担保","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"view","url":""},{"title":"其他","icon":"https://www.dafyun.cn/Public/images/app/m6.png","type":"view","url":""}]
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
         * title : 信用卡
         * icon : https://www.dafyun.cn/Public/images/app/m1.png
         *          * type : view
         * url :
         */

        private String title;
        private String icon;
        private String type;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
