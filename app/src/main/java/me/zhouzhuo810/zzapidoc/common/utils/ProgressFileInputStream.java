package me.zhouzhuo810.zzapidoc.common.utils;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by ZZ on 2016/9/15.
 */
public class ProgressFileInputStream extends FileInputStream {

    private static final int TEN_KILOBYTES = 1024*10;

    private FileInputStream inputStream;

    private long progress;
    private long lastUpdate;

    private long fileLength;

    private HCUploadUtil.OnUploadFinishListener listener;

    private Handler handler;

    public ProgressFileInputStream(String name, FileInputStream inputStream, HCUploadUtil.OnUploadFinishListener listener) throws FileNotFoundException {
        super(name);
        this.inputStream = inputStream;
        this.handler = new Handler(Looper.getMainLooper());
        this.progress = 0;
        this.lastUpdate = 0;
        this.fileLength = new File(name).length();
        this.listener = listener;
    }

    @Override
    public int read() throws IOException {
        int count = inputStream.read();
        return incrementCounterAndUpdateDisplay(count);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int count = inputStream.read(b, off, len);
        return incrementCounterAndUpdateDisplay(count);
    }

    private int incrementCounterAndUpdateDisplay(int count) {
        if (count > 0)
            progress += count;
        lastUpdate = maybeUpdateDisplay(progress, lastUpdate);
        return count;
    }

    private long maybeUpdateDisplay(final long progress, long lastUpdate) {
        if (progress - lastUpdate > TEN_KILOBYTES) {
            lastUpdate = progress;
            if (listener != null)
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onPro(progress, fileLength);
                    }
                });
        }
        return lastUpdate;
    }


}
