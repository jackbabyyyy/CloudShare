package com.daf.cloudshare.net;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.daf.cloudshare.LoginActivity;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.utils.Const;
import com.daf.cloudshare.utils.SP;
import com.qmuiteam.qmui.arch.QMUIFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    private volatile static HttpUtil okHttpUtil;//会被多线程使用，所以使用关键字volatile
    private OkHttpClient client;
    private Handler mHandler;
    private Context mContext;




    //私有化构造方法
    private HttpUtil(Context context){
        mContext=context;
        File sdcache = context.getExternalCacheDir();
        int cacheSize = 10 * 1024 *1024;//设置缓存大小
        OkHttpClient.Builder builder= new OkHttpClient.Builder()

                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(),cacheSize));//设置缓存的路径
                 client = builder.build();
                mHandler = new Handler();
    }
    //单例模式，全局得到一个OkHttpUtil对象
    public static HttpUtil getInstance(Context context){
        if (okHttpUtil == null){
            synchronized (HttpUtil.class){
                if (okHttpUtil == null){
                    okHttpUtil = new HttpUtil(context);
                }
            }
        }
        return okHttpUtil;
    }
 
    /**get异步请求
     * @param url
     * @param callback
     */
    public void getAsynHttp(String url, final ResultCallback callback){
         Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedCallback(call,e,callback);
            }
 
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendSuccessCallback(response.body().string(),callback);
            }
        });
    }
 
    /**提交表单数据
     * @param url
     * @param map
     * @param callback
     */
    public void postForm(String url, Map<String,String > map, final ResultCallback callback){
        FormBody.Builder form = new FormBody.Builder();//表单对象，包含以input开始的对象,以html表单为主
//        if (map != null && !map.isEmpty()){
            //遍历Map集合
        RequestBody body=null;
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                form.add(entry.getKey(), entry.getValue());
            }
            body = form.build();
        }
        else {
           body= RequestBody.create(null, "");
        }
            Request request = new Request.Builder().url(url).post(body)
                    .addHeader("token", SP.getToken(mContext)).build();//采用post提交数据

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                        Log.d("http", "fail: "+request.url());
                        sendFailedCallback(call,e,callback);
                }
 
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()&&response != null){
                        String s=response.body().string();
                        Log.d("http", "request: "+request.url());
                        Log.d("http", "response: "+s);
                        try {
                            JSONObject jsonObject=new JSONObject(s);

                            if (jsonObject.getString("code").equals(Const.token_error)){
                                //重新登录
                                handleTokenError();
                              return;
                            }

                            sendSuccessCallback(s,callback);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }
            });
//        }
 
    }

    private void handleTokenError(){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("chenzhiyuan", "token 过期 ");
                SP.put(mContext,Const.token,"");
                Toast.makeText(mContext,"登录已过期，请重新登录",Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, LoginActivity.class));

            }
        });
    }
 
    /**当请求失败时，都会调用这个方法
     * @param call
     * @param e
     * @param callback
     */
    private void sendFailedCallback(final Call call, final IOException e, final ResultCallback callback){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.i("main","当前线程："+Thread.currentThread().getName());
                if (callback != null){
                    callback.onError(call.request(),e);
                }
            }
        });
    }
 
    /**请求成功调用该方法
     * @param response  返回的数据
     * @param callback 回调的接口
     */
    private void sendSuccessCallback(final String response, final ResultCallback callback){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.i("main","当前线程："+Thread.currentThread().getName());
                if (callback != null){
                    try {
                        callback.onResponse(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
 
    //创建接口，回调给调用者
    public  interface ResultCallback{
        void onError(Request request,Exception e);
        void onResponse(String response) throws IOException;
    }
 
}