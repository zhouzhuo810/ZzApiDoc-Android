package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Arrays;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddErrorCodeResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddProjectResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */
public class AddErrorCodeActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llErrorCode;
    private EditText etErrorCode;
    private ImageView ivClearErrorCode;
    private LinearLayout llNote;
    private EditText etNote;
    private ImageView ivClearNote;
    private Button btnSubmit;

    private boolean global;
    private boolean group;
    private String interfaceId;
    private String groupId;
    private String projectId;

    private void assignViews() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llErrorCode = (LinearLayout) findViewById(R.id.ll_error_code);
        etErrorCode = (EditText) findViewById(R.id.et_error_code);
        ivClearErrorCode = (ImageView) findViewById(R.id.iv_clear_error_code);
        llNote = (LinearLayout) findViewById(R.id.ll_note);
        etNote = (EditText) findViewById(R.id.et_note);
        ivClearNote = (ImageView) findViewById(R.id.iv_clear_note);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_error_code;
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
        global = getIntent().getBooleanExtra("global", false);
        group = getIntent().getBooleanExtra("group", false);
        interfaceId = getIntent().getStringExtra("interfaceId");
        groupId = getIntent().getStringExtra("groupId");
        projectId = getIntent().getStringExtra("projectId");

    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });

        rlRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddErrorCodeActivity.this, ImportProjectActivity.class);
                startActWithIntent(intent);
            }
        });

        setEditListener(etErrorCode, ivClearErrorCode);
        setEditListener(etNote, ivClearNote);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addErrorCode();
            }
        });


    }

    private void addErrorCode() {
        String code = etErrorCode.getText().toString().trim();
        String ps = etNote.getText().toString().trim();
        if (code.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.code_not_nul_text));
            return;
        }
        int num = Integer.valueOf(code);

        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .addErrorCode(getUserId(), num, ps, interfaceId, groupId, projectId, global, group)
                .compose(RxHelper.<AddErrorCodeResult>io_main())
                .subscribe(new Subscriber<AddErrorCodeResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(AddErrorCodeResult addProjectResult) {
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
