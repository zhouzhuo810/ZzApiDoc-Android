package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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

import me.zhouzhuo.zzimagebox.ZzImageBox;
import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.ZApplication;
import me.zhouzhuo810.zzapidoc.common.api.JsonCallback;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddActivityResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.utils.ContentUtils;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * 添加Activity
 * Created by admin on 2017/8/19.
 */
public class AddActivityActivity extends BaseActivity {

    public static final int REQUEST_SELECT_PICTURE = 0x80;
    public static final int REQUEST_SELECT_PICTURE_GUIDE_ONE = 0x81;
    public static final int REQUEST_SELECT_PICTURE_GUIDE_TWO = 0x82;
    public static final int REQUEST_SELECT_PICTURE_GUIDE_THREE = 0x83;
    public static final int REQUEST_SELECT_PICTURE_GUIDE_FOUR = 0x84;
    public static final int REQUEST_SELECT_PICTURE_GUIDE_FIVE = 0x85;

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
    private LinearLayout llIsFirst;
    private CheckBox cbIsFirst;
    private LinearLayout llIsLandscape;
    private CheckBox cbIsLandscape;
    private Button btnSubmit;
    private CheckBox cbIsFullScreen;
    private ZzImageBox zibPic;

    private void assignViews() {
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
        llIsFirst = (LinearLayout) findViewById(R.id.ll_is_first);
        cbIsFirst = (CheckBox) findViewById(R.id.cb_is_first);
        llIsLandscape = (LinearLayout) findViewById(R.id.ll_is_landscape);
        cbIsLandscape = (CheckBox) findViewById(R.id.cb_is_landscape);
        cbIsFullScreen = (CheckBox) findViewById(R.id.cb_is_fullscreen);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        zibPic = (ZzImageBox) findViewById(R.id.zib_pic);
    }


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
        assignViews();
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
                translate();
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
                choosePic(REQUEST_SELECT_PICTURE);
            }
        });

        zibPic.setOnImageClickListener(new ZzImageBox.OnImageClickListener() {
            @Override
            public void onImageClick(int position, String filePath, ImageView iv) {

            }

            @Override
            public void onDeleteClick(int position, String filePath) {
                zibPic.removeImage(position);
            }

            @Override
            public void onAddClick() {
                int size = zibPic.getAllImages().size();
                switch (size) {
                    case 0:
                        choosePic(REQUEST_SELECT_PICTURE_GUIDE_ONE);
                        break;
                    case 1:
                        choosePic(REQUEST_SELECT_PICTURE_GUIDE_TWO);
                        break;
                    case 2:
                        choosePic(REQUEST_SELECT_PICTURE_GUIDE_THREE);
                        break;
                    case 3:
                        choosePic(REQUEST_SELECT_PICTURE_GUIDE_FOUR);
                        break;
                    case 4:
                        choosePic(REQUEST_SELECT_PICTURE_GUIDE_FIVE);
                        break;
                }

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
                    String words = result.getTranslations().get(0).replace("the ", "").replace("The ", "");
                    StringBuilder sb = new StringBuilder();
                    String child[] = words.split(" ");
                    for (String s : child) {
                        sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1, s.length()));
                    }
                    sb.append("Activity");
                    tvActName.setText(sb.toString());
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

    private void chooseTargetAct() {
        Intent intent = new Intent(this, ActivityManageActivity.class);
        intent.putExtra("choose", true);
        intent.putExtra("appId", appId);
        startActForResultWithIntent(intent, 0x01);
    }

    private void choosePic(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(Intent.createChooser(intent, "选择图片"), requestCode);
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
                case REQUEST_SELECT_PICTURE_GUIDE_ONE:
                    final Uri selectedUriOne = data.getData();
                    if (selectedUriOne != null) {
                        String path = ContentUtils.getRealPathFromUri(AddActivityActivity.this, selectedUriOne);
                        zibPic.addImage(path);
                    } else {
                        ToastUtils.showCustomBgToast("选择图片出错了，请选择其他图片");
                    }
                    break;
                case REQUEST_SELECT_PICTURE_GUIDE_TWO:
                    final Uri selectedUriTwo = data.getData();
                    if (selectedUriTwo != null) {
                        String path = ContentUtils.getRealPathFromUri(AddActivityActivity.this, selectedUriTwo);
                        zibPic.addImage(path);
                    } else {
                        ToastUtils.showCustomBgToast("选择图片出错了，请选择其他图片");
                    }
                    break;
                case REQUEST_SELECT_PICTURE_GUIDE_THREE:
                    final Uri selectedUriThree = data.getData();
                    if (selectedUriThree != null) {
                        String path = ContentUtils.getRealPathFromUri(AddActivityActivity.this, selectedUriThree);
                        zibPic.addImage(path);
                    } else {
                        ToastUtils.showCustomBgToast("选择图片出错了，请选择其他图片");
                    }
                    break;
                case REQUEST_SELECT_PICTURE_GUIDE_FOUR:
                    final Uri selectedUriFour = data.getData();
                    if (selectedUriFour != null) {
                        String path = ContentUtils.getRealPathFromUri(AddActivityActivity.this, selectedUriFour);
                        zibPic.addImage(path);
                    } else {
                        ToastUtils.showCustomBgToast("选择图片出错了，请选择其他图片");
                    }
                    break;
                case REQUEST_SELECT_PICTURE_GUIDE_FIVE:
                    final Uri selectedUriFive = data.getData();
                    if (selectedUriFive != null) {
                        String path = ContentUtils.getRealPathFromUri(AddActivityActivity.this, selectedUriFive);
                        zibPic.addImage(path);
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
        if (duration.length() == 0 && actType == 5) {
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
                .params("isLandscape", cbIsLandscape.isChecked())
                .params("isFullScreen", cbIsFullScreen.isChecked())
                .params("type", actType)
                .params("appId", appId)
                .params("targetActId", targetActId)
                .params("splashSecond", Integer.parseInt(duration))
                .params("userId", getUserId())
                .params("guideImgCount", zibPic.getAllImages().size())
                .isMultipart(true);
        if (splashPath != null) {
            post.params("splashImg", new File(splashPath));
        }
        for (int i = 0; i < zibPic.getAllImages().size(); i++) {
            String path = zibPic.getImagePathAt(i);
            switch (i) {
                case 0:
                    post.params("guideImgOne", new File(path));
                    break;
                case 1:
                    post.params("guideImgTwo", new File(path));
                    break;
                case 2:
                    post.params("guideImgThree", new File(path));
                    break;
                case 3:
                    post.params("guideImgFour", new File(path));
                    break;
                case 4:
                    post.params("guideImgFive", new File(path));
                    break;
            }
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
                switch (position) {
                    case 1:
                        llSplash.setVisibility(View.VISIBLE);
                        llSplashDuration.setVisibility(View.VISIBLE);
                        llTargetAct.setVisibility(View.VISIBLE);
                        zibPic.setVisibility(View.GONE);
                        tvActName.setText("SplashActivity");
                        break;
                    case 2:
                        llSplash.setVisibility(View.GONE);
                        llSplashDuration.setVisibility(View.GONE);
                        llTargetAct.setVisibility(View.VISIBLE);
                        zibPic.setVisibility(View.VISIBLE);
                        tvActName.setText("GuideActivity");
                        break;
                    default:
                        zibPic.setVisibility(View.GONE);
                        llSplash.setVisibility(View.GONE);
                        llSplashDuration.setVisibility(View.GONE);
                        llTargetAct.setVisibility(View.GONE);
                        tvActName.setText("");
                        break;
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
