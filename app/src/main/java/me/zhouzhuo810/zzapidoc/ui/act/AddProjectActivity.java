package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import me.zhouzhuo810.zzapidoc.common.api.entity.AddProjectResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */

public class AddProjectActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llProjectProperty;
    private TextView tvProjectProperty;
    private EditText etProjectName;
    private ImageView ivClearProjectName;
    private EditText etPs;
    private ImageView ivClearPs;
    private boolean show;
    private int property;
    private Button btnSubmit;


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_project;
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
        etProjectName = (EditText) findViewById(R.id.et_project_name);
        ivClearProjectName = (ImageView) findViewById(R.id.iv_clear_project_name);
        etPs = (EditText) findViewById(R.id.et_ps);
        ivClearPs = (ImageView) findViewById(R.id.iv_clear_ps);
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

        setEditListener(etProjectName, ivClearProjectName);
        setEditListener(etPs, ivClearPs);

        llProjectProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!show) {
                    chooseProperty();
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProject();
            }
        });


    }

    private void addProject() {
        String name = etProjectName.getText().toString().trim();
        String ps = etPs.getText().toString().trim();
        if (name.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.project_name_not_nul_text));
            return;
        }
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .addProject(name, property + "", ps, getUserId())
                .compose(RxHelper.<AddProjectResult>io_main())
                .subscribe(new Subscriber<AddProjectResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(AddProjectResult addProjectResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(addProjectResult.getMsg());
                        if (addProjectResult.getCode() == 1) {
                            closeAct();
                        }
                    }
                });
    }

    private void chooseProperty() {
        show = true;
        showListDialog(Arrays.asList("公有", "私有"), false, new DialogInterface.OnDismissListener() {
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
