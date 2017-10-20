package me.zhouzhuo810.zzapidoc.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Selection;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllQrCodeResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateQrCodeResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * Created by admin on 2017/10/20.
 */

public class UpdateQrCodeActivity extends BaseActivity {

    private TextView tvTitle;
    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private CheckBox cbPrivate;
    private EditText etQrcodeTitle;
    private ImageView ivClearQrcodeTitle;
    private EditText etContent;
    private Button btnSubmit;
    private String id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_qrcode;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        cbPrivate = (CheckBox) findViewById(R.id.cb_private);
        etQrcodeTitle = (EditText) findViewById(R.id.et_qrcode_title);
        ivClearQrcodeTitle = (ImageView) findViewById(R.id.iv_clear_qrcode_title);
        etContent = (EditText) findViewById(R.id.et_content);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void initData() {
        GetAllQrCodeResult.DataEntity entity = getIntent().getParcelableExtra("data");
        if (entity != null) {
            id = entity.getId();
            fillData(entity);
        }

    }

    private void fillData(GetAllQrCodeResult.DataEntity entity) {
        cbPrivate.setChecked(entity.getIsPrivate());
        etQrcodeTitle.setText(entity.getTitle());
        etContent.setText(entity.getContent());
        etContent.requestFocus();
        Selection.selectAll(etContent.getText());
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
        showPd(getString(R.string.loading_text), false);
        Api.getApi0()
                .updateQrCode(getUserId(), id, cbPrivate.isChecked(), title, content)
                .compose(RxHelper.<UpdateQrCodeResult>io_main())
                .subscribe(new Subscriber<UpdateQrCodeResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(UpdateQrCodeResult updateQrCodeResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(updateQrCodeResult.getMsg());
                        if (updateQrCodeResult.getCode() == 1) {
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
