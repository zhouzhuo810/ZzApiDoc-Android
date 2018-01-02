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
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteActivityResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllMyWidgetResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.adapter.WidgetListAdapter;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * Created by admin on 2017/8/19.
 */
public class ItemManageActivity extends BaseActivity {
    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private SwipeRefreshLayout refresh;
    private ListView lv;
    private TextView tvNoData;
    private String relativeId;

    private List<GetAllMyWidgetResult.DataBean> list;
    private WidgetListAdapter adapter;
    private String projectId;
    private String appId;
    private String pid;

    @Override
    public int getLayoutId() {
        return R.layout.activity_widget_manage;
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
        adapter = new WidgetListAdapter(this, list, R.layout.list_item_widget, true);
        lv.setAdapter(adapter);
    }

    @Override
    public void initData() {
        appId = getIntent().getStringExtra("appId");
        relativeId = getIntent().getStringExtra("relativeId");
        projectId = getIntent().getStringExtra("projectId");
        pid = getIntent().getStringExtra("pid");
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
                getData();
            }
        });

        rlRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemManageActivity.this, AddWidgetActivity.class);
                intent.putExtra("relativeId", relativeId);
                intent.putExtra("appId", appId);
                intent.putExtra("pid", pid);
                intent.putExtra("projectId", projectId);
                startActWithIntent(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int type = adapter.getmDatas().get(position).getType();
                Intent intent;
                switch (type) {
                    case 0:
                        // titlebar
                        intent = new Intent(ItemManageActivity.this, WidgetManageActivity.class);
                        intent.putExtra("pid", adapter.getmDatas().get(position).getId());
                        startActWithIntent(intent);
                        break;
                    case 1:
                        //setting item
                        intent = new Intent(ItemManageActivity.this, WidgetManageActivity.class);
                        intent.putExtra("pid", adapter.getmDatas().get(position).getId());
                        startActWithIntent(intent);
                        break;
                    case 2:
                        //title edit item
                        intent = new Intent(ItemManageActivity.this, WidgetManageActivity.class);
                        intent.putExtra("pid", adapter.getmDatas().get(position).getId());
                        startActWithIntent(intent);
                        break;
                    case 3:
                        //underline edit item
                        intent = new Intent(ItemManageActivity.this, WidgetManageActivity.class);
                        intent.putExtra("pid", adapter.getmDatas().get(position).getId());
                        startActWithIntent(intent);
                        break;
                }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                showListDialog(Arrays.asList("删除"), true, null, new IBaseActivity.OnItemClick() {
                    @Override
                    public void onItemClick(int pos, String s) {
                        switch (pos) {
                            case 0:
                                deleteWidget(adapter.getmDatas().get(position).getId());
                                break;
                        }
                    }
                });
                return true;
            }
        });

    }

    private void deleteWidget(String id) {
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .deleteWidget(id, getUserId())
                .compose(RxHelper.<DeleteActivityResult>io_main())
                .subscribe(new Subscriber<DeleteActivityResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(DeleteActivityResult deleteInterfaceGroupResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(deleteInterfaceGroupResult.getMsg());
                        if (deleteInterfaceGroupResult.getCode() == 1) {
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
        if (pid == null) {
            pid = "0";
        }
        Api.getApi0()
                .getAllMyWidget(relativeId, pid, getUserId())
                .compose(RxHelper.<GetAllMyWidgetResult>io_main())
                .subscribe(new Subscriber<GetAllMyWidgetResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh(refresh);
                    }

                    @Override
                    public void onNext(GetAllMyWidgetResult getAllMyWidgetResult) {
                        stopRefresh(refresh);
                        if (getAllMyWidgetResult.getCode() == 1) {
                            list = getAllMyWidgetResult.getData();
                            adapter.setmDatas(list);
                            adapter.notifyDataSetChanged();
                            if (list == null || list.size() == 0) {
                                tvNoData.setVisibility(View.VISIBLE);
                            } else {
                                tvNoData.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtils.showCustomBgToast(getAllMyWidgetResult.getMsg());
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
