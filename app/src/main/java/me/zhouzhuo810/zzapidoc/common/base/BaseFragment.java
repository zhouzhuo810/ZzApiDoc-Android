package me.zhouzhuo810.zzapidoc.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
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
import zhouzhuo810.me.zzandframe.ui.act.*;
import zhouzhuo810.me.zzandframe.ui.act.BaseActivity;

/**
 * Created by zz on 2017/6/26.
 */
public abstract class BaseFragment extends zhouzhuo810.me.zzandframe.ui.fgm.BaseFragment {

    protected Subscription subscription1;
    protected Subscription subscription2;
    protected Subscription subscription3;

    protected boolean isAttach;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isAttach = true;
    }

    protected <T> T findViewById(@IdRes int viewId) {
        return (T) rootView.findViewById(viewId);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        isAttach = false;
    }

    @Override
    public me.zhouzhuo810.zzapidoc.common.base.BaseActivity getBaseAct() {
        return (me.zhouzhuo810.zzapidoc.common.base.BaseActivity) getActivity();
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
