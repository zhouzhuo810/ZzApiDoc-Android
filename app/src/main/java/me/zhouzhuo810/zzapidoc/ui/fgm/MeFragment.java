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
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.Constants;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.base.BaseFragment;
import me.zhouzhuo810.zzapidoc.common.utils.ZSharedUtil;
import me.zhouzhuo810.zzapidoc.ui.act.LoginActivity;
import me.zhouzhuo810.zzapidoc.ui.act.RevisePswdActivity;
import me.zhouzhuo810.zzapidoc.ui.widget.roundimage.RoundedImageView;

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

        llPswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RevisePswdActivity.class);
                startActivityForResult(intent, 0x01);
            }
        });
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
