package me.zhouzhuo810.zzapidoc.ui.fgm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.Constants;
import me.zhouzhuo810.zzapidoc.common.api.entity.PdfEntity;
import me.zhouzhuo810.zzapidoc.common.base.BaseFragment;
import me.zhouzhuo810.zzapidoc.common.utils.FileSizeUtil;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.ui.act.PdfPreviewActivity;
import me.zhouzhuo810.zzapidoc.ui.adapter.PdfListAdapter;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * Created by zhouzhuo810 on 2017/9/15.
 */

public class PdfFragment extends BaseFragment {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private SwipeRefreshLayout refresh;
    private ListView lv;
    private TextView tvNoData;

    private List<PdfEntity> pdfs;
    private PdfListAdapter adapter;


    @Override
    public int getLayoutId() {
        return R.layout.fgm_pdf_manage;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) rootView.findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) rootView.findViewById(R.id.rl_right);
        refresh = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        lv = (ListView) rootView.findViewById(R.id.lv);
        tvNoData = (TextView) rootView.findViewById(R.id.tv_no_data);

        pdfs = new ArrayList<>();
        adapter = new PdfListAdapter(getActivity(), pdfs, R.layout.list_item_pdf, true);
        lv.setAdapter(adapter);
    }

    @Override
    public void initData() {
        startRefresh(refresh);
        getData();
    }

    private void getData() {
        File[] files = new File(Constants.EXPORT_PDF_PATH).listFiles();
        if (files != null) {
            pdfs.clear();
            for (File pdf : files) {
                PdfEntity entity = new PdfEntity();
                entity.setName(pdf.getName());
                entity.setPath(pdf.getAbsolutePath());
                entity.setSize(FileSizeUtil.getFileSize(pdf));
                pdfs.add(entity);
            }
            adapter.setmDatas(pdfs);
            adapter.notifyDataSetChanged();
        }
        stopRefresh(refresh);
    }


    @Override
    public void initEvent() {

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openPdf(pdfs.get(position));
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                getBaseAct().showListDialog(Arrays.asList("删除"), true, null, new IBaseActivity.OnItemClick() {
                    @Override
                    public void onItemClick(int i, String s) {
                        delete(pdfs.get(position));
                    }
                });
                return true;
            }
        });

    }

    private void openPdf(PdfEntity pdfEntity) {
        Intent intent = new Intent(getActivity(), PdfPreviewActivity.class);
        intent.putExtra("pdf", pdfEntity.getPath());
        startActWithIntent(intent);
    }

    private void delete(PdfEntity pdfEntity) {
        File file = new File(pdfEntity.getPath());
        if (file.exists()) {
            boolean delete = file.delete();
            if (delete) {
                ToastUtils.showCustomBgToast("删除成功！");
            } else {
                ToastUtils.showCustomBgToast("删除失败！");
            }
            startRefresh(refresh);
            getData();
        } else {
            ToastUtils.showCustomBgToast("文件不存在或已被删除！");
        }
    }


    @Override
    public void resume() {

    }

    @Override
    public void saveState(Bundle bundle) {

    }

    @Override
    public void restoreState(@Nullable Bundle bundle) {

    }

    @Override
    public void destroyView() {

    }
}
