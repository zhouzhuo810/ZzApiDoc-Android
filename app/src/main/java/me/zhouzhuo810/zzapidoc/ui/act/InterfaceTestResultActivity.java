package me.zhouzhuo810.zzapidoc.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddInterfaceExampleResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.InterfaceTestRequestArgEntity;
import me.zhouzhuo810.zzapidoc.common.api.entity.InterfaceTestRequestHeaderEntity;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.CopyUtils;
import me.zhouzhuo810.zzapidoc.common.utils.JSONTool;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * Created by admin on 2017/8/12.
 */

public class InterfaceTestResultActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llScroll;
    private TextView tvMethod;
    private TextView tvPath;
    private TextView tvTime;
    private LinearLayout llHeaders;
    private LinearLayout llParams;
    private Button btnExample;
    private EditText tvResult;

    private String interfaceId;
    private ArrayList<InterfaceTestRequestArgEntity> params;
    private ArrayList<InterfaceTestRequestHeaderEntity> headers;

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
        llHeaders = (LinearLayout) findViewById(R.id.ll_headers);
        llParams = (LinearLayout) findViewById(R.id.ll_params);
        btnExample = (Button) findViewById(R.id.btn_example);
        tvResult = (EditText) findViewById(R.id.tv_result);
    }

    @Override
    public void initData() {
        interfaceId = getIntent().getStringExtra("interfaceId");
        String path = getIntent().getStringExtra("path");
        tvPath.setText(path);
        String method = getIntent().getStringExtra("method");
        tvMethod.setText(method);
        headers = getIntent().getParcelableArrayListExtra("headers");
        if (headers != null) {
            fillHeaderParams();
        }
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

    private void fillHeaderParams() {
        llHeaders.removeAllViews();
        for (int i = 0; i < headers.size(); i++) {
            InterfaceTestRequestHeaderEntity entity = headers.get(i);
            addHeaderItem(i, entity.getName(), entity.getValue());
        }
    }

    private void addHeaderItem(final int position, String title, String value) {
        View root = LayoutInflater.from(this).inflate(R.layout.interface_test_item, null);
        TextView tvName = (TextView) root.findViewById(R.id.tv_name);
        final EditText etValue = (EditText) root.findViewById(R.id.et_value);
        etValue.setEnabled(false);
        tvName.setText(title);
        etValue.setText(value);
        AutoUtils.auto(root);
        llHeaders.addView(root, position);
    }
    private void fillParams() {
        llParams.removeAllViews();
        for (int i = 0; i < params.size(); i++) {
            InterfaceTestRequestArgEntity entity = params.get(i);
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
        PostRequest<String> post = OkGo.<String>post(path);
        if (headers != null) {
            for (InterfaceTestRequestHeaderEntity header : headers) {
                post.headers(header.getName(), header.getValue());
            }
        }
        if (params != null) {
            for (InterfaceTestRequestArgEntity param : params) {
                post.params(param.getName(), param.getValue());
            }
        }
        showPd(getString(R.string.submiting_text), false);
        final long time = System.currentTimeMillis();
        post.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                hidePd();
                long duration = System.currentTimeMillis() - time;
                tvTime.setText(duration + "毫秒");
                JSONTool tool = new JSONTool();
                tvResult.setText(tool.stringToJSON(response.body()));
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                hidePd();
                long duration = System.currentTimeMillis() - time;
                tvTime.setText(duration + "毫秒");
                tvResult.setText(response.getException() == null ? "" : response.getException().toString());
            }
        });
    }


    private void doGet(String path) {
        GetRequest<String> get = OkGo.<String>get(path);
        if (headers != null) {
            for (InterfaceTestRequestHeaderEntity header : headers) {
                get.headers(header.getName(), header.getValue());
            }
        }
        if (params != null) {
            for (InterfaceTestRequestArgEntity param : params) {
                get.params(param.getName(), param.getValue());
            }
        }
        showPd(getString(R.string.submiting_text), false);
        final long time = System.currentTimeMillis();
        get.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                hidePd();
                long duration = System.currentTimeMillis() - time;
                tvTime.setText(duration + "毫秒");
                JSONTool tool = new JSONTool();
                tvResult.setText(tool.stringToJSON(response.body()));
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                hidePd();
                long duration = System.currentTimeMillis() - time;
                tvTime.setText(duration + "毫秒");
                tvResult.setText(response.getException() == null ? "" : response.getException().toString());
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

        btnExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = tvResult.getText().toString();
                if (result.length() == 0) {
                    ToastUtils.showCustomBgToast("结果不能为空！");
                    return;
                }
                addExample(result);
            }
        });

    }

    private void addExample(String result) {
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0().
                addInterfaceExample(getUserId(), result, interfaceId)
                .compose(RxHelper.<AddInterfaceExampleResult>io_main())
                .subscribe(new Subscriber<AddInterfaceExampleResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(AddInterfaceExampleResult addInterfaceExampleResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(addInterfaceExampleResult.getMsg());
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
