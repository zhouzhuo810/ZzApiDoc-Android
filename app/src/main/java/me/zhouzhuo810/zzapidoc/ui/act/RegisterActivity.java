package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.UserRegisterResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * Created by zz on 2017/7/5.
 */

public class RegisterActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private EditText etPhone;
    private ImageView ivClearPhone;
    private EditText etPswd;
    private ImageView ivClearPswd;
    private EditText etName;
    private ImageView ivClearName;
    private EditText etEmail;
    private ImageView ivClearEmail;
    private LinearLayout llSex;
    private TextView tvSex;
    private Button btnSubmit;
    private boolean show;

    private void assignViews() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        etPhone = (EditText) findViewById(R.id.et_phone);
        ivClearPhone = (ImageView) findViewById(R.id.iv_clear_phone);
        etPswd = (EditText) findViewById(R.id.et_pswd);
        ivClearPswd = (ImageView) findViewById(R.id.iv_clear_pswd);
        etName = (EditText) findViewById(R.id.et_name);
        ivClearName = (ImageView) findViewById(R.id.iv_clear_name);
        etEmail = (EditText) findViewById(R.id.et_email);
        ivClearEmail = (ImageView) findViewById(R.id.iv_clear_email);
        llSex = (LinearLayout) findViewById(R.id.ll_sex);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
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
                doRegister();
            }
        });

        setEditListener(etPhone, ivClearPhone);
        setEditListener(etPswd, ivClearPswd);
        setEditListener(etEmail, ivClearEmail);
        setEditListener(etName, ivClearName);

        llSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!show) {
                    chooseSex();
                }
            }
        });
    }

    private void chooseSex() {
        show = true;
        showListDialog(Arrays.asList("男", "女"), false, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                show = false;
            }
        }, new OnItemClick() {
            @Override
            public void onItemClick(int position, String content) {
                show = false;
                tvSex.setText(content);
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

    private void doRegister() {
        showPd("注册中...", false);
        final String phone = etPhone.getText().toString().trim();
        final String pswd = etPswd.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String name = etName.getText().toString().trim();
        final String sex = tvSex.getText().toString().trim();

        if (phone.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.phone_not_nul_text));
            return;
        }
        if (pswd.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.pswd_not_nul_text));
            return;
        }

        Api.getApi0()
                .userRegister(phone, pswd, name, email, sex)
                .compose(RxHelper.<UserRegisterResult>io_main())
                .subscribe(new Subscriber<UserRegisterResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(UserRegisterResult loginResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(loginResult.getMsg());
                        if (loginResult.getCode() == 1) {
                            Intent intent = new Intent();
                            intent.putExtra("phone", phone);
                            intent.putExtra("pswd", pswd);
                            setResult(RESULT_OK, intent);
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
