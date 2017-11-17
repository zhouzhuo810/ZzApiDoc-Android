package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.DialogInterface;
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
import android.widget.TextView;

import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.TranslateErrorCode;
import com.youdao.sdk.ydonlinetranslate.TranslateListener;
import com.youdao.sdk.ydonlinetranslate.TranslateParameters;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;

import java.util.Arrays;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddResponseArgResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */

public class AddResponseParamsActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llArgType;
    private TextView tvArgType;
    private EditText etArgNote;
    private ImageView ivClearArgNote;
    private EditText etArgName;
    private ImageView ivClearArgName;
    private Button btnKeyWord;
    private EditText etArgDefValue;
    private ImageView ivClearArgDefValue;
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
        etArgNote = (EditText) findViewById(R.id.et_arg_note);
        ivClearArgNote = (ImageView) findViewById(R.id.iv_clear_arg_note);
        etArgName = (EditText) findViewById(R.id.et_arg_name);
        ivClearArgName = (ImageView) findViewById(R.id.iv_clear_arg_name);
        btnKeyWord = (Button) findViewById(R.id.btn_key_word);
        etArgDefValue = (EditText) findViewById(R.id.et_arg_def_value);
        ivClearArgDefValue = (ImageView) findViewById(R.id.iv_clear_arg_def_value);
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


        rlRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddResponseParamsActivity.this, ImportResponseParamsActivity.class);
                intent.putExtra("interfaceId", interfaceId);
                intent.putExtra("pid", pid);
                startActForResultWithIntent(intent, 0x01);
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

        btnKeyWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translate();
            }
        });


    }

    private void translate() {
        String title = etArgNote.getText().toString().trim();
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
                    Log.e("XXX", "trans=" + result.getTranslations().toString());
                    String word = result.getTranslations().get(0);
                    StringBuilder sb = new StringBuilder();
                    if (word.contains(" ")) {
                        String[] split = word.split(" ");
                        for (String s : split) {
                            sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
                        }
                    } else {
                        sb.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
                    }
                    String newWord = sb.toString()
                            .replace("the", "")
                            .replace("The", "")
                            .replace(".", "")
                            .replace("Equipment", "Device")
                            .replace("machine", "mac")
                            .replace("Machine", "Mac")
                            .replace("Number", "Num")
                            .replace("Maintenance", "Fix")
                            .replace("equipment", "device")
                            .replace("number", "num")
                            .replace("maintenance", "fix")
                            .replace("information", "info")
                            .replace("Information", "Info");
                    String text = newWord.substring(0, 1).toLowerCase() + newWord.substring(1);
                    etArgName.setText(text);

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


    private void chooseType() {
        show = true;
        showListDialog(Arrays.asList("string", "number", "object", "array[object]", "array[string]", "array", "file", "unknown", "array[number]"), true, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                show = false;
            }
        }, new IBaseActivity.OnItemClick() {
            @Override
            public void onItemClick(int i, String s) {
                show = false;
                typeId = i;
                tvArgType.setText(s);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x01:
                    closeAct();
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
