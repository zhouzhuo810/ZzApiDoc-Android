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
import me.zhouzhuo810.zzapidoc.ui.fgm.MeFragment;
import me.zhouzhuo810.zzapidoc.ui.fgm.ProjectFragment;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private int position;

    int[] tabPic = {
            R.drawable.ic_project,
            R.drawable.me
    };
    int[] tabPicPress = {
            R.drawable.ic_project_press,
            R.drawable.me_checked
    };
    String[] tabName;

    //再按一次退出标识
    private boolean isExit = false;

    private ProjectFragment projectFragment;
    private MeFragment meFragment;

    private LinearLayout llWork;
    private LinearLayout llMe;
    private ImageView ivWork;
    private ImageView ivMe;
    private TextView tvWork;
    private TextView tvMe;
    private RxPermissions rxPermissions;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        //TODO FIX ME 2016.09.22  by zz 重叠处理
        if (projectFragment == null && fragment instanceof ProjectFragment) {
            projectFragment = (ProjectFragment) fragment;
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

        llWork = (LinearLayout) findViewById(R.id.ll_project);
        llMe = (LinearLayout) findViewById(R.id.ll_setting);
        ivWork = (ImageView) findViewById(R.id.tab_image_project);
        ivMe = (ImageView) findViewById(R.id.tab_image_setting);
        tvWork = (TextView) findViewById(R.id.tab_name_project);
        tvMe = (TextView) findViewById(R.id.tab_name_setting);
    }

    @Override
    public void initData() {
        tabName = getResources().getStringArray(R.array.tab_name);

        tvWork.setText(tabName[0]);
        tvMe.setText(tabName[1]);

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
        llWork.setOnClickListener(this);
        llMe.setOnClickListener(this);
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
        ivWork.setImageResource(tabPic[0]);
        ivMe.setImageResource(tabPic[1]);
        tvWork.setTextColor(getResources().getColor(R.color.colorTabNormal));
        tvMe.setTextColor(getResources().getColor(R.color.colorTabNormal));
        switch (position) {
            case 0:
                tvWork.setTextColor(getResources().getColor(R.color.colorTabPress));
                ivWork.setImageResource(tabPicPress[0]);
                break;
            case 1:
                tvMe.setTextColor(getResources().getColor(R.color.colorTabPress));
                ivMe.setImageResource(tabPicPress[1]);
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
            case R.id.ll_setting:
                if (position != 1) {
                    check(1);
                    select(1);
                    position = 1;
                }
                break;
        }
    }
}
