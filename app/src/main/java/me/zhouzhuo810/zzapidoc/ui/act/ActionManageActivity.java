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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteActionResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteActivityResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllActionsResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.adapter.ActionListAdapter;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */
public class ActionManageActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private SwipeRefreshLayout refresh;
    private ListView lv;
    private TextView tvNoData;
    private List<GetAllActionsResult.DataEntity> list;
    private ActionListAdapter adapter;
    private boolean choose;
    private String widgetId;
    private String appId;
    private String projectId;
    private String pid;

    @Override
    public int getLayoutId() {
        return R.layout.activity_action_manage;
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
        lv = (ListView) findViewById(R.id.lv);
        tvNoData = (TextView) findViewById(R.id.tv_no_data);

        list = new ArrayList<>();
        adapter = new ActionListAdapter(this, list, R.layout.list_item_action, true);
        lv.setAdapter(adapter);
    }

    @Override
    public void initData() {
        widgetId = getIntent().getStringExtra("widgetId");
        appId = getIntent().getStringExtra("appId");
        projectId = getIntent().getStringExtra("projectId");
        choose = getIntent().getBooleanExtra("choose", false);
        pid = getIntent().getStringExtra("pid");
    }

    private void getData() {

        if (pid == null) {
            pid = "0";
        }
        Api.getApi0()
                .getAllActions(getUserId(), pid, widgetId)
                .compose(RxHelper.<GetAllActionsResult>io_main())
                .subscribe(new Subscriber<GetAllActionsResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh(refresh);
                    }

                    @Override
                    public void onNext(GetAllActionsResult getAllActionsResult) {
                        stopRefresh(refresh);
                        if (getAllActionsResult.getCode() == 1) {
                            list = getAllActionsResult.getData();
                            adapter.setmDatas(list);
                            adapter.notifyDataSetChanged();
                            if (list == null || list.size() == 0) {
                                tvNoData.setVisibility(View.VISIBLE);
                            } else {
                                tvNoData.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtils.showCustomBgToast(getAllActionsResult.getMsg());
                        }
                    }
                });
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
                Intent intent = new Intent(ActionManageActivity.this, AddActionActivity.class);
                intent.putExtra("appId", appId);
                intent.putExtra("widgetId", widgetId);
                intent.putExtra("pid", pid);
                intent.putExtra("projectId", projectId);
                startActWithIntent(intent);
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (choose) {
                    Intent intent = new Intent();
                    intent.putExtra("id", adapter.getmDatas().get(position).getId());
                    intent.putExtra("name", adapter.getmDatas().get(position).getName());
                    setResult(RESULT_OK, intent);
                    closeAct();
                } else {
                    Intent intent = new Intent(ActionManageActivity.this, ActionManageActivity.class);
                    intent.putExtra("pid", adapter.getmDatas().get(position).getId());
                    intent.putExtra("widgetId", widgetId);
                    intent.putExtra("choose", choose);
                    intent.putExtra("appId", appId);
                    intent.putExtra("projectId", projectId);
                    startActWithIntent(intent);
                }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                showListDialog(Arrays.asList("删除Action"), true, null, new IBaseActivity.OnItemClick() {
                    @Override
                    public void onItemClick(int pos, String s) {
                        switch (pos) {
                            case 0:
                                deleteAction(adapter.getmDatas().get(position).getId());
                                break;
                        }
                    }
                });
                return true;
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    private void deleteAction(String id) {
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .deleteAction(getUserId(), id)
                .compose(RxHelper.<DeleteActionResult>io_main())
                .subscribe(new Subscriber<DeleteActionResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(DeleteActionResult deleteActionResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(deleteActionResult.getMsg());
                        if (deleteActionResult.getCode() == 1) {
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
