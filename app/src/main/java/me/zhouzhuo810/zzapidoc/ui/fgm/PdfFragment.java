package me.zhouzhuo810.zzapidoc.ui.fgm;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import me.zhouzhuo810.zzapidoc.BuildConfig;
import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.Constants;
import me.zhouzhuo810.zzapidoc.common.api.entity.PdfEntity;
import me.zhouzhuo810.zzapidoc.common.base.BaseFragment;
import me.zhouzhuo810.zzapidoc.common.utils.DateUtil;
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
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    String modiTime1 = o1.lastModified() + "";
                    String modiTime2 = o2.lastModified() + "";
                    return modiTime2.compareTo(modiTime1);
                }
            });
            pdfs.clear();
            for (File pdf : files) {
                PdfEntity entity = new PdfEntity();
                entity.setName(pdf.getName());
                entity.setTime(DateUtil.formatDateToYMdHm(new Date(pdf.lastModified())));
                entity.setPath(pdf.getAbsolutePath());
                entity.setSize(FileSizeUtil.getFileSize(pdf));
                pdfs.add(entity);
            }
            if (pdfs.size() == 0) {
                tvNoData.setVisibility(View.VISIBLE);
            } else {
                tvNoData.setVisibility(View.GONE);
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

                getBaseAct().showListDialog(Arrays.asList("删除", "分享到QQ", "分享到微信"), true, null, new IBaseActivity.OnItemClick() {
                    @Override
                    public void onItemClick(int i, String s) {
                        switch (i) {
                            case 0:
                                delete(pdfs.get(position));
                                break;
                            case 1:
                                try {
                                    shareFileToQQ(new File(pdfs.get(position).getPath()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 2:
                                try {
                                    shareFileToWx(pdfs.get(position).getName(), new File(pdfs.get(position).getPath()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                        }

                    }
                });
                return true;
            }
        });

    }

    private void shareFileToQQ(File file) throws Exception {
        Intent share = new Intent(Intent.ACTION_SEND);
        ComponentName component = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
        share.setComponent(component);
        Uri uri = null;
        if (Build.VERSION.SDK_INT > 23) {
            uri = FileProvider.getUriForFile(getActivity(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);
        } else {
            uri = Uri.fromFile(file);
        }
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setType("*/*");
        startActivity(Intent.createChooser(share, "发送"));
    }

    private void shareFileToWx(String title, File file) throws Exception {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        Uri uri = null;
        if (Build.VERSION.SDK_INT > 23) {
            uri = FileProvider.getUriForFile(getActivity(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setAction("android.intent.action.SEND");
        intent.setType("file/*");
        intent.putExtra(Intent.EXTRA_TEXT, title);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(intent);
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
