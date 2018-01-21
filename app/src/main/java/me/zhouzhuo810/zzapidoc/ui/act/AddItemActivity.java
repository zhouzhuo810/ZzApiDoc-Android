package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.TranslateErrorCode;
import com.youdao.sdk.ydonlinetranslate.TranslateListener;
import com.youdao.sdk.ydonlinetranslate.TranslateParameters;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;

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

        btnKeyWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translate();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

    }


    private void translate() {
        String title = etTitle.getText().toString().trim();
        if (title.length() == 0) {
            ToastUtils.showCustomBgToast("请填写参数说明");
            return;
        }
        showPd(getString(R.string.submiting_text), false);
        Language langFrom = LanguageUtils.getLangByName("中文");
        Language langTo = LanguageUtils.getLangByName("英文");
        TranslateParameters tps = new TranslateParameters.Builder()
                .source("ZzApiDoc")
                .from(langFrom).to(langTo).build();

        Translator translator = Translator.getInstance(tps);
        translator.lookup(title, new TranslateListener() {
            @Override
            public void onResult(Translate result, String input) {//查询成功
                if (result.getTranslations() != null) {
                    String word = result.getTranslations().get(0);
                    String newWord = word.replace(" ", "_").replace("the_", "").replace("The_", "").replace(".", "").toLowerCase();
                    switch (type) {
                        case TYPE_RV_ITEM:
                            tvItemName.setText("rv_item_"+newWord);
                            break;
                        case TYPE_LV_ITEM:
                            tvItemName.setText("lv_item_"+newWord);
                            break;
                        case TYPE_HEADER:
                            tvItemName.setText("header_item_"+newWord);
                            break;
                        case TYPE_FOOTER:
                            tvItemName.setText("footer_item_"+newWord);
                            break;
                    }
                }
                hidePd();
            }

            @Override
            public void onError(TranslateErrorCode error) {//查询失败
                hidePd();
                ToastUtils.showCustomBgToast("错误代码：" + error.getCode());
            }
        });
    }

    private void addItem() {
        String title = etTitle.getText().toString().trim();
        String name = tvItemName.getText().toString().trim();
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .addItem(type, title, name, widgetId, getUserId())
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
