package me.zhouzhuo810.zzapidoc.ui.adapter;

import android.content.Context;

import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllErrorCodeResult;
import zhouzhuo810.me.zzandframe.ui.adapter.LvAutoBaseAdapter;

/**
 * Created by zhouzhuo810 on 2017/12/29.
 */
public class ErrorCodeListAdapter extends LvAutoBaseAdapter<GetAllErrorCodeResult.DataEntity> {
    public ErrorCodeListAdapter(Context context, List<GetAllErrorCodeResult.DataEntity> data) {

        super(context, data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_error_code;
    }

    @Override
    protected void fillData(ViewHolder viewHolder, GetAllErrorCodeResult.DataEntity dataEntity, int i) {
        viewHolder.setText(R.id.tv_code, dataEntity.getCode() + "")
                .setText(R.id.tv_time, dataEntity.getCreateTime())
                .setText(R.id.tv_create_man, dataEntity.getCreateMan())
                .setText(R.id.tv_note, dataEntity.getNote());

    }
}
