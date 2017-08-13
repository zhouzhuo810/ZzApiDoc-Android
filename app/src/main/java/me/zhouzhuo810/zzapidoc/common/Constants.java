package me.zhouzhuo810.zzapidoc.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by zz on 2017/6/26.
 */

public class Constants {

//    public static final String SERVER_IP = "http://192.168.0.218:8080/";
    public static final String SERVER_IP = "http://125.118.59.1:8888/";

    public static final String PIC_HEAD = "http://www.qqwork.net/HighnetCloudFile/";

    public static final String SP_KEY_OF_SERVER_IP = "sp_key_of_server_ip";

    public static final String SP_KEY_OF_IS_LOGIN = "sp_key_of_is_login";

    public static final String SP_KEY_OF_IS_FIRST_START = "sp_key_of_is_first_start";

    public static final String PATH_OF_IMG = "path_of_img";

    public static final String MIUI_STRING = "ro.miui.ui.version.name";

    public static final String APK_DOWNLOAD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ZzApiDoc/Apk";

    public static final String DBPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ZzApiDoc/Db"
            ;
    public static final String EXPORT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ZzApiDoc/JSON/";

    public static final String EXPORT_PDF_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ZzApiDoc/PDF/";

    public static final String PIC_UPLOAD_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ZzApiDoc/Image/Upload/";

    public static final String DBNAME = "QWork.db";

    public static final int DBVERSION = 1;

    public static final String SP_KEY_OF_IS_MSG_SORT = "sp_key_of_is_msg_sort";

    public static final String SP_KEY_OF_POSITION = "sp_key_of_position";
    public static final String SP_KEY_OF_REAL_NAME = "sp_key_of_real_name";
    public static final String SP_KEY_OF_USER_ID = "sp_key_of_user_id";
    public static final String SP_KEY_OF_PHONE = "sp_key_of_phone";
    public static final String SP_KEY_OF_PIC_URL = "sp_key_of_pic_url";
    public static final String PUT_FILE_ONE = "put_file_one";
    public static final String SP_KEY_OF_PSWD = "sp_key_of_pswd";

}
