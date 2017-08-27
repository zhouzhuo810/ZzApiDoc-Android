package me.zhouzhuo810.zzapidoc.ui.adapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllMyWidgetResult;
import me.zhouzhuo810.zzapidoc.common.rule.ISearch;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.CommonAdapter;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.ViewHolder;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */
public class WidgetListAdapter extends CommonAdapter<GetAllMyWidgetResult.DataBean> implements ISearch<GetAllMyWidgetResult.DataBean> {

    public WidgetListAdapter(Context context, List<GetAllMyWidgetResult.DataBean> datas, int layoutId, boolean isNeedScale) {
        super(context, datas, layoutId, isNeedScale);
    }

    @Override
    public void convert(ViewHolder holder, GetAllMyWidgetResult.DataBean dataEntity) {
        holder.setText(R.id.tv_name, dataEntity.getName())
                .setText(R.id.tv_time, dataEntity.getCreateTime())
                .setText(R.id.tv_type, dataEntity.getName())
                .setText(R.id.tv_name, dataEntity.getTitle())
                .setText(R.id.tv_res_id, dataEntity.getResId())
                .setText(R.id.tv_create_man, dataEntity.getCreateUserName());
    }

    @Override
    public void startSearch(String content) {
        if (mDatas == null) {
            return;
        }
        List<GetAllMyWidgetResult.DataBean> result = new ArrayList<>();
        for (GetAllMyWidgetResult.DataBean mData : mDatas) {
            if (mData.toSearch().contains(content)) {
                result.add(mData);
            }
        }
        mDatas = result;
        notifyDataSetChanged();
    }

    @Override
    public void cancelSearch(List<GetAllMyWidgetResult.DataBean> list) {
        mDatas = list;
        notifyDataSetChanged();
    }
}
