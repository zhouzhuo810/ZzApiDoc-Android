package me.zhouzhuo810.zzapidoc.ui.adapter;

import android.content.Context;

import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetRequestHeaderResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetRequestHeaderResult;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.CommonAdapter;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.ViewHolder;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */
public class RequestHeaderListAdapter extends CommonAdapter<GetRequestHeaderResult.DataBean> {

    public RequestHeaderListAdapter(Context context, List<GetRequestHeaderResult.DataBean> datas, int layoutId, boolean isNeedScale) {
        super(context, datas, layoutId, isNeedScale);
    }

    @Override
    public void convert(ViewHolder holder, GetRequestHeaderResult.DataBean dataEntity) {
        holder.setText(R.id.tv_name, dataEntity.getName())
                .setText(R.id.tv_time, dataEntity.getCreateTime())
                .setText(R.id.tv_value, dataEntity.getValue())
                .setText(R.id.tv_note, dataEntity.getNote())
                .setText(R.id.tv_create_man, dataEntity.getCreateUserName()+(dataEntity.isGlobal() ? " (全局)":""));
    }

}
