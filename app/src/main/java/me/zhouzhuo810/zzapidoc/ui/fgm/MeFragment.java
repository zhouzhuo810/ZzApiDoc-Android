package me.zhouzhuo810.zzapidoc.ui.fgm;

import android.content.Intent;
import android.os.Bundle;
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
    private Button btnExit;

    @Override
    public View getViews(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fgm_me, container, false);
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
        btnExit = (Button) rootView.findViewById(R.id.btn_exit);
    }

    @Override
    public void initData() {
        String photo = ZSharedUtil.getPicUrl();
        String name = ZSharedUtil.getRealName();
        String position = ZSharedUtil.getPosition();
        Glide.with(this).load(Constants.PIC_HEAD+photo).crossFade().error(R.drawable.photo_me).placeholder(R.drawable.photo_me).into(ivPhoto);
        tvName.setText(name);
        tvPosition.setText(position);
    }

    @Override
    public void initEvent() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((BaseActivity)getActivity()).showTwoBtnDialog("提示", "确定退出登录吗？", true, new BaseActivity.OnTwoBtnClick() {
                    @Override
                    public void onOk() {
                        ZSharedUtil.savePswd("");
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActWithIntent(intent);
                        ((BaseActivity)getActivity()).closeAct();
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        });
    }

    @Override
    public void onSaveState(Bundle bundle) {

    }

    @Override
    public void onStateRestored(Bundle bundle) {

    }
}
