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
import me.zhouzhuo810.zzapidoc.common.api.entity.AddRequestHeaderResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * 添加请求头
 * Created by zhouzhuo810 on 2017/8/11.
 */
public class AddRequestHeaderActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private EditText etHeaderName;
    private ImageView ivClearHeaderName;
    private EditText etHeaderValue;
    private ImageView ivClearHeaderValue;
    private EditText etNote;
    private ImageView ivClearNote;
    private Button btnSubmit;

    private String projectId;
    private boolean show;
    private String interfaceId;
    private boolean global = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_request_header;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        etHeaderName = (EditText) findViewById(R.id.et_header_name);
        ivClearHeaderName = (ImageView) findViewById(R.id.iv_clear_header_name);
        etHeaderValue = (EditText) findViewById(R.id.et_header_value);
        ivClearHeaderValue = (ImageView) findViewById(R.id.iv_clear_header_value);
        etNote = (EditText) findViewById(R.id.et_note);
        ivClearNote = (ImageView) findViewById(R.id.iv_clear_note);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void initData() {
        projectId = getIntent().getStringExtra("projectId");
        interfaceId = getIntent().getStringExtra("interfaceId");
        global = getIntent().getBooleanExtra("global", false);

    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });

        setEditListener(etHeaderName, ivClearHeaderName);
        setEditListener(etHeaderValue, ivClearHeaderValue);
        setEditListener(etNote, ivClearNote);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHeader();
            }
        });


    }

    private void addHeader() {
        String name = etHeaderName.getText().toString().trim();
        if (name.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.header_name_not_nul_text));
            return;
        }
        String value = etHeaderValue.getText().toString().trim();
        if (value.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.header_value_not_nul_text));
            return;
        }
        String note = etNote.getText().toString().trim();
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .addRequestHeader(name, value, note, interfaceId, projectId, getUserId(), global)
                .compose(RxHelper.<AddRequestHeaderResult>io_main())
                .subscribe(new Subscriber<AddRequestHeaderResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(AddRequestHeaderResult addProjectResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(addProjectResult.getMsg());
                        if (addProjectResult.getCode() == 1) {
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