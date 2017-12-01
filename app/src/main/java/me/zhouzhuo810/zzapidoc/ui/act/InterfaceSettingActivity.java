package me.zhouzhuo810.zzapidoc.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;
import zhouzhuo810.me.zzandframe.common.utils.ToastUtils;
import zhouzhuo810.me.zzandframe.ui.act.BaseActivity;

/**
 * Created by zhouzhuo810 on 2017/12/1.
 */
public class InterfaceSettingActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llChooseNameStyle;
    private TextView tvChooseNameStyle;
    private LinearLayout llDefVersion;
    private EditText etDefVersion;
    private ImageView ivClearDefVersion;
    private LinearLayout llDefPreffix;
    private EditText etDefPreffix;
    private ImageView ivClearDefPreffix;
    private LinearLayout llMidValue;
    private EditText etDefMidValue;
    private ImageView ivClearDefMidValue;
    private Button btnSubmit;

    private int style = 0;

    private void assignViews() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llChooseNameStyle = (LinearLayout) findViewById(R.id.ll_choose_name_style);
        tvChooseNameStyle = (TextView) findViewById(R.id.tv_choose_name_style);
        llDefVersion = (LinearLayout) findViewById(R.id.ll_def_version);
        etDefVersion = (EditText) findViewById(R.id.et_def_version);
        ivClearDefVersion = (ImageView) findViewById(R.id.iv_clear_def_version);
        llDefPreffix = (LinearLayout) findViewById(R.id.ll_def_preffix);
        etDefPreffix = (EditText) findViewById(R.id.et_def_preffix);
        ivClearDefPreffix = (ImageView) findViewById(R.id.iv_clear_def_preffix);
        llMidValue = (LinearLayout) findViewById(R.id.ll_mid_value);
        etDefMidValue = (EditText) findViewById(R.id.et_def_mid_value);
        ivClearDefMidValue = (ImageView) findViewById(R.id.iv_clear_def_mid_value);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_interface_setting;
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
        style = SharedUtil.getInt(this, "interface_name_style");
        switch (style) {
            case 0:
                tvChooseNameStyle.setText("版本号/请求方式/接口名称");
                llMidValue.setVisibility(View.GONE);
                llDefPreffix.setVisibility(View.GONE);
                llDefVersion.setVisibility(View.VISIBLE);
                break;
            case 1:
                llMidValue.setVisibility(View.VISIBLE);
                llDefPreffix.setVisibility(View.VISIBLE);
                llDefVersion.setVisibility(View.GONE);
                tvChooseNameStyle.setText("前缀/中值/接口名称");
                break;
            case 2:
                llMidValue.setVisibility(View.VISIBLE);
                llDefPreffix.setVisibility(View.GONE);
                llDefVersion.setVisibility(View.VISIBLE);
                tvChooseNameStyle.setText("版本/中值/接口名称");
                break;
        }
        String defPreffix = SharedUtil.getString(this, "preffix", "api");
        etDefPreffix.setText(defPreffix);
        String defMidValue = SharedUtil.getString(this, "midvalue", "Values");
        etDefMidValue.setText(defMidValue);
        String defVersion = SharedUtil.getString(this, "version", "1");
        etDefVersion.setText(defVersion);
    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });

        setEditListener(etDefMidValue, ivClearDefMidValue);
        setEditListener(etDefPreffix, ivClearDefPreffix);
        setEditListener(etDefVersion, ivClearDefVersion);

        llChooseNameStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseStyle();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSetting();
            }
        });
    }

    private void chooseStyle() {
        List<String> items = new ArrayList<>();
        items.add("版本号/请求方式/接口名称");
        items.add("前缀/中值/接口名称");
        items.add("版本号/中值/接口名称");
        showListDialog(items, false, null, new OnItemClick() {
            @Override
            public void onItemClick(int i, String s) {
                switch (i) {
                    case 0:
                        style = 0;
                        llMidValue.setVisibility(View.GONE);
                        llDefPreffix.setVisibility(View.GONE);
                        llDefVersion.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        style = 1;
                        llMidValue.setVisibility(View.VISIBLE);
                        llDefPreffix.setVisibility(View.VISIBLE);
                        llDefVersion.setVisibility(View.GONE);
                        break;
                    case 2:
                        style = 2;
                        llMidValue.setVisibility(View.VISIBLE);
                        llDefPreffix.setVisibility(View.GONE);
                        llDefVersion.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    private void saveSetting() {
        SharedUtil.putInt(this, "interface_name_style", style);
        SharedUtil.putString(this, "midvalue", etDefMidValue.getText().toString().trim());
        SharedUtil.putString(this, "preffix", etDefPreffix.getText().toString().trim());
        SharedUtil.putString(this, "version", etDefVersion.getText().toString().trim());
        ToastUtils.showCustomBgToast("保存成功！");
        closeAct();
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
