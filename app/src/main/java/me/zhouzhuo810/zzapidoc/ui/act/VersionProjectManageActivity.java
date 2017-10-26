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
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteVersionProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllVersionProjectResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.adapter.VersionProjectListAdapter;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * 版本项目管理
 * Created by zhouzhuo810 on 2017/10/26.
 */

public class VersionProjectManageActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private SwipeRefreshLayout refresh;
    private ListView lv;
    private TextView tvNoData;
    private VersionProjectListAdapter adapter;

    private void assignViews() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        lv = (ListView) findViewById(R.id.lv);
        tvNoData = (TextView) findViewById(R.id.tv_no_data);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_version_project_manage;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        assignViews();

        adapter = new VersionProjectListAdapter(this, null, R.layout.list_item_version_project, true);
        lv.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    private void getData() {
        Api.getApi0()
                .getAllVersionProject(getUserId())
                .compose(RxHelper.<GetAllVersionProjectResult>io_main())
                .subscribe(new Subscriber<GetAllVersionProjectResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh(refresh);
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(GetAllVersionProjectResult getAllVersionProjectResult) {
                        stopRefresh(refresh);
                        if (getAllVersionProjectResult.getCode() == 1) {
                            List<GetAllVersionProjectResult.DataEntity> list = getAllVersionProjectResult.getData();
                            adapter.setmDatas(list);
                            adapter.notifyDataSetChanged();
                            if (list == null || list.size() == 0) {
                                tvNoData.setVisibility(View.VISIBLE);
                            } else {
                                tvNoData.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtils.showCustomBgToast(getAllVersionProjectResult.getMsg());
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
                Intent intent = new Intent(VersionProjectManageActivity.this, AddVersionProjectActivity.class);
                startActWithIntent(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(VersionProjectManageActivity.this, VersionManageActivity.class);
                intent.putExtra("id", adapter.getmDatas().get(position).getId());
                startActWithIntent(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showD(adapter.getmDatas().get(position));
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

    private void showD(final GetAllVersionProjectResult.DataEntity dataEntity) {
        showListDialog(Arrays.asList("删除"), true, null, new IBaseActivity.OnItemClick() {
            @Override
            public void onItemClick(int position, String s) {
                switch (position) {
                    case 0:
                        showConfirmDialog(dataEntity);
                        break;
                }
            }
        });
    }

    private void showConfirmDialog(final GetAllVersionProjectResult.DataEntity dataEntity) {
        showTwoBtnDialog("删除", "确定删除吗", false, new IBaseActivity.OnTwoBtnClick() {
            @Override
            public void onOk() {
                delete(dataEntity.getId());
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void delete(String id) {
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .deleteVersionProject(getUserId(), id)
                .compose(RxHelper.<DeleteVersionProjectResult>io_main())
                .subscribe(new Subscriber<DeleteVersionProjectResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());

                    }

                    @Override
                    public void onNext(DeleteVersionProjectResult deleteVersionProjectResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(deleteVersionProjectResult.getMsg());
                        if (deleteVersionProjectResult.getCode() == 1) {
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
