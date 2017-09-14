package me.zhouzhuo810.zzapidoc.common.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetDictionaryResult;
import me.zhouzhuo810.zzapidoc.common.rx.ExitEvent;
import me.zhouzhuo810.zzapidoc.common.rx.RxBus;
import me.zhouzhuo810.zzapidoc.common.utils.ZSharedUtil;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.CommonAdapter;
import me.zhouzhuo810.zzapidoc.ui.adapter.base.ViewHolder;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zz on 2017/6/26.
 */
public abstract class BaseActivity extends zhouzhuo810.me.zzandframe.ui.act.BaseActivity {

    private boolean isForeground;

    private CompositeSubscription compositeSubscription;


    public interface OnTwoBtnClick {
        void onOk();

        void onCancel();
    }

    public interface OnTwoBtnEditClick {
        void onOk(String content);

        void onCancel();
    }

    public interface OnItemClick {
        void onItemClick(int position, String content);
    }

    public interface OnOneBtnClickListener {
        void onProgress(TextView tvProgress, ProgressBar pb);

        void onOK();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerExitObserver();
    }

    private void addSubscription(Subscription subscription) {
        if (compositeSubscription == null)
            compositeSubscription = new CompositeSubscription();
        if (subscription != null)
            compositeSubscription.add(subscription);
    }


    private void registerExitObserver() {
        Subscription subscription = RxBus.getInstance()
                .toObserverable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Object event) {
                        if (event instanceof ExitEvent) {
                            closeAct();
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void closeAllAct() {
        RxBus.getInstance().post(ExitEvent.getInstance());
    }

    public boolean isForeground() {
        return isForeground;
    }

    public String[] dicToString(List<GetDictionaryResult.DataBean> dic) {
        if (dic == null)
            return new String[0];
        String[] strings = new String[dic.size()];
        for (int i = 0; i < dic.size(); i++) {
            GetDictionaryResult.DataBean dataEntity = dic.get(i);
            strings[i] = dataEntity.getName();
        }
        return strings;
    }

    public List<String> dicToList(List<GetDictionaryResult.DataBean> dic) {
        if (dic == null)
            return new ArrayList<>();
        List<String> list=new ArrayList<>();
        for (int i = 0; i < dic.size(); i++) {
            GetDictionaryResult.DataBean dataEntity = dic.get(i);
            list.add(dataEntity.getName());
        }
        return list;
    }

    public String getUserId() {
        return ZSharedUtil.getUserId();
    }

    public void startActWithIntent(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    public void startActForResultWithIntent(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    public void closeAct() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    public void unSubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void startRefresh(SwipeRefreshLayout refreshLayout) {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
            refreshLayout.setProgressViewOffset(false, 0, AutoUtils.getPercentHeightSize(24));
            refreshLayout.setRefreshing(true);
        }
    }

    public void stopRefresh(SwipeRefreshLayout refreshLayout) {
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hidePd();
        hideTwoBtnDialog();
        if (compositeSubscription != null)
            compositeSubscription.unsubscribe();
    }
}
