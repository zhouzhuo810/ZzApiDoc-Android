package me.zhouzhuo810.zzapidoc.ui.act;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import org.xutils.common.util.LogUtil;

import java.io.File;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;

/**
 * 图片预览
 * Created by zz on 2015/12/29.
 */
public class ImagePreviewActivity extends BaseActivity {

    private ImageView iv;
    private PhotoViewAttacher attacher;

    @Override
    public int getLayoutId() {
        return R.layout.activity_img_preview;
    }

    @Override
    public boolean defaultBack() {
        return true;
    }

    @Override
    public void initView() {
        iv = (ImageView) findViewById(R.id.act_img_preview_iv);
        attacher = new PhotoViewAttacher(iv);
        attacher.setMaximumScale(10.0f);

        attacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 21) {
                    onBackPressed();
                } else {
                    closeAct();
                }
            }
        });
    }

    @Override
    public void initData() {

        final String url = getIntent().getStringExtra("url");
        LogUtil.i("url = " + url);
        if (url != null) {
            if (url.startsWith("http")) {
                Glide.with(this).load(url).placeholder(R.drawable.ic_default).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        attacher.update();
                        return false;
                    }
                }).into(iv);
            } else {
                LogUtil.i(url);
                Glide.with(this).load(new File(url)).placeholder(R.drawable.ic_default).listener(new RequestListener<File, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, File model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, File model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        attacher.update();
                        return false;
                    }
                }).into(iv);
            }
        } else {
            iv.setImageResource(R.drawable.ic_default);
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
    public void restoreState(Bundle bundle) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
