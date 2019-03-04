package com.daf.cloudshare.home.model;

import java.util.List;

/**
 * Created by PP on 2019/2/20.
 */
public class TopBtnBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : [{"title":"信用卡","icon":"https://www.dafyun.cn/Public/images/app/m1.png","type":"param","url":{"url":"projectList","type":"5"}},{"title":"大额","icon":"https://www.dafyun.cn/Public/images/app/m3.png","type":"param","url":{"url":"projectList","type":"4"}},{"title":"小额","icon":"https://www.dafyun.cn/Public/images/app/m2.png","type":"param","url":{"url":"projectList","type":"3"}},{"title":"车房抵","icon":"https://www.dafyun.cn/Public/images/app/m4.png","type":"param","url":{"url":"projectList","type":"1"}},{"title":"直营","icon":"https://www.dafyun.cn/Public/images/app/m5.png","type":"param","url":{"url":"projectList","type":"2"}},{"title":"更多","icon":"https://www.dafyun.cn/Public/images/app/m6.png","type":"param","url":{"url":"projectList"}}]
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
         * type : param
         * url : {"url":"projectList","type":"5"}
         */

        private String title;
        private String icon;
        private String type;
        private UrlBean url;

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

        public UrlBean getUrl() {
            return url;
        }

        public void setUrl(UrlBean url) {
            this.url = url;
        }

        public static class UrlBean {
            /**
             * url : projectList
             * type : 5
             */

            private String url;
            private String type;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
