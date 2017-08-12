package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.CopyUtils;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.adapter.InterfaceGroupListAdapter;
import me.zhouzhuo810.zzapidoc.ui.adapter.InterfaceListAdapter;
import rx.Subscriber;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */

public class InterfaceManageActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private SwipeRefreshLayout refresh;
    private ListView lv;
    private TextView tvNoData;
    private String projectId;
    private List<GetAllInterfaceResult.DataEntity> list;
    private InterfaceListAdapter adapter;
    private String groupId;


    @Override
    public int getLayoutId() {
        return R.layout.activity_interface_manage;
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
        adapter = new InterfaceListAdapter(this, list, R.layout.list_item_interface, true);
        lv.setAdapter(adapter);
    }

    @Override
    public void initData() {
        projectId = getIntent().getStringExtra("projectId");
        groupId = getIntent().getStringExtra("groupId");
    }

    private void getData() {

        Api.getApi0()
                .getInterfaceByGroupId(projectId, groupId, getUserId())
                .compose(RxHelper.<GetAllInterfaceResult>io_main())
                .subscribe(new Subscriber<GetAllInterfaceResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh(refresh);
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(GetAllInterfaceResult getAllInterfaceGroupResult) {
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
                Intent intent = new Intent(InterfaceManageActivity.this, AddInterfaceActivity.class);
                intent.putExtra("projectId", projectId);
                intent.putExtra("groupId", groupId);
                startActWithIntent(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                showListDialog(Arrays.asList("请求参数", "返回参数"), true, new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                }, new OnItemClick() {
                    @Override
                    public void onItemClick(int pos, String content) {
                        Intent intent;
                        switch (pos) {
                            case 0:
                                intent = new Intent(InterfaceManageActivity.this, RequestParamsManageActivity.class);
                                intent.putExtra("projectId", projectId);
                                intent.putExtra("groupId", groupId);
                                intent.putExtra("interfaceId", adapter.getmDatas().get(position).getId());
                                startActWithIntent(intent);
                                break;
                            case 1:
                                intent = new Intent(InterfaceManageActivity.this, ResponseParamsManageActivity.class);
                                intent.putExtra("projectId", projectId);
                                intent.putExtra("groupId", groupId);
                                intent.putExtra("interfaceId", adapter.getmDatas().get(position).getId());
                                startActWithIntent(intent);
                                break;
                        }
                    }
                });
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                showListDialog(Arrays.asList("删除接口", "复制接口地址"), true, null, new OnItemClick() {
                    @Override
                    public void onItemClick(int pos, String content) {
                        switch (pos) {
                            case 0:
                                deleteInterface(adapter.getmDatas().get(position).getId());
                                break;
                            case 1:
                                CopyUtils.copyPlainText(InterfaceManageActivity.this,
                                        adapter.getmDatas().get(position).getName(),
                                        adapter.getmDatas().get(position).getIp()
                                                + File.separator
                                                + adapter.getmDatas().get(position).getPath());
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

    private void deleteInterface(String id) {
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .deleteInterface(id, getUserId())
                .compose(RxHelper.<DeleteInterfaceResult>io_main())
                .subscribe(new Subscriber<DeleteInterfaceResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(DeleteInterfaceResult deleteInterfaceResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(deleteInterfaceResult.getMsg());
                        if (deleteInterfaceResult.getCode() == 1) {
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
