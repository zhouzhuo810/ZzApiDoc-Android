package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetRequestArgResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.InterfaceTestEntity;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * Created by admin on 2017/8/12.
 */

public class InterfaceTestActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private SwipeRefreshLayout refresh;
    private LinearLayout llScroll;
    private TextView tvPath;
    private TextView tvMethod;

    private ArrayList<InterfaceTestEntity> params;
    private String interfaceId;
    private String projectId;
    private String ip;
    private String method;
    private String path;


    @Override
    public int getLayoutId() {
        return R.layout.activity_interface_test;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        llScroll = (LinearLayout) findViewById(R.id.ll_scroll);
        tvPath = (TextView) findViewById(R.id.tv_path);
        tvMethod = (TextView) findViewById(R.id.tv_method);
    }

    @Override
    public void initData() {
        params = new ArrayList<>();
        interfaceId = getIntent().getStringExtra("interfaceId");
        projectId = getIntent().getStringExtra("projectId");
        method = getIntent().getStringExtra("method");
        ip = getIntent().getStringExtra("ip");
        path = getIntent().getStringExtra("path");
        tvPath.setText(ip+path);
        tvMethod.setText(method);

        startRefresh(refresh);
        getData();
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
                Intent intent = new Intent(InterfaceTestActivity.this, InterfaceTestResultActivity.class);
                intent.putExtra("params", params);
                intent.putExtra("method", method);
                intent.putExtra("path", ip+path);
                startActWithIntent(intent);
            }
        });
    }


    private void getData() {
        Api.getApi0()
                .getRequestArgByInterfaceIdAndPid(interfaceId, "0", getUserId())
                .compose(RxHelper.<GetRequestArgResult>io_main())
                .subscribe(new Subscriber<GetRequestArgResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh(refresh);
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(GetRequestArgResult getAllInterfaceGroupResult) {
                        stopRefresh(refresh);
                        if (getAllInterfaceGroupResult.getCode() == 1) {
                            List<GetRequestArgResult.DataBean> data = getAllInterfaceGroupResult.getData();
                            generateParams(data);
                            generateLayouts();
                        }
                    }
                });
    }

    private void generateLayouts() {
        if (params != null) {
            llScroll.removeAllViews();
            for (int i = 0; i < params.size(); i++) {
                InterfaceTestEntity entity = params.get(i);
                addItem(i, entity.getName(), entity.getValue());
            }
        }
    }

    private void generateParams(List<GetRequestArgResult.DataBean> data) {
        if (data != null) {
            params.clear();
            for (GetRequestArgResult.DataBean bean : data) {
                InterfaceTestEntity entity = new InterfaceTestEntity();
                entity.setType(bean.getType());
                entity.setName(bean.getName());
                entity.setValue(null);
                params.add(entity);
            }
        }
    }

    private void addItem(final int position, String title, String value) {
        View root = LayoutInflater.from(InterfaceTestActivity.this).inflate(R.layout.interface_test_item, null);
        TextView tvName = (TextView) root.findViewById(R.id.tv_name);
        final EditText etValue = (EditText) root.findViewById(R.id.et_value);
        final ImageView ivClearValue = (ImageView) root.findViewById(R.id.iv_clear_value);
        ivClearValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etValue.setText("");
            }
        });
        etValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    ivClearValue.setVisibility(View.VISIBLE);
                } else {
                    ivClearValue.setVisibility(View.GONE);
                }
                params.get(position).setValue(s.toString());
            }
        });

        tvName.setText(title);
        etValue.setText(value);
        AutoUtils.auto(root);
        llScroll.addView(root, position);

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
