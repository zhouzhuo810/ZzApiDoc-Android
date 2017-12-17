package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddActionResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.common.rx.RxHelper;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * Created by zhouzhuo810 on 2017/11/13.
 */
public class AddActionActivity extends BaseActivity {

    public static final int TYPE_DIALOG_PROGRESS = 0;
    public static final int TYPE_DIALOG_TWO_BTN = 1;
    public static final int TYPE_DIALOG_EDIT = 2;
    public static final int TYPE_DIALOG_UPDATE = 3;
    public static final int TYPE_DIALOG_LIST = 4;
    public static final int TYPE_DIALOG_TWO_BTN_IOS = 5;
    public static final int TYPE_CHOOSE_PIC = 6;
    public static final int TYPE_USE_API = 7;
    public static final int TYPE_TARGET_ACT = 8;
    public static final int TYPE_CLOSE_ACT = 9;
    public static final int TYPE_CLOSE_ALL_ACT = 10;

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llActionType;
    private TextView tvActionType;
    private LinearLayout llTargetApi;
    private TextView tvTargetApi;
    private LinearLayout llTargetAct;
    private TextView tvTargetAct;
    private LinearLayout llTitleText;
    private EditText etTitle;
    private ImageView ivClearTitle;
    private LinearLayout llMsgText;
    private EditText etMsg;
    private ImageView ivClearMsg;
    private LinearLayout llOkText;
    private EditText etOkText;
    private ImageView ivClearOkText;
    private LinearLayout llCancelText;
    private EditText etCancelText;
    private ImageView ivClearCancelText;
    private LinearLayout llHintText;
    private EditText etHintText;
    private ImageView ivClearHintText;
    private LinearLayout llDefValue;
    private EditText etDefValueText;
    private ImageView ivClearDefValueText;
    private LinearLayout llItems;
    private EditText etItems;
    private ImageView ivClearItems;
    private LinearLayout llShowOrHide;
    private CheckBox cbShowOrHide;
    private Button btnSubmit;
    private String appId;
    private String projectId;
    private int groupPosition;

    private void assignViews() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llActionType = (LinearLayout) findViewById(R.id.ll_action_type);
        tvActionType = (TextView) findViewById(R.id.tv_action_type);
        llTargetApi = (LinearLayout) findViewById(R.id.ll_target_api);
        tvTargetApi = (TextView) findViewById(R.id.tv_target_api);
        llTargetAct = (LinearLayout) findViewById(R.id.ll_target_act);
        tvTargetAct = (TextView) findViewById(R.id.tv_target_act);
        llTitleText = (LinearLayout) findViewById(R.id.ll_title_text);
        etTitle = (EditText) findViewById(R.id.et_title);
        ivClearTitle = (ImageView) findViewById(R.id.iv_clear_title);
        llMsgText = (LinearLayout) findViewById(R.id.ll_msg_text);
        etMsg = (EditText) findViewById(R.id.et_msg);
        ivClearMsg = (ImageView) findViewById(R.id.iv_clear_msg);
        llOkText = (LinearLayout) findViewById(R.id.ll_ok_text);
        etOkText = (EditText) findViewById(R.id.et_ok_text);
        ivClearOkText = (ImageView) findViewById(R.id.iv_clear_ok_text);
        llCancelText = (LinearLayout) findViewById(R.id.ll_cancel_text);
        etCancelText = (EditText) findViewById(R.id.et_cancel_text);
        ivClearCancelText = (ImageView) findViewById(R.id.iv_clear_cancel_text);
        llHintText = (LinearLayout) findViewById(R.id.ll_hint_text);
        etHintText = (EditText) findViewById(R.id.et_hint_text);
        ivClearHintText = (ImageView) findViewById(R.id.iv_clear_hint_text);
        llDefValue = (LinearLayout) findViewById(R.id.ll_def_value);
        etDefValueText = (EditText) findViewById(R.id.et_def_value_text);
        ivClearDefValueText = (ImageView) findViewById(R.id.iv_clear_def_value_text);
        llItems = (LinearLayout) findViewById(R.id.ll_items);
        etItems = (EditText) findViewById(R.id.et_items);
        ivClearItems = (ImageView) findViewById(R.id.iv_clear_items);
        llShowOrHide = (LinearLayout) findViewById(R.id.ll_show_or_hide);
        cbShowOrHide = (CheckBox) findViewById(R.id.cb_show_or_hide);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    private int type;
    private String widgetId;
    private String okApiId;
    private String okActId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_action;
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
        widgetId = getIntent().getStringExtra("widgetId");
        appId = getIntent().getStringExtra("appId");
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

        setEditListener(etCancelText, ivClearCancelText);
        setEditListener(etDefValueText, ivClearDefValueText);
        setEditListener(etHintText, ivClearHintText);
        setEditListener(etMsg, ivClearMsg);
        setEditListener(etOkText, ivClearOkText);
        setEditListener(etTitle, ivClearTitle);

        llActionType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseActionType();
            }
        });

        llTargetAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTargetAct();
            }
        });

        llTargetApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTargetApi();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAction();
            }
        });

    }


    private void chooseTargetApi() {
        Intent intent = new Intent(this, InterfaceGroupManageActivity.class);
        intent.putExtra("choose", true);
        intent.putExtra("projectId", projectId);
        startActForResultWithIntent(intent, 0x01);
    }

    private void chooseActionType() {
        List<String> items = new ArrayList<>();
        items.add("progress dialog");
        items.add("two btn dialog");
        items.add("edit dialog");
        items.add("update dialog");
        items.add("list dialog");
        items.add("two btn ios dialog");
        items.add("choose image");
        items.add("use api");
        items.add("open activity");
        items.add("close activity");
        items.add("close all activity");
        showListDialog(items, false, null, new IBaseActivity.OnItemClick() {
            @Override
            public void onItemClick(int i, String s) {
                type = i;
                tvActionType.setText(s);
            }
        });

    }

    private void chooseTargetAct() {
        Intent intent = new Intent(this, ActivityManageActivity.class);
        intent.putExtra("choose", true);
        intent.putExtra("appId", appId);
        startActForResultWithIntent(intent, 0x02);
    }

    private void addAction() {
        String title = etTitle.getText().toString().trim();
        String msg = etMsg.getText().toString().trim();
        String okText = etOkText.getText().toString().trim();
        String cancelText = etCancelText.getText().toString().trim();
        String defText = etDefValueText.getText().toString().trim();
        String hintText = etHintText.getText().toString().trim();
        boolean isHide = cbShowOrHide.isChecked();
        String name = tvActionType.getText().toString().trim();
        String items = etItems.getText().toString().trim();
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .addAction(getUserId(), type, name, widgetId,
                        title, msg, okText, cancelText, hintText, defText, isHide, items, okApiId, groupPosition, okActId)
                .compose(RxHelper.<AddActionResult>io_main())
                .subscribe(new Subscriber<AddActionResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(AddActionResult addActionResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(addActionResult.getMsg());
                        if (addActionResult.getCode() == 1) {
                            closeAct();
                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x01:
                    okApiId = data.getStringExtra("id");
                    String name = data.getStringExtra("name");
                    groupPosition = data.getIntExtra("groupPosition", 0);
                    tvTargetApi.setText(name);
                    break;
                case 0x02:
                    okActId = data.getStringExtra("id");
                    String actName = data.getStringExtra("name");
                    tvTargetAct.setText(actName);
                    break;
            }
        }
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
