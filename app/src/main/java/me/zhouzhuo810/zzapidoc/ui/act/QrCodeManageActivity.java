package me.zhouzhuo810.zzapidoc.ui.act;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.Constants;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteQrCodeResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllQrCodeResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.adapter.QrCodeRvAdapter;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * Created by zhouzhuo810 on 2017/10/20.
 */

public class QrCodeManageActivity extends BaseActivity {
    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private SwipeRefreshLayout refresh;
    private RecyclerView rv;
    private TextView tvNoData;

    private List<GetAllQrCodeResult.DataEntity> list;
    private QrCodeRvAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qrcode_manage;
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
        rv = (RecyclerView) findViewById(R.id.rv);
        tvNoData = (TextView) findViewById(R.id.tv_no_data);
    }

    @Override
    public void initData() {

        list = new ArrayList<>();
        adapter = new QrCodeRvAdapter(this, list);
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rv.setAdapter(adapter);
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
                Intent intent = new Intent(QrCodeManageActivity.this, AddQrCodeActivity.class);
                startActWithIntent(intent);
            }
        });

        adapter.setOnLongClick(new QrCodeRvAdapter.OnLongClick() {
            @Override
            public void onLongClick(final GetAllQrCodeResult.DataEntity dataEntity) {
                showListDialog(Arrays.asList("修改", "删除"), true, null, new IBaseActivity.OnItemClick() {
                    @Override
                    public void onItemClick(int position, String s) {
                        switch (position) {
                            case 0:
//                                Intent intent = new Intent()
                                break;
                            case 1:
                                deleteQrCode(dataEntity.getId());
                                break;
                        }
                    }
                });
            }

            @Override
            public void onClick(ImageView iv, String url) {
                startPreview(iv, url);
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

    }

    private void startPreview(ImageView iv, String path) {
        Intent intent = new Intent(this, ImagePreviewActivity.class);
        intent.putExtra("url", path);
        if (Build.VERSION.SDK_INT >= 21) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    iv, getString(R.string.transition_product_img));
            ActivityCompat.startActivity(this, intent, options.toBundle());
        } else {
            startActWithIntent(intent);
        }
    }

    private void deleteQrCode(final String id) {
        showTwoBtnDialog("删除", "确定删除吗？", false, new IBaseActivity.OnTwoBtnClick() {
            @Override
            public void onOk() {
                showPd(getString(R.string.submiting_text), false);
                Api.getApi0()
                        .deleteQrCode(getUserId(), id)
                        .compose(RxHelper.<DeleteQrCodeResult>io_main())
                        .subscribe(new Subscriber<DeleteQrCodeResult>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                hidePd();
                                ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                            }

                            @Override
                            public void onNext(DeleteQrCodeResult deleteQrCodeResult) {
                                hidePd();
                                if (deleteQrCodeResult.getCode() == 1) {
                                    startRefresh(refresh);
                                    getData();
                                } else {
                                    ToastUtils.showCustomBgToast(deleteQrCodeResult.getMsg());
                                }
                            }
                        });
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void getData() {
        Api.getApi0()
                .getAllQrCode(getUserId())
                .compose(RxHelper.<GetAllQrCodeResult>io_main())
                .subscribe(new Subscriber<GetAllQrCodeResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh(refresh);
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }


                    @Override
                    public void onNext(GetAllQrCodeResult getAllQrCodeResult) {
                        stopRefresh(refresh);
                        if (getAllQrCodeResult.getCode() == 1) {
                            list = getAllQrCodeResult.getData();
                            adapter.updateAll(list);
                            if (list == null || list.size() == 0) {
                                tvNoData.setVisibility(View.VISIBLE);
                            } else {
                                tvNoData.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtils.showCustomBgToast(getAllQrCodeResult.getMsg());
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
