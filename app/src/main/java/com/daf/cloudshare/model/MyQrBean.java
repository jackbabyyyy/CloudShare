package com.daf.cloudshare.model;

/**
 * Created by PP on 2019/3/4.
 */
public class MyQrBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : {"imgName":"产品申请列表","imgUrl":"https://www.winsharecn.cn/api.php?c=public&a=shareQRCode&url=aHR0cHM6Ly93d3cud2luc2hhcmVjbi5jbi9pbmRleC5waHA/YT1saXN0cHJvJmlkPTEwNjI3","regName":"账号注册","regUrl":"https://www.winsharecn.cn/api.php?c=public&a=shareQRCode&url=aHR0cHM6Ly93d3cud2luc2hhcmVjbi5jbi9pbmRleC5waHA/Yz1sb2dpbiZyZWc9MTA2Mjc=","proImg":"https://www.winsharecn.cn/Public/images/proHD.jpg","userImg":"https://www.winsharecn.cn/Public/images/regHD.jpg"}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * imgName : 产品申请列表
         * imgUrl : https://www.winsharecn.cn/api.php?c=public&a=shareQRCode&url=aHR0cHM6Ly93d3cud2luc2hhcmVjbi5jbi9pbmRleC5waHA/YT1saXN0cHJvJmlkPTEwNjI3
         * regName : 账号注册
         * regUrl : https://www.winsharecn.cn/api.php?c=public&a=shareQRCode&url=aHR0cHM6Ly93d3cud2luc2hhcmVjbi5jbi9pbmRleC5waHA/Yz1sb2dpbiZyZWc9MTA2Mjc=
         * proImg : https://www.winsharecn.cn/Public/images/proHD.jpg
         * userImg : https://www.winsharecn.cn/Public/images/regHD.jpg
         */

        public String imgName;
        public String imgUrl;
        public String regName;
        public String regUrl;
        public String proImg;
        public String userImg;
        public String shareUrl;
    }
}
