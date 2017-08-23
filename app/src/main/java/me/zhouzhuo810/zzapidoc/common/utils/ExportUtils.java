package me.zhouzhuo810.zzapidoc.common.utils;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;

import me.zhouzhuo810.zzapidoc.ZApplication;

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
    public static void exportDocToJsonFile(Context context, String userId, String projectId, String filePath, String fileName, final ProgressListener listener) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        String url = SharedUtil.getString(ZApplication.getInstance(), "server_config")+"/ZzApiDoc/v1/interface/downloadJson?userId="+userId+"&projectId="+projectId;
        if (listener != null) {
            listener.onStart();
        }
        OkGo.<File> get(url)
                .tag(context)
                .execute(new FileCallback(filePath, fileName) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        if (listener != null) {
                            listener.onOk();
                        }
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        if (listener != null) {
                            listener.onFail(response.getException() == null ? "" : response.getException().toString());
                        }
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        if (listener != null) {
                            listener.onLoad((int) (progress.currentSize*100.0f/progress.totalSize+0.5f));
                        }
                    }
                });

    }

    /**
     * 导出JSON文件并下载工具
     * @param userId
     * @param appId
     * @param filePath
     * @param fileName
     * @param listener
     */
    public static void exportAppToJsonFile(Context context, String userId, String appId, String filePath, String fileName, final ProgressListener listener) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        String url = SharedUtil.getString(ZApplication.getInstance(), "server_config")+"/ZzApiDoc/v1/application/downloadAppJson?userId="+userId+"&appId="+appId;
        if (listener != null) {
            listener.onStart();
        }
        OkGo.<File> get(url)
                .tag(context)
                .execute(new FileCallback(filePath, fileName) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        if (listener != null) {
                            listener.onOk();
                        }
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        if (listener != null) {
                            listener.onFail(response.getException() == null ? "" : response.getException().toString());
                        }
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        if (listener != null) {
                            listener.onLoad((int) (progress.currentSize*100.0f/progress.totalSize+0.5f));
                        }
                    }
                });

    }

    /**
     * 导出pdf文件并下载工具
     * @param userId
     * @param projectId
     * @param filePath
     * @param fileName
     * @param listener
     */
    public static void exportToPdfFile(Context context, String userId, String projectId, String filePath, String fileName, final ProgressListener listener) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String url = SharedUtil.getString(ZApplication.getInstance(), "server_config")+"/ZzApiDoc/v1/interface/downloadPdf?userId="+userId+"&projectId="+projectId;
        if (listener != null) {
            listener.onStart();
        }
        OkGo.<File> get(url)
                .tag(context)
                .execute(new FileCallback(filePath, fileName) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        if (listener != null) {
                            listener.onOk();
                        }
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        if (listener != null) {
                            listener.onFail(response.getException() == null ? "" : response.getException().toString());
                        }
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        if (listener != null) {
                            listener.onLoad((int) (progress.currentSize*100.0f/progress.totalSize+0.5f));
                        }
                    }
                });

    }
    /**
     * 导出Android项目文件并下载工具
     * @param userId
     * @param appId
     * @param filePath
     * @param fileName
     * @param listener
     */
    public static void exportToAppZipFile(Context context, String userId, String appId, String filePath, String fileName, final ProgressListener listener) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String url = SharedUtil.getString(ZApplication.getInstance(), "server_config")+"/ZzApiDoc/v1/application/downloadApplication?userId="+userId+"&appId="+appId;
        if (listener != null) {
            listener.onStart();
        }
        OkGo.<File> get(url)
                .tag(context)
                .execute(new FileCallback(filePath, fileName) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        if (listener != null) {
                            listener.onOk();
                        }
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        if (listener != null) {
                            listener.onFail(response.getException() == null ? "" : response.getException().toString());
                        }
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        if (listener != null) {
                            listener.onLoad((int) (progress.currentSize*100.0f/progress.totalSize+0.5f));
                        }
                    }
                });

    }
}
