package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.TranslateErrorCode;
import com.youdao.sdk.ydonlinetranslate.TranslateListener;
import com.youdao.sdk.ydonlinetranslate.TranslateParameters;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.ZApplication;
import me.zhouzhuo810.zzapidoc.common.api.JsonCallback;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddActivityResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.utils.ContentUtils;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;

/**
 * Created by admin on 2017/8/19.
 */
public class AddFragmentActivity extends BaseActivity {

    public static final int REQUEST_SELECT_PICTURE = 0x80;

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llSplash;
    private ImageView ivSplash;
    private EditText etFgmPosition;
    private ImageView ivClearFgmPosition;
    private EditText etActTitle;
    private ImageView ivClearActTitle;
    private TextView tvFgmName;
    private Button btnKeyWord;
    private Button btnSubmit;

    private int actType;
    private String splashPath;
    private String appId;
    private String activityId;
    private int count;
    private String pid;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_fragment;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llSplash = (LinearLayout) findViewById(R.id.ll_splash);
        ivSplash = (ImageView) findViewById(R.id.iv_splash);
        etFgmPosition = (EditText) findViewById(R.id.et_fgm_position);
        ivClearFgmPosition = (ImageView) findViewById(R.id.iv_clear_fgm_position);
        etActTitle = (EditText) findViewById(R.id.et_act_title);
        ivClearActTitle = (ImageView) findViewById(R.id.iv_clear_act_title);
        tvFgmName = (TextView) findViewById(R.id.tv_fgm_name);
        btnKeyWord = (Button) findViewById(R.id.btn_key_word);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void initData() {
        activityId = getIntent().getStringExtra("activityId");
        appId = getIntent().getStringExtra("appId");
        pid = getIntent().getStringExtra("pid");
        count = getIntent().getIntExtra("count", 0);
        etFgmPosition.setText("" + count);
    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });
        setEditListener(etActTitle, ivClearActTitle);
        setEditListener(etFgmPosition, ivClearFgmPosition);

        btnKeyWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translate();
            }
        });

        ivSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAct();
            }
        });
    }


    private void translate() {
        String title = etActTitle.getText().toString().trim();
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
                    sb.append("Fragment");
                    String newWord = sb.toString().replace("the", "").replace("The", "").replace(".", "");

                    tvFgmName.setText(newWord);
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

    private void choosePic() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x01:
                    activityId = data.getStringExtra("id");
                    String name = data.getStringExtra("name");
                    tvFgmName.setText(name);
                    break;
                case REQUEST_SELECT_PICTURE:
                    final Uri selectedUri = data.getData();
                    if (selectedUri != null) {
                        ivSplash.setImageURI(selectedUri);
                        splashPath = ContentUtils.getRealPathFromUri(AddFragmentActivity.this, selectedUri);
                    } else {
                        ToastUtils.showCustomBgToast("选择图片出错了，请选择其他图片");
                    }
                    break;
            }
        }
    }


    private void addAct() {
        String name = tvFgmName.getText().toString().trim();
        String title = etActTitle.getText().toString().trim();
        String position = etFgmPosition.getText().toString().trim();
        if (position.length() == 0 && actType == 1) {
            ToastUtils.showCustomBgToast("序号不能为空");
            return;
        }
        if (name.length() == 0) {
            ToastUtils.showCustomBgToast("Fragment名称不能为空");
            return;
        }
        showPd(getString(R.string.submiting_text), false);
        PostRequest<AddActivityResult> post = OkGo.<AddActivityResult>post(SharedUtil.getString(ZApplication.getInstance(), "server_config")
                + "/ZzApiDoc/v1/fragment/addFragment")
                .params("pid", pid == null ? "0" : pid)
                .params("name", name)
                .params("title", title)
                .params("type", actType)
                .params("appId", appId)
                .params("activityId", activityId)
                .params("position", Integer.parseInt(position))
                .params("userId", getUserId())
                .isMultipart(true);
        post.execute(new JsonCallback<AddActivityResult>(AddActivityResult.class) {
            @Override
            public void onSuccess(Response<AddActivityResult> response) {
                hidePd();
                AddActivityResult body = response.body();
                ToastUtils.showCustomBgToast(body.getMsg());
                if (body.getCode() == 1) {
                    closeAct();
                }
            }

            @Override
            public void onError(Response<AddActivityResult> response) {
                super.onError(response);
                hidePd();
                Throwable exception = response.getException();
                ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + (exception == null ? "" : exception.toString()));

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
