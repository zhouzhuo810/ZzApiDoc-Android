package me.zhouzhuo810.zzapidoc.ui.adapter;

import android.content.Context;
import android.widget.ProgressBar;

import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllMyTodoListWebResult;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.CommonAdapter;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.ViewHolder;
import zhouzhuo810.me.zzandframe.ui.adapter.RvAutoBaseAdapter;

/**
 * Created by zhouzhuo810 on 2017/10/26.
 */

public class TodoListAdapter extends RvAutoBaseAdapter<GetAllMyTodoListWebResult.DataEntity> {


    public TodoListAdapter(Context context, List<GetAllMyTodoListWebResult.DataEntity> data) {
        super(context, data);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.list_item_todo;
    }

    @Override
    protected void fillData(ViewHolder holder, GetAllMyTodoListWebResult.DataEntity dataEntity, int position) {
        holder.setText(R.id.tv_name, dataEntity.getContent())
                .setText(R.id.tv_time, dataEntity.getCreateTime())
                .setText(R.id.tv_desc, dataEntity.getNote() + "（完成进度：" + dataEntity.getProgress() + "%）")
                .setText(R.id.tv_create_man, dataEntity.getCreateUserName() + "创建 - > " + dataEntity.getHandlePersonName() + "执行");
        ProgressBar progressBar = holder.getView(R.id.pb);
        progressBar.setProgress(dataEntity.getProgress());
    }
}
