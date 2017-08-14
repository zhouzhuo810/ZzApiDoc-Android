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
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetDictionaryResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.CopyUtils;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
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
                showListDialog(Arrays.asList("请求头", "请求参数", "返回参数"), true, new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                }, new OnItemClick() {
                    @Override
                    public void onItemClick(int pos, String content) {
                        Intent intent;
                        switch (pos) {
                            case 0:
                                intent = new Intent(InterfaceManageActivity.this, RequestHeaderManageActivity.class);
                                intent.putExtra("projectId", projectId);
                                intent.putExtra("groupId", groupId);
                                intent.putExtra("interfaceId", adapter.getmDatas().get(position).getId());
                                startActWithIntent(intent);
                                break;
                            case 1:
                                intent = new Intent(InterfaceManageActivity.this, RequestParamsManageActivity.class);
                                intent.putExtra("projectId", projectId);
                                intent.putExtra("groupId", groupId);
                                intent.putExtra("interfaceId", adapter.getmDatas().get(position).getId());
                                startActWithIntent(intent);
                                break;
                            case 2:
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
                showListDialog(Arrays.asList("修改请求方式", "修改接口路径", "删除接口", "复制接口地址", "测试接口"), true, null, new OnItemClick() {
                    @Override
                    public void onItemClick(int pos, String content) {
                        switch (pos) {
                            case 0:
                                reviseMethod(adapter.getmDatas().get(position));
                                break;
                            case 1:
                                revisePath(adapter.getmDatas().get(position));
                                break;
                            case 2:
                                deleteInterface(adapter.getmDatas().get(position).getId());
                                break;
                            case 3:
                                CopyUtils.copyPlainText(InterfaceManageActivity.this,
                                        adapter.getmDatas().get(position).getName(),
                                        adapter.getmDatas().get(position).getIp()
                                                + File.separator
                                                + adapter.getmDatas().get(position).getPath());
                                ToastUtils.showCustomBgToast("已复制到剪切板");
                                break;
                            case 4:
                                Intent intent = new Intent(InterfaceManageActivity.this, InterfaceTestActivity.class);
                                intent.putExtra("interfaceId", adapter.getmDatas().get(position).getId());
                                intent.putExtra("projectId", projectId);
                                intent.putExtra("ip", adapter.getmDatas().get(position).getIp());
                                intent.putExtra("path", adapter.getmDatas().get(position).getPath());
                                intent.putExtra("method", adapter.getmDatas().get(position).getMethod());
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

    private void revisePath(final GetAllInterfaceResult.DataEntity entity) {
        showTwoBtnEditDialog("修改路径", "请输入接口路径", entity.getPath(), false, new OnTwoBtnEditClick() {
            @Override
            public void onOk(String content) {
                showPd(getString(R.string.submiting_text), false);
                Api.getApi0()
                        .updateInterface(entity.getId(), entity.getName(), content, projectId, groupId, "", entity.getNote(), getUserId(), null, null)
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

            @Override
            public void onCancel() {

            }
        });

    }

    private void reviseMethod(final GetAllInterfaceResult.DataEntity entity) {
        showPd(getString(R.string.loading_text), false);
        Api.getApi0()
                .getDictionary("method")
                .compose(RxHelper.<GetDictionaryResult>io_main())
                .subscribe(new Subscriber<GetDictionaryResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                        hidePd();
                    }

                    @Override
                    public void onNext(final GetDictionaryResult getDictionaryResult) {
                        hidePd();
                        if (getDictionaryResult.getCode() == 1) {
                            List<String> strings = dicToList(getDictionaryResult.getData());
                            showListDialog(strings, true, null, new OnItemClick() {
                                @Override
                                public void onItemClick(int position, String content) {
                                    showPd(getString(R.string.submiting_text), false);
                                    Api.getApi0()
                                            .updateInterface(entity.getId(), entity.getName(), "", projectId, groupId, getDictionaryResult.getData().get(position).getId(), entity.getNote(), getUserId(), null, null)
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
                            });
                        } else {
                            ToastUtils.showCustomBgToast(getDictionaryResult.getMsg());
                        }
                    }
                });

    }

    private void deleteInterface(final String id) {
        showTwoBtnDialog("删除接口", "确定删除吗？", true, new OnTwoBtnClick() {
            @Override
            public void onOk() {
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
            public void onCancel() {

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
