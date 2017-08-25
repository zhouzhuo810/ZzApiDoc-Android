package me.zhouzhuo810.zzapidoc.ui.act;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Arrays;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.ZApplication;
import me.zhouzhuo810.zzapidoc.common.Constants;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.JsonCallback;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddApplicationResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddProjectResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */
public class AddApplicationActivity extends BaseActivity {

    public static final int REQUEST_SELECT_PICTURE = 0x80;

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private EditText etAppName;
    private ImageView ivClearAppName;
    private EditText etPackageName;
    private ImageView ivClearPackageName;
    private EditText etVercionCode;
    private ImageView ivClearVercionCode;
    private EditText etVercionName;
    private ImageView ivClearVercionName;
    private EditText etMinSdk;
    private ImageView ivClearMinSdk;
    private EditText etTargetSdk;
    private ImageView ivClearTargetSdk;
    private EditText etCompileSdk;
    private ImageView ivClearCompileSdk;
    private EditText etMainColor;
    private ImageView ivClearMainColor;
    private LinearLayout llApi;
    private TextView tvApi;
    private LinearLayout llMultidex;
    private TextView tvMultidex;
    private LinearLayout llMinify;
    private TextView tvMinify;
    private EditText etPs;
    private ImageView ivClearPs;
    private Button btnSubmit;
    private ImageView ivLogo;

    private String apiId;
    private String logoPath;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_application;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        etAppName = (EditText) findViewById(R.id.et_app_name);
        ivClearAppName = (ImageView) findViewById(R.id.iv_clear_app_name);
        etPackageName = (EditText) findViewById(R.id.et_package_name);
        ivClearPackageName = (ImageView) findViewById(R.id.iv_clear_package_name);
        etVercionCode = (EditText) findViewById(R.id.et_vercion_code);
        ivClearVercionCode = (ImageView) findViewById(R.id.iv_clear_vercion_code);
        etVercionName = (EditText) findViewById(R.id.et_vercion_name);
        ivClearVercionName = (ImageView) findViewById(R.id.iv_clear_vercion_name);
        etMinSdk = (EditText) findViewById(R.id.et_min_sdk);
        ivClearMinSdk = (ImageView) findViewById(R.id.iv_clear_min_sdk);
        etTargetSdk = (EditText) findViewById(R.id.et_target_sdk);
        ivClearTargetSdk = (ImageView) findViewById(R.id.iv_clear_target_sdk);
        etCompileSdk = (EditText) findViewById(R.id.et_compile_sdk);
        ivClearCompileSdk = (ImageView) findViewById(R.id.iv_clear_compile_sdk);
        etMainColor = (EditText) findViewById(R.id.et_main_color);
        ivClearMainColor = (ImageView) findViewById(R.id.iv_clear_main_color);
        llApi = (LinearLayout) findViewById(R.id.ll_api);
        tvApi = (TextView) findViewById(R.id.tv_api);
        llMultidex = (LinearLayout) findViewById(R.id.ll_multidex);
        tvMultidex = (TextView) findViewById(R.id.tv_multidex);
        llMinify = (LinearLayout) findViewById(R.id.ll_minify);
        tvMinify = (TextView) findViewById(R.id.tv_minify);
        etPs = (EditText) findViewById(R.id.et_ps);
        ivClearPs = (ImageView) findViewById(R.id.iv_clear_ps);
        ivLogo = (ImageView) findViewById(R.id.iv_logo);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });

        rlRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AddApplicationActivity.this, ImportProjectActivity.class);
//                startActWithIntent(intent);
            }
        });

        llApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseApi();
            }
        });

        llMinify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseMinify();
            }
        });

        llMultidex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseMiltidex();
            }
        });

        setEditListener(etAppName, ivClearAppName);
        setEditListener(etCompileSdk, ivClearCompileSdk);
        setEditListener(etMainColor, ivClearMainColor);
        setEditListener(etMinSdk, ivClearMinSdk);
        setEditListener(etPs, ivClearPs);
        setEditListener(etTargetSdk, ivClearTargetSdk);
        setEditListener(etVercionCode, ivClearVercionCode);
        setEditListener(etVercionName, ivClearVercionName);

        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addApp();
            }
        });

    }

    private void choosePic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_SELECT_PICTURE);
    }

    private void chooseMinify() {
        showListDialog(Arrays.asList("false", "true"), true, null, new OnItemClick() {
            @Override
            public void onItemClick(int position, String content) {
                tvMinify.setText(content);
            }
        });
    }

    private void chooseMiltidex() {
        showListDialog(Arrays.asList("false", "true"), true, null, new OnItemClick() {
            @Override
            public void onItemClick(int position, String content) {
                tvMultidex.setText(content);
            }
        });
    }

    private void chooseApi() {
        Intent intent = new Intent(this, ApiChooseActivity.class);
        startActForResultWithIntent(intent, 0x01);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x01:
                    apiId = data.getStringExtra("id");
                    String name = data.getStringExtra("name");
                    tvApi.setText(name);
                    break;
                case REQUEST_SELECT_PICTURE:
                    final Uri selectedUri = data.getData();
                    if (selectedUri != null) {
                        startCropActivity(data.getData());
                    } else {
                        ToastUtils.showCustomBgToast("选择图片出错了，请选择其他图片");
                    }
                    break;
                case UCrop.REQUEST_CROP:
                    handleCropResult(data);
                    break;
            }
        }
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            logoPath = resultUri.getPath();
            ivLogo.setImageURI(resultUri);
        } else {
            ToastUtils.showCustomBgToast(getString(R.string.crop_error));
        }
    }

    private void startCropActivity(@NonNull Uri uri) {
        File dir = new File(Constants.PIC_CROP_ROOT_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String destinationFileName = Constants.PIC_CROP_ROOT_PATH + "ic_launcher_"+System.currentTimeMillis()%1000+".png";

        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(getResources().getColor(R.color.colorMain));
        UCrop.of(uri, Uri.parse("file://" + destinationFileName)).withOptions(options)
                .withAspectRatio(1, 1).start(this);
    }

    private void addApp() {
        String name = etAppName.getText().toString().trim();
        String versionCode = etVercionCode.getText().toString().trim();
        String versionName = etVercionName.getText().toString().trim();
        String packageName = etPackageName.getText().toString().trim();
        String minSDK = etMinSdk.getText().toString().trim();
        String targetSDK = etTargetSdk.getText().toString().trim();
        String compileSDK = etCompileSdk.getText().toString().trim();
        String mainColor = etMainColor.getText().toString().trim();
        String ps = etPs.getText().toString().trim();
        String minify = tvMinify.getText().toString().trim();
        String multidex = tvMultidex.getText().toString().trim();
        if (name.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.app_name_not_nul_text));
            return;
        }
        if (packageName.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.package_name_not_nul_text));
            return;
        }
        if (versionCode.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.versioncode_not_nul_text));
            return;
        }
        if (versionName.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.versionname_not_nul_text));
            return;
        }
        if (minSDK.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.minsdk_not_nul_text));
            return;
        }
        if (targetSDK.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.targetsdk_not_nul_text));
            return;
        }
        if (compileSDK.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.compilesdk_not_nul_text));
            return;
        }
        showPd(getString(R.string.submiting_text), false);
        PostRequest<AddApplicationResult> post = OkGo.<AddApplicationResult>post(SharedUtil.getString(ZApplication.getInstance(), "server_config")
                + "/ZzApiDoc/v1/application/addApplication")
                .params("appName", name)
                .params("versionName", versionName)
                .params("packageName", packageName)
                .params("colorMain", mainColor)
                .params("minSDK", minSDK)
                .params("compileSDK", compileSDK)
                .params("targetSDK", targetSDK)
                .params("versionCode", versionCode)
                .params("multiDex", multidex.equals("true"))
                .params("minifyEnabled", minify.equals("true"))
                .params("apiId", apiId)
                .params("userId", getUserId())
                .isMultipart(true);
        if (logoPath != null) {
            post.params("logo", new File(logoPath));
        }
        post.execute(new JsonCallback<AddApplicationResult>(AddApplicationResult.class) {
            @Override
            public void onSuccess(Response<AddApplicationResult> response) {
                AddApplicationResult body = response.body();
                hidePd();
                ToastUtils.showCustomBgToast(body.getMsg());
                if (body.getCode() == 1) {
                    closeAct();
                }
            }

            @Override
            public void onError(Response<AddApplicationResult> response) {
                super.onError(response);
                hidePd();
                ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + (response.getException() == null ? "" : response.getException().toString()));
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
