package me.zhouzhuo810.zzapidoc.ui.adapter;

import android.content.Context;

import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.CommonAdapter;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.ViewHolder;

/**
 *
 * Created by zhouzhuo810 on 2017/8/11.
 */
public class InterfaceGroupListAdapter extends CommonAdapter<GetAllInterfaceGroupResult.DataBean> {

    public InterfaceGroupListAdapter(Context context, List<GetAllInterfaceGroupResult.DataBean> datas, int layoutId, boolean isNeedScale) {
        super(context, datas, layoutId, isNeedScale);
    }

    @Override
    public void convert(ViewHolder holder, GetAllInterfaceGroupResult.DataBean dataEntity) {

        holder.setText(R.id.tv_name, dataEntity.getName())
                .setText(R.id.tv_time, dataEntity.getCreateTime())
                .setText(R.id.tv_create_man, dataEntity.getCreateUserName())
                .setText(R.id.tv_interface_no, dataEntity.getInterfaceNo() + "");
    }

}
