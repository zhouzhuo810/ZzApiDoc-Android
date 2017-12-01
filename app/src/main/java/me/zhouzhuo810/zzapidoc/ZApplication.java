package me.zhouzhuo810.zzapidoc;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.youdao.sdk.app.YouDaoApplication;

import org.xutils.x;

import me.zhouzhuo810.zzapidoc.common.Constants;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;
import zhouzhuo810.me.zzandframe.common.utils.ToastUtils;
import zhouzhuo810.me.zzandframe.ui.app.BaseApplication;


/**
 * Created by zhouzhuo810 on 2017/7/21.
 */

public class ZApplication extends BaseApplication {

    private static Application INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        //init xUtils
        x.Ext.init(this);
        x.Ext.setDebug(true);

        Logger.addLogAdapter(new AndroidLogAdapter());

        try {
//            YouDaoApplication.init(this, "57e67e07898fdc4b");
            YouDaoApplication.init(this, "7f2356e58990a4f9");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ToastUtils.init(this, R.color.colorMain, R.color.colorWhite);

        /*
         Bugly SDK初始化
        * 参数1：上下文对象
        * 参数2：APPID，平台注册时得到,注意替换成你的appId
        * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
        */
        try {
            CrashReport.initCrashReport(getApplicationContext(), "53991427eb", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String server_config = SharedUtil.getString(this, "server_config");
        if (server_config == null) {
            SharedUtil.putString(this, "server_config", Constants.SERVER_IP);
        }

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
