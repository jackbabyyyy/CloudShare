package com.daf.cloudshare;

import android.content.Context;

import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.utils.SP;

/**
 * Created by PP on 2019/4/22.
 */
public class VersionManager {


    public static void setToc(Context context,boolean isToc){
        SP.put(context,"toc",isToc);
        String url=null;
        if (isToc){
             url="https://www.winsharecn.cn/diffuse.php?";
            SP.put(context,"baseurl",url);
            MyApplication.BASEURL=url;


        }else{
             url="https://www.winsharecn.cn/api.php?";
            SP.put(context,"baseurl",url);
            MyApplication.BASEURL=url;
        }
        AppUrl.base=url;
    //    AppUrl.reset();


    }
    public static boolean isToc(Context context){
        if ((boolean) SP.get(context,"toc",false)){
            return true;
        }else{
            return false;
        }
    }

    public static void setNewVersionHasLook(Context context){
        SP.put(context,"new_version",true);
    }
    public static boolean isNewVerionHasLook(Context context){

        if (BuildConfig.APPLICATION_ID.equals("cn.dafyun.app.salesmanqxpc")){
            return true;
        }
        else

        return (boolean) SP.get(context,"new_version",false);
    }
}
