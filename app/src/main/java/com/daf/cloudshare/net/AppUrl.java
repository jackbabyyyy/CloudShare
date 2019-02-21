package com.daf.cloudshare.net;

/**
 * Created by PP on 2019/2/20.
 */
public class AppUrl {
    public static final String base="https://www.dafyun.cn/";
    public static final String login=base+"api.php?c=public&a=checkLogin";

    public static final String myInfo=base+"api.php?c=user&a=getMyInfo";

    public static final String banner=base+"api.php?c=common&a=getIndexBanner";
    public static final String topBtn=base+"api.php?c=common&a=getTopBtn";
    public static final String newPrj=base+"api.php?c=project&a=getNewProject";
    public static final String hotPrj=base+"api.php?c=project&a=getHotProject";


}