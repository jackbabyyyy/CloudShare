package com.daf.cloudshare.model;

/**
 * Created by PP on 2019/4/3.
 */
public class DialogBean {

    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : {"title":"开通任性贷抽奖赢大礼","icon":"https://www.dafyun.cn/Public/images/app/suning.jpg","type":"product","url":"189","point":"index"}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * title : 开通任性贷抽奖赢大礼
         * icon : https://www.dafyun.cn/Public/images/app/suning.jpg
         * type : product
         * url : 189
         * point : index
         */

        public String title;
        public String icon;
        public String type;
        public String url;
        public String point;
    }
}
