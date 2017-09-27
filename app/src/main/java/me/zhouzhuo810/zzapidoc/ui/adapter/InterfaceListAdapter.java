package me.zhouzhuo810.zzapidoc.ui.adapter;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.rule.ISearch;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.CommonAdapter;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.ViewHolder;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */
public class InterfaceListAdapter extends CommonAdapter<GetAllInterfaceResult.DataEntity> implements ISearch<GetAllInterfaceResult.DataEntity> {

    public InterfaceListAdapter(Context context, List<GetAllInterfaceResult.DataEntity> datas, int layoutId, boolean isNeedScale) {
        super(context, datas, layoutId, isNeedScale);
    }

    @Override
    public void convert(ViewHolder holder, GetAllInterfaceResult.DataEntity dataEntity) {
        holder.setText(R.id.tv_name, dataEntity.getName())
                .setText(R.id.tv_time, dataEntity.getCreateTime())
                .setText(R.id.tv_method, dataEntity.getMethod())
                .setText(R.id.tv_note, dataEntity.getNote())
                .setText(R.id.tv_path, dataEntity.getPath())
                .setVisible(R.id.iv_test, dataEntity.isTest() ? View.VISIBLE : View.GONE)
                .setText(R.id.tv_create_man, dataEntity.getCreateUserName())
                .setText(R.id.tv_request_header_no, dataEntity.getRequestHeadersNo() + "")
                .setText(R.id.tv_request_params_no, dataEntity.getRequestParamsNo() + "")
                .setText(R.id.tv_response_params_no, dataEntity.getResponseParamsNo() + "");
    }

    @Override
    public void startSearch(String content) {
        if (mDatas == null) {
            return;
        }
        List<GetAllInterfaceResult.DataEntity> result = new ArrayList<>();
        for (GetAllInterfaceResult.DataEntity mData : mDatas) {
            if (mData.toSearch().contains(content)) {
                result.add(mData);
            }
        }
        mDatas = result;
        notifyDataSetChanged();
    }

    @Override
    public void cancelSearch(List<GetAllInterfaceResult.DataEntity> list) {
        mDatas = list;
        notifyDataSetChanged();
    }
}
