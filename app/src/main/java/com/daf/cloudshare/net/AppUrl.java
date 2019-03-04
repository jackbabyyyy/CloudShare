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

    public static final String toolsList=base+"api.php?c=common&a=getToolsList";

    public static final String prjList=base+"api.php?c=project&a=getProjectList";
    public static final String prjInfo=base+"api.php?c=project&a=getProjectInfo";

    public static final String myPrj=base+"api.php?c=project&a=getMyProject";
    public static final String myPrjDataList=base+"api.php?c=project&a=getDataList";

    public static final String myQr=base+"api.php?c=user&a=shareQRCode";
    public static final String modifyPass=base+"api.php?c=user&a=changePassword";
    public static final String addAdvice=base+"api.php?c=common&a=addAdvice";

    public static final String checkVersion=base+"api.php?c=public&a=checkVersion";


}
