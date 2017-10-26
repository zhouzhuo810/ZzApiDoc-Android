package me.zhouzhuo810.zzapidoc.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddVersionResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * 添加版本
 * Created by zhouzhuo810 on 2017/10/26.
 */
public class AddVersionActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private EditText etVersionName;
    private ImageView ivClearVersionName;
    private EditText etVersionCode;
    private ImageView ivClearVersionCode;
    private EditText etVersionDesc;
    private Button btnSubmit;
    private String id;

    private void assignViews() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        etVersionName = (EditText) findViewById(R.id.et_version_name);
        ivClearVersionName = (ImageView) findViewById(R.id.iv_clear_version_name);
        etVersionCode = (EditText) findViewById(R.id.et_version_code);
        ivClearVersionCode = (ImageView) findViewById(R.id.iv_clear_version_code);
        etVersionDesc = (EditText) findViewById(R.id.et_version_desc);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_version;
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
    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {
        String versionName = etVersionName.getText().toString().trim();
        String versionCode = etVersionCode.getText().toString().trim();
        if (versionCode.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.versionCode_not_nul_text));
            return;
        }
        String desc = etVersionDesc.getText().toString().trim();
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .addVersion(getUserId(), id, versionName, Integer.parseInt(versionCode), desc)
                .compose(RxHelper.<AddVersionResult>io_main())
                .subscribe(new Subscriber<AddVersionResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(AddVersionResult addVersionResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(addVersionResult.getMsg());
                        if (addVersionResult.getCode() == 1) {
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
