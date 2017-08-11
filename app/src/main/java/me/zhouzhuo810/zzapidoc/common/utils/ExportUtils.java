package me.zhouzhuo810.zzapidoc.common.utils;

import java.io.File;

import me.zhouzhuo.zzhttp.ZzHttp;
import me.zhouzhuo.zzhttp.callback.Callback;
import me.zhouzhuo810.zzapidoc.common.Constants;

/**
 * Created by zhouzhuo810 on 2017/8/10.
 */

public class ExportUtils {


    public interface ProgressListener {
        void onStart();
        void onLoad(int progress);
        void onCancel();
        void onFail(String error);
        void onOk();
    }


    /**
     * 导出JSON文件并下载工具
     * @param userId
     * @param projectId
     * @param filePath
     * @param fileName
     * @param listener
     */
    public static void exportToJsonFile(String userId, String projectId, String filePath, String fileName, final ProgressListener listener) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        String url = Constants.SERVER_IP+"/v1/interface/downloadJson?userId="+userId+"&projectId="+projectId;
        if (listener != null) {
            listener.onStart();
        }
        ZzHttp.getInstance().download(url, filePath, new Callback.ProgressDownloadCallback() {
            @Override
            public void onProgress(float progress, int currentSize, int totalSize) {
                if (listener != null) {
                    listener.onLoad((int) (progress+0.5f));
                }
            }

            @Override
            public void onSuccess(String result) {

                if (listener != null) {
                    listener.onOk();
                }
            }

            @Override
            public void onFailure(String error) {
                if (listener != null) {
                    listener.onFail(error);
                }
            }
        });
/*
        RequestParams params = new RequestParams(Constants.SERVER_IP+"/v1/interface/downloadJson");
        params.addQueryStringParameter("userId", userId);
        params.addQueryStringParameter("projectId", projectId);
        params.setSaveFilePath(filePath+fileName);
        params.setConnectTimeout(2*60*1000);
        params.setMultipart(true);
        params.setAutoRename(false);
        params.setAutoResume(true);
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                if (listener != null) {
                    listener.onOk();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (listener != null) {
                    listener.onFail(ex.toString());
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if (listener != null) {
                    listener.onCancel();
                }
            }

            @Override
            public void onFinished() {
                if (listener != null) {
                    listener.onOk();
                }
            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                if (listener != null) {
                    listener.onLoad((int) (current*100.0/total+0.5));
                }
            }
        });*/


    }
}
