package me.zhouzhuo810.zzapidoc.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import java.io.File;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;

/**
 * Created by zhouzhuo810 on 2017/9/15.
 */

public class PdfPreviewActivity extends BaseActivity {

    private PDFView pdfView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pdf_preview;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        pdfView = (PDFView) findViewById(R.id.pdfView);


    }

    @Override
    public void initData() {
        String path = getIntent().getStringExtra("pdf");
        if (path != null) {
            showPd(getString(R.string.loading_text), false);
            pdfView.fromFile(new File(path))
                    .defaultPage(0)
                    .enableSwipe(true)
                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            hidePd();
                        }
                    })
                    .onError(new OnErrorListener() {
                        @Override
                        public void onError(Throwable t) {
                            hidePd();
                            ToastUtils.showCustomBgToast(t.toString());
                        }
                    })
                    .onPageChange(new OnPageChangeListener() {
                        @Override
                        public void onPageChanged(int page, int pageCount) {

                        }
                    })
                    .load();
        }
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void saveState(Bundle bundle) {

    }

    @Override
    public void restoreState(@Nullable Bundle bundle) {

    }
}
