package com.daf.cloudshare.model;

/**
 * Created by PP on 2019/3/22.
 */
public class FavoriteResBean {


    /**
     * code : 90000
     * msg : 添加成功！
     * data : {"f_id":"18"}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * f_id : 18
         */

        public String f_id;
    }
}
