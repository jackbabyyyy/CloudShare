package com.daf.cloudshare.model;

import java.util.List;

/**
 * Created by PP on 2019/3/15.
 */
public class TypeBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : [{"t_id":"1","t_name":"信用卡","t_subtitle":"各大银行信用卡","t_logo":"https://www.dafyun.cn/Public/images/app/xyk.png","t_background":"#CC00FF","t_order":"0","t_type":"0"},{"t_id":"2","t_name":"抵押贷","t_subtitle":"车贷、房贷等抵押类贷款","t_logo":"https://www.dafyun.cn/Public/images/app/cfd.png","t_background":"#FFB000","t_order":"0","t_type":"0"},{"t_id":"3","t_name":"消费金融","t_subtitle":"各类分期贷款","t_logo":"https://www.dafyun.cn/Public/images/app/xfd.png","t_background":"#FFB000","t_order":"0","t_type":"0"},{"t_id":"4","t_name":"信用贷","t_subtitle":"各种无抵押贷款","t_logo":"https://www.dafyun.cn/Public/images/app/xyd.png","t_background":"#CC00FF","t_order":"0","t_type":"0"}]
     */

    public String code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * t_id : 1
         * t_name : 信用卡
         * t_subtitle : 各大银行信用卡
         * t_logo : https://www.dafyun.cn/Public/images/app/xyk.png
         * t_background : #CC00FF
         * t_order : 0
         * t_type : 0
         */

        public String t_id;
        public String t_name;
        public String t_subtitle;
        public String t_logo;
        public String t_background;
        public String t_order;
        public String t_type;
    }
}
