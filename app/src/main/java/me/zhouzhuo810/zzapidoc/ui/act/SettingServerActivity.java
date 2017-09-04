package me.zhouzhuo810.zzapidoc.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.ZApplication;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.IpEntity;
import me.zhouzhuo810.zzapidoc.common.api.entity.PhoneEntity;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.db.DbUtils;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;

/**
 * Created by zhouzhuo810 on 2017/8/14.
 */

public class SettingServerActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private AutoCompleteTextView etIp;
    private ImageView ivClearIp;
    private Button btnSave;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_server;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        etIp = (AutoCompleteTextView) findViewById(R.id.et_ip);
        ivClearIp = (ImageView) findViewById(R.id.iv_clear_ip);
        btnSave = (Button) findViewById(R.id.btn_save);
    }

    @Override
    public void initData() {
        String ip = SharedUtil.getString(ZApplication.getInstance(), "server_config");
        if (ip != null) {
            etIp.setText(ip);
        }
        initAuto();
    }

    private void initAuto() {
        List<IpEntity> allIps = DbUtils.getAllIps();
        List<String> phones = generateIps(allIps);
        etIp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, phones));
    }

    private List<String> generateIps(List<IpEntity> allPhones) {
        List<String> p = new ArrayList<>();
        if (allPhones != null) {
            for (IpEntity allPhone : allPhones) {
                p.add(allPhone.getIp());
            }
        }
        return p;
    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });
        setEditListener(etIp, ivClearIp);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = etIp.getText().toString().trim();
                if (ip.length() == 0) {
                    ToastUtils.showCustomBgToast(getString(R.string.ip_not_nul_text));
                    return;
                }
                if (!ip.endsWith("/")) {
                    ToastUtils.showCustomBgToast("IP地址必须以'/'结尾");
                    return;
                }
                DbUtils.saveIp(ip);
                SharedUtil.putString(ZApplication.getInstance(), "server_config", ip);
                Api.clearApi0();
                Api.getApi0();
                ToastUtils.showCustomBgToast("保存成功");
                setResult(RESULT_OK, null);
                closeAct();
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
