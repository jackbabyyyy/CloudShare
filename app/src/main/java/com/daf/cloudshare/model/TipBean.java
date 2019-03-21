package com.daf.cloudshare.model;

import java.util.List;

/**
 * Created by PP on 2019/3/21.
 */
public class TipBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : [{"p_id":"109","p_logo":"./Public/images/Uploads/201810/2018-10-30/1540894135_1987923855.png","p_name":"民生银行","p_resume":""},{"p_id":"208","p_logo":"./Public/images/Uploads/201903/2019-03-15/1552621259_1064877813.jpg","p_name":"乐分期","p_resume":"有支付宝，每周还款"},{"p_id":"209","p_logo":"./Public/images/Uploads/201903/2019-03-15/1552621667_1443495193.jpg","p_name":"77分期","p_resume":"有支付宝，每周还款"},{"p_id":"106","p_logo":"./Public/images/Uploads/201810/2018-10-30/1540893960_1934146473.png","p_name":"交通银行","p_resume":""},{"p_id":"183","p_logo":"./Public/images/Uploads/201901/2019-01-02/1546418568_1256595931.png","p_name":"兴业银行","p_resume":""},{"p_id":"205","p_logo":"./Public/images/Uploads/201903/2019-03-13/1552461500_2090345028.jpg","p_name":"中原银行","p_resume":""},{"p_id":"203","p_logo":"./Public/images/Uploads/201903/2019-03-13/1552459794_143548066.jpg","p_name":"郑州银行","p_resume":""},{"p_id":"105","p_logo":"./Public/images/Uploads/201810/2018-10-30/1540893780_1578435927.png","p_name":"浦发银行","p_resume":""},{"p_id":"201","p_logo":"./Public/images/Uploads/201903/2019-03-07/1551940931_1643415472.png","p_name":"招商银行","p_resume":""},{"p_id":"145","p_logo":"./Public/images/Uploads/201811/2018-11-13/1542093789_1509052007.png","p_name":"上海银行","p_resume":""}]
     */

    public String code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * p_id : 109
         * p_logo : ./Public/images/Uploads/201810/2018-10-30/1540894135_1987923855.png
         * p_name : 民生银行
         * p_resume :
         */

        public String p_id;
        public String p_logo;
        public String p_name;
        public String p_resume;
    }
}
