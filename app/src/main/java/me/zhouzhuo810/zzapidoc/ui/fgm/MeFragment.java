package me.zhouzhuo810.zzapidoc.ui.fgm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.Arrays;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.ZApplication;
import me.zhouzhuo810.zzapidoc.common.Constants;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.base.BaseFragment;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;
import me.zhouzhuo810.zzapidoc.common.utils.SystemUtil;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.common.utils.ZSharedUtil;
import me.zhouzhuo810.zzapidoc.ui.act.LoginActivity;
import me.zhouzhuo810.zzapidoc.ui.act.PublishVersionActivity;
import me.zhouzhuo810.zzapidoc.ui.act.RevisePswdActivity;
import me.zhouzhuo810.zzapidoc.ui.widget.roundimage.RoundedImageView;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.common.utils.ApkUtils;
import zhouzhuo810.me.zzandframe.common.utils.FileUtils;

/**
 * Created by zz on 2017/6/26.
 */

public class MeFragment extends BaseFragment {
    private RoundedImageView ivPhoto;
    private TextView tvName;
    private TextView tvPosition;
    private LinearLayout llVersion;
    private LinearLayout llClear;
    private LinearLayout llUpdate;
    private LinearLayout llAbout;
    private LinearLayout llSetting;
    private LinearLayout llPswd;
    private Button btnExit;


    @Override
    public int getLayoutId() {
        return R.layout.fgm_me;
    }

    @Override
    public void initView() {
        ivPhoto = (RoundedImageView) rootView.findViewById(R.id.iv_photo);
        tvName = (TextView) rootView.findViewById(R.id.tv_name);
        tvPosition = (TextView) rootView.findViewById(R.id.tv_position);
        llVersion = (LinearLayout) rootView.findViewById(R.id.ll_version_publish);
        llClear = (LinearLayout) rootView.findViewById(R.id.ll_clear);
        llUpdate = (LinearLayout) rootView.findViewById(R.id.ll_update);
        llAbout = (LinearLayout) rootView.findViewById(R.id.ll_about);
        llSetting = (LinearLayout) rootView.findViewById(R.id.ll_setting);
        llPswd = (LinearLayout) rootView.findViewById(R.id.ll_pswd);
        btnExit = (Button) rootView.findViewById(R.id.btn_exit);
    }

    @Override
    public void initData() {
        String photo = ZSharedUtil.getPicUrl();
        String name = ZSharedUtil.getRealName();
        String position = ZSharedUtil.getPosition();
        Glide.with(this).load(Constants.PIC_HEAD + photo).crossFade().error(R.drawable.photo_me).placeholder(R.drawable.photo_me).into(ivPhoto);
        tvName.setText(name);
        tvPosition.setText(position);
    }

    @Override
    public void initEvent() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((BaseActivity) getActivity()).showTwoBtnDialog("提示", "确定退出登录吗？", true, new BaseActivity.OnTwoBtnClick() {
                    @Override
                    public void onOk() {
                        exitLogin();
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        });

        llClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCache();
            }
        });

        llVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publish();
            }
        });

        llUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUpdate();
            }
        });

        llPswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RevisePswdActivity.class);
                startActivityForResult(intent, 0x01);
            }
        });
    }

    private void clearCache() {
        getBaseAct().showListDialog(Arrays.asList("清理JSON文件", "清理PDF文件", "清理APK文件"), false, null, new BaseActivity.OnItemClick() {
            @Override
            public void onItemClick(int position, String content) {
                switch (position) {
                    case 0:
                        FileUtils.deleteFiles(Constants.EXPORT_PATH);
                        break;
                    case 1:
                        FileUtils.deleteFiles(Constants.EXPORT_PDF_PATH);
                        break;
                    case 2:
                        FileUtils.deleteFiles(Constants.APK_DOWNLOAD_DIR);
                        break;
                }
                ToastUtils.showCustomBgToast(getString(R.string.clear_ok));
            }
        });
    }

    private void publish() {
        Intent intent = new Intent(getActivity(), PublishVersionActivity.class);
        startActivity(intent);
    }

    private void checkUpdate() {
        getBaseAct().showPd(getString(R.string.checking_text), false);
        Api.getApi0()
                .checkUpdate(SystemUtil.getPackageInfo(getActivity()).versionCode)
                .compose(RxHelper.<UpdateResult>io_main())
                .subscribe(new Subscriber<UpdateResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getBaseAct().hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(final UpdateResult updateResult) {
                        getBaseAct().hidePd();
                        if (updateResult.getCode() == 1) {
                            getBaseAct().showTwoBtnDialog("更新", updateResult.getData().getUpdateInfo(), true, new BaseActivity.OnTwoBtnClick() {
                                @Override
                                public void onOk() {
                                    downloadApk(updateResult.getData().getVersionName(), updateResult.getData().getAddress());
                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                        } else {
                            ToastUtils.showCustomBgToast(updateResult.getMsg());
                        }
                    }
                });

    }

    private void downloadApk(final String versionName, String address) {
        final TextView[] tv = {null};
        final ProgressBar[] pb = {null};
        getBaseAct().showUpdateDialog("更新", "已下载 0%", false, new BaseActivity.OnOneBtnClickListener() {
            @Override
            public void onProgress(TextView tvProgress, ProgressBar pro) {
                tv[0] = tvProgress;
                pb[0] = pro;
            }

            @Override
            public void onOK() {
                getBaseAct().hideUpdateDialog();
            }
        });
        OkGo.<File>get(SharedUtil.getString(ZApplication.getInstance(), "server_config") + address)
                .tag(getActivity())
                .execute(new FileCallback(Constants.APK_DOWNLOAD_DIR, "ZzApiDoc_" + versionName + ".apk") {
                    @Override
                    public void onSuccess(Response<File> response) {
                        if (tv[0] != null) {
                            tv[0].setText("下载完成！");
                        }
                        getBaseAct().hideUpdateDialog();
                        installApk(Constants.APK_DOWNLOAD_DIR, "ZzApiDoc_" + versionName + ".apk");
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        if (tv[0] != null) {
                            tv[0].setText("下载出错了！" + response.getException() == null ? "" : response.getException().toString());
                        }
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        int pro = (int) (progress.currentSize * 100.0f / progress.totalSize + 0.5f);
                        if (tv[0] != null) {
                            tv[0].setText("已下载 " + pro + "%");
                        }
                        if (pb[0] != null) {
                            pb[0].setProgress((pro));
                        }
                    }
                });
    }

    private void installApk(String s, String name) {
        ApkUtils.installApk(getActivity(), "me.zhouzhuo810.zzapidoc", s, name);
    }

    private void exitLogin() {
        ZSharedUtil.savePswd("");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getBaseAct().startActWithIntent(intent);
        getBaseAct().closeAct();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 0x01:
                    exitLogin();
                    break;
            }
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void saveState(Bundle bundle) {

    }

    @Override
    public void restoreState(@Nullable Bundle bundle) {

    }

    @Override
    public void destroyView() {

    }

}
