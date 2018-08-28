package me.zhouzhuo810.zzapidoc.ui.fgm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Arrays;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteTodoListResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllMyTodoListTodayResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllMyTodoListWebResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.SendEmailResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseFragment;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.CopyUtils;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.act.AddTodoActivity;
import me.zhouzhuo810.zzapidoc.ui.act.EditTodoActivity;
import me.zhouzhuo810.zzapidoc.ui.adapter.TodoListAdapter;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;
import zhouzhuo810.me.zzandframe.ui.adapter.RvAutoBaseAdapter;

public class TodoFragment extends BaseFragment {

    private RelativeLayout rlRight;
    private SmartRefreshLayout refresh;
    private RecyclerView rv;
    private LinearLayout llNoData;
    private TodoListAdapter adapter;

    private int pageIndex = 1;

    @Override
    public int getLayoutId() {
        return R.layout.fgm_todo;
    }

    @Override
    public void initView() {
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        refresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        llNoData = (LinearLayout) findViewById(R.id.ll_no_data);
    }

    @Override
    public void initData() {
        adapter = new TodoListAdapter(getActivity(), null);
        rv.setAdapter(adapter);
    }

    @Override
    public void initEvent() {
        rlRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTodoActivity.class);
                startActivity(intent);
            }
        });

        Button btnCopyTodo = findViewById(R.id.btn_copy_today);
        Button btnCopyMonthTodo = findViewById(R.id.btn_copy_cur_month);
        Button btnCopyWeekTodo = findViewById(R.id.btn_copy_cur_week);
        btnCopyTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyTodo();
            }
        });
        btnCopyMonthTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyTodoMonth();
            }
        });
        btnCopyWeekTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyTodoWeek();
            }
        });


        adapter.setOnItemClickListener(new RvAutoBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int i) {
                getBaseAct().showListDialog(Arrays.asList("编辑", "复制"), true, null, new IBaseActivity.OnItemClick() {
                    @Override
                    public void onItemClick(int pos, String s) {
                        switch (pos) {
                            case 0:
                                Intent intent = new Intent(getActivity(), EditTodoActivity.class);
                                intent.putExtra("todo", adapter.getData().get(i));
                                startActivity(intent);
                                break;
                            case 1:
                                copyOneTodo(adapter.getData().get(i));
                                break;
                        }
                    }
                });
            }
        });
        adapter.setOnItemLongClickListener(new RvAutoBaseAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, final int i) {
                getBaseAct().showListDialog(Arrays.asList("发送邮件", "删除"), true, null, new IBaseActivity.OnItemClick() {
                    @Override
                    public void onItemClick(int pos, String s) {
                        switch (pos) {
                            case 0:
                                sendEmail(adapter.getData().get(i).getId());
                                break;
                            case 1:
                                confirmDelete(adapter.getData().get(i).getId());
                                break;
                        }
                    }
                });
                return true;
            }
        });

        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getTodoList();
            }
        });

        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadMoreList();
            }
        });
    }

    private void copyOneTodo(GetAllMyTodoListWebResult.DataEntity datum) {
        if (datum != null) {
            CopyUtils.copyPlainText(getActivity(), datum.getContent(), "1" + "." + datum.getContent() + ":" + datum.getNote() + "（" + "完成进度：" + datum.getProgress() + " %)\n");
            ToastUtils.showCustomBgToast("复制成功！");
        }
    }

    private void confirmDelete(final String todoId) {
        getBaseAct().showTwoBtnDialog("删除", "确认删除吗？", false, new IBaseActivity.OnTwoBtnClick() {
            @Override
            public void onOk() {
                Api.getApi0()
                        .deleteTodoList(todoId, getUserId())
                        .compose(RxHelper.<DeleteTodoListResult>io_main())
                        .subscribe(new Subscriber<DeleteTodoListResult>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(DeleteTodoListResult deleteTodoListResult) {
                                ToastUtils.showCustomBgToast(deleteTodoListResult.getMsg());
                                if (deleteTodoListResult.getCode() == 1) {
                                    refresh.autoRefresh();
                                }
                            }
                        });
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void sendEmail(String todoId) {
        Api.getApi0()
                .sendEmail(getUserId(), todoId)
                .compose(RxHelper.<SendEmailResult>io_main())
                .subscribe(new Subscriber<SendEmailResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(SendEmailResult sendEmailResult) {
                        ToastUtils.showCustomBgToast(sendEmailResult.getMsg());
                    }
                });
    }

    private void copyTodo() {
        Api.getApi0()
                .getAllMyTodoListToday(getUserId())
                .compose(RxHelper.<GetAllMyTodoListTodayResult>io_main())
                .subscribe(new Subscriber<GetAllMyTodoListTodayResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text));
                    }

                    @Override
                    public void onNext(GetAllMyTodoListTodayResult getAllMyTodoListTodayResult) {
                        if (getAllMyTodoListTodayResult.getCode() == 1) {
                            List<GetAllMyTodoListTodayResult.DataEntity> data = getAllMyTodoListTodayResult.getData();
                            if (data != null) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < data.size(); i++) {
                                    GetAllMyTodoListTodayResult.DataEntity datum = data.get(i);
                                    sb.append(i+1).append(".").append(datum.getContent()).append(":").append(datum.getNote()).append("（").append("完成进度：").append(datum.getProgress()).append(" %)\n");
                                }
                                CopyUtils.copyPlainText(getContext(), "今日待办", sb.toString());
                                ToastUtils.showCustomBgToast("复制成功！");
                            }
                        }
                    }
                });
    }

    private void copyTodoMonth() {
        Api.getApi0()
                .getAllMyTodoListCurMonth(getUserId())
                .compose(RxHelper.<GetAllMyTodoListTodayResult>io_main())
                .subscribe(new Subscriber<GetAllMyTodoListTodayResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text));
                    }

                    @Override
                    public void onNext(GetAllMyTodoListTodayResult getAllMyTodoListTodayResult) {
                        if (getAllMyTodoListTodayResult.getCode() == 1) {
                            List<GetAllMyTodoListTodayResult.DataEntity> data = getAllMyTodoListTodayResult.getData();
                            if (data != null) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < data.size(); i++) {
                                    GetAllMyTodoListTodayResult.DataEntity datum = data.get(i);
                                    sb.append(i+1).append(".").append(datum.getContent()).append(":").append(datum.getNote()).append("（").append("完成进度：").append(datum.getProgress()).append(" %)\n");
                                }
                                CopyUtils.copyPlainText(getContext(), "当月待办", sb.toString());
                                ToastUtils.showCustomBgToast("复制成功！");
                            }
                        }
                    }
                });
    }

    private void copyTodoWeek() {
        Api.getApi0()
                .getAllMyTodoListCurWeek(getUserId())
                .compose(RxHelper.<GetAllMyTodoListTodayResult>io_main())
                .subscribe(new Subscriber<GetAllMyTodoListTodayResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text));
                    }

                    @Override
                    public void onNext(GetAllMyTodoListTodayResult getAllMyTodoListTodayResult) {
                        if (getAllMyTodoListTodayResult.getCode() == 1) {
                            List<GetAllMyTodoListTodayResult.DataEntity> data = getAllMyTodoListTodayResult.getData();
                            if (data != null) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < data.size(); i++) {
                                    GetAllMyTodoListTodayResult.DataEntity datum = data.get(i);
                                    sb.append(i+1).append(".").append(datum.getContent()).append(":").append(datum.getNote()).append("（").append("完成进度：").append(datum.getProgress()).append(" %)\n");
                                }
                                CopyUtils.copyPlainText(getContext(), "本周待办", sb.toString());
                                ToastUtils.showCustomBgToast("复制成功！");
                            }
                        }
                    }
                });
    }

    private void loadMoreList() {
        String userId = getUserId();
        Api.getApi0()
                .getAllMyTodoListWeb(userId, pageIndex + 1)
                .compose(RxHelper.<GetAllMyTodoListWebResult>io_main())
                .subscribe(new Subscriber<GetAllMyTodoListWebResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        refresh.finishLoadMore();
                    }

                    @Override
                    public void onNext(GetAllMyTodoListWebResult getAllMyTodoListWebResult) {
                        pageIndex = getAllMyTodoListWebResult.getIndexPage();
                        int totalPage = getAllMyTodoListWebResult.getTotalPage();
                        List<GetAllMyTodoListWebResult.DataEntity> rows = getAllMyTodoListWebResult.getRows();
                        if (adapter.getData() != null) {
                            adapter.getData().addAll(rows);
                            adapter.notifyDataSetChanged();
                        }

                        if (pageIndex == totalPage) {
                            refresh.finishLoadMoreWithNoMoreData();
                        } else {
                            refresh.finishLoadMore();
                        }
                    }
                });
    }

    private void getTodoList() {
        String userId = getUserId();
        Api.getApi0()
                .getAllMyTodoListWeb(userId, 1)
                .compose(RxHelper.<GetAllMyTodoListWebResult>io_main())
                .subscribe(new Subscriber<GetAllMyTodoListWebResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        refresh.finishRefresh();
                    }

                    @Override
                    public void onNext(GetAllMyTodoListWebResult getAllMyTodoListWebResult) {
                        int indexPage = getAllMyTodoListWebResult.getIndexPage();
                        int totalPage = getAllMyTodoListWebResult.getTotalPage();
                        List<GetAllMyTodoListWebResult.DataEntity> rows = getAllMyTodoListWebResult.getRows();
                        adapter.updateAll(rows);

                        if (rows == null || rows.size() == 0) {
                            llNoData.setVisibility(View.VISIBLE);
                        } else {
                            llNoData.setVisibility(View.GONE);
                        }

                        refresh.finishRefresh();
                        if (indexPage == totalPage) {
                            refresh.finishLoadMoreWithNoMoreData();
                        }
                    }
                });
    }


    @Override
    public void resume() {
        getTodoList();
    }

    @Override
    public void saveState(Bundle outState) {

    }

    @Override
    public void restoreState(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void destroyView() {

    }
}
