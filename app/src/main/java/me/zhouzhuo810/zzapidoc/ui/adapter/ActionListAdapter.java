package me.zhouzhuo810.zzapidoc.ui.adapter;

import android.content.Context;

import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllActionsResult;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.CommonAdapter;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.ViewHolder;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */
public class ActionListAdapter extends CommonAdapter<GetAllActionsResult.DataEntity> {
    public static final int TYPE_DIALOG_PROGRESS = 0;
    public static final int TYPE_DIALOG_TWO_BTN = 1;
    public static final int TYPE_DIALOG_EDIT = 2;
    public static final int TYPE_DIALOG_UPDATE = 3;
    public static final int TYPE_DIALOG_LIST = 4;
    public static final int TYPE_DIALOG_TWO_BTN_IOS = 5;
    public static final int TYPE_CHOOSE_PIC = 6;
    public static final int TYPE_USE_API = 7;
    public static final int TYPE_TARGET_ACT = 8;
    public static final int TYPE_CLOSE_ACT = 9;
    public static final int TYPE_CLOSE_ALL_ACT = 10;

    public ActionListAdapter(Context context, List<GetAllActionsResult.DataEntity> datas, int layoutId, boolean isNeedScale) {
        super(context, datas, layoutId, isNeedScale);
    }

    @Override
    public void convert(ViewHolder holder, GetAllActionsResult.DataEntity dataEntity) {
        holder.setText(R.id.tv_name, dataEntity.getName())
                .setText(R.id.tv_time, dataEntity.getCreateTime())
                .setText(R.id.tv_create_man, "-> " + dataEntity.getCreatePerson());
        switch (dataEntity.getType()) {
            case TYPE_TARGET_ACT:
                holder.setText(R.id.tv_ip, dataEntity.getOkActName());
                break;
            case TYPE_USE_API:
                holder.setText(R.id.tv_ip, dataEntity.getOkApiName());
                break;
            default:
                holder.setText(R.id.tv_ip, dataEntity.getTitle());
                break;
        }
    }

}
