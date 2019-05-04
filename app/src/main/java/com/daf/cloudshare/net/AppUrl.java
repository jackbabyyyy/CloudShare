package com.daf.cloudshare.net;

import android.os.StatFs;

import com.daf.cloudshare.MyApplication;
import com.daf.cloudshare.utils.StringUtil;

import static com.daf.cloudshare.MyApplication.BASEURL;

/**
 * Created by PP on 2019/2/20.
 */
public class AppUrl {
    public static  String login= "https://www.winsharecn.cn/diffuse.php?c=public&a=checkLogin";
    public static  String checkVersion="https://www.winsharecn.cn/diffuse.php?c=public&a=checkVersion";
    public static  String banner=BASEURL+"c=common&a=getIndexBanner";
    public static  String topBtn=BASEURL+"c=common&a=getTopBtn";
    public static  String newPrj=BASEURL+"c=project&a=getNewProject";
    public static  String hotPrj=BASEURL+"c=project&a=getHotProject";

    public static  String toolsList=BASEURL+"c=common&a=getToolsList";

    public static  String prjList=BASEURL+"c=project&a=getProjectList";
    public static  String prjInfo=BASEURL+"c=project&a=getProjectInfo";

    public static  String myPrj=BASEURL+"c=project&a=getMyProject";
    public static  String myPrjDataList=BASEURL+"c=project&a=getDataList";

    public static  String myQr=BASEURL+"c=user&a=shareQRCode";
    public static  String modifyPass=BASEURL+"c=user&a=changePassword";
    public static  String addAdvice=BASEURL+"c=common&a=addAdvice";


    public static  String saveAvatar=BASEURL+"c=user&a=saveAvatar";
    public static  String getMyInfo=BASEURL+"c=user&a=getMyInfo";


    public static  String getProType=BASEURL+"c=project&a=getProType";
    public static  String getTopFavorite=BASEURL+"c=favorite&a=getTopFavorite";
    public static  String addToFavorite=BASEURL+"c=favorite&a=addToFavorite";
    public static  String cancelFavorite=BASEURL+"c=favorite&a=removeFromFavorite";
    public static  String getFavorite=BASEURL+"c=favorite&a=getFavorite";
    public static  String getIndexTip=BASEURL+"c=project&a=getIndexTip";

    public static  String getDialog=BASEURL+"c=common&a=getIndexTip";

//    public static  String getProSetJson=BASEURL+"index.php?c=index&a=getProSetJson";
    public static  String getSearchConfig=BASEURL+"c=common&a=getSearchConfig";
    public static  String searchProject=BASEURL+"c=project&a=searchProject";

    public static  String getPoster=BASEURL+"c=project&a=getPoster";

    public static  String changeInfo=BASEURL+"c=user&a=saveMyInfo";
    public static  String getMyTeam=BASEURL+"c=user&a=getMyTeam";
    public static  String getMyParent=BASEURL+"c=user&a=getMyParent";
    public static  String getHotBank=BASEURL+"c=project&a=getHotBank";

    public static String backPass=BASEURL+"c=public&a=checkRegMobile";
    public static String backPass2=BASEURL+"c=public&a=sendPwdCodeMsg";
    public static String backPass3=BASEURL+"c=public&a=setNewPassword";



    public static  String toc3="https://www.dafyun.cn/Public/h5/cooperate.html";
    public static  String toc4="https://www.winsharecn.cn/Public/h5/guide.html";


    public static void reset(){

        banner=BASEURL+"c=common&a=getIndexBanner";
        topBtn=BASEURL+"c=common&a=getTopBtn";
        newPrj=BASEURL+"c=project&a=getNewProject";
        hotPrj=BASEURL+"c=project&a=getHotProject";
        toolsList=BASEURL+"c=common&a=getToolsList";
        prjList=BASEURL+"c=project&a=getProjectList";
        prjInfo=BASEURL+"c=project&a=getProjectInfo";
        myPrj=BASEURL+"c=project&a=getMyProject";
        myPrjDataList=BASEURL+"c=project&a=getDataList";
        myQr=BASEURL+"c=user&a=shareQRCode";
        modifyPass=BASEURL+"c=user&a=changePassword";
        addAdvice=BASEURL+"c=common&a=addAdvice";
        saveAvatar=BASEURL+"c=user&a=saveAvatar";
        getMyInfo=BASEURL+"c=user&a=getMyInfo";
        getProType=BASEURL+"c=project&a=getProType";
        getTopFavorite=BASEURL+"c=favorite&a=getTopFavorite";
        addToFavorite=BASEURL+"c=favorite&a=addToFavorite";
        cancelFavorite=BASEURL+"c=favorite&a=removeFromFavorite";
        getFavorite=BASEURL+"c=favorite&a=getFavorite";
        getIndexTip=BASEURL+"c=project&a=getIndexTip";
        getDialog=BASEURL+"c=common&a=getIndexTip";
        getSearchConfig=BASEURL+"c=common&a=getSearchConfig";
        searchProject=BASEURL+"c=project&a=searchProject";
        getPoster=BASEURL+"c=project&a=getPoster";
        changeInfo=BASEURL+"c=user&a=saveMyInfo";
        getMyTeam=BASEURL+"c=user&a=getMyTeam";
        getMyParent=BASEURL+"c=user&a=getMyParent";
        getHotBank=BASEURL+"c=project&a=getHotBank";
        backPass=BASEURL+"c=public&a=checkRegMobile";
        backPass2=BASEURL+"c=public&a=sendPwdCodeMsg";
        backPass3=BASEURL+"c=public&a=setNewPassword";

    }







}
