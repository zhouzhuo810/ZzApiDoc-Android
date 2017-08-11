package me.zhouzhuo810.zzapidoc.ui.fgm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllProjectResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseFragment;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.act.AddProjectActivity;
import me.zhouzhuo810.zzapidoc.ui.act.InterfaceGroupManageActivity;
import me.zhouzhuo810.zzapidoc.ui.adapter.ProjectListAdapter;
import retrofit2.http.POST;
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
                startActivity(intent);
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
