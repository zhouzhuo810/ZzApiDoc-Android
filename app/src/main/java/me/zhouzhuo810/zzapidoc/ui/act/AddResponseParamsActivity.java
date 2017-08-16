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
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddResponseArgResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetDictionaryResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */

public class AddResponseParamsActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llArgType;
    private TextView tvArgType;
    private EditText etArgName;
    private ImageView ivClearArgName;
    private EditText etArgDefValue;
    private ImageView ivClearArgDefValue;
    private EditText etArgNote;
    private ImageView ivClearArgNote;
    private Button btnSubmit;

    private String projectId;
    private String groupId;
    private boolean show;
    private int typeId = 0;
    private String pid;
    private String interfaceId;
    private boolean global;


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_response_params;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llArgType = (LinearLayout) findViewById(R.id.ll_arg_type);
        tvArgType = (TextView) findViewById(R.id.tv_arg_type);
        etArgName = (EditText) findViewById(R.id.et_arg_name);
        ivClearArgName = (ImageView) findViewById(R.id.iv_clear_arg_name);
        etArgDefValue = (EditText) findViewById(R.id.et_arg_def_value);
        ivClearArgDefValue = (ImageView) findViewById(R.id.iv_clear_arg_def_value);
        etArgNote = (EditText) findViewById(R.id.et_arg_note);
        ivClearArgNote = (ImageView) findViewById(R.id.iv_clear_arg_note);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void initData() {
        projectId = getIntent().getStringExtra("projectId");
        groupId = getIntent().getStringExtra("groupId");
        interfaceId = getIntent().getStringExtra("interfaceId");
        pid = getIntent().getStringExtra("pid");
        global = getIntent().getBooleanExtra("global", false);

        typeId = 0;
        tvArgType.setText("string");
    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });

        llArgType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!show) {
                    chooseType();
                }
            }
        });

        setEditListener(etArgName, ivClearArgName);
        setEditListener(etArgNote, ivClearArgNote);
        setEditListener(etArgDefValue, ivClearArgDefValue);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInterface();
            }
        });


    }

    private void chooseType() {
        show = true;
        showListDialog(Arrays.asList("string","number","object","array[object]","array[string]"), true, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                show = false;
            }
        }, new OnItemClick() {
            @Override
            public void onItemClick(int position, String content) {
                show = false;
                typeId = position;
                tvArgType.setText(content);
            }
        });
    }

    private void addInterface() {
        String name = etArgName.getText().toString().trim();
        if (name.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.interface_name_not_nul_text));
            return;
        }
        if (pid == null) {
            pid = "0";
        }
        String note = etArgNote.getText().toString().trim();
        showPd(getString(R.string.submiting_text), false);
        String defValue = etArgDefValue.getText().toString().trim();
        Api.getApi0()
                .addResponseArg(pid, name, defValue, typeId, projectId, interfaceId, note, getUserId(), global)
                .compose(RxHelper.<AddResponseArgResult>io_main())
                .subscribe(new Subscriber<AddResponseArgResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(AddResponseArgResult addProjectResult) {
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
