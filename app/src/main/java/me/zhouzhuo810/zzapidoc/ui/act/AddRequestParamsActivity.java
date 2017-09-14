package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.DialogInterface;
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

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class AddRequestParamsActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llArgType;
    private TextView tvArgType;
    private LinearLayout llRequire;
    private TextView tvRequire;
    private CheckBox cbRequire;
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
    private boolean global = false;


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_request_params;
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
        llRequire = (LinearLayout) findViewById(R.id.ll_require);
        tvRequire = (TextView) findViewById(R.id.tv_require);
        cbRequire = (CheckBox) findViewById(R.id.cb_require);
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

        tvRequire.setText("true");
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

        btnKeyWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateKeyword();
            }
        });


    }


    private void generateKeyword() {
        if (etArgNote.getText().toString().trim().length()==0) {
            ToastUtils.showCustomBgToast("请填写参数说明");
            return;
        }
        String title = etArgNote.getText().toString().trim();
        showPd(getString(R.string.submiting_text), false);
        OkGo.<String>get("http://fanyi.youdao.com/openapi.do")
                .params("keyfrom", "WordsHelper")
                .params("key", "1678465943")
                .params("type", "data")
                .params("doctype", "json")
                .params("version", "1.1")
                .params("q", title)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        hidePd();
                        String body = response.body();
                        try {
                            JSONObject object = new JSONObject(body);
                            JSONArray array = object.getJSONArray("translation");
                            String word = array.getString(0);
                            StringBuilder sb = new StringBuilder();
                            if (word.contains(" ")) {
                                String[] split = word.split(" ");
                                for (String s : split) {
                                    sb.append(s.substring(0,1).toUpperCase()).append(s.substring(1));
                                }
                            } else {
                                sb.append(word.substring(0,1).toUpperCase()).append(word.substring(1));
                            }
                            String newWord = sb.toString().replace("the", "").replace("The", "").replace(".","");
                            etArgName.setText(newWord.substring(0,1).toLowerCase()+newWord.substring(1));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.showCustomBgToast(e.toString());
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        hidePd();
                        ToastUtils.showCustomBgToast(response.getException().toString());
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
        String defValue = etArgDefValue.getText().toString().trim();
        String note = etArgNote.getText().toString().trim();
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .addRequestArg(pid, name, defValue, typeId, projectId, interfaceId, note, getUserId(), cbRequire.isChecked(), global)
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
