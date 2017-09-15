package me.zhouzhuo810.zzapidoc.ui.adapter;

import android.content.Context;

import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.PdfEntity;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.CommonAdapter;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.ViewHolder;

/**
 * Created by zhouzhuo810 on 2017/9/15.
 */

public class PdfListAdapter extends CommonAdapter<PdfEntity> {

    public PdfListAdapter(Context context, List<PdfEntity> datas, int layoutId, boolean isNeedScale) {
        super(context, datas, layoutId, isNeedScale);
    }

    @Override
    public void convert(ViewHolder holder, PdfEntity pdfEntity) {
        holder.setText(R.id.tv_name, pdfEntity.getName())
                .setText(R.id.tv_size, pdfEntity.getSize());
    }
}
