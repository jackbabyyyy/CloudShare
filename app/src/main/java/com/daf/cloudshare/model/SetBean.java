package com.daf.cloudshare.model;

import com.daf.cloudshare.view.FilterItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PP on 2019/4/11.
 */
public class SetBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : {"top":{"name":"项目分类","param":"protype","type":"radio","select":[{"name":"信用卡","value":"1"},{"name":"抵押贷","value":"2"},{"name":"小微金融","value":"3"},{"name":"消费金融","value":"4"}]},"mid":{"name":"额度（万元）","param":"money","type":"range"},"fot":{"name":"期数","param":"month","type":"radio","select":[{"name":"1个月","value":"1"},{"name":"3个月","value":"3"},{"name":"6个月","value":"6"},{"name":"9个月","value":"9"},{"name":"12个月","value":"12"},{"name":"24个月","value":"24"}]}}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * top : {"name":"项目分类","param":"protype","type":"radio","select":[{"name":"信用卡","value":"1"},{"name":"抵押贷","value":"2"},{"name":"小微金融","value":"3"},{"name":"消费金融","value":"4"}]}
         * mid : {"name":"额度（万元）","param":"money","type":"range"}
         * fot : {"name":"期数","param":"month","type":"radio","select":[{"name":"1个月","value":"1"},{"name":"3个月","value":"3"},{"name":"6个月","value":"6"},{"name":"9个月","value":"9"},{"name":"12个月","value":"12"},{"name":"24个月","value":"24"}]}
         */

        public TopBean top=new TopBean();
        public MidBean mid;
        public FotBean fot;

        public static class TopBean {
            /**
             * name : 项目分类
             * param : protype
             * type : radio
             * select : [{"name":"信用卡","value":"1"},{"name":"抵押贷","value":"2"},{"name":"小微金融","value":"3"},{"name":"消费金融","value":"4"}]
             */

            public String name="";
            public String param="";
            public String type="";
            public List<FilterItem> select=new ArrayList<>();


        }

        public static class MidBean {
            /**
             * name : 额度（万元）
             * param : money
             * type : range
             */

            public String name;
            public String param;
            public String type;
        }

        public static class FotBean {
            /**
             * name : 期数
             * param : month
             * type : radio
             * select : [{"name":"1个月","value":"1"},{"name":"3个月","value":"3"},{"name":"6个月","value":"6"},{"name":"9个月","value":"9"},{"name":"12个月","value":"12"},{"name":"24个月","value":"24"}]
             */

            public String name;
            public String param;
            public String type;
            public List<FilterItem> select;


        }
    }
}


