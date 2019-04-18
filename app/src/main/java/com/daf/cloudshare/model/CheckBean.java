package com.daf.cloudshare.model;

/**
 * Created by PP on 2019/3/29.
 */
public class CheckBean {


    /**
     * code : 80001
     * msg : 获取数据成功！
     * data : {"app_versions_android":{"app_id":"com.miaoyueapp.apps.jiedaizhijia","android_url":"http://r.m.miaoyueapp.com/loanHome_v1.0.9.apk","android_version":"1.0.9","level":"2","update_info":"","version_id":"10"},"app_versions_ios":{"app_id":"com.miaoyueapp.apps.jiedaizhijia","ios_url":"itms-services://?action=download-manifest&url=https://v.m.aiyesoft.com/apps/loan/ad/jdzj/v1.6/5/manifest.plist","ios_ver":"12","version_id":"10"}}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * app_versions_android : {"app_id":"com.miaoyueapp.apps.jiedaizhijia","android_url":"http://r.m.miaoyueapp.com/loanHome_v1.0.9.apk","android_version":"1.0.9","level":"2","update_info":"","version_id":"10"}
         * app_versions_ios : {"app_id":"com.miaoyueapp.apps.jiedaizhijia","ios_url":"itms-services://?action=download-manifest&url=https://v.m.aiyesoft.com/apps/loan/ad/jdzj/v1.6/5/manifest.plist","ios_ver":"12","version_id":"10"}
         */

        public AppVersionsAndroidBean app_versions_android=new AppVersionsAndroidBean();
        public AppVersionsIosBean app_versions_ios;

        public static class AppVersionsAndroidBean {
            /**
             * app_id : com.miaoyueapp.apps.jiedaizhijia
             * android_url : http://r.m.miaoyueapp.com/loanHome_v1.0.9.apk
             * android_version : 1.0.9
             * level : 2
             * update_info :
             * version_id : 10
             */

            public String app_id="";
            public String android_url="";
            public String android_version="";
            public String level="";
            public String update_info="";
            public String version_id="0";
        }

        public static class AppVersionsIosBean {
            /**
             * app_id : com.miaoyueapp.apps.jiedaizhijia
             * ios_url : itms-services://?action=download-manifest&url=https://v.m.aiyesoft.com/apps/loan/ad/jdzj/v1.6/5/manifest.plist
             * ios_ver : 12
             * version_id : 10
             */

            public String app_id;
            public String ios_url;
            public String ios_ver;
            public String version_id;
        }
    }
}
