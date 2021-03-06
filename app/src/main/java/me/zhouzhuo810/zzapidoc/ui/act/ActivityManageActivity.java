package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllMyActivityResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.adapter.ActivityListAdapter;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */
public class ActivityManageActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private SwipeRefreshLayout refresh;
    private ListView lv;
    private TextView tvNoData;
    private String appId;
    private List<GetAllMyActivityResult.DataBean> list;
    private ActivityListAdapter adapter;
    private boolean choose;
    private String projectId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_activity_manage;
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
        adapter = new ActivityListAdapter(this, list, R.layout.list_item_interface_group, true);
        lv.setAdapter(adapter);
    }

    @Override
    public void initData() {
        appId = getIntent().getStringExtra("appId");
        projectId = getIntent().getStringExtra("projectId");
        choose = getIntent().getBooleanExtra("choose", false);
    }

    private void getData() {

        Api.getApi0()
                .getAllMyActivity(appId, getUserId())
                .compose(RxHelper.<GetAllMyActivityResult>io_main())
                .subscribe(new Subscriber<GetAllMyActivityResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh(refresh);
                    }

                    @Override
                    public void onNext(GetAllMyActivityResult getAllInterfaceGroupResult) {
                        stopRefresh(refresh);
                        if (getAllInterfaceGroupResult.getCode() == 1) {
                            list = getAllInterfaceGroupResult.getData();
                            adapter.setmDatas(list);
                            adapter.notifyDataSetChanged();
                            if (list == null || list.size() == 0) {
                                tvNoData.setVisibility(View.VISIBLE);
                            } else {
                                tvNoData.setVisibility(View.GONE);
                            }
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
                Intent intent = new Intent(ActivityManageActivity.this, AddActivityActivity.class);
                intent.putExtra("appId", appId);
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
                    Intent intent;
                    switch (adapter.getmDatas().get(position).getType()) {
                        case 0:
                            //empty act
                            intent = new Intent(ActivityManageActivity.this, WidgetManageActivity.class);
                            intent.putExtra("appId", appId);
                            intent.putExtra("relativeId", adapter.getmDatas().get(position).getId());
                            intent.putExtra("projectId", projectId);
                            startActWithIntent(intent);
                            break;
                        case 1:
                            //splash

                            break;
                        case 2:
                            //guide

                            break;
                        case 3:
                            //bottom tab
                            intent = new Intent(ActivityManageActivity.this, FragmentManageActivity.class);
                            intent.putExtra("appId", appId);
                            intent.putExtra("activityId", adapter.getmDatas().get(position).getId());
                            intent.putExtra("pid", "0");
                            intent.putExtra("projectId", projectId);
                            startActWithIntent(intent);
                            break;
                        case 4:
                            //top tab
                            intent = new Intent(ActivityManageActivity.this, FragmentManageActivity.class);
                            intent.putExtra("appId", appId);
                            intent.putExtra("activityId", adapter.getmDatas().get(position).getId());
                            intent.putExtra("projectId", projectId);
                            intent.putExtra("pid", "0");
                            startActWithIntent(intent);
                            break;
                    }
                }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                showListDialog(Arrays.asList("编辑Activity", "删除Activity", "预览"), true, null, new IBaseActivity.OnItemClick() {
                    @Override
                    public void onItemClick(int pos, String s) {
                        Intent intent;
                        switch (pos) {
                            case 0:
                                intent = new Intent(ActivityManageActivity.this, EditActivityActivity.class);
                                intent.putExtra("actId", adapter.getmDatas().get(position).getId());
                                startActWithIntent(intent);
                                break;
                            case 1:
                                deleteActivity(adapter.getmDatas().get(position).getId());
                                break;
                            case 2:
                                intent = new Intent(ActivityManageActivity.this, PreviewActivity.class);
                                intent.putExtra("id", adapter.getmDatas().get(position).getId());
                                startActWithIntent(intent);
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

    private void deleteActivity(String id) {
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .deleteActivity(id, getUserId())
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
