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
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private LinearLayout llShowTitle;
    private CheckBox cbShowTitle;
    private Button btnSubmit;

    private int actType;
    private String splashPath;
    private String appId;
    private String activityId;
    private int count;

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
        llShowTitle = (LinearLayout) findViewById(R.id.ll_show_title);
        cbShowTitle = (CheckBox) findViewById(R.id.cb_show_title);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void initData() {
        activityId = getIntent().getStringExtra("activityId");
        appId = getIntent().getStringExtra("appId");
        cbShowTitle.setChecked(true);
        count = getIntent().getIntExtra("count", 0);
        etFgmPosition.setText(""+count);
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
                generateKeyword();
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
                            sb.append("Fragment");
                            tvFgmName.setText(sb.toString());
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
        if (position.length()==0 && actType == 1) {
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
                .params("name", name)
                .params("title", title)
                .params("showTitle", cbShowTitle.isChecked())
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
