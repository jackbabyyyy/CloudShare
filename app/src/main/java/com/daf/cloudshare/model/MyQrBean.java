package com.daf.cloudshare.model;

/**
 * Created by PP on 2019/3/4.
 */
public class MyQrBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : {"imgName":"产品申请列表","imgUrl":"https://www.dafyun.cn/api.php?c=public&a=shareQRCode&url=aHR0cHM6Ly93d3cuZGFmeXVuLmNuL2luZGV4LnBocD9hPWxpc3Rwcm8maWQ9MTA2Mjc="}
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
         * imgName : 产品申请列表
         * imgUrl : https://www.dafyun.cn/api.php?c=public&a=shareQRCode&url=aHR0cHM6Ly93d3cuZGFmeXVuLmNuL2luZGV4LnBocD9hPWxpc3Rwcm8maWQ9MTA2Mjc=
         */

        private String imgName;
        private String imgUrl;

        public String getImgName() {
            return imgName;
        }

        public void setImgName(String imgName) {
            this.imgName = imgName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
