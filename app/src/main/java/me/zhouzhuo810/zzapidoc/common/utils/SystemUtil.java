package me.zhouzhuo810.zzapidoc.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import org.xutils.common.util.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by zz on 2016/7/8.
 */
public class SystemUtil {
    /**
     * 判断是不是小米系统
     * @param propName
     * @return
     */
    public static String getSystemProperty(String propName){
        String line;
        BufferedReader input = null;
        try
        {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        }
        catch (IOException ex)
        {
            Log.e("SystemUtil", "Unable to read sysprop " + propName, ex);
            return null;
        }
        finally
        {
            if(input != null)
            {
                try
                {
                    input.close();
                }
                catch (IOException e)
                {
                    Log.e("SystemUtil", "Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }

    public static PackageInfo getPackageInfo(Context context){
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(e.getLocalizedMessage());
        }
        return  new PackageInfo();
    }
}
