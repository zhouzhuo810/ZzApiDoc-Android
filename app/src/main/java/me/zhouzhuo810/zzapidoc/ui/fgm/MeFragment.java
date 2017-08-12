package me.zhouzhuo810.zzapidoc.ui.fgm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.zhouzhuo.zzhttp.ZzHttp;
import me.zhouzhuo.zzhttp.callback.Callback;
import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.Constants;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.base.BaseFragment;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.SystemUtil;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.common.utils.ZSharedUtil;
import me.zhouzhuo810.zzapidoc.ui.act.LoginActivity;
import me.zhouzhuo810.zzapidoc.ui.act.RevisePswdActivity;
import me.zhouzhuo810.zzapidoc.ui.widget.roundimage.RoundedImageView;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.common.utils.ApkUtils;

/**
 * Created by zz on 2017/6/26.
 */

public class MeFragment extends BaseFragment {
    private RoundedImageView ivPhoto;
    private TextView tvName;
    private TextView tvPosition;
    private LinearLayout llNotice;
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
        llNotice = (LinearLayout) rootView.findViewById(R.id.ll_notice);
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
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text)+e.toString());
                    }

                    @Override
                    public void onNext(final UpdateResult updateResult) {
                        getBaseAct().hidePd();
                        ToastUtils.showCustomBgToast(updateResult.getMsg());
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
            }
        });
        ZzHttp.getInstance().download(Constants.SERVER_IP + address,
                Constants.APK_DOWNLOAD_DIR, new Callback.ProgressDownloadCallback() {
                    @Override
                    public void onProgress(float progress, int currentSize, int totalSize) {
                        if (tv[0] != null) {
                            tv[0].setText("已下载 " +((int)progress)+"%");
                        }
                        if (pb[0] != null) {
                            pb[0].setProgress(((int)(progress+0.5)));
                        }

                    }

                    @Override
                    public void onSuccess(String result) {
                        if (tv[0] != null) {
                            tv[0].setText("下载完成！");
                        }
                        getBaseAct().hideUpdateDialog();
                        installApk(Constants.APK_DOWNLOAD_DIR, "ZzApiDoc_"+versionName+".apk");
                    }

                    @Override
                    public void onFailure(String error) {
                        if (tv[0] != null) {
                            tv[0].setText("下载出错了！"+error);
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
