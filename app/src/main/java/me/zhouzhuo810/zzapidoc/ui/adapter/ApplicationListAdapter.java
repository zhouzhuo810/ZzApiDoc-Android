package me.zhouzhuo810.zzapidoc.ui.adapter;

import android.content.Context;

import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllApplicationResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllProjectResult;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.CommonAdapter;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.ViewHolder;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */
public class ApplicationListAdapter extends CommonAdapter<GetAllApplicationResult.DataBean> {

    public ApplicationListAdapter(Context context, List<GetAllApplicationResult.DataBean> datas, int layoutId, boolean isNeedScale) {
        super(context, datas, layoutId, isNeedScale);
    }

    @Override
    public void convert(ViewHolder holder, GetAllApplicationResult.DataBean dataEntity) {


        holder.setText(R.id.tv_name, dataEntity.getChName())
                .setText(R.id.tv_time, dataEntity.getCreateTime())
                .setText(R.id.tv_create_man, dataEntity.getPackageName())
                .setText(R.id.tv_desc, "minSDK=" + dataEntity.getMinSDK() + "; targetSDK=" + dataEntity.getTargetSDK()
                        + "; compileSDK=" + dataEntity.getCompileSDK())
                .setText(R.id.tv_act_no, ""+dataEntity.getActCount())
                .setText(R.id.tv_fgm_no, ""+dataEntity.getFgmCount());
    }

}
