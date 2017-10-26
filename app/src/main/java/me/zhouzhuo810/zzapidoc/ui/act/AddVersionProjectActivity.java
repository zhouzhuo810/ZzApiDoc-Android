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
import me.zhouzhuo810.zzapidoc.common.api.entity.AddVersionProjectResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * Created by zhouzhuo810 on 2017/10/26.
 */

public class AddVersionProjectActivity extends BaseActivity {
    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private EditText etVersionName;
    private ImageView ivClearVersionName;
    private EditText etVersionDesc;
    private Button btnSubmit;

    private void assignViews() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        etVersionName = (EditText) findViewById(R.id.et_version_name);
        ivClearVersionName = (ImageView) findViewById(R.id.iv_clear_version_name);
        etVersionDesc = (EditText) findViewById(R.id.et_version_desc);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_version_project;
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
        String name = etVersionName.getText().toString().trim();
        String note = etVersionDesc.getText().toString().trim();
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .addVersionProject(getUserId(), name, note)
                .compose(RxHelper.<AddVersionProjectResult>io_main())
                .subscribe(new Subscriber<AddVersionProjectResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(AddVersionProjectResult addVersionProjectResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(addVersionProjectResult.getMsg());
                        if (addVersionProjectResult.getCode() == 1) {
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
