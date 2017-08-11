package me.zhouzhuo810.zzapidoc;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

import org.xutils.x;


/**
 * Created by zhouzhuo810 on 2017/7/21.
 */

public class ZApplication extends Application {

    private static Application INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        //init xUtils
        x.Ext.init(this);
        x.Ext.setDebug(true);

        Logger.addLogAdapter(new AndroidLogAdapter());

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


    public static Application getInstance() {
        return INSTANCE;
    }
}
