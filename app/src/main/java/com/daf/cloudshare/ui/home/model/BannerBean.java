package com.daf.cloudshare.ui.home.model;

import java.util.List;

/**
 * Created by PP on 2019/2/21.
 */
public class BannerBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : [{"title":"为金融机构提供一站式服务","icon":"https://www.dafyun.cn/Public/images/app/banner.png","type":"view","url":""}]
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
         * title : 为金融机构提供一站式服务
         * icon : https://www.dafyun.cn/Public/images/app/banner.png
         * type : view
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
