package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.ImportProjectEntity;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * 导入项目
 * Created by zhouzhuo810 on 2017/8/14.
 */
public class ImportProjectActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llProjectProperty;
    private TextView tvProjectProperty;
    private EditText etJson;
    private boolean show;
    private int property;

    @Override
    public int getLayoutId() {
        return R.layout.activity_import_project;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llProjectProperty = (LinearLayout) findViewById(R.id.ll_project_property);
        tvProjectProperty = (TextView) findViewById(R.id.tv_project_property);
        etJson = (EditText) findViewById(R.id.et_json);
    }

    @Override
    public void initData() {
        property = 0;
        tvProjectProperty.setText("公有");
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
                importProject(json);
            }
        });

        llProjectProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!show) {
                    chooseProperty();
                }
            }
        });

    }


    private void chooseProperty() {
        show = true;
        showListDialog(Arrays.asList("公有", "私有"), true, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                show = false;
            }
        }, new OnItemClick() {
            @Override
            public void onItemClick(int position, String content) {
                show = false;
                property = position;
                tvProjectProperty.setText(content);
            }
        });
    }


    private void importProject(String json) {
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .importProject(json, property + "", getUserId())
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
