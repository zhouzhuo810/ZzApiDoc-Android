package me.zhouzhuo810.zzapidoc;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

import org.xutils.x;

import me.zhouzhuo810.zzapidoc.common.Constants;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;


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

        SharedUtil.putString(this, "server_config", Constants.SERVER_IP);

        initOkGo();
    }

    private void initOkGo() {
        //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
        OkGo.getInstance().init(this);
    }


    public static Application getInstance() {
        return INSTANCE;
    }
}
