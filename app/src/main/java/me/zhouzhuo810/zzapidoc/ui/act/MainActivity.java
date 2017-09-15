package me.zhouzhuo810.zzapidoc.ui.act;

import android.Manifest;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.Timer;
import java.util.TimerTask;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.fgm.AppFragment;
import me.zhouzhuo810.zzapidoc.ui.fgm.MeFragment;
import me.zhouzhuo810.zzapidoc.ui.fgm.PdfFragment;
import me.zhouzhuo810.zzapidoc.ui.fgm.ProjectFragment;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private int position;

    int[] tabPic = {
            R.drawable.ic_project,
            R.drawable.doc_normal,
            R.drawable.ic_app,
            R.drawable.me
    };
    int[] tabPicPress = {
            R.drawable.ic_project_press,
            R.drawable.doc_press,
            R.drawable.ic_app_press,
            R.drawable.me_checked
    };
    String[] tabName;

    //再按一次退出标识
    private boolean isExit = false;

    private ProjectFragment projectFragment;
    private PdfFragment pdfFragment;
    private AppFragment appFragment;
    private MeFragment meFragment;

    private LinearLayout llProject;
    private ImageView ivImageProject;
    private TextView tvNameProject;
    private LinearLayout llDoc;
    private ImageView ivImageDoc;
    private TextView tvNameDoc;
    private LinearLayout llApp;
    private ImageView ivImageApp;
    private TextView tvNameApp;
    private LinearLayout llSetting;
    private ImageView ivImageSetting;
    private TextView tvNameSetting;

    private RxPermissions rxPermissions;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        //TODO FIX ME 2016.09.22  by zz 重叠处理
        if (projectFragment == null && fragment instanceof ProjectFragment) {
            projectFragment = (ProjectFragment) fragment;
        } else if (pdfFragment == null && fragment instanceof PdfFragment) {
            pdfFragment = (PdfFragment) fragment;
        } else if (appFragment == null && fragment instanceof AppFragment) {
            appFragment = (AppFragment) fragment;
        } else if (meFragment == null && fragment instanceof MeFragment) {
            meFragment = (MeFragment) fragment;
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rxPermissions = new RxPermissions(this);

        llProject = (LinearLayout) findViewById(R.id.ll_project);
        ivImageProject = (ImageView) findViewById(R.id.iv_image_project);
        tvNameProject = (TextView) findViewById(R.id.tv_name_project);
        llDoc = (LinearLayout) findViewById(R.id.ll_doc);
        ivImageDoc = (ImageView) findViewById(R.id.iv_image_doc);
        tvNameDoc = (TextView) findViewById(R.id.tv_name_doc);
        llApp = (LinearLayout) findViewById(R.id.ll_app);
        ivImageApp = (ImageView) findViewById(R.id.iv_image_app);
        tvNameApp = (TextView) findViewById(R.id.tv_name_app);
        llSetting = (LinearLayout) findViewById(R.id.ll_setting);
        ivImageSetting = (ImageView) findViewById(R.id.iv_image_setting);
        tvNameSetting = (TextView) findViewById(R.id.tv_name_setting);
    }

    @Override
    public void initData() {
        tabName = getResources().getStringArray(R.array.tab_name);

        tvNameProject.setText(tabName[0]);
        tvNameDoc.setText(tabName[1]);
        tvNameApp.setText(tabName[2]);
        tvNameSetting.setText(tabName[3]);

        check(0);
        select(0);

        if (!rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {

                            } else {
                                ToastUtils.showCustomBgToast("您拒绝了存储权限，部分功能将不能正常使用");
                            }
                        }
                    });
        }

    }

    @Override
    public void initEvent() {
        llProject.setOnClickListener(this);
        llDoc.setOnClickListener(this);
        llApp.setOnClickListener(this);
        llSetting.setOnClickListener(this);
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


    public void check(int position) {
        ivImageProject.setImageResource(tabPic[0]);
        ivImageDoc.setImageResource(tabPic[1]);
        ivImageApp.setImageResource(tabPic[2]);
        ivImageSetting.setImageResource(tabPic[3]);
        tvNameProject.setTextColor(getResources().getColor(R.color.colorTabNormal));
        tvNameDoc.setTextColor(getResources().getColor(R.color.colorTabNormal));
        tvNameApp.setTextColor(getResources().getColor(R.color.colorTabNormal));
        tvNameSetting.setTextColor(getResources().getColor(R.color.colorTabNormal));
        switch (position) {
            case 0:
                tvNameProject.setTextColor(getResources().getColor(R.color.colorTabPress));
                ivImageProject.setImageResource(tabPicPress[0]);
                break;
            case 1:
                tvNameDoc.setTextColor(getResources().getColor(R.color.colorTabPress));
                ivImageDoc.setImageResource(tabPicPress[1]);
                break;
            case 2:
                tvNameApp.setTextColor(getResources().getColor(R.color.colorTabPress));
                ivImageApp.setImageResource(tabPicPress[2]);
                break;
            case 3:
                tvNameSetting.setTextColor(getResources().getColor(R.color.colorTabPress));
                ivImageSetting.setImageResource(tabPicPress[3]);
                break;
        }


    }

    private void select(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        hideFragment(ft);
        switch (position) {
            case 0:
                if (projectFragment == null) {
                    projectFragment = new ProjectFragment();
                    ft.add(R.id.act_main_realtabcontent, projectFragment);
                } else {
                    ft.attach(projectFragment);
                }
                break;
            case 1:
                if (pdfFragment == null) {
                    pdfFragment = new PdfFragment();
                    ft.add(R.id.act_main_realtabcontent, pdfFragment);
                } else {
                    ft.attach(pdfFragment);
                }
                break;
            case 2:
                if (appFragment == null) {
                    appFragment = new AppFragment();
                    ft.add(R.id.act_main_realtabcontent, appFragment);
                } else {
                    ft.attach(appFragment);
                }
                break;
            case 3:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    ft.add(R.id.act_main_realtabcontent, meFragment);
                } else {
                    ft.attach(meFragment);
                }
                break;
        }
        ft.commit();
    }


    private void hideFragment(FragmentTransaction ft) {
        if (projectFragment != null) {
            ft.detach(projectFragment);
        }
        if (pdfFragment != null) {
            ft.detach(pdfFragment);
        }
        if (appFragment != null) {
            ft.detach(appFragment);
        }
        if (meFragment != null) {
            ft.detach(meFragment);
        }
    }

    @Override
    public void onBackPressed() {
        if (isExit) {
            finish();
        } else {
            isExit = true;
            ToastUtils.showCustomBgToast(getString(R.string.press_angin_exit_text));
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        }
    }


    @Override
    public void saveState(Bundle bundle) {

    }

    @Override
    public void restoreState(Bundle bundle) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_project:
                if (position != 0) {
                    check(0);
                    select(0);
                    position = 0;
                }
                break;
            case R.id.ll_doc:
                if (position != 1) {
                    check(1);
                    select(1);
                    position = 1;
                }
                break;
            case R.id.ll_app:
                if (position != 2) {
                    check(2);
                    select(2);
                    position = 2;
                }
                break;
            case R.id.ll_setting:
                if (position != 3) {
                    check(3);
                    select(3);
                    position = 3;
                }
                break;
        }
    }
}
