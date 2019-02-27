package com.daf.cloudshare.model;

import java.util.List;

/**
 * Created by PP on 2019/2/27.
 */
public class MyPrjDataListBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : [{"name":"张三","idcard":"110101199001011819","mobile":"14800000000","id":"225040"},{"name":"王五","idcard":"110101199001019976","mobile":"14700000000","id":"225035"}]
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
         * name : 张三
         * idcard : 110101199001011819
         * mobile : 14800000000
         * id : 225040
         */

        private String name;
        private String idcard;
        private String mobile;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
