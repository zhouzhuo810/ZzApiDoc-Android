package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
import me.zhouzhuo810.zzapidoc.common.api.JsonCallback;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddActivityResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.utils.ContentUtils;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;

/**
 * Created by admin on 2017/8/19.
 */
public class AddWidgetActivity extends BaseActivity {

    public static final int REQUEST_SELECT_PICTURE = 0x80;

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llWidgetType;
    private TextView tvWidgetType;
    private LinearLayout llTargetAct;
    private TextView tvTargetAct;
    private TextView tvWidgetName;
    private EditText etHint;
    private ImageView ivClearHint;
    private LinearLayout llTitle;
    private EditText etTitle;
    private ImageView ivClearTitle;
    private LinearLayout llKeyWord;
    private TextView tvKeyWord;
    private Button btnKeyWord;
    private LinearLayout llDefValue;
    private EditText etDefValue;
    private ImageView ivClearDefValue;
    private LinearLayout llLeftText;
    private EditText etLeftTitle;
    private ImageView ivClearLeftTitle;
    private LinearLayout llRightText;
    private EditText etRightText;
    private ImageView ivClearRightText;
    private LinearLayout llRightImg;
    private ImageView ivRightImg;
    private TextView tvRightImg;
    private LinearLayout llShowLeftImg;
    private TextView tvShowLeftImg;
    private LinearLayout llShowRightImg;
    private TextView tvShowRightImg;
    private LinearLayout llShowLeftText;
    private TextView tvShowLeftText;
    private LinearLayout llShowRightText;
    private TextView tvShowRightText;
    private LinearLayout llLeftLayout;
    private TextView tvLeftLayout;
    private LinearLayout llShowRightLayout;
    private TextView tvShowRightLayout;
    private Button btnSubmit;


    private int widgetType;
    private String splashPath;
    private String appId;
    private String targetActId;
    private String relativeId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_widget;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llWidgetType = (LinearLayout) findViewById(R.id.ll_widget_type);
        tvWidgetType = (TextView) findViewById(R.id.tv_widget_type);
        llTargetAct = (LinearLayout) findViewById(R.id.ll_target_act);
        tvTargetAct = (TextView) findViewById(R.id.tv_target_act);
        tvWidgetName = (TextView) findViewById(R.id.tv_widget_name);
        etHint = (EditText) findViewById(R.id.et_hint);
        ivClearHint = (ImageView) findViewById(R.id.iv_clear_hint);
        llTitle = (LinearLayout) findViewById(R.id.ll_title);
        etTitle = (EditText) findViewById(R.id.et_title);
        ivClearTitle = (ImageView) findViewById(R.id.iv_clear_title);
        llKeyWord = (LinearLayout) findViewById(R.id.ll_key_word);
        tvKeyWord = (TextView) findViewById(R.id.tv_key_word);
        btnKeyWord = (Button) findViewById(R.id.btn_key_word);
        llDefValue = (LinearLayout) findViewById(R.id.ll_def_value);
        etDefValue = (EditText) findViewById(R.id.et_def_value);
        ivClearDefValue = (ImageView) findViewById(R.id.iv_clear_def_value);
        llLeftText = (LinearLayout) findViewById(R.id.ll_left_text);
        etLeftTitle = (EditText) findViewById(R.id.et_left_title);
        ivClearLeftTitle = (ImageView) findViewById(R.id.iv_clear_left_title);
        llRightText = (LinearLayout) findViewById(R.id.ll_right_text);
        etRightText = (EditText) findViewById(R.id.et_right_text);
        ivClearRightText = (ImageView) findViewById(R.id.iv_clear_right_text);
        llRightImg = (LinearLayout) findViewById(R.id.ll_right_img);
        ivRightImg = (ImageView) findViewById(R.id.iv_right_img);
        tvRightImg = (TextView) findViewById(R.id.tv_right_img);
        llShowLeftImg = (LinearLayout) findViewById(R.id.ll_show_left_img);
        tvShowLeftImg = (TextView) findViewById(R.id.tv_show_left_img);
        llShowRightImg = (LinearLayout) findViewById(R.id.ll_show_right_img);
        tvShowRightImg = (TextView) findViewById(R.id.tv_show_right_img);
        llShowLeftText = (LinearLayout) findViewById(R.id.ll_show_left_text);
        tvShowLeftText = (TextView) findViewById(R.id.tv_show_left_text);
        llShowRightText = (LinearLayout) findViewById(R.id.ll_show_right_text);
        tvShowRightText = (TextView) findViewById(R.id.tv_show_right_text);
        llLeftLayout = (LinearLayout) findViewById(R.id.ll_left_layout);
        tvLeftLayout = (TextView) findViewById(R.id.tv_left_layout);
        llShowRightLayout = (LinearLayout) findViewById(R.id.ll_show_right_layout);
        tvShowRightLayout = (TextView) findViewById(R.id.tv_show_right_layout);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void initData() {
        appId = getIntent().getStringExtra("appId");

        relativeId = getIntent().getStringExtra("relativeId");


    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });
        setEditListener(etDefValue, ivClearDefValue);
        setEditListener(etHint, ivClearHint);
        setEditListener(etLeftTitle, ivClearLeftTitle);
        setEditListener(etRightText, ivClearRightText);
        setEditListener(etTitle, ivClearTitle);

        llWidgetType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseWidgetType();
            }
        });

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

        chooseShow(llShowLeftImg, tvShowLeftImg);
        chooseShow(llShowLeftText, tvShowLeftText);
        chooseShow(llShowRightImg, tvShowRightImg);
        chooseShow(llShowRightText, tvShowRightText);
        chooseShow(llShowRightLayout, tvShowRightLayout);

        llRightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWidget();
            }
        });
    }

    private void generateKeyword() {
        String title = etTitle.getText().toString().trim();
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
                            String newWord = word.replace(" ", "").replace("the", "").replace("The", "").toLowerCase();
                            tvKeyWord.setText(newWord);
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
                        ivRightImg.setImageURI(selectedUri);
                        splashPath = ContentUtils.getRealPathFromUri(AddWidgetActivity.this, selectedUri);
                    } else {
                        ToastUtils.showCustomBgToast("选择图片出错了，请选择其他图片");
                    }
                    break;
            }
        }
    }


    private void addWidget() {
        String defvalue = etDefValue.getText().toString().trim();
        String hint = etHint.getText().toString().trim();
        String title = etTitle.getText().toString().trim();
        String leftTitle = etLeftTitle.getText().toString().trim();
        String rightText = etRightText.getText().toString().trim();
        String showLeftImg = tvShowLeftImg.getText().toString().trim();
        String showRightImg = tvShowRightImg.getText().toString().trim();
        String showLeftText = tvShowLeftText.getText().toString().trim();
        String showRightText = tvShowRightText.getText().toString().trim();
        String showRightLayout = tvShowRightLayout.getText().toString().trim();
        String name = tvWidgetName.getText().toString().trim();
        String resId = tvKeyWord.getText().toString().trim();

        PostRequest<AddActivityResult> post = OkGo.<AddActivityResult>post(SharedUtil.getString(ZApplication.getInstance(), "server_config")
                + "/ZzApiDoc/v1/widget/addWidget")
                .params("name", name)
                .params("title", title)
                .params("resId", resId)
                .params("type", widgetType)
                .params("appId", appId)
                .params("relativeId", relativeId)
                .params("targetActId", targetActId)
                .params("defValue", defvalue)
                .params("hint", hint)
                .params("leftTitleText", leftTitle)
                .params("rightTitleText", rightText)
                .params("showLeftTitleImg", showLeftImg.equals("true"))
                .params("showRightTitleImg", showRightImg.equals("true"))
                .params("showLeftTitleText", showLeftText.equals("true"))
                .params("showRightTitleText", showRightText.equals("true"))
                .params("showRightTitleLayout", showRightLayout.equals("true"))
                .params("userId", getUserId())
                .isMultipart(true);
        if (splashPath != null) {
            post.params("rightTitleImg", new File(splashPath));
        }
        post.execute(new JsonCallback<AddActivityResult>(AddActivityResult.class) {
            @Override
            public void onSuccess(Response<AddActivityResult> response) {
                AddActivityResult body = response.body();
                ToastUtils.showCustomBgToast(body.getMsg());
                if (body.getCode() == 1) {
                    closeAct();
                }
            }

            @Override
            public void onError(Response<AddActivityResult> response) {
                super.onError(response);
                Throwable exception = response.getException();
                ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + (exception == null ? "" : exception.toString()));

            }
        });
    }

    private void chooseShow(LinearLayout ll, final TextView tv) {
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog(Arrays.asList("true", "false"), true, null, new OnItemClick() {
                    @Override
                    public void onItemClick(int position, String content) {
                        tv.setText(content);
                    }
                });
            }
        });
    }

    private void chooseWidgetType() {
        List<String> items = new ArrayList<>();
        items.add("TitleBar");
        items.add("SettingItem");
        items.add("EditText");
        items.add("InfoText");
        items.add("Button");
        showListDialog(items, true, null, new OnItemClick() {
            @Override
            public void onItemClick(int position, String content) {
                switch (position) {
                    case 0:
                        tvWidgetName.setText("TitleBar");
                        showTitleBarItem(true);
                        break;
                    case 1:
                        tvWidgetName.setText("SettingItem");
                        showTitleBarItem(false);
                        break;
                    case 2:
                        tvWidgetName.setText("Edit");
                        showTitleBarItem(false);
                        break;
                    case 3:
                        tvWidgetName.setText("Info");
                        showTitleBarItem(false);
                        break;
                    case 4:
                        tvWidgetName.setText("Button");
                        showTitleBarItem(false);
                        break;
                }
                widgetType = position;
                tvWidgetType.setText(content);
            }
        });
    }

    private void showTitleBarItem(boolean b) {
        llTargetAct.setVisibility(b?View.VISIBLE:View.GONE);
        llDefValue.setVisibility(b?View.GONE:View.VISIBLE);
        llShowLeftText.setVisibility(b?View.VISIBLE:View.GONE);
        llLeftLayout.setVisibility(b?View.VISIBLE:View.GONE);
        llShowRightLayout.setVisibility(b?View.VISIBLE:View.GONE);
        llLeftText.setVisibility(b?View.VISIBLE:View.GONE);
        llShowLeftImg.setVisibility(b?View.VISIBLE:View.GONE);
        llRightImg.setVisibility(b?View.VISIBLE:View.GONE);
        llShowRightImg.setVisibility(b?View.VISIBLE:View.GONE);
        llRightText.setVisibility(b?View.VISIBLE:View.GONE);
        llShowRightText.setVisibility(b?View.VISIBLE:View.GONE);
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
