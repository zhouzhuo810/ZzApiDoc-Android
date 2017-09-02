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
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteApplicationResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllApplicationResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.base.BaseFragment;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.CopyUtils;
import me.zhouzhuo810.zzapidoc.common.utils.ExportUtils;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;
import me.zhouzhuo810.zzapidoc.common.utils.SystemUtil;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.act.ActivityManageActivity;
import me.zhouzhuo810.zzapidoc.ui.act.AddApplicationActivity;
import me.zhouzhuo810.zzapidoc.ui.adapter.ApplicationListAdapter;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.common.utils.ApkUtils;

/**
 * Created by zhouzhuo810 on 2017/7/21.
 */

public class AppFragment extends BaseFragment {

    private ApplicationListAdapter adapter;

    private List<GetAllApplicationResult.DataBean> list;
    private SwipeRefreshLayout refresh;
    private ListView lv;
    private TextView tvNoData;
    private RelativeLayout rlRight;

    @Override
    public int getLayoutId() {
        return R.layout.fgm_app;
    }

    @Override
    public void initView() {
        refresh = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        lv = (ListView) rootView.findViewById(R.id.lv);
        rlRight = (RelativeLayout) rootView.findViewById(R.id.rl_right);
        tvNoData = (TextView) rootView.findViewById(R.id.tv_no_data);

        list = new ArrayList<>();
        adapter = new ApplicationListAdapter(getActivity(), list, R.layout.list_item_my_application, true);
        lv.setAdapter(adapter);
    }

    @Override
    public void initData() {
    }

    private void getData() {
        Api.getApi0()
                .getAllMyApplication(getUserId())
                .compose(RxHelper.<GetAllApplicationResult>io_main())
                .subscribe(new Subscriber<GetAllApplicationResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getBaseAct().stopRefresh(refresh);
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(GetAllApplicationResult getAllProjectResult) {
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
                Intent intent = new Intent(getActivity(), AddApplicationActivity.class);
                getBaseAct().startActWithIntent(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ActivityManageActivity.class);
                intent.putExtra("appId", adapter.getmDatas().get(position).getId());
                intent.putExtra("projectId", adapter.getmDatas().get(position).getApiId());
                getBaseAct().startActWithIntent(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                getBaseAct().showListDialog(Arrays.asList("导出JSON文件", "导出项目文件","导出并安装APK文件", "复制JSON下载地址", "复制项目下载地址", "删除项目"), true, null, new BaseActivity.OnItemClick() {
                    @Override
                    public void onItemClick(int pos, String content) {
                        switch (pos) {
                            case 0:
                                exportJson(adapter.getmDatas().get(position).getId());
                                break;
                            case 1:
                                exportApp(adapter.getmDatas().get(position).getId());
                                break;
                            case 2:
                                exportApk(adapter.getmDatas().get(position).getId());
                                break;
                            case 3:
                                copy(adapter.getmDatas().get(position).getAppName(), SharedUtil.getString(ZApplication.getInstance(), "server_config") + "ZzApiDoc/v1/application/downloadAppJson?userId=" + getUserId() + "&appId=" + adapter.getmDatas().get(position).getId());
                                break;
                            case 4:
                                copy(adapter.getmDatas().get(position).getAppName(), SharedUtil.getString(ZApplication.getInstance(), "server_config") + "ZzApiDoc/v1/application/downloadApplication?userId=" + getUserId() + "&appId=" + adapter.getmDatas().get(position).getId());
                                break;
                            case 5:
                                deleteProject(adapter.getmDatas().get(position).getId());
                                break;
                        }
                    }
                });
                return true;
            }
        });
    }

    private void exportApk(final String appId) {
        final String name = System.currentTimeMillis() + ".apk";
        ExportUtils.exportToApkFile(getActivity(), getUserId(), appId, Constants.EXPORT_DEBUG_APK_PATH, name, new ExportUtils.ProgressListener() {
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
                ToastUtils.showCustomBgToast("文件已保存到" + Constants.EXPORT_DEBUG_APK_PATH + name);
                ApkUtils.installApk(ZApplication.getInstance(), SystemUtil.getPackageInfo(ZApplication.getInstance()).packageName, Constants.EXPORT_DEBUG_APK_PATH, name);
            }
        });
    }

    private void deleteProject(final String appId) {
        getBaseAct().showTwoBtnDialog("删除应用", "确定删除吗？", true, new BaseActivity.OnTwoBtnClick() {
            @Override
            public void onOk() {
                getBaseAct().showPd(getString(R.string.submiting_text), false);
                Api.getApi0()
                        .deleteApplication(getUserId(), appId)
                        .compose(RxHelper.<DeleteApplicationResult>io_main())
                        .subscribe(new Subscriber<DeleteApplicationResult>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                getBaseAct().hidePd();
                                ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                            }

                            @Override
                            public void onNext(DeleteApplicationResult deleteProjectResult) {
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

    private void copy(String name, String s) {
        CopyUtils.copyPlainText(getActivity(), name, s);
        ToastUtils.showCustomBgToast("已复制到剪切板");
    }

    private void exportJson(String id) {
        final String name = System.currentTimeMillis() + ".txt";
        ExportUtils.exportAppToJsonFile(getActivity(), getUserId(), id, Constants.EXPORT_ANDROID_PATH, name, new ExportUtils.ProgressListener() {
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
                ToastUtils.showCustomBgToast("文件已保存到" + Constants.EXPORT_ANDROID_PATH + name);
            }
        });
    }

    private void exportApp(String id) {
        final String name = System.currentTimeMillis() + ".zip";
        ExportUtils.exportToAppZipFile(getActivity(), getUserId(), id, Constants.EXPORT_ZIP_PATH, name, new ExportUtils.ProgressListener() {
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
                ToastUtils.showCustomBgToast("文件已保存到" + Constants.EXPORT_ZIP_PATH + name);
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
