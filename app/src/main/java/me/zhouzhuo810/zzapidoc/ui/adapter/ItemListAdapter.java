package me.zhouzhuo810.zzapidoc.ui.adapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllItemsResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllMyWidgetResult;
import me.zhouzhuo810.zzapidoc.common.rule.ISearch;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.CommonAdapter;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.ViewHolder;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */
public class ItemListAdapter extends CommonAdapter<GetAllItemsResult.DataEntity> implements ISearch<GetAllItemsResult.DataEntity> {

    public ItemListAdapter(Context context, List<GetAllItemsResult.DataEntity> datas, int layoutId, boolean isNeedScale) {
        super(context, datas, layoutId, isNeedScale);
    }

    @Override
    public void convert(ViewHolder holder, GetAllItemsResult.DataEntity dataEntity) {
        holder.setText(R.id.tv_name, dataEntity.getName())
                .setText(R.id.tv_time, dataEntity.getCreateTime())
                .setText(R.id.tv_type, dataEntity.getName())
                .setText(R.id.tv_name, dataEntity.getWidgetName())
                .setText(R.id.tv_res_id, dataEntity.getResId())
                .setText(R.id.tv_create_man, dataEntity.getCreatePerson());
    }

    @Override
    public void startSearch(String content) {
        if (mDatas == null) {
            return;
        }
        List<GetAllItemsResult.DataEntity> result = new ArrayList<>();
        for (GetAllItemsResult.DataEntity mData : mDatas) {
            if (mData.toSearch().contains(content)) {
                result.add(mData);
            }
        }
        mDatas = result;
        notifyDataSetChanged();
    }

    @Override
    public void cancelSearch(List<GetAllItemsResult.DataEntity> list) {
        mDatas = list;
        notifyDataSetChanged();
    }
}
