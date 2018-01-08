package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import me.zhouzhuo810.zzapidoc.BuildConfig;
import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteVersionProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteVersionRecordResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteVersionResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllVersionProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllVersionRecordResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllVersionResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.CopyUtils;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.adapter.VersionListAdapter;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * 版本管理
 * Created by zhouzhuo810 on 2017/10/26.
 */
public class VersionManageActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private SwipeRefreshLayout refresh;
    private ListView lv;
    private TextView tvNoData;
    private VersionListAdapter adapter;
    private String id;

    private void assignViews() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        lv = (ListView) findViewById(R.id.lv);
        tvNoData = (TextView) findViewById(R.id.tv_no_data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_version_manage;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        assignViews();

        adapter = new VersionListAdapter(this, null, R.layout.list_item_version, true);
        lv.setAdapter(adapter);
    }

    @Override
    public void initData() {
        id = getIntent().getStringExtra("id");
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
                Intent intent = new Intent(VersionManageActivity.this, AddVersionActivity.class);
                intent.putExtra("id", id);
                startActWithIntent(intent);
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(VersionManageActivity.this, VersionRecordManageActivity.class);
                intent.putExtra("id", adapter.getmDatas().get(position).getId());
                intent.putExtra("projectId", adapter.getmDatas().get(position).getProjectId());
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


    private void showD(final GetAllVersionResult.DataEntity dataEntity) {
        showListDialog(Arrays.asList("复制所有记录", "分享记录到QQ好友", "分享记录到微信好友", "删除"), true, null, new IBaseActivity.OnItemClick() {
            @Override
            public void onItemClick(int position, String s) {
                switch (position) {
                    case 0:
                        copyAllRecords(dataEntity);
                        break;
                    case 1:
                        shareAllRecordsQQ(dataEntity);
                        break;
                    case 2:
                        shareAllRecordsWX(dataEntity);
                        break;
                    case 3:
                        showConfirmDialog(dataEntity);
                        break;
                }
            }
        });
    }

    private void copyAllRecords(final GetAllVersionResult.DataEntity dataEntity) {
        showPd(getString(R.string.loading_text), false);
        Api.getApi0()
                .getAllVersionRecord(getUserId(), dataEntity.getId())
                .compose(RxHelper.<GetAllVersionRecordResult>io_main())
                .subscribe(new Subscriber<GetAllVersionRecordResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(GetAllVersionRecordResult getAllVersionRecordResult) {
                        hidePd();
                        if (getAllVersionRecordResult.getCode() == 1) {
                            List<GetAllVersionRecordResult.DataEntity> data = getAllVersionRecordResult.getData();
                            if (data != null) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < data.size(); i++) {
                                    GetAllVersionRecordResult.DataEntity entity = data.get(i);
                                    sb.append(i + 1).append(". ").append(entity.getNote()).append("\n");
                                }
                                CopyUtils.copyPlainText(VersionManageActivity.this, dataEntity.getName(), sb.toString());
                                ToastUtils.showCustomBgToast("已复制到剪切板");
                            } else {
                                ToastUtils.showCustomBgToast("暂无记录");
                            }
                        } else {
                            ToastUtils.showCustomBgToast(getAllVersionRecordResult.getMsg());
                        }
                    }
                });
    }

    private void shareAllRecordsQQ(final GetAllVersionResult.DataEntity dataEntity) {
        showPd(getString(R.string.loading_text), false);
        Api.getApi0()
                .getAllVersionRecord(getUserId(), dataEntity.getId())
                .compose(RxHelper.<GetAllVersionRecordResult>io_main())
                .subscribe(new Subscriber<GetAllVersionRecordResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(GetAllVersionRecordResult getAllVersionRecordResult) {
                        hidePd();
                        if (getAllVersionRecordResult.getCode() == 1) {
                            List<GetAllVersionRecordResult.DataEntity> data = getAllVersionRecordResult.getData();
                            if (data != null) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < data.size(); i++) {
                                    GetAllVersionRecordResult.DataEntity entity = data.get(i);
                                    sb.append(i + 1).append(". ").append(entity.getNote()).append("\n");
                                }
                                shareQQ(VersionManageActivity.this, sb.toString());
                            } else {
                                ToastUtils.showCustomBgToast("暂无记录");
                            }
                        } else {
                            ToastUtils.showCustomBgToast(getAllVersionRecordResult.getMsg());
                        }
                    }
                });
    }

    private void shareAllRecordsWX(final GetAllVersionResult.DataEntity dataEntity) {
        showPd(getString(R.string.loading_text), false);
        Api.getApi0()
                .getAllVersionRecord(getUserId(), dataEntity.getId())
                .compose(RxHelper.<GetAllVersionRecordResult>io_main())
                .subscribe(new Subscriber<GetAllVersionRecordResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(GetAllVersionRecordResult getAllVersionRecordResult) {
                        hidePd();
                        if (getAllVersionRecordResult.getCode() == 1) {
                            List<GetAllVersionRecordResult.DataEntity> data = getAllVersionRecordResult.getData();
                            if (data != null) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < data.size(); i++) {
                                    GetAllVersionRecordResult.DataEntity entity = data.get(i);
                                    sb.append(i + 1).append(". ").append(entity.getNote()).append("\n");
                                }
                                shareToFriend(VersionManageActivity.this, sb.toString());
                            } else {
                                ToastUtils.showCustomBgToast("暂无记录");
                            }
                        } else {
                            ToastUtils.showCustomBgToast(getAllVersionRecordResult.getMsg());
                        }
                    }
                });
    }


    /**
     * 分享文字给QQ好友
     *
     * @param context
     * @param content
     */
    public void shareQQ(Context context, String content) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType("text/plain");
        try {
            sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
            Intent chooserIntent = Intent.createChooser(sendIntent, "选择分享途径");
            if (chooserIntent == null) {
                return;
            }
            context.startActivity(sendIntent);
        } catch (Exception e) {
            context.startActivity(sendIntent);
        }
    }

    /**
     * 分享文字给微信好友
     *
     * @param content
     */
    private void shareToFriend(Context context, String content) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        try {
            intent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
            Intent chooserIntent = Intent.createChooser(intent, "选择分享途径");
            if (chooserIntent == null) {
                return;
            }
            context.startActivity(intent);
        } catch (Exception e) {
            context.startActivity(intent);
        }
    }

    private void showConfirmDialog(final GetAllVersionResult.DataEntity dataEntity) {
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
                .deleteVersion(getUserId(), id)
                .compose(RxHelper.<DeleteVersionResult>io_main())
                .subscribe(new Subscriber<DeleteVersionResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());

                    }

                    @Override
                    public void onNext(DeleteVersionResult deleteVersionProjectResult) {
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

    private void getData() {
        Api.getApi0()
                .getAllVersion(getUserId(), id)
                .compose(RxHelper.<GetAllVersionResult>io_main())
                .subscribe(new Subscriber<GetAllVersionResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh(refresh);
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(GetAllVersionResult getAllVersionResult) {
                        stopRefresh(refresh);
                        if (getAllVersionResult.getCode() == 1) {
                            List<GetAllVersionResult.DataEntity> data = getAllVersionResult.getData();
                            adapter.setmDatas(data);
                            adapter.notifyDataSetChanged();
                            if (data == null || data.size() == 0) {
                                tvNoData.setVisibility(View.VISIBLE);
                            } else {
                                tvNoData.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtils.showCustomBgToast(getAllVersionResult.getMsg());
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
