package me.zhouzhuo810.zzapidoc.ui.fgm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.ZApplication;
import me.zhouzhuo810.zzapidoc.common.Constants;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllProjectResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.base.BaseFragment;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.CopyUtils;
import me.zhouzhuo810.zzapidoc.common.utils.ExportUtils;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.act.AddProjectActivity;
import me.zhouzhuo810.zzapidoc.ui.act.AddRequestHeaderActivity;
import me.zhouzhuo810.zzapidoc.ui.act.AddRequestParamsActivity;
import me.zhouzhuo810.zzapidoc.ui.act.AddResponseParamsActivity;
import me.zhouzhuo810.zzapidoc.ui.act.InterfaceGroupManageActivity;
import me.zhouzhuo810.zzapidoc.ui.adapter.ProjectListAdapter;
import rx.Subscriber;

/**
 * Created by zhouzhuo810 on 2017/7/21.
 */

public class ProjectFragment extends BaseFragment {

    private ProjectListAdapter adapter;

    private List<GetAllProjectResult.DataEntity> list;
    private SwipeRefreshLayout refresh;
    private ListView lv;
    private TextView tvNoData;
    private RelativeLayout rlRight;

    @Override
    public int getLayoutId() {
        return R.layout.fgm_project;
    }

    @Override
    public void initView() {
        refresh = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        lv = (ListView) rootView.findViewById(R.id.lv);
        rlRight = (RelativeLayout) rootView.findViewById(R.id.rl_right);
        tvNoData = (TextView) rootView.findViewById(R.id.tv_no_data);

        list = new ArrayList<>();
        adapter = new ProjectListAdapter(getActivity(), list, R.layout.list_item_my_project, true);
        lv.setAdapter(adapter);
    }

    @Override
    public void initData() {
    }

    private void getData() {
        Api.getApi0()
                .getAllProject(getUserId())
                .compose(RxHelper.<GetAllProjectResult>io_main())
                .subscribe(new Subscriber<GetAllProjectResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getBaseAct().stopRefresh(refresh);
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(GetAllProjectResult getAllProjectResult) {
                        getBaseAct().stopRefresh(refresh);
                        if (getAllProjectResult.getCode() == 1) {
                            list = getAllProjectResult.getData();
                            adapter.setmDatas(list);
                            adapter.notifyDataSetChanged();
                            if (list == null || list.size() == 0) {
                                tvNoData.setVisibility(View.VISIBLE);
                            } else {
                                tvNoData.setVisibility(View.GONE);
                            }
                        } else {
                            tvNoData.setVisibility(View.GONE);
                            ToastUtils.showCustomBgToast(getAllProjectResult.getMsg());
                        }
                    }
                });
    }

    @Override
    public void initEvent() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        rlRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddProjectActivity.class);
                getBaseAct().startActWithIntent(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), InterfaceGroupManageActivity.class);
                intent.putExtra("projectId", adapter.getmDatas().get(position).getId());
                getBaseAct().startActWithIntent(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                getBaseAct().showListDialog(Arrays.asList("导出JSON文件", "导出PDF文件", "复制JSON下载地址", "复制PDF下载地址", "添加全局请求头", "添加全局请求参数",
                        "添加全局返回参数", "删除项目"), true, null, new BaseActivity.OnItemClick() {
                    @Override
                    public void onItemClick(int pos, String content) {
                        switch (pos) {
                            case 0:
                                exportJson(adapter.getmDatas().get(position).getId());
                                break;
                            case 1:
                                exportPdf(adapter.getmDatas().get(position).getId());
                                break;
                            case 2:
                                copy(adapter.getmDatas().get(position).getName(), SharedUtil.getString(ZApplication.getInstance(), "server_config") + "ZzApiDoc/v1/interface/downloadJson?userId=" + getUserId() + "&projectId=" + adapter.getmDatas().get(position).getId());
                                break;
                            case 3:
                                copy(adapter.getmDatas().get(position).getName(), SharedUtil.getString(ZApplication.getInstance(), "server_config") + "ZzApiDoc/v1/interface/downloadPdf?userId=" + getUserId() + "&projectId=" + adapter.getmDatas().get(position).getId());
                                break;
                            case 4:
                                addGlobalRequestHeader(adapter.getmDatas().get(position).getId());
                                break;
                            case 5:
                                addGlobalRequestArg(adapter.getmDatas().get(position).getId());
                                break;
                            case 6:
                                addGlobalResponseArg(adapter.getmDatas().get(position).getId());
                                break;
                            case 7:
                                deleteProject(adapter.getmDatas().get(position).getId());
                                break;
                        }
                    }
                });
                return true;
            }
        });
    }

    private void deleteProject(final String projectId) {
        getBaseAct().showTwoBtnDialog("删除项目", "确定删除吗？", true, new BaseActivity.OnTwoBtnClick() {
            @Override
            public void onOk() {
                getBaseAct().showPd(getString(R.string.submiting_text), false);
                Api.getApi0()
                        .deleteProject(projectId, getUserId())
                        .compose(RxHelper.<DeleteProjectResult>io_main())
                        .subscribe(new Subscriber<DeleteProjectResult>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                getBaseAct().hidePd();
                                ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                            }

                            @Override
                            public void onNext(DeleteProjectResult deleteProjectResult) {
                                getBaseAct().hidePd();
                                ToastUtils.showCustomBgToast(deleteProjectResult.getMsg());
                                if (deleteProjectResult.getCode() == 1) {
                                    getBaseAct().startRefresh(refresh);
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

    private void addGlobalRequestHeader(String projectId) {
        Intent intent = new Intent(getActivity(), AddRequestHeaderActivity.class);
        intent.putExtra("projectId", projectId);
        intent.putExtra("interfaceId", "");
        intent.putExtra("global", true);
        getBaseAct().startActWithIntent(intent);
    }

    private void addGlobalRequestArg(String projectId) {
        Intent intent = new Intent(getActivity(), AddRequestParamsActivity.class);
        intent.putExtra("projectId", projectId);
        intent.putExtra("groupId", "");
        intent.putExtra("interfaceId", "");
        intent.putExtra("global", true);
        getBaseAct().startActWithIntent(intent);
    }

    private void addGlobalResponseArg(String projectId) {
        Intent intent = new Intent(getActivity(), AddResponseParamsActivity.class);
        intent.putExtra("projectId", projectId);
        intent.putExtra("groupId", "");
        intent.putExtra("interfaceId", "");
        intent.putExtra("global", true);
        getBaseAct().startActWithIntent(intent);
    }

    private void copy(String name, String s) {
        CopyUtils.copyPlainText(getActivity(), name, s);
        ToastUtils.showCustomBgToast("已复制到剪切板");
    }

    private void exportJson(String id) {
        final String name = System.currentTimeMillis() + ".txt";
        ExportUtils.exportToJsonFile(getActivity(), getUserId(), id, Constants.EXPORT_PATH, name, new ExportUtils.ProgressListener() {
            TextView tv;
            ProgressBar pb;

            @Override
            public void onStart() {
                getBaseAct().showUpdateDialog("导出", "正在导出...", false, new BaseActivity.OnOneBtnClickListener() {
                    @Override
                    public void onProgress(TextView tvProgress, ProgressBar progressBar) {
                        tv = tvProgress;
                        pb = progressBar;
                    }

                    @Override
                    public void onOK() {
                        getBaseAct().hideUpdateDialog();
                    }
                });
            }

            @Override
            public void onLoad(final int progress) {
                Log.e("TTT", "progress=" + progress);
                tv.setText(progress + "%");
                pb.setProgress(progress);
            }

            @Override
            public void onCancel() {
                Log.e("TTT", "cancel");
                getBaseAct().hideUpdateDialog();
            }

            @Override
            public void onFail(String error) {
                Log.e("TTT", "fail");
                getBaseAct().hideUpdateDialog();
                ToastUtils.showCustomBgToast(error);
            }

            @Override
            public void onOk() {
                Log.e("TTT", "ok");
                getBaseAct().hideUpdateDialog();
                ToastUtils.showCustomBgToast("文件已保存到" + Constants.EXPORT_PATH + name);
            }
        });
    }

    private void exportPdf(String id) {
        final String name = System.currentTimeMillis() + ".pdf";
        ExportUtils.exportToPdfFile(getActivity(), getUserId(), id, Constants.EXPORT_PDF_PATH, name, new ExportUtils.ProgressListener() {
            TextView tv;
            ProgressBar pb;

            @Override
            public void onStart() {
                getBaseAct().showUpdateDialog("导出", "正在导出...", false, new BaseActivity.OnOneBtnClickListener() {
                    @Override
                    public void onProgress(TextView tvProgress, ProgressBar progressBar) {
                        tv = tvProgress;
                        pb = progressBar;
                    }

                    @Override
                    public void onOK() {
                        getBaseAct().hideUpdateDialog();
                    }
                });
            }

            @Override
            public void onLoad(final int progress) {
                Log.e("TTT", "progress=" + progress);
                tv.setText(progress + "%");
                pb.setProgress(progress);
            }

            @Override
            public void onCancel() {
                Log.e("TTT", "cancel");
                getBaseAct().hideUpdateDialog();
            }

            @Override
            public void onFail(String error) {
                Log.e("TTT", "fail");
                getBaseAct().hideUpdateDialog();
                ToastUtils.showCustomBgToast(error);
            }

            @Override
            public void onOk() {
                Log.e("TTT", "ok");
                getBaseAct().hideUpdateDialog();
                ToastUtils.showCustomBgToast("文件已保存到" + Constants.EXPORT_PDF_PATH + name);
            }
        });
    }

    @Override
    public void resume() {
        getBaseAct().startRefresh(refresh);
        getData();
    }

    @Override
    public void saveState(Bundle bundle) {

    }

    @Override
    public void restoreState(@Nullable Bundle bundle) {

    }

    @Override
    public void destroyView() {

    }

}
