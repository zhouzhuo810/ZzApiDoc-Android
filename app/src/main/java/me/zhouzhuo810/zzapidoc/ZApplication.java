package me.zhouzhuo810.zzapidoc;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.xutils.x;


/**
 * Created by zhouzhuo810 on 2017/7/21.
 */

public class ZApplication extends Application {

    private static ZApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        //init xUtils
        x.Ext.init(this);
        x.Ext.setDebug(true);

        Logger.addLogAdapter(new AndroidLogAdapter());

        AutoLayoutConifg.getInstance().useDeviceSize();

        /*
         Bugly SDK初始化
        * 参数1：上下文对象
        * 参数2：APPID，平台注册时得到,注意替换成你的appId
        * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
        */
        try {
            CrashReport.initCrashReport(getApplicationContext(), "72a5af001f", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 清理Glide缓存
     */
    public void clearImageCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(INSTANCE).clearMemory();
                Glide.get(INSTANCE).clearDiskCache();
            }
        }).start();
    }

    public static ZApplication getInstance() {
        return INSTANCE;
    }
}
