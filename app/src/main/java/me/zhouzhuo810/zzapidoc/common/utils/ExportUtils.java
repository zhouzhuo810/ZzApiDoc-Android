package me.zhouzhuo810.zzapidoc.common.utils;

import org.xutils.common.Callback;
import org.xutils.common.util.FileUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import me.zhouzhuo810.zzapidoc.common.Constants;

/**
 * Created by zhouzhuo810 on 2017/8/10.
 */

public class ExportUtils {


    public interface ProgressListener {
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
        RequestParams params = new RequestParams(Constants.SERVER_IP+"/v1/interface/downloadJson");
        params.addQueryStringParameter("userId", userId);
        params.addQueryStringParameter("projectId", projectId);
        params.setSaveFilePath(filePath+fileName);
        params.setConnectTimeout(2*60*1000);
        params.setAutoRename(true);
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
        });


    }
}
