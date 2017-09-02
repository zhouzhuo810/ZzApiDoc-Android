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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

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

/**
 * Created by admin on 2017/8/19.
 */
public class AddActivityActivity extends BaseActivity {

    public static final int REQUEST_SELECT_PICTURE = 0x80;

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llSplash;
    private ImageView ivSplash;
    private LinearLayout llSplashDuration;
    private EditText etSplashDuration;
    private ImageView ivClearSplashDuration;
    private LinearLayout llTargetAct;
    private TextView tvTargetAct;
    private EditText etActName;
    private ImageView ivClearActName;
    private EditText etActTitle;
    private ImageView ivClearActTitle;
    private LinearLayout llActType;
    private TextView tvActType;
    private LinearLayout llShowTitle;
    private TextView tvShowTitle;
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
        llSplash = (LinearLayout) findViewById(R.id.ll_splash);
        ivSplash = (ImageView) findViewById(R.id.iv_splash);
        llSplashDuration = (LinearLayout) findViewById(R.id.ll_splash_duration);
        etSplashDuration = (EditText) findViewById(R.id.et_splash_duration);
        ivClearSplashDuration = (ImageView) findViewById(R.id.iv_clear_splash_duration);
        llTargetAct = (LinearLayout) findViewById(R.id.ll_target_act);
        tvTargetAct = (TextView) findViewById(R.id.tv_target_act);
        etActName = (EditText) findViewById(R.id.et_act_name);
        ivClearActName = (ImageView) findViewById(R.id.iv_clear_act_name);
        etActTitle = (EditText) findViewById(R.id.et_act_title);
        ivClearActTitle = (ImageView) findViewById(R.id.iv_clear_act_title);
        llActType = (LinearLayout) findViewById(R.id.ll_act_type);
        tvActType = (TextView) findViewById(R.id.tv_act_type);
        llShowTitle = (LinearLayout) findViewById(R.id.ll_show_title);
        tvShowTitle = (TextView) findViewById(R.id.tv_show_title);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void initData() {
        appId = getIntent().getStringExtra("appId");
        tvShowTitle.setText("true");
    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });
        setEditListener(etActName, ivClearActName);
        setEditListener(etActTitle, ivClearActTitle);
        setEditListener(etSplashDuration, ivClearSplashDuration);

        llTargetAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTargetAct();
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

        llShowTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseShowTitle();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAct();
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
        String name = etActName.getText().toString().trim();
        String title = etActTitle.getText().toString().trim();
        String showTitle = tvShowTitle.getText().toString().trim();
        String duration = etSplashDuration.getText().toString().trim();
        if (duration.length()==0 && actType == 5) {
            ToastUtils.showCustomBgToast("启动时间不能为空");
            return;
        }
        Log.e("TTT", "splashPath=" + splashPath);
        showPd(getString(R.string.submiting_text), false);
        PostRequest<AddActivityResult> post = OkGo.<AddActivityResult>post(SharedUtil.getString(ZApplication.getInstance(), "server_config")
                + "/ZzApiDoc/v1/activity/addActivity")
                .params("name", name)
                .params("title", title)
                .params("showTitle", showTitle.equals("true"))
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

    private void chooseShowTitle() {
        showListDialog(Arrays.asList("true", "false"), true, null, new OnItemClick() {
            @Override
            public void onItemClick(int position, String content) {
                tvShowTitle.setText(content);
            }
        });
    }

    private void chooseActType() {
        List<String> items = new ArrayList<>();
        items.add("TitleBar+ListView");
        items.add("TitleBar+RecyclerView");
        items.add("TitleBar+BottomTabBar+FrameLayout");
        items.add("TitleBar+TopTabBar+ViewPager");
        items.add("TitleBar+SettingItem");
        items.add("SplashActivity");
        items.add("TitleBar+items+Submit");
        items.add("TitleBar+items");
        showListDialog(items, true, null, new OnItemClick() {
            @Override
            public void onItemClick(int position, String content) {
                if (position == 5) {
                    llSplash.setVisibility(View.VISIBLE);
                    llSplashDuration.setVisibility(View.VISIBLE);
                    llTargetAct.setVisibility(View.VISIBLE);
                    etActName.setText("SplashActivity");
                    tvShowTitle.setText("false");
                } else {
                    llSplash.setVisibility(View.GONE);
                    llSplashDuration.setVisibility(View.GONE);
                    llTargetAct.setVisibility(View.GONE);
                    etActName.setText("");
                    tvShowTitle.setText("true");
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
