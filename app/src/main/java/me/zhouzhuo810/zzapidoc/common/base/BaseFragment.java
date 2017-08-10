package me.zhouzhuo810.zzapidoc.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.autolayout.utils.AutoUtils;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.utils.ZSharedUtil;
import rx.Subscription;

/**
 * Created by zz on 2017/6/26.
 */
public abstract class BaseFragment extends Fragment {

    protected Subscription subscription1;
    protected Subscription subscription2;
    protected Subscription subscription3;

    protected boolean isAttach;
    protected View rootView;

    public abstract View getViews(LayoutInflater inflater, ViewGroup container);

    public abstract void initView();

    public abstract void initData();

    public abstract void initEvent();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getViews(inflater, container);
        initView();
        initData();
        initEvent();
        return rootView;
    }

    public void setSubscription1(Subscription subscription1) {
        unSubscribe(subscription1);
        this.subscription1 = subscription1;
    }

    public void setSubscription2(Subscription subscription2) {
        unSubscribe(subscription2);
        this.subscription2 = subscription2;
    }

    public void setSubscription3(Subscription subscription3) {
        unSubscribe(subscription3);
        this.subscription3 = subscription3;
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
    public void startActWithIntent(Intent intent) {
        startActivity(intent);
        ((BaseActivity) getActivity()).overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    public void startActWithIntentForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        ((BaseActivity) getActivity()).overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        onSaveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        onStateRestored(savedInstanceState);
        super.onViewStateRestored(savedInstanceState);
    }

    public abstract void onSaveState(Bundle bundle);

    public abstract void onStateRestored(Bundle bundle);


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isAttach = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttach = false;
    }

    public String getUserId() {
        return ZSharedUtil.getUserId();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unSubscribe(subscription1);
        unSubscribe(subscription2);
        unSubscribe(subscription3);
    }
}
