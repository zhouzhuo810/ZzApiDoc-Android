package me.zhouzhuo810.zzapidoc.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

import me.zhouzhuo.zzhttp.ZzHttp;
import me.zhouzhuo.zzhttp.callback.Callback;
import me.zhouzhuo.zzhttp.params.HttpParams;
import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.InterfaceTestEntity;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.utils.CopyUtils;
import me.zhouzhuo810.zzapidoc.common.utils.JSONTool;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;

/**
 * Created by admin on 2017/8/12.
 */

public class InterfaceTestResultActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llScroll;
    private TextView tvPath;
    private TextView tvTime;
    private LinearLayout llParams;
    private ArrayList<InterfaceTestEntity> params;
    private TextView tvResult;
    private TextView tvMethod;

    @Override
    public int getLayoutId() {
        return R.layout.activity_interface_test_result;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llScroll = (LinearLayout) findViewById(R.id.ll_scroll);
        tvMethod = (TextView) findViewById(R.id.tv_method);
        tvPath = (TextView) findViewById(R.id.tv_path);
        tvTime = (TextView) findViewById(R.id.tv_time);
        llParams = (LinearLayout) findViewById(R.id.ll_params);
        tvResult = (TextView) findViewById(R.id.tv_result);
    }

    @Override
    public void initData() {
        String path = getIntent().getStringExtra("path");
        tvPath.setText(path);
        String method = getIntent().getStringExtra("method");
        tvMethod.setText(method);
        params = getIntent().getParcelableArrayListExtra("params");
        if (params != null) {
            fillParams();
            if (method != null) {
                if (method.equals("GET")) {
                    doGet(path);
                } else {
                    doPost(path);
                }
            }
        }
    }

    private void fillParams() {
        llParams.removeAllViews();
        for (int i = 0; i < params.size(); i++) {
            InterfaceTestEntity entity = params.get(i);
            addItem(i, entity.getName(), entity.getValue());
        }
    }


    private void addItem(final int position, String title, String value) {
        View root = LayoutInflater.from(this).inflate(R.layout.interface_test_item, null);
        TextView tvName = (TextView) root.findViewById(R.id.tv_name);
        final EditText etValue = (EditText) root.findViewById(R.id.et_value);
        etValue.setEnabled(false);
        tvName.setText(title);
        etValue.setText(value);
        AutoUtils.auto(root);
        llParams.addView(root, position);

    }

    private void doPost(String path) {
        HttpParams httpParams = new HttpParams();
        httpParams.setConnectTimeout(20*1000);
        httpParams.setReadTimeout(20*1000);
        for (InterfaceTestEntity param : params) {
            httpParams.addStringParam(param.getName(), param.getValue());
        }
        showPd(getString(R.string.submiting_text), false);
        final long time = System.currentTimeMillis();
        ZzHttp.getInstance().setBaseUrl(path).post(httpParams, String.class, new Callback.ZzCallback<String>() {
            @Override
            public void onSuccess(String result) {
                hidePd();
                long duration = System.currentTimeMillis() - time;
                tvTime.setText(duration + "毫秒");
                JSONTool tool = new JSONTool();
                tvResult.setText(tool.stringToJSON(result));
            }

            @Override
            public void onFailure(String error) {
                hidePd();
                long duration = System.currentTimeMillis() - time;
                tvTime.setText(duration + "毫秒");
                tvResult.setText(error);
            }
        });
    }


    private void doGet(String path) {
        HttpParams httpParams = new HttpParams();
        httpParams.setConnectTimeout(20*1000);
        httpParams.setReadTimeout(20*1000);
        for (InterfaceTestEntity param : params) {
            httpParams.addStringParam(param.getName(), param.getValue());
        }
        showPd(getString(R.string.submiting_text), false);
        final long time = System.currentTimeMillis();
        ZzHttp.getInstance().setBaseUrl(path).get(httpParams, String.class, new Callback.ZzCallback<String>() {
            @Override
            public void onSuccess(String result) {
                hidePd();
                long duration = System.currentTimeMillis() - time;
                tvTime.setText(duration + "毫秒");
                JSONTool tool = new JSONTool();
                tvResult.setText(tool.stringToJSON(result));
            }

            @Override
            public void onFailure(String error) {
                hidePd();
                long duration = System.currentTimeMillis() - time;
                tvTime.setText(duration + "毫秒");
                tvResult.setText(error);
            }
        });
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
                CopyUtils.copyPlainText(InterfaceTestResultActivity.this, "result", tvResult.getText().toString());
                ToastUtils.showCustomBgToast("已复制到剪切板");
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
