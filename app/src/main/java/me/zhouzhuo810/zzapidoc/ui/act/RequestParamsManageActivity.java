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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteArgResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetRequestArgResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetResponseArgResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateResponseArgResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.adapter.RequestArgListAdapter;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */

public class RequestParamsManageActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private SwipeRefreshLayout refresh;
    private ListView lv;
    private TextView tvNoData;
    private String projectId;
    private List<GetRequestArgResult.DataBean> list;
    private RequestArgListAdapter adapter;
    private String groupId;
    private String interfaceId;
    private String pid;
    private boolean global;


    @Override
    public int getLayoutId() {
        return R.layout.activity_request_params_manage;
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
        adapter = new RequestArgListAdapter(this, list, R.layout.list_item_params, true);
        lv.setAdapter(adapter);
    }

    @Override
    public void initData() {
        projectId = getIntent().getStringExtra("projectId");
        groupId = getIntent().getStringExtra("groupId");
        interfaceId = getIntent().getStringExtra("interfaceId");
        pid = getIntent().getStringExtra("pid");
        global = getIntent().getBooleanExtra("global", false);
    }

    private void getData() {
        if (pid == null) {
            pid = "0";
        }
        Api.getApi0()
                .getRequestArgByInterfaceIdAndPid(interfaceId, projectId, pid, getUserId())
                .compose(RxHelper.<GetRequestArgResult>io_main())
                .subscribe(new Subscriber<GetRequestArgResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh(refresh);
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text)+e.toString());
                    }

                    @Override
                    public void onNext(GetRequestArgResult getAllInterfaceGroupResult) {
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
                Intent intent = new Intent(RequestParamsManageActivity.this, AddRequestParamsActivity.class);
                intent.putExtra("projectId", projectId);
                intent.putExtra("groupId", groupId);
                intent.putExtra("interfaceId", interfaceId);
                intent.putExtra("pid", pid);
                intent.putExtra("global", global);
                startActWithIntent(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int type = adapter.getmDatas().get(position).getType();
                String pid = adapter.getmDatas().get(position).getId();
                String interfaceId = adapter.getmDatas().get(position).getInterfaceId();
                Intent intent;
                switch (type) {
                    case 0:
                        //string
                        break;
                    case 1:
                        //number
                        break;
                    case 2:
                        //object
                        intent = new Intent(RequestParamsManageActivity.this, RequestParamsManageActivity.class);
                        intent.putExtra("pid", pid);
                        intent.putExtra("interfaceId", interfaceId);
                        intent.putExtra("projectId", projectId);
                        intent.putExtra("global", global);
                        startActWithIntent(intent);
                        break;
                    case 3:
                        //array[object]
                        intent = new Intent(RequestParamsManageActivity.this, RequestParamsManageActivity.class);
                        intent.putExtra("pid", pid);
                        intent.putExtra("interfaceId", interfaceId);
                        intent.putExtra("projectId", projectId);
                        intent.putExtra("global", global);
                        startActWithIntent(intent);
                        break;
                    case 4:
                        //array[string]

                        break;
                    case 5:
                        //array

                        break;
                    case 6:
                        //file

                        break;
                }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                showListDialog(Arrays.asList("修改类型", "修改名称", "修改备注", "删除"), true, null, new IBaseActivity.OnItemClick() {
                    @Override
                    public void onItemClick(int pos, String content) {
                        switch (pos) {
                            case 0:
                                reviseType(adapter.getmDatas().get(position));
                                break;
                            case 1:
                                reviseName(adapter.getmDatas().get(position));
                                break;
                            case 2:
                                revisePs(adapter.getmDatas().get(position));
                                break;
                            case 3:
                                delete(adapter.getmDatas().get(position).getId());
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


    private void revisePs(final GetRequestArgResult.DataBean dataBean) {
        showTwoBtnEditDialog("修改备注", "请输入备注", dataBean.getNote(), false, new IBaseActivity.OnTwoBtnEditClick() {
            @Override
            public void onOk(String s) {
                if (s.length() == 0) {
                    ToastUtils.showCustomBgToast(getString(R.string.param_note_not_nul));
                    return;
                }
                showPd(getString(R.string.submiting_text), false);
                Api.getApi0().updateRequestArg(dataBean.getId(),
                        dataBean.getPid(), dataBean.getName(), dataBean.getDefValue(), dataBean.getType(),
                        dataBean.getInterfaceId(), s, getUserId(), dataBean.isRequire(), dataBean.isGlobal())
                        .compose(RxHelper.<UpdateResponseArgResult>io_main())
                        .subscribe(new Subscriber<UpdateResponseArgResult>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                hidePd();
                                ToastUtils.showCustomBgToast(getString(R.string.no_net_text)+e.toString());
                            }

                            @Override
                            public void onNext(UpdateResponseArgResult updateResponseArgResult) {
                                hidePd();
                                ToastUtils.showCustomBgToast(updateResponseArgResult.getMsg());
                                if (updateResponseArgResult.getCode()==1) {
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

    private void reviseName(final GetRequestArgResult.DataBean dataBean) {
        showTwoBtnEditDialog("修改名称", "请输入名称", dataBean.getName(), false, new IBaseActivity.OnTwoBtnEditClick() {
            @Override
            public void onOk(String s) {
                if (s.length() == 0) {
                    ToastUtils.showCustomBgToast(getString(R.string.param_name_not_nul));
                    return;
                }
                showPd(getString(R.string.submiting_text), false);
                Api.getApi0().updateRequestArg(dataBean.getId(),
                        dataBean.getPid(), s, dataBean.getDefValue(), dataBean.getType(),
                        dataBean.getInterfaceId(), dataBean.getNote(), getUserId(), dataBean.isRequire(), dataBean.isGlobal())
                        .compose(RxHelper.<UpdateResponseArgResult>io_main())
                        .subscribe(new Subscriber<UpdateResponseArgResult>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                hidePd();
                                ToastUtils.showCustomBgToast(getString(R.string.no_net_text)+e.toString());
                            }

                            @Override
                            public void onNext(UpdateResponseArgResult updateResponseArgResult) {
                                hidePd();
                                ToastUtils.showCustomBgToast(updateResponseArgResult.getMsg());
                                if (updateResponseArgResult.getCode()==1) {
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

    private void reviseType(final GetRequestArgResult.DataBean dataBean) {
        showListDialog(Arrays.asList("string", "int", "object", "array[object]", "array[string]", "array", "file", "unknown", "array[int]","float","array[float]"), false, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        }, new IBaseActivity.OnItemClick() {
            @Override
            public void onItemClick(int i, String s) {
                showPd(getString(R.string.submiting_text), false);
                Api.getApi0().updateRequestArg(dataBean.getId(),
                        dataBean.getPid(), dataBean.getName(), dataBean.getDefValue(), i,
                        dataBean.getInterfaceId(), dataBean.getNote(), getUserId(), dataBean.isRequire(), dataBean.isGlobal())
                        .compose(RxHelper.<UpdateResponseArgResult>io_main())
                        .subscribe(new Subscriber<UpdateResponseArgResult>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                hidePd();
                                ToastUtils.showCustomBgToast(getString(R.string.no_net_text)+e.toString());
                            }

                            @Override
                            public void onNext(UpdateResponseArgResult updateResponseArgResult) {
                                hidePd();
                                ToastUtils.showCustomBgToast(updateResponseArgResult.getMsg());
                                if (updateResponseArgResult.getCode()==1) {
                                    startRefresh(refresh);
                                    getData();
                                }
                            }
                        });
            }
        });
    }


    private void delete(String id) {
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .deleteRequestArg(id, getUserId())
                .compose(RxHelper.<DeleteArgResult>io_main())
                .subscribe(new Subscriber<DeleteArgResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());

                    }

                    @Override
                    public void onNext(DeleteArgResult deleteArgResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(deleteArgResult.getMsg());
                        if (deleteArgResult.getCode()==1) {
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
