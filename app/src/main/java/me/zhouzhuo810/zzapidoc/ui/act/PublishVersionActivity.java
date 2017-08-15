package me.zhouzhuo810.zzapidoc.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.OnDismissListener;

import java.util.Calendar;
import java.util.Date;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.PublishVersionEntity;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.DateUtil;
import me.zhouzhuo810.zzapidoc.common.utils.SystemUtil;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * 版本发布
 * Created by zhouzhuo810 on 2017/8/15.
 */
public class PublishVersionActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llReleaseDate;
    private TextView tvReleaseDate;
    private EditText etVersionCode;
    private ImageView ivClearVersionCode;
    private EditText etVersionName;
    private ImageView ivClearVersionName;
    private EditText etUpdateInfo;
    private ImageView ivClearUpdateInfo;
    private Button btnSubmit;
    private boolean show;


    @Override
    public int getLayoutId() {
        return R.layout.activity_version_publish;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llReleaseDate = (LinearLayout) findViewById(R.id.ll_release_date);
        tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        etVersionCode = (EditText) findViewById(R.id.et_version_code);
        ivClearVersionCode = (ImageView) findViewById(R.id.iv_clear_version_code);
        etVersionName = (EditText) findViewById(R.id.et_version_name);
        ivClearVersionName = (ImageView) findViewById(R.id.iv_clear_version_name);
        etUpdateInfo = (EditText) findViewById(R.id.et_update_info);
        ivClearUpdateInfo = (ImageView) findViewById(R.id.iv_clear_update_info);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void initData() {
        ivClearUpdateInfo.setVisibility(View.VISIBLE);
        ivClearVersionCode.setVisibility(View.VISIBLE);
        ivClearVersionName.setVisibility(View.VISIBLE);
        tvReleaseDate.setText(DateUtil.formatDateToYMdHms(new Date()));
        int versionCode = SystemUtil.getPackageInfo(this).versionCode;
        String versionName = SystemUtil.getPackageInfo(this).versionName;
        etVersionCode.setText((versionCode + 1) + "");
        etVersionName.setText(versionName.substring(0, versionName.lastIndexOf(".") + 1) + (Integer.parseInt(versionName.substring(versionName.lastIndexOf(".") + 1, versionName.length())) + 1));
        etUpdateInfo.setText("\n 1.修复了若干bug");
    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });

        setEditListener(etUpdateInfo, ivClearUpdateInfo);
        setEditListener(etVersionCode, ivClearVersionCode);
        setEditListener(etVersionName, ivClearVersionName);

        chooseDate(llReleaseDate, tvReleaseDate);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void chooseDate(LinearLayout ll, final TextView tv) {
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!show) {
                    show = true;
                    //时间选择器
                    TimePickerView pvTime = new TimePickerView.Builder(PublishVersionActivity.this, new TimePickerView.OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {//选中事件回调
                            tv.setText(DateUtil.formatDateToYMdHms(date));
                        }
                    })
                            .setType(new boolean[]{true, true, true, true, true, true})
                            .build();
                    pvTime.setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(Object o) {
                            show = false;
                        }
                    });
                    pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                    pvTime.show();
                }
            }
        });
    }


    private void submit() {
        String versionCode = etVersionCode.getText().toString().trim();
        String versionName = etVersionName.getText().toString().trim();
        String info = etUpdateInfo.getText().toString().trim();
        String time = tvReleaseDate.getText().toString().trim();

        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .publishVersion(versionCode, versionName, info, time, getUserId())
                .compose(RxHelper.<PublishVersionEntity>io_main())
                .subscribe(new Subscriber<PublishVersionEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(PublishVersionEntity publishVersionEntity) {
                        hidePd();
                        ToastUtils.showCustomBgToast(publishVersionEntity.getMsg());
                        if (publishVersionEntity.getCode() == 1) {
                            closeAct();
                        }
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
