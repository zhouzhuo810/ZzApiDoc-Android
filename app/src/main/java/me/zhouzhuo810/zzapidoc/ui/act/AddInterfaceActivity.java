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

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.TranslateErrorCode;
import com.youdao.sdk.ydonlinetranslate.TranslateListener;
import com.youdao.sdk.ydonlinetranslate.TranslateParameters;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetDictionaryResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */

public class AddInterfaceActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llRequestMethod;
    private TextView tvRequestMethod;
    private EditText etInterfaceName;
    private ImageView ivClearInterfaceName;
    private EditText etPath;
    private ImageView ivClearPath;
    private Button btnKeyWord;
    private EditText etVersion;
    private ImageView ivClearVersion;
    private EditText etPs;
    private ImageView ivClearPs;
    private Button btnSubmit;

    private String projectId;
    private String groupId;
    private boolean show;
    private String methodId;


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_interface;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llRequestMethod = (LinearLayout) findViewById(R.id.ll_request_method);
        tvRequestMethod = (TextView) findViewById(R.id.tv_request_method);
        etInterfaceName = (EditText) findViewById(R.id.et_interface_name);
        ivClearInterfaceName = (ImageView) findViewById(R.id.iv_clear_interface_name);
        etPath = (EditText) findViewById(R.id.et_path);
        ivClearPath = (ImageView) findViewById(R.id.iv_clear_path);
        btnKeyWord = (Button) findViewById(R.id.btn_key_word);
        etVersion = (EditText) findViewById(R.id.et_version);
        ivClearVersion = (ImageView) findViewById(R.id.iv_clear_version);
        etPs = (EditText) findViewById(R.id.et_ps);
        ivClearPs = (ImageView) findViewById(R.id.iv_clear_ps);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void initData() {
        projectId = getIntent().getStringExtra("projectId");
        groupId = getIntent().getStringExtra("groupId");

        chooseMethodAuto();
    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });

        llRequestMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!show) {
                    chooseMethod();
                }
            }
        });

        setEditListener(etInterfaceName, ivClearInterfaceName);
        setEditListener(etPath, ivClearPath);
        setEditListener(etPs, ivClearPs);

        btnKeyWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translate();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInterface();
            }
        });


    }

    private void translate() {
        String title = etInterfaceName.getText().toString().trim();
        if (title.length()==0) {
            ToastUtils.showCustomBgToast("请填写接口名称");
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
                    Log.e("XXX", "trans="+result.getTranslations().toString());
                    String word = result.getTranslations().get(0);
                    StringBuilder sb = new StringBuilder();
                    if (word.contains(" ")) {
                        String[] split = word.split(" ");
                        for (String s : split) {
                            sb.append(s.substring(0,1).toUpperCase()).append(s.substring(1));
                        }
                    } else {
                        sb.append(word.substring(0,1).toUpperCase()).append(word.substring(1));
                    }
                    String newWord = sb.toString().replace("the", "").replace("The", "").replace(".","")
                            .replace("Equipment", "Device")
                            .replace("machine", "mac")
                            .replace("Machine", "Mac")
                            .replace("Number", "Num")
                            .replace("Maintenance", "Fix")
                            .replace("equipment", "device")
                            .replace("number", "num")
                            .replace("maintenance", "fix")
                            .replace("information", "info")
                            .replace("Information", "Info");;
                    String method = tvRequestMethod.getText().toString().trim();
                    String version = etVersion.getText().toString().trim();
                    etPath.setText("/v"+version+"/"+method+"/"+newWord.substring(0,1).toLowerCase()+newWord.substring(1));
                }
                hidePd();
            }

            @Override
            public void onError(TranslateErrorCode error) {//查询失败
                hidePd();
                ToastUtils.showCustomBgToast("错误代码："+error.getCode());
            }
        });
    }


    private void chooseMethod() {
        show = true;
        showPd(getString(R.string.loading_text), false);
        Api.getApi0()
                .getDictionary("method")
                .compose(RxHelper.<GetDictionaryResult>io_main())
                .subscribe(new Subscriber<GetDictionaryResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        show = false;
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                        hidePd();
                    }

                    @Override
                    public void onNext(final GetDictionaryResult getDictionaryResult) {
                        hidePd();
                        if (getDictionaryResult.getCode() == 1) {
                            List<String> strings = dicToList(getDictionaryResult.getData());
                            showListDialog(strings, true, new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    show = false;
                                }
                            }, new IBaseActivity.OnItemClick() {
                                @Override
                                public void onItemClick(int i, String s) {
                                    show = false;
                                    methodId = getDictionaryResult.getData().get(i).getId();
                                    tvRequestMethod.setText(s);
                                }
                            });
                        } else {
                            ToastUtils.showCustomBgToast(getDictionaryResult.getMsg());
                        }
                    }
                });
    }

    private void chooseMethodAuto() {
        Api.getApi0()
                .getDictionary("method")
                .compose(RxHelper.<GetDictionaryResult>io_main())
                .subscribe(new Subscriber<GetDictionaryResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(final GetDictionaryResult getDictionaryResult) {
                        if (getDictionaryResult.getCode() == 1) {
                            if (getDictionaryResult.getData() != null && getDictionaryResult.getData().size() > 0) {
                                methodId = getDictionaryResult.getData().get(0).getId();
                                tvRequestMethod.setText(getDictionaryResult.getData().get(0).getName());
                            }
                        } else {
                            ToastUtils.showCustomBgToast(getDictionaryResult.getMsg());
                        }
                    }
                });
    }

    private void addInterface() {
        String name = etInterfaceName.getText().toString().trim();
        if (name.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.interface_name_not_nul_text));
            return;
        }
        String path = etPath.getText().toString().trim();
        if (path.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.path_not_nul_text));
            return;
        }
        String note = etPs.getText().toString().trim();
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .addInterface(name, groupId, methodId, getUserId(),note, projectId, path, null, null)
                .compose(RxHelper.<AddInterfaceResult>io_main())
                .subscribe(new Subscriber<AddInterfaceResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(AddInterfaceResult addProjectResult) {
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
