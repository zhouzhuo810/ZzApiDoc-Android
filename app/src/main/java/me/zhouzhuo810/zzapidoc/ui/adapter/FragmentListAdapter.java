package me.zhouzhuo810.zzapidoc.ui.adapter;

import android.content.Context;

import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllMyFragmentResult;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.CommonAdapter;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.ViewHolder;

/**
 *
 * Created by zhouzhuo810 on 2017/8/11.
 */
public class FragmentListAdapter extends CommonAdapter<GetAllMyFragmentResult.DataEntity> {

    public FragmentListAdapter(Context context, List<GetAllMyFragmentResult.DataEntity> datas, int layoutId, boolean isNeedScale) {
        super(context, datas, layoutId, isNeedScale);
    }

    @Override
    public void convert(ViewHolder holder, GetAllMyFragmentResult.DataEntity dataEntity) {
        holder.setText(R.id.tv_name, dataEntity.getName())
                .setText(R.id.tv_ip, dataEntity.getTitle())
                .setText(R.id.tv_time, dataEntity.getCreateTime());
    }

}
