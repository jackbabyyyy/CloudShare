package com.daf.cloudshare.model;

import java.util.List;

/**
 * Created by PP on 2019/4/13.
 */
public class PosterBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : {"name":"技术测试","telephone":"14200000000","jumpUrl":"http://www.winsharecn.cn/index.php?a=sq&id=MTA2MjcjIzIxOCMjODgzc2dk","imgList":["https://www.winsharecn.cn/Public/images/Uploads/201904/2019-04-10/1554877812_65924001.jpg","https://www.winsharecn.cn/Public/images/Uploads/201904/2019-04-10/1554878038_207002521.png"]}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * name : 技术测试
         * telephone : 14200000000
         * jumpUrl : http://www.winsharecn.cn/index.php?a=sq&id=MTA2MjcjIzIxOCMjODgzc2dk
         * imgList : ["https://www.winsharecn.cn/Public/images/Uploads/201904/2019-04-10/1554877812_65924001.jpg","https://www.winsharecn.cn/Public/images/Uploads/201904/2019-04-10/1554878038_207002521.png"]
         */

        public String name;
        public String telephone;
        public String jumpUrl;
        public List<String> imgList;
    }
}
