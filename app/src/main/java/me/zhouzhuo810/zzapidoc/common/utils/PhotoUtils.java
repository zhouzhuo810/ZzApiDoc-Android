package me.zhouzhuo810.zzapidoc.common.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;


import java.io.File;

import me.zhouzhuo810.zzapidoc.BuildConfig;
import me.zhouzhuo810.zzapidoc.common.Constants;

/**
 * Created by zz on 2017/6/26.
 */

public class PhotoUtils {
    public static void call(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }

    public static void installApk(Context context, String fileName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT > 23) {
            //FIX ME by ZZ : 7.0
            Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".provider",
                    new File(Constants.APK_DOWNLOAD_DIR + File.separator + fileName));
            //这flag很关键
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            intent.setDataAndType(Uri.fromFile(new File(Constants.APK_DOWNLOAD_DIR + File.separator + fileName)),
                    "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
