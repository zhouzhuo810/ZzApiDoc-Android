package me.zhouzhuo810.zzapidoc.common.utils;


import me.zhouzhuo810.zzapidoc.ZApplication;
import me.zhouzhuo810.zzapidoc.common.Constants;

/**
 * Created by zz on 2016/5/16.
 */
public class ZSharedUtil {

    public static void saveServerIp(String serverIp) {
        SharedUtil.putString(ZApplication.getInstance(), Constants.SP_KEY_OF_SERVER_IP, serverIp);
    }

    public static String getServerIp() {
        return SharedUtil.getString(ZApplication.getInstance(), Constants.SP_KEY_OF_SERVER_IP);
    }


    public static void savePhone(String phone) {
        SharedUtil.putString(ZApplication.getInstance(), Constants.SP_KEY_OF_PHONE, phone);
    }

    public static String getPhone() {
        return SharedUtil.getString(ZApplication.getInstance(), Constants.SP_KEY_OF_PHONE);
    }

    public static void savePswd(String pswd) {
        SharedUtil.putString(ZApplication.getInstance(), Constants.SP_KEY_OF_PSWD, pswd);
    }

    public static String getPswd() {
        return SharedUtil.getString(ZApplication.getInstance(), Constants.SP_KEY_OF_PSWD);
    }

    public static void savePicUrl(String picUrl) {
        SharedUtil.putString(ZApplication.getInstance(), Constants.SP_KEY_OF_PIC_URL, picUrl);
    }

    public static String getPicUrl() {
        return SharedUtil.getString(ZApplication.getInstance(), Constants.SP_KEY_OF_PIC_URL);
    }


    public static void saveUserId(String userId) {
        SharedUtil.putString(ZApplication.getInstance(), Constants.SP_KEY_OF_USER_ID, userId);
    }

    public static String getUserId() {
        return SharedUtil.getString(ZApplication.getInstance(), Constants.SP_KEY_OF_USER_ID);
    }

    public static void saveRealName(String realName) {
        SharedUtil.putString(ZApplication.getInstance(), Constants.SP_KEY_OF_REAL_NAME, realName);
    }

    public static String getRealName() {
        return SharedUtil.getString(ZApplication.getInstance(), Constants.SP_KEY_OF_REAL_NAME);
    }

    public static void savePosition(String Position) {
        SharedUtil.putString(ZApplication.getInstance(), Constants.SP_KEY_OF_POSITION, Position);
    }

    public static String getPosition() {
        return SharedUtil.getString(ZApplication.getInstance(), Constants.SP_KEY_OF_POSITION);
    }


    public static boolean getIsFirstStart() {
        return SharedUtil.getBoolean(ZApplication.getInstance(), Constants.SP_KEY_OF_IS_FIRST_START, true);
    }

    public static void saveFirstStart(boolean firstStart) {
        SharedUtil.putBoolean(ZApplication.getInstance(), Constants.SP_KEY_OF_IS_FIRST_START, firstStart);
    }

    public static boolean getIsLogin() {
        return SharedUtil.getBoolean(ZApplication.getInstance(), Constants.SP_KEY_OF_IS_LOGIN, false);
    }

    public static void saveIsLogin(boolean isLogin) {
        SharedUtil.putBoolean(ZApplication.getInstance(), Constants.SP_KEY_OF_IS_LOGIN, isLogin);
    }


    public static boolean getMsgSort() {
        return SharedUtil.getBoolean(ZApplication.getInstance(), Constants.SP_KEY_OF_IS_MSG_SORT, true);
    }

    public static void saveMsgSort(boolean isSort) {
        SharedUtil.putBoolean(ZApplication.getInstance(), Constants.SP_KEY_OF_IS_MSG_SORT, isSort);
    }


}
