package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Arrays;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddProjectResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */

public class AddInterfaceGroupActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private EditText etInterfaceGroupName;
    private ImageView ivClearInterfaceGroupName;
    private Button btnSubmit;
    private String projectId;


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_interface_group;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        etInterfaceGroupName = (EditText) findViewById(R.id.et_interface_group_name);
        ivClearInterfaceGroupName = (ImageView) findViewById(R.id.iv_clear_interface_group_name);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void initData() {
        projectId = getIntent().getStringExtra("projectId");
    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });

        setEditListener(etInterfaceGroupName, ivClearInterfaceGroupName);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGroup();
            }
        });


    }

    private void addGroup() {
        String name = etInterfaceGroupName.getText().toString().trim();
        if (name.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.group_name_not_nul_text));
            return;
        }
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .addInterfaceGroup(name, projectId, getUserId())
                .compose(RxHelper.<AddInterfaceGroupResult>io_main())
                .subscribe(new Subscriber<AddInterfaceGroupResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(AddInterfaceGroupResult addProjectResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(addProjectResult.getMsg());
                        if (addProjectResult.getCode() == 1) {
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
