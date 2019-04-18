package com.daf.cloudshare.ui.web;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.daf.cloudshare.net.AppUrl;

import java.util.List;

public class JSInterface extends Object {
    private WebView  webView;
    private Activity myActivity;
    private double mLat;
    private double mLon;

    public JSInterface(WebView webView, Activity myActivity){
        this.webView = webView;
        this.myActivity = myActivity;
        initLocation();
    }

    @JavascriptInterface
    public void getAppCall(final String dataParam){
        System.out.println("JS调用了Android的hello方法");
        Log.d("jsInterface",dataParam);

        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + dataParam);
        intent.setData(data);
        this.myActivity.startActivity(intent);

        if (this.webView != null){
            this.webView.post(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    webView.loadUrl("javascript:window.appJs.appCallCallback('"+dataParam+"','callfinsh');");
                }
            });
        }
    }

    @JavascriptInterface
    public void appGps(final String funBakParm){
        System.out.println("JS调用了Android的hello方法");
        Log.d("jsInterface",funBakParm);

        // TODO:实现GPS的获取 ->lat  lon



        if (this.webView != null){
            this.webView.post(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    webView.loadUrl("javascript:window.appJs.appGpsCallback('"+funBakParm+"',"+ mLat +","+ mLon +");");
                }
            });
        }
    }

    public void appDevice(final String funBakParm){
        System.out.println("JS调用了Android的hello方法");
        Log.d("jsInterface",funBakParm);

        if (this.webView != null){
            this.webView.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    webView.loadUrl("javascript:appJs.appDeviceCallback('"+funBakParm+"','android');");
                }
            });
        }
    }

    public void appUserCall(final String funBakParm){
        System.out.println("JS调用了Android的hello方法");
        Log.d("jsInterface",funBakParm);

        // TODO:将用户基本信息-登录返回基本数据 ->strUserJson
        final String strUserJson = "{}";
        if (this.webView != null){
            this.webView.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    webView.loadUrl("javascript:appJs.appUserCallback('"+funBakParm+"','"+strUserJson+"');");
                }
            });
        }
    }

    public void appToken(final String funBakParm){
        System.out.println("JS调用了Android的hello方法");
        Log.d("jsInterface",funBakParm);

        // TODO:将用户token ->token
        final String token = "";
        if (this.webView != null){
            this.webView.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    webView.loadUrl("javascript:appJs.appTokenCallback('"+funBakParm+"','"+token+"');");
                }
            });
        }
    }

    public void appScan(final String funBakParm){
        System.out.println("JS调用了Android的hello方法");
        Log.d("jsInterface",funBakParm);

        // TODO:实现扫描二维码
        final String strScan = "";
        if (this.webView != null){
            this.webView.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    webView.loadUrl("javascript:appJs.appScanCallback('"+funBakParm+"','"+strScan+"');");
                }
            });
        }
    }

    public void appPhoto(final String funBakParm){
        System.out.println("JS调用了Android的hello方法");
        Log.d("jsInterface",funBakParm);

        // TODO:实现获取图片库-并上传至服务器-将图片url返回给webview
        final String strPhotPath = ""; //
        if (this.webView != null){
            this.webView.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    webView.loadUrl("javascript:appJs.appPhotoCallback('"+funBakParm+"','"+strPhotPath+"');");
                }
            });
        }
    }

    public void appCameraCallback(final String funBakParm){
        System.out.println("JS调用了Android的hello方法");
        Log.d("jsInterface",funBakParm);

        // TODO:实现相机拍照功能-并上传至服务器-将图片url返回给webview
        final String strPhotPath = ""; //
        if (this.webView != null){
            this.webView.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    webView.loadUrl("javascript:appJs.appCameraCallback('"+funBakParm+"','"+strPhotPath+"');");
                }
            });
        }
    }

    public void appImage(final String funBakParm){
        System.out.println("JS调用了Android的hello方法");
        Log.d("jsInterface",funBakParm);

        // TODO:实现相册或者相机拍照功能-并上传至服务器-将图片url返回给webview
        final String strPhotPath = ""; //
        if (this.webView != null){
            this.webView.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    webView.loadUrl("javascript:appJs.appImageCallback('"+funBakParm+"','"+strPhotPath+"');");
                }
            });
        }
    }

    public void appUpload(final String funBakParm){
        System.out.println("JS调用了Android的hello方法");
        Log.d("jsInterface",funBakParm);

        // TODO:实现选择文件并上传文件，将文件URL返回给webview
        final String strFilePath = ""; //
        if (this.webView != null){
            this.webView.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    webView.loadUrl("javascript:appJs.appUploadCallback('"+funBakParm+"','"+strFilePath+"');");
                }
            });
        }
    }

    public void appFormPost(final String dataParam){
        System.out.println("JS调用了Android的hello方法");
        Log.d("jsInterface",dataParam);

        // TODO:实现表单上传 header=>上传数据协议头，bodys=>上传数据的post数据,url=>上传地址
       // dataParam = {"header":[{"token":"111"}],"bodys":[{"name":""zhannsan},{"url":"https://www.baidu.com"}],"url":"strUrl"}}
        final String returnData =  "";//上传返回的内容
        if (this.webView != null){
            this.webView.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    webView.loadUrl("javascript:appJs.appFormPostCallback('"+dataParam+"','"+returnData+"');");
                }
            });
        }
    }

    public void appJsonPost(final String dataParam){
        System.out.println("JS调用了Android的hello方法");
        Log.d("jsInterface",dataParam);

        // TODO:实现表单上传 header=>上传数据协议头，bodys=>上传数据的post数据,url=>上传地址
        // dataParam = {"header":[{"token":"111"}],"bodys":[{"name":""zhannsan},{"url":"https://www.baidu.com"}],"url":"strUrl"}}
        final String returnData =  "";//上传返回的内容
        if (this.webView != null){
            this.webView.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    webView.loadUrl("javascript:appJs.appJsonPostCallback('"+dataParam+"','"+returnData+"');");
                }
            });
        }
    }

    public void appGet(final String dataParam){
        System.out.println("JS调用了Android的hello方法");
        Log.d("jsInterface",dataParam);

        // TODO:实现表单上传 header=>上传数据协议头，bodys=>上传数据的post数据,url=>上传地址,url=>上传地址
        // dataParam = {"header":[{"token":"111"}],"bodys":[{"name":""zhannsan},{"url":"https://www.baidu.com"}],"url":"strUrl"}
        final String returnData =  "";//上传返回的内容
        if (this.webView != null){
            this.webView.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    webView.loadUrl("javascript:appJs.appGetCallback('"+dataParam+"','"+returnData+"');");
                }
            });
        }
    }

    public void appDeclaration(final String dataParam){
        System.out.println("JS调用了Android的hello方法");
        Log.d("jsInterface",dataParam);

        // TODO:实现表单上传 header=>上传数据协议头，bodys=>上传数据的post数据 =>上传到报单接口.
        // dataParam = {"header":[{"token":"111"}],"bodys":[{"name":""zhannsan},{"url":"https://www.baidu.com"}]}
        final String returnData =  "";//上传返回的内容
        if (this.webView != null){
            this.webView.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    webView.loadUrl("javascript:appJs.appDeclarationCallback('"+dataParam+"','"+returnData+"');");
                }
            });
        }
    }


    String locationProvider;
    LocationManager locationManager;
    private void initLocation() {
        //1.获取位置管理器
        locationManager = (LocationManager) myActivity.getSystemService(Context.LOCATION_SERVICE);
        //2.获取位置提供器，GPS或是NetWork
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是网络定位
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS定位
            locationProvider = LocationManager.GPS_PROVIDER;
        } else {

            return;
        }

        // 需要检查权限,否则编译报错,想抽取成方法都不行,还是会报错。只能这样重复 code 了。
        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission( myActivity, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission( myActivity, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (ActivityCompat.checkSelfPermission( myActivity, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {

        }
        // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
        locationManager.requestLocationUpdates( locationProvider, 0, 0, locationListener );
    }


    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */
    LocationListener locationListener = new LocationListener() {

        /**
         * 当某个位置提供者的状态发生改变时
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        /**
         * 某个设备打开时
         */
        @Override
        public void onProviderEnabled(String provider) {

        }

        /**
         * 某个设备关闭时
         */
        @Override
        public void onProviderDisabled(String provider) {
        }

        /**
         * 手机位置发生变动
         */
        @Override
        public void onLocationChanged(Location location) {
            location.getAccuracy();//精确度
            mLat = location.getLatitude() ;
            mLon = location.getLongitude() ;


        }
    };
}


