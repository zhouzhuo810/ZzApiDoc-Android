package me.zhouzhuo810.zzapidoc.ui.adapter;

import android.content.Context;

import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllVersionProjectResult;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.CommonAdapter;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.ViewHolder;

/**
 * Created by zhouzhuo810 on 2017/10/26.
 */

public class VersionProjectListAdapter extends CommonAdapter<GetAllVersionProjectResult.DataEntity> {

    public VersionProjectListAdapter(Context context, List<GetAllVersionProjectResult.DataEntity> datas, int layoutId, boolean isNeedScale) {
        super(context, datas, layoutId, isNeedScale);
    }

    @Override
    public void convert(ViewHolder holder, GetAllVersionProjectResult.DataEntity dataEntity) {
        holder.setText(R.id.tv_name, dataEntity.getName())
                .setText(R.id.tv_desc, dataEntity.getNote())
                .setText(R.id.tv_time, dataEntity.getCreateTime())
                .setText(R.id.tv_create_man, dataEntity.getUserName());
    }
}
