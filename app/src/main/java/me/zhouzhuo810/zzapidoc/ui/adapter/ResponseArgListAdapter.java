package me.zhouzhuo810.zzapidoc.ui.adapter;

import android.content.Context;

import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetRequestArgResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetResponseArgResult;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.CommonAdapter;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.ViewHolder;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */
public class ResponseArgListAdapter extends CommonAdapter<GetResponseArgResult.DataBean> {

    public ResponseArgListAdapter(Context context, List<GetResponseArgResult.DataBean> datas, int layoutId, boolean isNeedScale) {
        super(context, datas, layoutId, isNeedScale);
    }

    @Override
    public void convert(ViewHolder holder, GetResponseArgResult.DataBean dataEntity) {
        holder.setText(R.id.tv_name, dataEntity.getName())
                .setText(R.id.tv_time, dataEntity.getCreateTime())
                .setText(R.id.tv_note, dataEntity.getNote())
                .setText(R.id.tv_create_man, dataEntity.getCreateUserName());
        switch (dataEntity.getType()) {
            case 0:
                holder.setText(R.id.tv_type, "string");
                break;
            case 1:
                holder.setText(R.id.tv_type, "number");
                break;
            case 2:
                holder.setText(R.id.tv_type, "object");
                break;
            case 3:
                holder.setText(R.id.tv_type, "array[object]");
                break;
            case 4:
                holder.setText(R.id.tv_type, "array[string]");
                break;
        }
    }

}
