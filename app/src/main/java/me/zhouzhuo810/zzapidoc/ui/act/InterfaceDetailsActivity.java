package me.zhouzhuo810.zzapidoc.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.ZApplication;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetInterfaceDetailsResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.CopyUtils;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * 接口详情
 * Created by admin on 2017/8/14.
 */
public class InterfaceDetailsActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llScroll;
    private TextView tvName;
    private TextView tvMethod;
    private TextView tvPath;
    private LinearLayout llGlobalHeaders;
    private LinearLayout llHeaders;
    private LinearLayout llGlobalReq;
    private LinearLayout llReq;
    private LinearLayout llGlobalRes;
    private LinearLayout llRes;
    private TextView tvResult;
    private SwipeRefreshLayout refresh;
    private String interfaceId;


    @Override
    public int getLayoutId() {
        return R.layout.activity_interface_details;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llScroll = (LinearLayout) findViewById(R.id.ll_scroll);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvMethod = (TextView) findViewById(R.id.tv_method);
        tvPath = (TextView) findViewById(R.id.tv_path);
        llGlobalHeaders = (LinearLayout) findViewById(R.id.ll_global_headers);
        llHeaders = (LinearLayout) findViewById(R.id.ll_headers);
        llGlobalReq = (LinearLayout) findViewById(R.id.ll_global_req);
        llReq = (LinearLayout) findViewById(R.id.ll_req);
        llGlobalRes = (LinearLayout) findViewById(R.id.ll_global_res);
        llRes = (LinearLayout) findViewById(R.id.ll_res);
        tvResult = (TextView) findViewById(R.id.tv_result);
    }

    @Override
    public void initData() {
        interfaceId = getIntent().getStringExtra("interfaceId");
        startRefresh(refresh);
        getData();
    }

    private void getData() {
        Api.getApi0()
                .getInterfaceDetails(interfaceId, getUserId())
                .compose(RxHelper.<GetInterfaceDetailsResult>io_main())
                .subscribe(new Subscriber<GetInterfaceDetailsResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh(refresh);
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(GetInterfaceDetailsResult getInterfaceDetailsResult) {
                        stopRefresh(refresh);
                        if (getInterfaceDetailsResult.getCode() == 1) {
                            updateData(getInterfaceDetailsResult.getData());
                        } else {
                            ToastUtils.showCustomBgToast(getInterfaceDetailsResult.getMsg());
                        }

                    }
                });
    }

    private void updateData(GetInterfaceDetailsResult.DataBean data) {
        if (data == null) {
            return;
        }
        tvName.setText(data.getName());
        tvMethod.setText(data.getHttpMethod());
        tvPath.setText(data.getPath());
        fillGlobalHeaders(data.getGlobalrequestHeader());
        fillHeaders(data.getRequestHeader());
        fillGlobalReq(data.getGlobalRequestArgs());
        fillReq(data.getRequestArgs());
        fillGlobalRes(data.getGlobalResponseArgs());
        fillRes(data.getResponseArgs());
        tvResult.setText(data.getExample());
    }

    private void fillRes(List<GetInterfaceDetailsResult.DataBean.ResponseArgsBean> responseArgs) {
        if (responseArgs == null) {
            return;
        }
        llRes.removeAllViews();
        for (GetInterfaceDetailsResult.DataBean.ResponseArgsBean responseArg : responseArgs) {
            addItem(llRes, 0, responseArg.getName(), "",responseArg.getNote());
        }
    }

    private void fillGlobalRes(List<GetInterfaceDetailsResult.DataBean.GlobalResponseArgsBean> globalResponseArgs) {
        if (globalResponseArgs == null) {
            return;
        }
        llGlobalRes.removeAllViews();
        for (GetInterfaceDetailsResult.DataBean.GlobalResponseArgsBean globalResponseArg : globalResponseArgs) {
            addItem(llGlobalRes, 0, globalResponseArg.getName(), "",globalResponseArg.getNote());
        }
    }

    private void fillReq(List<GetInterfaceDetailsResult.DataBean.RequestArgsBean> requestArgs) {
        if (requestArgs == null) {
            return;
        }
        llReq.removeAllViews();
        for (GetInterfaceDetailsResult.DataBean.RequestArgsBean requestArg : requestArgs) {
            addItem(llReq, 0, requestArg.getName(), "",requestArg.getNote());
        }
    }

    private void fillGlobalReq(List<GetInterfaceDetailsResult.DataBean.GlobalRequestArgsBean> globalRequestArgs) {
        if (globalRequestArgs == null) {
            return;
        }
        llGlobalReq.removeAllViews();
        for (GetInterfaceDetailsResult.DataBean.GlobalRequestArgsBean globalRequestArg : globalRequestArgs) {
            addItem(llGlobalReq, 0, globalRequestArg.getName(), "",globalRequestArg.getNote());
        }
    }

    private void fillHeaders(List<GetInterfaceDetailsResult.DataBean.RequestHeaderBean> requestHeader) {
        if (requestHeader == null) {
            return;
        }
        llHeaders.removeAllViews();
        for (GetInterfaceDetailsResult.DataBean.RequestHeaderBean requestHeaderBean : requestHeader) {
            addItem(llHeaders, 0, requestHeaderBean.getName(), requestHeaderBean.getValue()
                    ,requestHeaderBean.getNote());
        }
    }

    private void fillGlobalHeaders(List<GetInterfaceDetailsResult.DataBean.GlobalrequestHeaderBean> globalrequestHeader) {
        if (globalrequestHeader == null) {
            return;
        }
        llGlobalHeaders.removeAllViews();
        for (GetInterfaceDetailsResult.DataBean.GlobalrequestHeaderBean globalrequestHeaderBean : globalrequestHeader) {
            addItem(llGlobalHeaders, 0, globalrequestHeaderBean.getName(), globalrequestHeaderBean.getValue()
            ,globalrequestHeaderBean.getNote());
        }
    }

    private void addItem(LinearLayout ll, final int position, String title, String value, String hint) {
        View root = LayoutInflater.from(this).inflate(R.layout.interface_test_item, null);
        TextView tvName = (TextView) root.findViewById(R.id.tv_name);
        final EditText etValue = (EditText) root.findViewById(R.id.et_value);
        etValue.setHint(hint);
        etValue.setEnabled(false);
        tvName.setText(title);
        etValue.setText(value);
        AutoUtils.auto(root);
        ll.addView(root, position);
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
                CopyUtils.copyPlainText(ZApplication.getInstance(), "json", tvResult.getText().toString().trim());
                ToastUtils.showCustomBgToast("已复制到剪切板");
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
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
