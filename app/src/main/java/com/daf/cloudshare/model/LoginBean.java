package com.daf.cloudshare.model;

/**
 * Created by PP on 2019/2/20.
 */
public class LoginBean {


    /**
     * code : 10000
     * msg : 登陆成功！
     * data : {"u_id":"10627","u_isagent":"1","a_id":"686","a_level":"1","u_username":"yunTest","u_name":"技术测试","u_email":"yunTest@dafyun.cn","u_lasttime":"2019-02-20 09:21:18","u_lastip":"47.100.9.0","a_company":"该机构仅用于内部技术测试","token":"e0280a5c436a35b2c0d566ae970300c8eec4b05668bc26f3d28d3c1e698c1da1d4bda2c3a34b03f31477803145ba15343116199e290d4595a4b4b8e14d80d68dbe362d5acff171e1b48a04ef3bf556334948e64f296c3bfd014a0b4aacc95c97"}
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
         * u_id : 10627
         * u_isagent : 1
         * a_id : 686
         * a_level : 1
         * u_username : yunTest
         * u_name : 技术测试
         * u_email : yunTest@dafyun.cn
         * u_lasttime : 2019-02-20 09:21:18
         * u_lastip : 47.100.9.0
         * a_company : 该机构仅用于内部技术测试
         * token : e0280a5c436a35b2c0d566ae970300c8eec4b05668bc26f3d28d3c1e698c1da1d4bda2c3a34b03f31477803145ba15343116199e290d4595a4b4b8e14d80d68dbe362d5acff171e1b48a04ef3bf556334948e64f296c3bfd014a0b4aacc95c97
         */

        private String u_id;
        private String u_isagent;
        private String a_id;
        private String a_level;
        private String u_username;
        private String u_name;
        private String u_email;
        private String u_lasttime;
        private String u_lastip;
        private String a_company;
        private String token;

        public String getU_id() {
            return u_id;
        }

        public void setU_id(String u_id) {
            this.u_id = u_id;
        }

        public String getU_isagent() {
            return u_isagent;
        }

        public void setU_isagent(String u_isagent) {
            this.u_isagent = u_isagent;
        }

        public String getA_id() {
            return a_id;
        }

        public void setA_id(String a_id) {
            this.a_id = a_id;
        }

        public String getA_level() {
            return a_level;
        }

        public void setA_level(String a_level) {
            this.a_level = a_level;
        }

        public String getU_username() {
            return u_username;
        }

        public void setU_username(String u_username) {
            this.u_username = u_username;
        }

        public String getU_name() {
            return u_name;
        }

        public void setU_name(String u_name) {
            this.u_name = u_name;
        }

        public String getU_email() {
            return u_email;
        }

        public void setU_email(String u_email) {
            this.u_email = u_email;
        }

        public String getU_lasttime() {
            return u_lasttime;
        }

        public void setU_lasttime(String u_lasttime) {
            this.u_lasttime = u_lasttime;
        }

        public String getU_lastip() {
            return u_lastip;
        }

        public void setU_lastip(String u_lastip) {
            this.u_lastip = u_lastip;
        }

        public String getA_company() {
            return a_company;
        }

        public void setA_company(String a_company) {
            this.a_company = a_company;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
