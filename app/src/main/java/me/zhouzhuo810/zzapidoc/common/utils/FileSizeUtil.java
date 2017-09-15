package me.zhouzhuo810.zzapidoc.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by zhouzhuo810 on 2017/9/15.
 */

public class FileSizeUtil {

    public static String getFileSize(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            String resource_size = "";
            DecimalFormat df = new DecimalFormat("#.##");
            double resourcesize = (double) ((double) fis.available() / 1024);
            if ((double) ((double) fis.available() / 1024) > 1000) {
                resource_size = df.format((double) ((double) fis.available() / 1024 / 1024)) + " MB";
            } else {
                resource_size = df.format((double) ((double) fis.available() / 1024)) + " KB";
            }
            return resource_size;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "0 KB";
    }
}
