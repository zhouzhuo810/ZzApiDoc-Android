package me.zhouzhuo810.zzapidoc.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.autolayout.utils.AutoUtils;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.ZApplication;


public class ToastUtils {
    private static Toast toast;

    private ToastUtils() {
    }

    public static void showCustomBgToast(String msg) {
        LayoutInflater inflater = (LayoutInflater) ZApplication.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_custom, null);
        AutoUtils.auto(view);
        TextView message = (TextView) view.findViewById(R.id.toast_tv);
        message.setText(msg);
        if (toast == null) {
            toast = new Toast(ZApplication.getInstance());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
        }
        toast.setView(view);
        toast.show();
    }

    public static void hideCustomBgToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    public static void showShortToast(int stringId) {
        Toast.makeText(ZApplication.getInstance(), stringId, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(int strId) {
        Toast.makeText(ZApplication.getInstance(), strId, Toast.LENGTH_LONG).show();
    }
}
