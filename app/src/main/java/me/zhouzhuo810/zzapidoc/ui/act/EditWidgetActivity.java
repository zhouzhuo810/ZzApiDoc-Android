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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.ZApplication;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.JsonCallback;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddActivityResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetWidgetDetailResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.utils.ContentUtils;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.common.rx.RxHelper;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * 编辑控件
 * Created by admin on 2017/8/19.
 */
public class EditWidgetActivity extends BaseActivity {

    private static final int TYPE_TITLE_BAR = 0;
    private static final int TYPE_SETTING_ITEM = 1;
    private static final int TYPE_TITLE_EDIT_ITEM = 2;
    private static final int TYPE_UNDERLINE_EDIT_ITEM = 3;
    private static final int TYPE_INFO_ITEM = 4;
    private static final int TYPE_SUBMIT_BTN_ITEM = 5;
    private static final int TYPE_EXIT_BTN_ITEM = 6;
    private static final int TYPE_LETTER_RV = 7;
    private static final int TYPE_SCROLL_VIEW = 8;
    private static final int TYPE_LINEAR_LAYOUT = 9;
    private static final int TYPE_RELATIVE_LAYOUT = 10;
    private static final int TYPE_IMAGE_VIEW = 11;
    private static final int TYPE_TEXT_VIEW = 12;
    private static final int TYPE_CHECK_BOX = 13;
    private static final int TYPE_RV = 14;
    private static final int TYPE_LV = 15;
    private static final int TYPE_SCROLLABLE_LV = 16;
    private static final int TYPE_EDIT_TEXT = 17;

    public static final int REQUEST_SELECT_PICTURE = 0x80;

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llWidgetType;
    private TextView tvWidgetType;
    private TextView tvWidgetName;
    private EditText etHint;
    private ImageView ivClearHint;
    private LinearLayout llTitle;
    private EditText etTitle;
    private ImageView ivClearTitle;
    private LinearLayout llKeyWord;
    private EditText tvKeyWord;
    private Button btnKeyWord;
    private Button btnWidthZero;
    private Button btnWidthMatchParent;
    private Button btnWidthWrapContent;
    private EditText etWidgetWidth;
    private ImageView ivClearWidgetWidth;
    private Button btnHeightZero;
    private Button btnHeightMatchParent;
    private Button btnHeightWrapContent;
    private EditText etWidgetHeight;
    private ImageView ivClearWidgetHeight;
    private LinearLayout llTextSize;
    private Button btnFontThirty;
    private Button btnFontForty;
    private Button btnFontFifty;
    private EditText etTextSize;
    private ImageView ivClearTextSize;
    private LinearLayout llWidgetWeight;
    private Button btnWeightZero;
    private Button btnWeightOne;
    private EditText etWidgetWeight;
    private ImageView ivClearWidgetWeight;
    private LinearLayout llLeftMargin;
    private EditText etLeftMargin;
    private ImageView ivClearLeftMargin;
    private LinearLayout llRightMargin;
    private Button btnMarginRight;
    private EditText etRightMargin;
    private ImageView ivClearRightMargin;
    private LinearLayout llTopMargin;
    private EditText etTopMargin;
    private ImageView ivClearTopMargin;
    private LinearLayout llBottomMargin;
    private Button btnMarginBottom;
    private EditText etBottomMargin;
    private ImageView ivClearBottomMargin;
    private LinearLayout llLeftPadding;
    private EditText etLeftPadding;
    private ImageView ivClearLeftPadding;
    private LinearLayout llRightPadding;
    private Button btnPaddingRight;
    private EditText etRightPadding;
    private ImageView ivClearRightPadding;
    private LinearLayout llTopPadding;
    private EditText etTopPadding;
    private ImageView ivClearTopPadding;
    private LinearLayout llBottomPadding;
    private Button btnPaddingBottom;
    private EditText etBottomPadding;
    private ImageView ivClearBottomPadding;
    private LinearLayout llBackground;
    private TextView tvBackground;
    private LinearLayout llTextColor;
    private TextView tvTextColor;
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
    private LinearLayout llOrientation;
    private TextView tvOrientation;
    private LinearLayout llGravity;
    private TextView tvGravity;
    private Button btnSubmit;
    private TextView tvTitle;

    private void assignViews() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llWidgetType = (LinearLayout) findViewById(R.id.ll_widget_type);
        tvWidgetType = (TextView) findViewById(R.id.tv_widget_type);
        tvWidgetName = (TextView) findViewById(R.id.tv_widget_name);
        etHint = (EditText) findViewById(R.id.et_hint);
        ivClearHint = (ImageView) findViewById(R.id.iv_clear_hint);
        llTitle = (LinearLayout) findViewById(R.id.ll_title);
        etTitle = (EditText) findViewById(R.id.et_title);
        ivClearTitle = (ImageView) findViewById(R.id.iv_clear_title);
        llKeyWord = (LinearLayout) findViewById(R.id.ll_key_word);
        tvKeyWord = (EditText) findViewById(R.id.tv_key_word);
        btnKeyWord = (Button) findViewById(R.id.btn_key_word);
        btnWidthZero = (Button) findViewById(R.id.btn_width_zero);
        btnWidthMatchParent = (Button) findViewById(R.id.btn_width_match_parent);
        btnWidthWrapContent = (Button) findViewById(R.id.btn_width_wrap_content);
        etWidgetWidth = (EditText) findViewById(R.id.et_widget_width);
        ivClearWidgetWidth = (ImageView) findViewById(R.id.iv_clear_widget_width);
        btnHeightZero = (Button) findViewById(R.id.btn_height_zero);
        btnHeightMatchParent = (Button) findViewById(R.id.btn_height_match_parent);
        btnHeightWrapContent = (Button) findViewById(R.id.btn_height_wrap_content);
        etWidgetHeight = (EditText) findViewById(R.id.et_widget_height);
        ivClearWidgetHeight = (ImageView) findViewById(R.id.iv_clear_widget_height);
        llTextSize = (LinearLayout) findViewById(R.id.ll_text_size);
        btnFontThirty = (Button) findViewById(R.id.btn_font_thirty);
        btnFontForty = (Button) findViewById(R.id.btn_font_forty);
        btnFontFifty = (Button) findViewById(R.id.btn_font_fifty);
        etTextSize = (EditText) findViewById(R.id.et_text_size);
        ivClearTextSize = (ImageView) findViewById(R.id.iv_clear_text_size);
        llWidgetWeight = (LinearLayout) findViewById(R.id.ll_widget_weight);
        btnWeightZero = (Button) findViewById(R.id.btn_weight_zero);
        btnWeightOne = (Button) findViewById(R.id.btn_weight_one);
        etWidgetWeight = (EditText) findViewById(R.id.et_widget_weight);
        ivClearWidgetWeight = (ImageView) findViewById(R.id.iv_clear_widget_weight);
        llLeftMargin = (LinearLayout) findViewById(R.id.ll_left_margin);
        etLeftMargin = (EditText) findViewById(R.id.et_left_margin);
        ivClearLeftMargin = (ImageView) findViewById(R.id.iv_clear_left_margin);
        llRightMargin = (LinearLayout) findViewById(R.id.ll_right_margin);
        btnMarginRight = (Button) findViewById(R.id.btn_margin_right);
        etRightMargin = (EditText) findViewById(R.id.et_right_margin);
        ivClearRightMargin = (ImageView) findViewById(R.id.iv_clear_right_margin);
        llTopMargin = (LinearLayout) findViewById(R.id.ll_top_margin);
        etTopMargin = (EditText) findViewById(R.id.et_top_margin);
        ivClearTopMargin = (ImageView) findViewById(R.id.iv_clear_top_margin);
        llBottomMargin = (LinearLayout) findViewById(R.id.ll_bottom_margin);
        btnMarginBottom = (Button) findViewById(R.id.btn_margin_bottom);
        etBottomMargin = (EditText) findViewById(R.id.et_bottom_margin);
        ivClearBottomMargin = (ImageView) findViewById(R.id.iv_clear_bottom_margin);
        llLeftPadding = (LinearLayout) findViewById(R.id.ll_left_padding);
        etLeftPadding = (EditText) findViewById(R.id.et_left_padding);
        ivClearLeftPadding = (ImageView) findViewById(R.id.iv_clear_left_padding);
        llRightPadding = (LinearLayout) findViewById(R.id.ll_right_padding);
        btnPaddingRight = (Button) findViewById(R.id.btn_padding_right);
        etRightPadding = (EditText) findViewById(R.id.et_right_padding);
        ivClearRightPadding = (ImageView) findViewById(R.id.iv_clear_right_padding);
        llTopPadding = (LinearLayout) findViewById(R.id.ll_top_padding);
        etTopPadding = (EditText) findViewById(R.id.et_top_padding);
        ivClearTopPadding = (ImageView) findViewById(R.id.iv_clear_top_padding);
        llBottomPadding = (LinearLayout) findViewById(R.id.ll_bottom_padding);
        btnPaddingBottom = (Button) findViewById(R.id.btn_padding_bottom);
        etBottomPadding = (EditText) findViewById(R.id.et_bottom_padding);
        ivClearBottomPadding = (ImageView) findViewById(R.id.iv_clear_bottom_padding);
        llBackground = (LinearLayout) findViewById(R.id.ll_background);
        tvBackground = (TextView) findViewById(R.id.tv_background);
        llTextColor = (LinearLayout) findViewById(R.id.ll_text_color);
        tvTextColor = (TextView) findViewById(R.id.tv_text_color);
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
        llOrientation = (LinearLayout) findViewById(R.id.ll_orientation);
        tvOrientation = (TextView) findViewById(R.id.tv_orientation);
        llGravity = (LinearLayout) findViewById(R.id.ll_gravity);
        tvGravity = (TextView) findViewById(R.id.tv_gravity);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    private String widgetId;
    private int widgetType;
    private String splashPath;
    private String appId;
    private String relativeId;
    private String projectId;
    private String pid;

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

        widgetId = getIntent().getStringExtra("widgetId");

        tvTitle.setText("编辑Widget");
        tvTextColor.setText("@color/colorBlack");

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
        setEditListener(etTextSize, ivClearTextSize);
        setEditListener(etEtHint, ivClearEtHint);

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

        btnWeightOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWidgetWeight.setText("1");
            }
        });
        btnWeightZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWidgetWeight.setText("0");
            }
        });

        btnMarginRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String leftMargin = etLeftMargin.getText().toString().trim();
                etRightMargin.setText(leftMargin);
            }
        });

        btnMarginBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etBottomMargin.setText(etTopMargin.getText().toString().trim());
            }
        });

        btnPaddingRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etRightPadding.setText(etLeftPadding.getText().toString().trim());
            }
        });

        btnPaddingBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etBottomPadding.setText(etTopPadding.getText().toString().trim());
            }
        });

        btnFontThirty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTextSize.setText("30");
            }
        });
        btnFontForty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTextSize.setText("40");
            }
        });
        btnFontFifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTextSize.setText("50");
            }
        });

        btnWidthZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWidgetWidth.setText("0");
            }
        });

        btnHeightZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWidgetHeight.setText("0");
            }
        });



        llBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseBackground();
            }
        });


        llOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseOrientation();
            }
        });

        llWidgetType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseWidgetType();
            }
        });

        btnKeyWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translate();
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

        llTextColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTextColor();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWidget();
            }
        });

        getData();
    }

    private void chooseTextColor() {
        List<String> items = new ArrayList<>();
        items.add("自定义");
        items.add("@color/colorBlack");
        items.add("@color/colorPrimary");
        items.add("@color/colorWhite");
        items.add("@color/colorGrayB");
        items.add("@color/colorGrayD");
        showListDialog(items, true, null, new IBaseActivity.OnItemClick() {
            @Override
            public void onItemClick(int i, String s) {
                if (i == 0) {
                    showTwoBtnEditDialog("文字顔色", "请输入文字颜色", "#", false, new IBaseActivity.OnTwoBtnEditClick() {
                        @Override
                        public void onOk(String s) {
                            tvTextColor.setText(s);
                        }

                        @Override
                        public void onCancel() {
                        }
                    });
                } else {
                    tvTextColor.setText(s);
                }
            }
        });
    }

    private void chooseBackground() {
        List<String> items = new ArrayList<>();
        items.add("@color/colorGrayBg");
        items.add("@color/colorPrimary");
        items.add("@color/colorWhite");
        items.add("@color/colorBlack");
        items.add("@drawable/setting_item_bg_selector");
        showListDialog(items, true, null, new IBaseActivity.OnItemClick() {
            @Override
            public void onItemClick(int i, String s) {
                tvBackground.setText(s);
            }
        });
    }

    private void chooseOrientation() {
        List<String> items = new ArrayList<>();
        items.add("horizontal");
        items.add("vertical");
        showListDialog(items, true, null, new IBaseActivity.OnItemClick() {
            @Override
            public void onItemClick(int i, String s) {
                tvOrientation.setText(s);
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
        items.add("bottom");
        items.add("top|left");
        items.add("top|right");
        items.add("right|center_vertical");
        showListDialog(items, true, null, new IBaseActivity.OnItemClick() {
            @Override
            public void onItemClick(int i, String s) {
                tvGravity.setText(s);
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
                    Log.e("XXX", "trans=" + result.getTranslations().toString());
                    String word = result.getTranslations().get(0);
                    String newWord = word.replace(" ", "_").replace("the_", "").replace("The_", "").replace(".", "").toLowerCase();
                    tvKeyWord.setText(newWord);
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
                case REQUEST_SELECT_PICTURE:
                    final Uri selectedUri = data.getData();
                    if (selectedUri != null) {
                        ivRightImg.setImageURI(selectedUri);
                        splashPath = ContentUtils.getRealPathFromUri(EditWidgetActivity.this, selectedUri);
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
        String rightMargin = etRightMargin.getText().toString().trim();
        String topMargin = etTopMargin.getText().toString().trim();
        String bottomMargin = etBottomMargin.getText().toString().trim();
        String leftPadding = etLeftPadding.getText().toString().trim();
        String rightPadding = etLeftPadding.getText().toString().trim();
        String topPadding = etTopPadding.getText().toString().trim();
        String bottomPadding = etBottomPadding.getText().toString().trim();
        String name = tvWidgetName.getText().toString().trim();
        String resId = tvKeyWord.getText().toString().trim();
        String orientation = tvOrientation.getText().toString().trim();
        String background = tvBackground.getText().toString().trim();
        String gravity = tvGravity.getText().toString().trim();
        String textSize = etTextSize.getText().toString().trim();
        String textColor = tvTextColor.getText().toString().trim();
        showPd(getString(R.string.submiting_text), false);
        PostRequest<AddActivityResult> post = OkGo.<AddActivityResult>post(SharedUtil.getString(ZApplication.getInstance(), "server_config")
                + "/ZzApiDoc/v1/widget/updateWidget")
                .params("widgetId", widgetId)
                .params("name", name)
                .params("title", title)
                .params("resId", resId)
                .params("gravity", gravity)
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
                .params("showLeftTitleLayout", cbShowLeftLayout.isChecked())
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
                .params("orientation", orientation)
                .params("textSize", textSize)
                .params("textColor", textColor)
                .params("userId", getUserId())
                .isMultipart(true);
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

    private void chooseWidgetType() {
        List<String> items = new ArrayList<>();
        items.add("TitleBar");
        items.add("SettingItem");
        items.add("TitleEditItem");
        items.add("UnderlineEditItem");
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
        items.add("RecyclerView");
        items.add("ListView");
        items.add("ScrollableListView");
        items.add("EditText");
        showListDialog(items, true, null, new IBaseActivity.OnItemClick() {
            @Override
            public void onItemClick(int i, String s) {
                setType(i);
            }
        });
    }

    private void setType(int i) {
        List<String> items = new ArrayList<>();
        items.add("TitleBar");
        items.add("SettingItem");
        items.add("TitleEditItem");
        items.add("UnderlineEditItem");
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
        items.add("RecyclerView");
        items.add("ListView");
        items.add("ScrollableListView");
        items.add("EditText");
        hideAll();
        switch (i) {
            case TYPE_TITLE_BAR:
                showTitleBarItem();
                break;
            case TYPE_SETTING_ITEM:
                showSettingItem();
                break;
            case TYPE_TITLE_EDIT_ITEM:
                showTitleEditItem();
                break;
            case TYPE_UNDERLINE_EDIT_ITEM:
                showUnderlineEditItem();
                break;
            case TYPE_INFO_ITEM:
                showInfoItem();
                break;
            case TYPE_SUBMIT_BTN_ITEM:
                showSubmitBtn();
                break;
            case TYPE_EXIT_BTN_ITEM:
                showExitBtn();
                break;
            case TYPE_LETTER_RV:
                showSideBar();
                break;
            case TYPE_SCROLL_VIEW:
                showScrollView();
                break;
            case TYPE_LINEAR_LAYOUT:
                showLinear();
                break;
            case TYPE_RELATIVE_LAYOUT:
                showRelative();
                break;
            case TYPE_IMAGE_VIEW:
                showImageView();
                break;
            case TYPE_TEXT_VIEW:
                showTextView();
                break;
            case TYPE_CHECK_BOX:
                showCheckBox();
                break;
            case TYPE_RV:
                showRv();
                break;
            case TYPE_LV:
                showLv();
                break;
            case TYPE_SCROLLABLE_LV:
                showScrollLv();
                break;
            case TYPE_EDIT_TEXT:
                showEditText();
                break;
        }
        widgetType = i;
        tvWidgetType.setText(items.get(i));
    }


    private void showEditText() {
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);
        llGravity.setVisibility(View.VISIBLE);
        llWidgetWeight.setVisibility(View.VISIBLE);
        llHint.setVisibility(View.VISIBLE);
        showPaddingMarin();
    }

    private void showScrollLv() {
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);
        showPaddingMarin();
    }

    private void showLv() {
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);
        showPaddingMarin();
    }

    private void showRv() {
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);
        llOrientation.setVisibility(View.VISIBLE);
        tvOrientation.setText("vertical");
        etTitle.setText("rv");
        tvKeyWord.setText("rv");
        etWidgetWidth.setText("-1");
        etWidgetHeight.setText("-1");
        showPaddingMarin();
    }


    private void showCheckBox() {
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);
        showPaddingMarin();
    }

    private void showTextView() {
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);
        llHint.setVisibility(View.VISIBLE);
        llGravity.setVisibility(View.VISIBLE);
        llTextColor.setVisibility(View.VISIBLE);
        llTextSize.setVisibility(View.VISIBLE);
        llWidgetWeight.setVisibility(View.VISIBLE);
        showPaddingMarin();
    }

    private void showImageView() {
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);
        llLeftImg.setVisibility(View.VISIBLE);
        showPaddingMarin();
    }

    private void showRelative() {
        showPaddingMarin();
    }

    private void showLinear() {
        llWidgetWeight.setVisibility(View.VISIBLE);
        llGravity.setVisibility(View.VISIBLE);
        llOrientation.setVisibility(View.VISIBLE);
        llBackground.setVisibility(View.VISIBLE);
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);
        tvOrientation.setText("horizontal");
        showPaddingMarin();
    }


    private void showScrollView() {
        showPaddingMarin();
    }

    private void showSideBar() {
    }

    private void showExitBtn() {
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);

        setMargin(40);

        showPaddingMarin();
    }

    private void setMargin(int px) {
        etLeftMargin.setText(px + "");
        etRightMargin.setText(px + "");
        etTopMargin.setText(px + "");
        etBottomMargin.setText(px + "");
    }


    private void showSubmitBtn() {
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);

        setMargin(40);

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
        llHint.setVisibility(View.GONE);
        llOrientation.setVisibility(View.GONE);
        llTextSize.setVisibility(View.GONE);
        llTextColor.setVisibility(View.GONE);

        setMargin(0);
    }


    private void showUnderlineEditItem() {
        llLeftText.setVisibility(View.VISIBLE);
        llShowLeftText.setVisibility(View.VISIBLE);
        llLeftImg.setVisibility(View.VISIBLE);
        llDefValue.setVisibility(View.VISIBLE);
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);

        showPaddingMarin();

        etLeftMargin.setText("40");
        etRightMargin.setText("40");
    }

    private void showTitleEditItem() {
        llLeftText.setVisibility(View.VISIBLE);
        llShowLeftText.setVisibility(View.VISIBLE);
        llLeftImg.setVisibility(View.VISIBLE);
        llDefValue.setVisibility(View.VISIBLE);
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);
    }

    private void showTitleBarItem() {
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
        llLeftImg.setVisibility(View.VISIBLE);
        llShowLeftImg.setVisibility(View.VISIBLE);
        llTitle.setVisibility(View.VISIBLE);
        llKeyWord.setVisibility(View.VISIBLE);

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

    private void getData() {
        showPd(getString(R.string.loading_text), false);
        Api.getApi0()
                .getWidgetDetail(getUserId(), widgetId)
                .compose(RxHelper.<GetWidgetDetailResult>io_main())
                .subscribe(new Subscriber<GetWidgetDetailResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(GetWidgetDetailResult getWidgetDetailResult) {
                        hidePd();
                        if (getWidgetDetailResult.getCode() == 1) {
                            fillData(getWidgetDetailResult.getData());
                        } else {
                            ToastUtils.showCustomBgToast(getWidgetDetailResult.getMsg());
                        }
                    }
                });
    }

    private void fillData(GetWidgetDetailResult.DataEntity data) {
        if (data != null) {
            tvWidgetName.setText(data.getName());
            etTitle.setText(data.getTitle());
            etBottomMargin.setText(data.getMarginBottom() + "");
            etTopMargin.setText(data.getMarginTop() + "");
            etLeftMargin.setText(data.getMarginLeft() + "");
            etRightMargin.setText(data.getMarginRight() + "");
            etTopPadding.setText(data.getPaddingTop() + "");
            etLeftPadding.setText(data.getPaddingLeft() + "");
            etRightPadding.setText(data.getPaddingRight() + "");
            etBottomPadding.setText(data.getPaddingBottom() + "");
            etWidgetWeight.setText(data.getWeight() + "");
            tvGravity.setText(data.getGravity() + "");
            etHint.setText(data.getHint());
            etWidgetWidth.setText(data.getWidth() + "");
            etWidgetHeight.setText(data.getHeight() + "");
            tvBackground.setText(data.getBackground());
            etDefValue.setText(data.getDefValue());
            tvKeyWord.setText(data.getResId());
            etLeftTitle.setText(data.getLeftTitleText());
            etRightText.setText(data.getRightTitleText());
            cbShowLeftImg.setChecked(data.isShowLeftTitleImg());
            cbShowLeftLayout.setChecked(data.isShowLeftTitleLayout());
            cbShowLeftText.setChecked(data.isShowLeftTitleText());
            cbShowRightImg.setChecked(data.isShowRightTitleImg());
            cbShowRightText.setChecked(data.isShowRightTitleText());
            cbShowRightLayout.setChecked(data.isShowRightTitleLayout());
            tvTextColor.setText(data.getTextColor());
            etTextSize.setText(data.getTextSize() + "");
            pid = data.getPid();
            appId = data.getAppId();
            relativeId = data.getRelativeId();
            setType(data.getType());
        }
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

