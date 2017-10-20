package me.zhouzhuo810.zzapidoc.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.ZApplication;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllQrCodeResult;
import me.zhouzhuo810.zzapidoc.common.utils.SharedUtil;
import zhouzhuo810.me.zzandframe.ui.adapter.RvAutoBaseAdapter;

/**
 * Created by zhouzhuo810 on 2017/10/20.
 */

public class QrCodeRvAdapter extends RvAutoBaseAdapter<GetAllQrCodeResult.DataEntity> {

    private OnLongClick onLongClick;
    private String serverIp;

    public interface OnLongClick {
        void onLongClick(GetAllQrCodeResult.DataEntity dataEntity);

        void onClick(ImageView iv, String url);
    }

    public void setOnLongClick(OnLongClick onLongClick) {
        this.onLongClick = onLongClick;
    }

    public QrCodeRvAdapter(Context context, List<GetAllQrCodeResult.DataEntity> data) {
        super(context, data);
        serverIp = SharedUtil.getString(ZApplication.getInstance(), "server_config");
    }

    @Override
    protected int getLayoutId(int i) {
        return R.layout.list_item_qrcode;
    }

    @Override
    protected void fillData(ViewHolder viewHolder, final GetAllQrCodeResult.DataEntity dataEntity, int i) {
        viewHolder.setText(R.id.tv_title, dataEntity.getTitle());
        final ImageView iv = viewHolder.getView(R.id.iv_pic);
        Glide.with(context).load(serverIp+dataEntity.getUrl()).crossFade().into(iv);
        viewHolder.setOnLongClickListener(R.id.ll_root, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onLongClick != null) {
                    onLongClick.onLongClick(dataEntity);
                }
                return true;
            }
        });
        viewHolder.setOnClickListener(R.id.ll_root, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLongClick != null) {
                    onLongClick.onClick(iv, serverIp+dataEntity.getUrl());
                }
            }
        });

    }
}
