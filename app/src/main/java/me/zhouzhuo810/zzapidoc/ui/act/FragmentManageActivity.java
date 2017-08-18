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
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.adapter.InterfaceGroupListAdapter;
import rx.Subscriber;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */

public class FragmentManageActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private SwipeRefreshLayout refresh;
    private ListView lv;
    private TextView tvNoData;
    private String projectId;
    private List<GetAllInterfaceGroupResult.DataBean> list;
    private InterfaceGroupListAdapter adapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment_manage;
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
        adapter = new InterfaceGroupListAdapter(this, list, R.layout.list_item_interface_group, true);
        lv.setAdapter(adapter);
    }

    @Override
    public void initData() {
        projectId = getIntent().getStringExtra("projectId");
    }

    private void getData() {

        Api.getApi0()
                .getAllInterfaceGroup(projectId, getUserId())
                .compose(RxHelper.<GetAllInterfaceGroupResult>io_main())
                .subscribe(new Subscriber<GetAllInterfaceGroupResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh(refresh);
                    }

                    @Override
                    public void onNext(GetAllInterfaceGroupResult getAllInterfaceGroupResult) {
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
                Intent intent = new Intent(FragmentManageActivity.this, AddInterfaceGroupActivity.class);
                intent.putExtra("projectId", projectId);
                startActWithIntent(intent);
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FragmentManageActivity.this, InterfaceManageActivity.class);
                intent.putExtra("projectId", projectId);
                intent.putExtra("groupId", adapter.getmDatas().get(position).getId());
                startActWithIntent(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                showListDialog(Arrays.asList("删除接口", "修改接口地址"), true, null, new OnItemClick() {
                    @Override
                    public void onItemClick(int pos, String content) {
                        switch (pos) {
                            case 0:
                                deleteGroup(adapter.getmDatas().get(position).getId());
                                break;
                            case 1:
                                changeIpAddr(adapter.getmDatas().get(position));
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

    private void changeIpAddr(final GetAllInterfaceGroupResult.DataBean group) {
        showTwoBtnEditDialog("修改接口地址", "请输入新的接口地址", group.getIp(), false, new OnTwoBtnEditClick() {
            @Override
            public void onOk(String content) {
                group.setIp(content);
                updateGroup(group);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void updateGroup(GetAllInterfaceGroupResult.DataBean group) {
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .updateInterfaceGroup(group.getId(), group.getName(), projectId, group.getIp(), getUserId())
                .compose(RxHelper.<UpdateInterfaceResult>io_main())
                .subscribe(new Subscriber<UpdateInterfaceResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(UpdateInterfaceResult updateInterfaceResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(updateInterfaceResult.getMsg());
                        if (updateInterfaceResult.getCode() == 1) {
                            startRefresh(refresh);
                            getData();
                        }
                    }
                });
    }

    private void deleteGroup(String id) {
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .deleteInterfaceGroup(id, getUserId())
                .compose(RxHelper.<DeleteInterfaceGroupResult>io_main())
                .subscribe(new Subscriber<DeleteInterfaceGroupResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(DeleteInterfaceGroupResult deleteInterfaceGroupResult) {
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
