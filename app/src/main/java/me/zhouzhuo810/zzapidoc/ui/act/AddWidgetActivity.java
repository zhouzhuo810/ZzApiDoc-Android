package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
    private LinearLayout llTargetApi;
    private TextView tvTargetApi;
    private TextView tvWidgetName;
    private EditText etHint;
    private ImageView ivClearHint;
    private LinearLayout llTitle;
    private EditText etTitle;
    private ImageView ivClearTitle;
    private LinearLayout llKeyWord;
    private TextView tvKeyWord;
    private Button btnKeyWord;
    private Button btnWidthMatchParent;
    private Button btnWidthWrapContent;
    private EditText etWidgetWidth;
    private ImageView ivClearWidgetWidth;
    private Button btnHeightMatchParent;
    private Button btnHeightWrapContent;
    private EditText etWidgetHeight;
    private ImageView ivClearWidgetHeight;
    private LinearLayout llWidgetWeight;
    private EditText etWidgetWeight;
    private ImageView ivClearWidgetWeight;
    private LinearLayout llLeftMargin;
    private EditText etLeftMargin;
    private ImageView ivClearLeftMargin;
    private LinearLayout llRightMargin;
    private EditText etRightMargin;
    private ImageView ivClearRightMargin;
    private LinearLayout llTopMargin;
    private EditText etTopMargin;
    private ImageView ivClearTopMargin;
    private LinearLayout llBottomMargin;
    private EditText etBottomMargin;
    private ImageView ivClearBottomMargin;
    private LinearLayout llLeftPadding;
    private EditText etLeftPadding;
    private ImageView ivClearLeftPadding;
    private LinearLayout llRightPadding;
    private EditText etRightPadding;
    private ImageView ivClearRightPadding;
    private LinearLayout llTopPadding;
    private EditText etTopPadding;
    private ImageView ivClearTopPadding;
    private LinearLayout llBottomPadding;
    private EditText etBottomPadding;
    private ImageView ivClearBottomPadding;
    private LinearLayout llBackground;
    private TextView tvBackground;
    private LinearLayout llHint;
    private EditText etEtHint;
    private ImageView ivClearEtHint;
    private LinearLayout llDefValue;
    private EditText etDefValue;
    private ImageView ivClearDefValue;
    private LinearLayout llLeftText;
    private EditText etLeftTitle;
    private ImageView ivClearLeftTitle;
    private LinearLayout llRightText;
    private EditText etRightText;
    private ImageView ivClearRightText;
    private LinearLayout llLeftImg;
    private ImageView ivLeftImg;
    private TextView tvLeftImg;
    private LinearLayout llRightImg;
    private ImageView ivRightImg;
    private TextView tvRightImg;
    private LinearLayout llShowLeftImg;
    private CheckBox cbShowLeftImg;
    private LinearLayout llShowRightImg;
    private CheckBox cbShowRightImg;
    private LinearLayout llShowLeftText;
    private CheckBox cbShowLeftText;
    private LinearLayout llShowRightText;
    private CheckBox cbShowRightText;
    private LinearLayout llShowLeftLayout;
    private CheckBox cbShowLeftLayout;
    private LinearLayout llShowRightLayout;
    private CheckBox cbShowRightLayout;
    private LinearLayout llGravity;
    private TextView tvGravity;
    private Button btnSubmit;

    private void assignViews() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llWidgetType = (LinearLayout) findViewById(R.id.ll_widget_type);
        tvWidgetType = (TextView) findViewById(R.id.tv_widget_type);
        llTargetAct = (LinearLayout) findViewById(R.id.ll_target_act);
        tvTargetAct = (TextView) findViewById(R.id.tv_target_act);
        llTargetApi = (LinearLayout) findViewById(R.id.ll_target_api);
        tvTargetApi = (TextView) findViewById(R.id.tv_target_api);
        tvWidgetName = (TextView) findViewById(R.id.tv_widget_name);
        etHint = (EditText) findViewById(R.id.et_hint);
        ivClearHint = (ImageView) findViewById(R.id.iv_clear_hint);
        llTitle = (LinearLayout) findViewById(R.id.ll_title);
        etTitle = (EditText) findViewById(R.id.et_title);
        ivClearTitle = (ImageView) findViewById(R.id.iv_clear_title);
        llKeyWord = (LinearLayout) findViewById(R.id.ll_key_word);
        tvKeyWord = (TextView) findViewById(R.id.tv_key_word);
        btnKeyWord = (Button) findViewById(R.id.btn_key_word);
        btnWidthMatchParent = (Button) findViewById(R.id.btn_width_match_parent);
        btnWidthWrapContent = (Button) findViewById(R.id.btn_width_wrap_content);
        etWidgetWidth = (EditText) findViewById(R.id.et_widget_width);
        ivClearWidgetWidth = (ImageView) findViewById(R.id.iv_clear_widget_width);
        btnHeightMatchParent = (Button) findViewById(R.id.btn_height_match_parent);
        btnHeightWrapContent = (Button) findViewById(R.id.btn_height_wrap_content);
        etWidgetHeight = (EditText) findViewById(R.id.et_widget_height);
        ivClearWidgetHeight = (ImageView) findViewById(R.id.iv_clear_widget_height);
        llWidgetWeight = (LinearLayout) findViewById(R.id.ll_widget_weight);
        etWidgetWeight = (EditText) findViewById(R.id.et_widget_weight);
        ivClearWidgetWeight = (ImageView) findViewById(R.id.iv_clear_widget_weight);
        llLeftMargin = (LinearLayout) findViewById(R.id.ll_left_margin);
        etLeftMargin = (EditText) findViewById(R.id.et_left_margin);
        ivClearLeftMargin = (ImageView) findViewById(R.id.iv_clear_left_margin);
        llRightMargin = (LinearLayout) findViewById(R.id.ll_right_margin);
        etRightMargin = (EditText) findViewById(R.id.et_right_margin);
        ivClearRightMargin = (ImageView) findViewById(R.id.iv_clear_right_margin);
        llTopMargin = (LinearLayout) findViewById(R.id.ll_top_margin);
        etTopMargin = (EditText) findViewById(R.id.et_top_margin);
        ivClearTopMargin = (ImageView) findViewById(R.id.iv_clear_top_margin);
        llBottomMargin = (LinearLayout) findViewById(R.id.ll_bottom_margin);
        etBottomMargin = (EditText) findViewById(R.id.et_bottom_margin);
        ivClearBottomMargin = (ImageView) findViewById(R.id.iv_clear_bottom_margin);
        llLeftPadding = (LinearLayout) findViewById(R.id.ll_left_padding);
        etLeftPadding = (EditText) findViewById(R.id.et_left_padding);
        ivClearLeftPadding = (ImageView) findViewById(R.id.iv_clear_left_padding);
        llRightPadding = (LinearLayout) findViewById(R.id.ll_right_padding);
        etRightPadding = (EditText) findViewById(R.id.et_right_padding);
        ivClearRightPadding = (ImageView) findViewById(R.id.iv_clear_right_padding);
        llTopPadding = (LinearLayout) findViewById(R.id.ll_top_padding);
        etTopPadding = (EditText) findViewById(R.id.et_top_padding);
        ivClearTopPadding = (ImageView) findViewById(R.id.iv_clear_top_padding);
        llBottomPadding = (LinearLayout) findViewById(R.id.ll_bottom_padding);
        etBottomPadding = (EditText) findViewById(R.id.et_bottom_padding);
        ivClearBottomPadding = (ImageView) findViewById(R.id.iv_clear_bottom_padding);
        llBackground = (LinearLayout) findViewById(R.id.ll_background);
        tvBackground = (TextView) findViewById(R.id.tv_background);
        llHint = (LinearLayout) findViewById(R.id.ll_hint);
        etEtHint = (EditText) findViewById(R.id.et_et_hint);
        ivClearEtHint = (ImageView) findViewById(R.id.iv_clear_et_hint);
        llDefValue = (LinearLayout) findViewById(R.id.ll_def_value);
        etDefValue = (EditText) findViewById(R.id.et_def_value);
        ivClearDefValue = (ImageView) findViewById(R.id.iv_clear_def_value);
        llLeftText = (LinearLayout) findViewById(R.id.ll_left_text);
        etLeftTitle = (EditText) findViewById(R.id.et_left_title);
        ivClearLeftTitle = (ImageView) findViewById(R.id.iv_clear_left_title);
        llRightText = (LinearLayout) findViewById(R.id.ll_right_text);
        etRightText = (EditText) findViewById(R.id.et_right_text);
        ivClearRightText = (ImageView) findViewById(R.id.iv_clear_right_text);
        llLeftImg = (LinearLayout) findViewById(R.id.ll_left_img);
        ivLeftImg = (ImageView) findViewById(R.id.iv_left_img);
        tvLeftImg = (TextView) findViewById(R.id.tv_left_img);
        llRightImg = (LinearLayout) findViewById(R.id.ll_right_img);
        ivRightImg = (ImageView) findViewById(R.id.iv_right_img);
        tvRightImg = (TextView) findViewById(R.id.tv_right_img);
        llShowLeftImg = (LinearLayout) findViewById(R.id.ll_show_left_img);
        cbShowLeftImg = (CheckBox) findViewById(R.id.cb_show_left_img);
        llShowRightImg = (LinearLayout) findViewById(R.id.ll_show_right_img);
        cbShowRightImg = (CheckBox) findViewById(R.id.cb_show_right_img);
        llShowLeftText = (LinearLayout) findViewById(R.id.ll_show_left_text);
        cbShowLeftText = (CheckBox) findViewById(R.id.cb_show_left_text);
        llShowRightText = (LinearLayout) findViewById(R.id.ll_show_right_text);
        cbShowRightText = (CheckBox) findViewById(R.id.cb_show_right_text);
        llShowLeftLayout = (LinearLayout) findViewById(R.id.ll_show_left_layout);
        cbShowLeftLayout = (CheckBox) findViewById(R.id.cb_show_left_layout);
        llShowRightLayout = (LinearLayout) findViewById(R.id.ll_show_right_layout);
        cbShowRightLayout = (CheckBox) findViewById(R.id.cb_show_right_layout);
        llGravity = (LinearLayout) findViewById(R.id.ll_gravity);
        tvGravity = (TextView) findViewById(R.id.tv_gravity);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    private int widgetType;
    private String splashPath;
    private String appId;
    private String targetActId;
    private String relativeId;
    private String targetApiId;
    private String projectId;
    private String pid;
    private String background;

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
        assignViews();
    }

    @Override
    public void initData() {
        appId = getIntent().getStringExtra("appId");

        relativeId = getIntent().getStringExtra("relativeId");
        projectId = getIntent().getStringExtra("projectId");
        pid = getIntent().getStringExtra("pid");

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
        setEditListener(etLeftMargin, ivClearLeftMargin);
        setEditListener(etLeftPadding, ivClearLeftPadding);
        setEditListener(etRightMargin, ivClearRightMargin);
        setEditListener(etRightPadding, ivClearRightPadding);
        setEditListener(etTopMargin, ivClearTopMargin);
        setEditListener(etTopPadding, ivClearTopPadding);
        setEditListener(etBottomMargin, ivClearBottomMargin);
        setEditListener(etBottomPadding, ivClearBottomPadding);
        setEditListener(etWidgetHeight, ivClearWidgetHeight);
        setEditListener(etWidgetWidth, ivClearWidgetWidth);
        setEditListener(etWidgetWeight, ivClearWidgetWeight);

        btnWidthMatchParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWidgetWidth.setText("-1");
            }
        });

        btnWidthWrapContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWidgetWidth.setText("-2");
            }
        });

        btnHeightMatchParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWidgetHeight.setText("-1");
            }
        });

        btnHeightWrapContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWidgetHeight.setText("-2");
            }
        });


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

        llTargetApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTargetApi();
            }
        });

        btnKeyWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateKeyword();
            }
        });

        llGravity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseGravity();
            }
        });

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

    private void chooseGravity() {
        List<String> items = new ArrayList<>();
        items.add("center_vertical");
        items.add("center_horizontal");
        items.add("center");
        items.add("left");
        items.add("right");
        items.add("right|center_vertical");
        items.add("top|left");
        showListDialog(items, true, null, new OnItemClick() {
            @Override
            public void onItemClick(int position, String content) {
                tvGravity.setText(content);
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

    private void chooseTargetApi() {
        Intent intent = new Intent(this, InterfaceGroupManageActivity.class);
        intent.putExtra("choose", true);
        intent.putExtra("projectId", projectId);
        startActForResultWithIntent(intent, 0x02);
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
                case 0x02:
                    targetApiId = data.getStringExtra("id");
                    String apiName = data.getStringExtra("name");
                    tvTargetApi.setText(apiName);
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
        String width = etWidgetWidth.getText().toString().trim();
        String height = etWidgetHeight.getText().toString().trim();
        String weight = etWidgetWeight.getText().toString().trim();
        String leftMargin = etLeftMargin.getText().toString().trim();
        String rightMargin = etLeftMargin.getText().toString().trim();
        String topMargin = etTopMargin.getText().toString().trim();
        String bottomMargin = etBottomMargin.getText().toString().trim();
        String leftPadding = etLeftPadding.getText().toString().trim();
        String rightPadding = etLeftPadding.getText().toString().trim();
        String topPadding = etTopPadding.getText().toString().trim();
        String bottomPadding = etBottomPadding.getText().toString().trim();
        String name = tvWidgetName.getText().toString().trim();
        String resId = tvKeyWord.getText().toString().trim();

        showPd(getString(R.string.submiting_text), false);
        PostRequest<AddActivityResult> post = OkGo.<AddActivityResult>post(SharedUtil.getString(ZApplication.getInstance(), "server_config")
                + "/ZzApiDoc/v1/widget/addWidget")
                .params("name", name)
                .params("title", title)
                .params("resId", resId)
                .params("type", widgetType)
                .params("appId", appId)
                .params("relativeId", relativeId)
                .params("defValue", defvalue)
                .params("hint", hint)
                .params("leftTitleText", leftTitle)
                .params("rightTitleText", rightText)
                .params("showLeftTitleImg", cbShowLeftImg.isChecked())
                .params("showRightTitleImg", cbShowRightImg.isChecked())
                .params("showLeftTitleText", cbShowLeftText.isChecked())
                .params("showRightTitleText", cbShowRightText.isChecked())
                .params("showRightTitleLayout", cbShowRightLayout.isChecked())
                .params("pid", pid)
                .params("background", background)
                .params("width", width)
                .params("height", height)
                .params("weight", weight)
                .params("marginLeft", leftMargin)
                .params("marginRight", rightMargin)
                .params("marginTop", topMargin)
                .params("marginBottom", bottomMargin)
                .params("paddingLeft", leftPadding)
                .params("paddingRight", rightPadding)
                .params("paddingTop", topPadding)
                .params("paddingBottom", bottomPadding)
                .params("userId", getUserId())
                .isMultipart(true);
        if (targetActId != null) {
            post.params("targetActId", targetActId);
        }
        if (targetApiId != null) {
            post.params("targetApiId", targetApiId);
        }
        if (splashPath != null) {
            post.params("rightTitleImg", new File(splashPath));
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
        items.add("EditItem");
        items.add("InfoItem");
        items.add("SubmitButton");
        items.add("ExitButton");
        items.add("SideBar+RecyclerView");
        items.add("ScrollView");
        items.add("LinearLayout");
        items.add("RelativeLayout");
        items.add("ImageView");
        items.add("TextView");
        items.add("CheckBox");
        showListDialog(items, true, null, new OnItemClick() {
            @Override
            public void onItemClick(int position, String content) {
                tvWidgetName.setText(content);
                hideAll();
                switch (position) {
                    case 0:
                        showTitleBarItem();
                        break;
                    case 1:
                        showSettingItem();
                        break;
                    case 2:
                        showEditItem();
                        break;
                    case 3:
                        showInfoItem();
                        break;
                    case 4:
                        showSubmitBtn();
                        break;
                    case 5:
                        showExitBtn();
                        break;
                    case 6:
                        showSideBar();
                        break;
                    case 7:
                        showScrollView();
                        break;
                    case 8:
                        showLinear();
                        break;
                    case 9:
                        showRelative();
                        break;
                    case 10:
                        showImageView();
                        break;
                    case 11:
                        showTextView();
                        break;
                    case 12:
                        showCheckBox();
                        break;
                }
                widgetType = position;
                tvWidgetType.setText(content);
            }
        });
    }

    private void showCheckBox() {

    }

    private void showTextView() {
    }

    private void showImageView() {

    }

    private void showRelative() {
        showPaddingMarin();
    }

    private void showLinear() {
        showPaddingMarin();
        llGravity.setVisibility(View.VISIBLE);
    }


    private void showScrollView() {
        showPaddingMarin();
    }

    private void showSideBar() {
    }

    private void showExitBtn() {
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);
        llTargetAct.setVisibility(View.VISIBLE);
        llTargetApi.setVisibility(View.VISIBLE);

        showPaddingMarin();
    }


    private void showSubmitBtn() {
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);
        llTargetAct.setVisibility(View.VISIBLE);
        llTargetApi.setVisibility(View.VISIBLE);

        showPaddingMarin();
    }

    private void showInfoItem() {
        llShowLeftText.setVisibility(View.VISIBLE);
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);
        llDefValue.setVisibility(View.VISIBLE);
        llHint.setVisibility(View.VISIBLE);

        showPaddingMarin();
    }

    private void hideAll() {
        llTargetAct.setVisibility(View.GONE);
        llTargetApi.setVisibility(View.GONE);
        llRightImg.setVisibility(View.GONE);
        llBackground.setVisibility(View.GONE);
        llBottomPadding.setVisibility(View.GONE);
        llDefValue.setVisibility(View.GONE);
        llKeyWord.setVisibility(View.GONE);
        llShowLeftLayout.setVisibility(View.GONE);
        llShowRightLayout.setVisibility(View.GONE);
        llShowRightImg.setVisibility(View.GONE);
        llShowRightText.setVisibility(View.GONE);
        llShowLeftImg.setVisibility(View.GONE);
        llShowLeftText.setVisibility(View.GONE);
        llLeftText.setVisibility(View.GONE);
        llLeftImg.setVisibility(View.GONE);
        llRightText.setVisibility(View.GONE);
        llTitle.setVisibility(View.GONE);
        llWidgetWeight.setVisibility(View.GONE);
        llRightMargin.setVisibility(View.GONE);
        llRightPadding.setVisibility(View.GONE);
        llLeftMargin.setVisibility(View.GONE);
        llLeftPadding.setVisibility(View.GONE);
        llTopMargin.setVisibility(View.GONE);
        llTopPadding.setVisibility(View.GONE);
        llBottomPadding.setVisibility(View.GONE);
        llBottomMargin.setVisibility(View.GONE);
        llGravity.setVisibility(View.GONE);
    }

    private void showEditItem() {
        llLeftText.setVisibility(View.VISIBLE);
        llShowLeftText.setVisibility(View.VISIBLE);
        llShowLeftImg.setVisibility(View.VISIBLE);
        llLeftImg.setVisibility(View.VISIBLE);
        llHint.setVisibility(View.VISIBLE);
        llDefValue.setVisibility(View.VISIBLE);
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);

        showPaddingMarin();
    }

    private void showTitleBarItem() {
        llTargetAct.setVisibility(View.VISIBLE);
        llShowLeftLayout.setVisibility(View.VISIBLE);
        llShowRightLayout.setVisibility(View.VISIBLE);
        llShowRightImg.setVisibility(View.VISIBLE);
        llShowRightText.setVisibility(View.VISIBLE);
        llShowLeftImg.setVisibility(View.VISIBLE);
        llShowLeftText.setVisibility(View.VISIBLE);
        llLeftText.setVisibility(View.VISIBLE);
        llRightText.setVisibility(View.VISIBLE);
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);
        llWidgetWeight.setVisibility(View.VISIBLE);
    }

    private void showSettingItem() {
        llTargetAct.setVisibility(View.VISIBLE);
        llLeftImg.setVisibility(View.VISIBLE);
        llShowLeftImg.setVisibility(View.VISIBLE);
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);
        llShowRightImg.setVisibility(View.VISIBLE);
        llRightImg.setVisibility(View.VISIBLE);

        showPaddingMarin();
    }

    private void showPaddingMarin() {
        llRightMargin.setVisibility(View.VISIBLE);
        llRightPadding.setVisibility(View.VISIBLE);
        llLeftMargin.setVisibility(View.VISIBLE);
        llLeftPadding.setVisibility(View.VISIBLE);
        llTopMargin.setVisibility(View.VISIBLE);
        llTopPadding.setVisibility(View.VISIBLE);
        llBottomPadding.setVisibility(View.VISIBLE);
        llBottomMargin.setVisibility(View.VISIBLE);
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
