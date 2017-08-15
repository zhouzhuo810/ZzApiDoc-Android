package me.zhouzhuo810.zzapidoc.ui.adapter;

import android.content.Context;

import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetRequestArgResult;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.CommonAdapter;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.ViewHolder;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */
public class RequestArgListAdapter extends CommonAdapter<GetRequestArgResult.DataBean> {

    public RequestArgListAdapter(Context context, List<GetRequestArgResult.DataBean> datas, int layoutId, boolean isNeedScale) {
        super(context, datas, layoutId, isNeedScale);
    }

    @Override
    public void convert(ViewHolder holder, GetRequestArgResult.DataBean dataEntity) {
        holder.setText(R.id.tv_name, dataEntity.getName())
                .setText(R.id.tv_time, dataEntity.getCreateTime())
                .setText(R.id.tv_note, dataEntity.getNote() + " (" + (dataEntity.isRequire() ? "必填" : "非必填") + ")")
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
            case 5:
                //array
                holder.setText(R.id.tv_type, "array");
                break;
            case 6:
                //file
                holder.setText(R.id.tv_type, "file");
                break;
            default:
                holder.setText(R.id.tv_type, "未知");
                break;
        }
    }

}
