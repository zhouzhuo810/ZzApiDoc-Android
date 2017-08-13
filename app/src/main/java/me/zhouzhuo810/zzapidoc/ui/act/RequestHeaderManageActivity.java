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
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteRequestHeaderResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetRequestHeaderResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.adapter.RequestHeaderListAdapter;
import rx.Subscriber;

/**
 * Created by admin on 2017/8/13.
 */

public class RequestHeaderManageActivity extends BaseActivity {
    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private SwipeRefreshLayout refresh;
    private ListView lv;
    private TextView tvNoData;
    private String projectId;
    private List<GetRequestHeaderResult.DataBean> list;
    private RequestHeaderListAdapter adapter;
    private String interfaceId;


    @Override
    public int getLayoutId() {
        return R.layout.activity_request_header_manage;
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
        adapter = new RequestHeaderListAdapter(this, list, R.layout.list_item_header, true);
        lv.setAdapter(adapter);
    }

    @Override
    public void initData() {
        projectId = getIntent().getStringExtra("projectId");
        interfaceId = getIntent().getStringExtra("interfaceId");
    }

    private void getData() {
        Api.getApi0()
                .getRequestHeaderByInterfaceId(interfaceId, getUserId())
                .compose(RxHelper.<GetRequestHeaderResult>io_main())
                .subscribe(new Subscriber<GetRequestHeaderResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh(refresh);
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text)+e.toString());
                    }

                    @Override
                    public void onNext(GetRequestHeaderResult getAllInterfaceGroupResult) {
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
                Intent intent = new Intent(RequestHeaderManageActivity.this, AddRequestHeaderActivity.class);
                intent.putExtra("projectId", projectId);
                intent.putExtra("interfaceId", interfaceId);
                startActWithIntent(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                showListDialog(Arrays.asList("删除"), true, null, new OnItemClick() {
                    @Override
                    public void onItemClick(int pos, String content) {
                        switch (pos) {
                            case 0:
                                delete(adapter.getmDatas().get(position).getId());
                                break;
                        }
                    }
                });
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    private void delete(String id) {
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .deleteRequestHeader(id, getUserId())
                .compose(RxHelper.<DeleteRequestHeaderResult>io_main())
                .subscribe(new Subscriber<DeleteRequestHeaderResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());

                    }

                    @Override
                    public void onNext(DeleteRequestHeaderResult deleteArgResult) {
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
