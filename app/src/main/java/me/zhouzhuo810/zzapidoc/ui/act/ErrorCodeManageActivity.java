package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteErrorCodeResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllErrorCodeResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.ui.adapter.ErrorCodeListAdapter;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.common.rx.RxHelper;
import zhouzhuo810.me.zzandframe.common.utils.ToastUtils;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * 错误码管理
 * Created by zhouzhuo810 on 2017/12/29.
 */
public class ErrorCodeManageActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private SwipeRefreshLayout refresh;
    private ListView lv;
    private TextView tvNoData;

    private boolean global;
    private boolean group;
    private String interfaceId;
    private String groupId;
    private String projectId;

    private ErrorCodeListAdapter adapter;

    private void assignViews() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        lv = (ListView) findViewById(R.id.lv);
        tvNoData = (TextView) findViewById(R.id.tv_no_data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_error_code_manage;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        assignViews();
    }

    @Override
    public void initData() {
        global = getIntent().getBooleanExtra("global", false);
        group = getIntent().getBooleanExtra("group", false);
        interfaceId = getIntent().getStringExtra("interfaceId");
        groupId = getIntent().getStringExtra("groupId");
        projectId = getIntent().getStringExtra("projectId");

        adapter = new ErrorCodeListAdapter(this, null);
        lv.setAdapter(adapter);
    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stopRefresh(refresh);
            }
        });

        rlRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ErrorCodeManageActivity.this, AddErrorCodeActivity.class);
                intent.putExtra("global", global);
                intent.putExtra("group", group);
                intent.putExtra("interfaceId", interfaceId);
                intent.putExtra("groupId", groupId);
                intent.putExtra("projectId", projectId);
                startActWithIntent(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                showListDialog(Arrays.asList("删除接口"), true, null, new IBaseActivity.OnItemClick() {
                    @Override
                    public void onItemClick(int pos, String s) {
                        switch (pos) {
                            case 0:
                                deleteErrorCode(adapter.getItem(position).getId());
                                break;
                        }
                    }
                });
                return true;
            }
        });
    }

    private void deleteErrorCode(String id) {
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .deleteErrorCode(getUserId(), id)
                .compose(RxHelper.<DeleteErrorCodeResult>io_main())
                .subscribe(new Subscriber<DeleteErrorCodeResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(DeleteErrorCodeResult deleteErrorCodeResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(deleteErrorCodeResult.getMsg());
                        if (deleteErrorCodeResult.getCode() == 1) {
                            startRefresh(refresh);
                            getData();
                        }
                    }
                });
    }

    @Override
    public void resume() {
        startRefresh(refresh);
        getData();
    }

    private void getData() {
        Api.getApi0()
                .getAllErrorCode(getUserId(), interfaceId, groupId, projectId, global, group)
                .compose(RxHelper.<GetAllErrorCodeResult>io_main())
                .subscribe(new Subscriber<GetAllErrorCodeResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh(refresh);
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(GetAllErrorCodeResult getAllErrorCodeResult) {
                        stopRefresh(refresh);
                        if (getAllErrorCodeResult.getCode() == 0) {
                            ToastUtils.showCustomBgToast(getAllErrorCodeResult.getMsg());
                        } else {
                            adapter.updateAll(getAllErrorCodeResult.getData());
                        }
                    }
                });
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
