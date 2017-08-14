package me.zhouzhuo810.zzapidoc.common.api;

import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;


import me.zhouzhuo810.zzapidoc.ZApplication;
import me.zhouzhuo810.zzapidoc.common.Constants;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;

/**
 * Api调用
 */
public class Api {
    private static Api0 api0;

    public static Api0 getApi0() {
        if (api0 == null) {
            String serverIp = SharedUtil.getString(ZApplication.getInstance(), "server_config");
            synchronized (Api.class) {
                if (api0 == null) {
                    File cache = ZApplication.getInstance().getCacheDir();
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                    HttpLoggingInterceptor logging1 = new HttpLoggingInterceptor();
                    logging1.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient client = new OkHttpClient.Builder()
                            .cache(new Cache(cache, 10 * 1024 * 1024))
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .addInterceptor(logging)
                            .addInterceptor(logging1)
                            .build();


                    CookieHandler.setDefault(getCookieManager());
                    Retrofit retrofit = new Retrofit.Builder()
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())//添加 json 转换器
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加 RxJava 适配器
                            .baseUrl(serverIp == null ? Constants.SERVER_IP : serverIp)
                            .build();
                    api0 = retrofit.create(Api0.class);
                }
            }
        }
        return api0;
    }

    public static void clearApi0() {
        api0 = null;
    }

    private static CookieManager getCookieManager() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        return cookieManager;
    }
}