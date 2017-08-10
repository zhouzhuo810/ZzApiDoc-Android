package me.zhouzhuo810.zzapidoc.common.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by zz on 2016/4/1.
 */
public class HCUploadUtil {

    public interface OnUploadFinishListener {
        void onSuccess();

        void onFail();

        void onPro(long current, long total);
    }

    /**
     * 通过ftp上传文件
     *
     * @param url          ftp服务器地址 如： 192.168.1.110
     * @param port         端口如 ： 21
     * @param username     登录名
     * @param password     密码
     * @param remotePath   上到ftp服务器的磁盘路径
     * @param fileNamePath 要上传的文件路径
     * @param fileName     要上传的文件名
     * @return
     */
    public static void ftpUpload(final String url, final String port, final String username, final String password, final String remotePath, final String fileNamePath, final String fileName, final OnUploadFinishListener listener) {

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                FTPClient ftpClient = new FTPClient();
                FileInputStream fis = null;
                try {
                    ftpClient.connect(url, Integer.parseInt(port));
                    boolean loginResult = ftpClient.login(username, password);
                    int returnCode = ftpClient.getReplyCode();
                    if (loginResult && FTPReply.isPositiveCompletion(returnCode)) {// 如果登录成功
                        ftpClient.makeDirectory(remotePath);
                        // 设置上传目录
                        ftpClient.changeWorkingDirectory(remotePath);
                        ftpClient.setBufferSize(1024);
                        ftpClient.setControlEncoding("UTF-8");
                        ftpClient.enterLocalPassiveMode();
                        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                        fis = new FileInputStream(fileNamePath + fileName);
                        ftpClient.storeFile(fileName, fis);

                        try {
                            ftpClient.disconnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return 1;
                    } else {// 如果登录失败

                        try {
                            ftpClient.disconnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return 2;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    return 2;
                } finally {
                    //IOUtils.closeQuietly(fis);
                    try {
                        ftpClient.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                if (integer == 1) {
                    if (listener != null) {
                        listener.onSuccess();
                    }
                } else {
                    if (listener != null) {
                        listener.onFail();
                    }
                }
            }
        }.execute();
    }

    /**
     * 通过ftp上传文件
     *
     * @param url          ftp服务器地址 如： 192.168.1.110
     * @param port         端口如 ： 21
     * @param username     登录名
     * @param password     密码
     * @param remotePath   上到ftp服务器的磁盘路径
     * @param fileNamePath 要上传的文件路径
     * @param fileName     要上传的文件名
     * @return
     */
    public static FTPClient ftpUploadCancelable(final String url, final String port, final String username, final String password, final String remotePath, final String fileNamePath, final String fileName, final OnUploadFinishListener listener) {
        Log.e("FTP", "url = " + url);
        Log.e("FTP", "port = " + port);
        Log.e("FTP", "username = " + username);
        Log.e("FTP", "password = " + password);
        Log.e("FTP", "remotePath = " + remotePath);
        Log.e("FTP", "fileNamePath = " + fileNamePath);
        Log.e("FTP", "fileName = " + fileName);

        final FTPClient ftpClient = new FTPClient();
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {

                try {
                    ftpClient.connect(url, Integer.parseInt(port));
                    boolean loginResult = ftpClient.login(username, password);
                    int returnCode = ftpClient.getReplyCode();
                    if (loginResult && FTPReply.isPositiveCompletion(returnCode)) {// 如果登录成功
                        ftpClient.makeDirectory(remotePath);
                        // 设置上传目录
                        ftpClient.changeWorkingDirectory(remotePath);
                        ftpClient.setBufferSize(1024);
                        ftpClient.setControlEncoding("UTF-8");
                        ftpClient.enterLocalPassiveMode();
                        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

                        FileInputStream fileInputStream = new ProgressFileInputStream(fileNamePath + fileName, new FileInputStream(fileNamePath + fileName), listener);
                        ftpClient.storeFile(fileName, fileInputStream);

                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            ftpClient.disconnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return 1;
                    } else {// 如果登录失败

                        try {
                            ftpClient.disconnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return 2;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    return 2;
                } finally {
                    //IOUtils.closeQuietly(fis);
                    try {
                        ftpClient.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                if (integer == 1) {
                    if (listener != null) {
                        listener.onSuccess();
                    }
                } else {
                    if (listener != null) {
                        listener.onFail();
                    }
                }
            }
        }.execute();
        return ftpClient;
    }


    public static void stopUpload(FTPClient client) {
        if (client != null) {
            try {
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
