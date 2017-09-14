package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.ZApplication;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.JsonCallback;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddActivityResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.utils.ContentUtils;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * Created by admin on 2017/8/19.
 */
public class AddActivityActivity extends BaseActivity {

    public static final int REQUEST_SELECT_PICTURE = 0x80;

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llActType;
    private TextView tvActType;
    private LinearLayout llSplash;
    private ImageView ivSplash;
    private LinearLayout llSplashDuration;
    private EditText etSplashDuration;
    private ImageView ivClearSplashDuration;
    private LinearLayout llTargetAct;
    private TextView tvTargetAct;
    private EditText etActTitle;
    private ImageView ivClearActTitle;
    private TextView tvActName;
    private Button btnKeyWord;
//    private LinearLayout llShowTitle;
//    private CheckBox cbShowTitle;
    private LinearLayout llIsFirst;
    private CheckBox cbIsFirst;
    private Button btnSubmit;


    private int actType;
    private String splashPath;
    private String appId;
    private String targetActId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_activity;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llActType = (LinearLayout) findViewById(R.id.ll_act_type);
        tvActType = (TextView) findViewById(R.id.tv_act_type);
        llSplash = (LinearLayout) findViewById(R.id.ll_splash);
        ivSplash = (ImageView) findViewById(R.id.iv_splash);
        llSplashDuration = (LinearLayout) findViewById(R.id.ll_splash_duration);
        etSplashDuration = (EditText) findViewById(R.id.et_splash_duration);
        ivClearSplashDuration = (ImageView) findViewById(R.id.iv_clear_splash_duration);
        llTargetAct = (LinearLayout) findViewById(R.id.ll_target_act);
        tvTargetAct = (TextView) findViewById(R.id.tv_target_act);
        etActTitle = (EditText) findViewById(R.id.et_act_title);
        ivClearActTitle = (ImageView) findViewById(R.id.iv_clear_act_title);
        tvActName = (TextView) findViewById(R.id.tv_act_name);
        btnKeyWord = (Button) findViewById(R.id.btn_key_word);
//        llShowTitle = (LinearLayout) findViewById(R.id.ll_show_title);
//        cbShowTitle = (CheckBox) findViewById(R.id.cb_show_title);
        llIsFirst = (LinearLayout) findViewById(R.id.ll_is_first);
        cbIsFirst = (CheckBox) findViewById(R.id.cb_is_first);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void initData() {
        appId = getIntent().getStringExtra("appId");
//        cbShowTitle.setChecked(true);
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
        setEditListener(etSplashDuration, ivClearSplashDuration);

        llTargetAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTargetAct();
            }
        });

        btnKeyWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateKeyword();
            }
        });

        llActType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseActType();
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


    private void generateKeyword() {
        String title = etActTitle.getText().toString().trim();
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
                            StringBuilder sb = new StringBuilder();
                            String words = array.getString(0).replace("the ", "").replace("The ", "");
                            String child[] = words.split(" ");
                            for (String s : child) {
                                sb.append(s.substring(0,1).toUpperCase()).append(s.substring(1, s.length()));
                            }
                            sb.append("Activity");
                            tvActName.setText(sb.toString());
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


    private void chooseTargetAct() {
        Intent intent = new Intent(this, ActivityManageActivity.class);
        intent.putExtra("choose", true);
        intent.putExtra("appId", appId);
        startActForResultWithIntent(intent, 0x01);
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
                    targetActId = data.getStringExtra("id");
                    String name = data.getStringExtra("name");
                    tvTargetAct.setText(name);
                    break;
                case REQUEST_SELECT_PICTURE:
                    final Uri selectedUri = data.getData();
                    if (selectedUri != null) {
                        ivSplash.setImageURI(selectedUri);
                        splashPath = ContentUtils.getRealPathFromUri(AddActivityActivity.this, selectedUri);
                    } else {
                        ToastUtils.showCustomBgToast("选择图片出错了，请选择其他图片");
                    }
                    break;
            }
        }
    }


    private void addAct() {
        String name = tvActName.getText().toString().trim();
        String title = etActTitle.getText().toString().trim();
        String duration = etSplashDuration.getText().toString().trim();
        if (duration.length()==0 && actType == 5) {
            ToastUtils.showCustomBgToast("启动时间不能为空");
            return;
        }
        if (name.length() == 0) {
            ToastUtils.showCustomBgToast("Activity名称不能为空");
            return;
        }
        Log.e("TTT", "splashPath=" + splashPath);
        showPd(getString(R.string.submiting_text), false);
        PostRequest<AddActivityResult> post = OkGo.<AddActivityResult>post(SharedUtil.getString(ZApplication.getInstance(), "server_config")
                + "/ZzApiDoc/v1/activity/addActivity")
                .params("name", name)
                .params("title", title)
                .params("showTitle", true)
                .params("isFirst", cbIsFirst.isChecked())
                .params("type", actType)
                .params("appId", appId)
                .params("targetActId", targetActId)
                .params("splashSecond", Integer.parseInt(duration))
                .params("userId", getUserId())
                .isMultipart(true);
        if (splashPath != null) {
            post.params("splashImg", new File(splashPath));
        }
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


    private void chooseActType() {
        List<String> items = new ArrayList<>();
        items.add("Empty Activity");
        items.add("Splash Activity");
        items.add("Guide Activity");
        items.add("Bottom Fragment");
        items.add("Top Fragment");
        showListDialog(items, true, null, new IBaseActivity.OnItemClick() {
            @Override
            public void onItemClick(int position, String content) {
                if (position == 1) {
                    llSplash.setVisibility(View.VISIBLE);
                    llSplashDuration.setVisibility(View.VISIBLE);
                    llTargetAct.setVisibility(View.VISIBLE);
                    tvActName.setText("SplashActivity");
                } else {
                    llSplash.setVisibility(View.GONE);
                    llSplashDuration.setVisibility(View.GONE);
                    llTargetAct.setVisibility(View.GONE);
                    tvActName.setText("");
                }
                actType = position;
                tvActType.setText(content);
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
