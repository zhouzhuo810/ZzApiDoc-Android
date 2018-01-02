package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddActionResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddItemResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.common.rx.RxHelper;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * Created by zhouzhuo810 on 2017/11/13.
 */
public class AddItemActivity extends BaseActivity {

    public static final int TYPE_RV_ITEM = 0;
    public static final int TYPE_LV_ITEM = 1;
    public static final int TYPE_HEADER = 2;
    public static final int TYPE_FOOTER = 3;


    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llTitleText;
    private EditText etTitle;
    private ImageView ivClearTitle;
    private LinearLayout llItemName;
    private EditText tvItemName;
    private Button btnKeyWord;
    private Button btnSubmit;
    private String widgetId;
    private String widgetPid;
    private int type;

    private void assignViews() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llTitleText = (LinearLayout) findViewById(R.id.ll_title_text);
        etTitle = (EditText) findViewById(R.id.et_title);
        ivClearTitle = (ImageView) findViewById(R.id.iv_clear_title);
        llItemName = (LinearLayout) findViewById(R.id.ll_item_name);
        tvItemName = (EditText) findViewById(R.id.tv_item_name);
        btnKeyWord = (Button) findViewById(R.id.btn_key_word);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_item;
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
        widgetPid = getIntent().getStringExtra("widgetPid");
        type = getIntent().getIntExtra("type", TYPE_RV_ITEM);
    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });

        setEditListener(etTitle, ivClearTitle);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

    }

    private void addItem() {
        String title = etTitle.getText().toString().trim();
        String name = tvItemName.getText().toString().trim();
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .addItem(type, title, name, widgetId, widgetPid, getUserId())
                .compose(RxHelper.<AddItemResult>io_main())
                .subscribe(new Subscriber<AddItemResult>() {
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
                    public void onNext(AddItemResult addActionResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(addActionResult.getMsg());
                        if (addActionResult.getCode() == 1) {
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
