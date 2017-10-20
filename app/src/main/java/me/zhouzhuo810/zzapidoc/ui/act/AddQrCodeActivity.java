package me.zhouzhuo810.zzapidoc.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddQrCodeResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * Created by zhouzhuo810 on 2017/10/20.
 */

public class AddQrCodeActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private EditText etQrcodeTitle;
    private ImageView ivClearQrcodeTitle;
    private EditText etContent;
    private Button btnSubmit;
    private CheckBox cbPrivate;


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_qrcode;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        etQrcodeTitle = (EditText) findViewById(R.id.et_qrcode_title);
        ivClearQrcodeTitle = (ImageView) findViewById(R.id.iv_clear_qrcode_title);
        etContent = (EditText) findViewById(R.id.et_content);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        cbPrivate = (CheckBox) findViewById(R.id.cb_private);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });

        setEditListener(etQrcodeTitle, ivClearQrcodeTitle);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {
        String title = etQrcodeTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .addQrCode(getUserId(), cbPrivate.isChecked(), title, content)
                .compose(RxHelper.<AddQrCodeResult>io_main())
                .subscribe(new Subscriber<AddQrCodeResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(AddQrCodeResult addQrCodeResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(addQrCodeResult.getMsg());
                        if (addQrCodeResult.getCode() == 1) {
                            closeAct();
                        }
                    }
                });
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
