package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.PhoneEntity;
import me.zhouzhuo810.zzapidoc.common.api.entity.UserLoginResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.db.DbUtils;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.common.utils.ZSharedUtil;
import rx.Subscriber;

/**
 * Created by zz on 2017/7/5.
 */
public class LoginActivity extends BaseActivity {
    private AutoCompleteTextView etPhone;
    //    private EditText etPhone;
    private EditText etPswd;
    private Button btnLogin;
    private ImageView ivClearPhone;
    private ImageView ivClearPswd;
    private ImageView ivSetting;
    private LinearLayout llTop;
    private Button btnRegister;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        etPhone = (AutoCompleteTextView) findViewById(R.id.et_phone);
//        etPhone = (EditText) findViewById(R.id.et_phone);
        etPswd = (EditText) findViewById(R.id.et_pswd);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_register);

        llTop = (LinearLayout) findViewById(R.id.ll_top);

        ivClearPhone = (ImageView) findViewById(R.id.iv_clear_phone);
        ivClearPswd = (ImageView) findViewById(R.id.iv_clear_pswd);

        ivSetting = (ImageView) findViewById(R.id.iv_setting);
    }

    @Override
    public void initData() {
        String phone = ZSharedUtil.getPhone();
        if (!TextUtils.isEmpty(phone)) {
            etPhone.setText(phone);
            String pswd = ZSharedUtil.getPswd();
            if (!TextUtils.isEmpty(pswd)) {
                etPswd.setText(pswd);
                doLogin(phone, pswd);
            }
        }
        initAuto();

    }

    private void initAuto() {
        List<PhoneEntity> allPhones = DbUtils.getAllPhones();
        List<String> phones = generatePhones(allPhones);
        etPhone.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, phones));
    }

    private List<String> generatePhones(List<PhoneEntity> allPhones) {
        List<String> p = new ArrayList<>();
        if (allPhones != null) {
            for (PhoneEntity allPhone : allPhones) {
                p.add(allPhone.getPhone());
            }
        }
        return p;
    }

    @Override
    public void initEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = etPhone.getText().toString().trim();
                final String pswd = etPswd.getText().toString().trim();
                doLogin(phone, pswd);
            }
        });

        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SettingServerActivity.class);
                startActForResultWithIntent(intent, 0x03);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

        setEditListener(etPhone, ivClearPhone);
        setEditListener(etPswd, ivClearPswd);

        llTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
        Intent intent = new Intent(this, RegisterActivity.class);
        startActForResultWithIntent(intent, 0x01);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x01:
                    String phone = data.getStringExtra("phone");
                    String pswd = data.getStringExtra("pswd");
                    doLogin(phone, pswd);
                    break;
                case 0x03:

                    break;
            }
        }
    }

    private void doLogin(final String phone, final String pswd) {
        if (phone.length() == 0) {
            ToastUtils.showCustomBgToast("手机号不能为空！");
            return;
        }
        if (pswd.length() == 0) {
            ToastUtils.showCustomBgToast("密码不能为空！");
            return;
        }
        showPd("登陆中...", false);
        Api.getApi0()
                .userLogin(phone, pswd)
                .compose(RxHelper.<UserLoginResult>io_main())
                .subscribe(new Subscriber<UserLoginResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(UserLoginResult loginResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(loginResult.getMsg());
                        if (loginResult.getCode() == 1) {
                            String name = loginResult.getData().getName();
                            String picUrl = loginResult.getData().getPic();
                            String userId = loginResult.getData().getId();
                            DbUtils.savePhone(phone);
                            ZSharedUtil.savePswd(pswd);
                            ZSharedUtil.savePhone(phone);
                            ZSharedUtil.saveRealName(name);
                            ZSharedUtil.savePicUrl(picUrl);
                            ZSharedUtil.saveUserId(userId);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActWithIntent(intent);
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
