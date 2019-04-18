package com.daf.cloudshare.model;

import java.util.List;

/**
 * Created by PP on 2019/2/27.
 */
public class MyPrjDataListBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : [{"name":"张三","idcard":"110101199001011819","mobile":"14800000000","id":"225040","time":"2019-02-20 09:33:01","d_status":"状态：取消"},{"name":"王五","idcard":"110101199001019976","mobile":"14700000000","id":"225035","time":"2019-02-20 09:28:58","d_status":"状态：暂无"}]
     */

    public String code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * name : 张三
         * idcard : 110101199001011819
         * mobile : 14800000000
         * id : 225040
         * time : 2019-02-20 09:33:01
         * d_status : 状态：取消
         */

        public String name;
        public String idcard;
        public String mobile;
        public String id;
        public String time;
        public String d_status;
    }
}
