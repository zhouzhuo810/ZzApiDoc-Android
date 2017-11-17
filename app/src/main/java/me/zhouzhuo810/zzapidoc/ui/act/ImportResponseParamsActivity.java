package me.zhouzhuo810.zzapidoc.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.ImportProjectEntity;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.JSONTool;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * 导入返回参数
 * Created by zhouzhuo810 on 2017/8/14.
 */
public class ImportResponseParamsActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private EditText etJson;
    private Button btnFormat;
    private String interfaceId;
    private String pid;

    @Override
    public int getLayoutId() {
        return R.layout.activity_import_response_params;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        etJson = (EditText) findViewById(R.id.et_json);
        btnFormat = (Button) findViewById(R.id.btn_format);
    }

    @Override
    public void initData() {
        interfaceId = getIntent().getStringExtra("interfaceId");
        pid = getIntent().getStringExtra("pid");
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
                String json = etJson.getText().toString().trim();
                importResponseParams(json);
            }
        });

        btnFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formatJson();
            }
        });

    }

    private void formatJson() {
        String json = etJson.getText().toString().trim();
        if (json.length() > 0) {
            String formatedJson = new JSONTool().stringToJSON(json);
            etJson.setText(formatedJson);
        }
    }

    private void importResponseParams(String json) {
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .importResponseArg(json, interfaceId, pid, getUserId())
                .compose(RxHelper.<ImportProjectEntity>io_main())
                .subscribe(new Subscriber<ImportProjectEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                    }

                    @Override
                    public void onNext(ImportProjectEntity project) {
                        hidePd();
                        ToastUtils.showCustomBgToast(project.getMsg());
                        if (project.getCode() == 1) {
                            setResult(RESULT_OK, null);
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
