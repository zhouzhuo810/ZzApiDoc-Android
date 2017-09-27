package me.zhouzhuo810.zzapidoc.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebView;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.PreviewUIResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * 预览
 * Created by zhouzhuo810 on 2017/9/25.
 */

public class PreviewActivity extends BaseActivity {

    private SwipeRefreshLayout refresh;
    private WebView webView;
    private String id;

    private void assignViews() {
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        webView = (WebView) findViewById(R.id.web_view);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_preview;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        assignViews();
    }

    @Override
    public void initData() {

        id = getIntent().getStringExtra("id");

        webView.getSettings().setJavaScriptEnabled(true);

        startRefresh(refresh);

        getData();

    }

    @Override
    public void initEvent() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    private void getData() {
        Api.getApi0()
                .previewUI(id, getUserId())
                .compose(RxHelper.<PreviewUIResult>io_main())
                .subscribe(new Subscriber<PreviewUIResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh(refresh);
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text)+e.toString());
                    }

                    @Override
                    public void onNext(PreviewUIResult previewUIResult) {
                        stopRefresh(refresh);
                        if (previewUIResult.getCode()==1) {
                            String html = previewUIResult.getData().getContent();
                            webView.loadData(html, "text/html; charset=UTF-8", null);//这种写法可以正确解码
                        }
                    }
                });

    }

    @Override
    public void resume() {
        webView.onResume();
    }

    @Override
    public void pause() {
        webView.onPause();
    }

    @Override
    public void destroy() {
        webView.destroy();
    }

    @Override
    public void saveState(Bundle bundle) {

    }

    @Override
    public void restoreState(@Nullable Bundle bundle) {

    }
}
