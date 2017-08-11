package me.zhouzhuo810.zzapidoc.ui.act;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateUserPasswordResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * 修改密码
 * Created by zhouzhuo810 on 2017/7/27.
 */

public class RevisePswdActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private EditText etOldPswd;
    private ImageView ivClearOldPswd;
    private EditText etNewPswd;
    private ImageView ivClearNewPswd;
    private EditText etNewPswdConfirm;
    private ImageView ivClearNewPswdConfirm;
    private Button btnSubmit;


    @Override
    public int getLayoutId() {
        return R.layout.activity_revise_pswd;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        etOldPswd = (EditText) findViewById(R.id.et_old_pswd);
        ivClearOldPswd = (ImageView) findViewById(R.id.iv_clear_old_pswd);
        etNewPswd = (EditText) findViewById(R.id.et_new_pswd);
        ivClearNewPswd = (ImageView) findViewById(R.id.iv_clear_new_pswd);
        etNewPswdConfirm = (EditText) findViewById(R.id.et_new_pswd_confirm);
        ivClearNewPswdConfirm = (ImageView) findViewById(R.id.iv_clear_new_pswd_confirm);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
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

        setEditListener(etNewPswd, ivClearNewPswd);
        setEditListener(etNewPswdConfirm, ivClearNewPswdConfirm);
        setEditListener(etOldPswd, ivClearOldPswd);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePswd();
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

    private void updatePswd() {
        String old = etOldPswd.getText().toString().trim();
        String n = etNewPswd.getText().toString().trim();
        String nc = etNewPswdConfirm.getText().toString().trim();
        if (!n.equals(nc)) {
            ToastUtils.showCustomBgToast("两次输入密码不一致");
            return;
        }
        if (old.length() == 0) {
            ToastUtils.showCustomBgToast("请输入旧密码！");
            return;
        }
        if (n.length() == 0) {
            ToastUtils.showCustomBgToast("请输入新密码！");
            return;
        }
        if (nc.length() == 0) {
            ToastUtils.showCustomBgToast("请确认新密码！");
            return;
        }
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .updateUserPassword(getUserId(), old, n)
                .compose(RxHelper.<UpdateUserPasswordResult>io_main())
                .subscribe(new Subscriber<UpdateUserPasswordResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                    }

                    @Override
                    public void onNext(UpdateUserPasswordResult updateUserImageResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(updateUserImageResult.getMsg());
                        if (updateUserImageResult.getCode() == 1) {
                            setResult(RESULT_OK, null);
                            closeAct();
                        }
                    }
                });
    }

    @Override
    public void saveState(Bundle bundle) {

    }

    @Override
    public void restoreState(Bundle bundle) {

    }

}
