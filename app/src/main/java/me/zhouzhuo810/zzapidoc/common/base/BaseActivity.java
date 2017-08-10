package me.zhouzhuo810.zzapidoc.common.base;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetDictionaryResult;
import me.zhouzhuo810.zzapidoc.common.rx.ExitEvent;
import me.zhouzhuo810.zzapidoc.common.rx.RxBus;
import me.zhouzhuo810.zzapidoc.common.utils.ZSharedUtil;
import me.zhouzhuo810.zzapidoc.ui.widget.autolayout.AutoLayoutWidgetActivity;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zz on 2017/6/26.
 */
public abstract class BaseActivity extends AutoLayoutWidgetActivity {

    private boolean isForeground;

    protected Subscription subscription1;
    protected Subscription subscription2;
    protected Subscription subscription3;

    private CompositeSubscription compositeSubscription;

    private Dialog pd;
    private Dialog twoBtnD;
    private Dialog twoBtnEtD;

    public interface OnTwoBtnClick {
        void onOk();

        void onCancel();
    }

    public interface OnTwoBtnEditClick {
        void onOk(String content);

        void onCancel();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerExitObserver();

        setContentView(getLayoutId());

        initView();

        initData();

        initEvent();
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

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    public abstract void initEvent();

    public abstract void saveState(Bundle bundle);

    public abstract void restoreState(Bundle bundle);


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


    public void setEditListener(final EditText et, final ImageView iv) {
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    iv.setVisibility(View.VISIBLE);
                } else {
                    iv.setVisibility(View.GONE);
                }
            }
        });
    }

    public String getUserId() {
        return ZSharedUtil.getUserId();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        restoreState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
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

    @Override
    public void onBackPressed() {
        if (isDefaultBackClose()) {
            super.onBackPressed();
        } else {
            closeAct();
        }
    }

    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }


    public abstract boolean isDefaultBackClose();

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

    public void showPd(String msg, boolean cancelable) {
        View convertView = LayoutInflater.from(this).inflate(R.layout.layout_progress_dialog, null);
        AutoUtils.auto(convertView);
        TextView tvContent = (TextView) convertView.findViewById(R.id.tv_msg);
        tvContent.setText(msg);
        pd = new Dialog(this, R.style.transparentWindow);
        Window window = pd.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        pd.onWindowAttributesChanged(wl);
        pd.setCanceledOnTouchOutside(cancelable);
        pd.setContentView(convertView);
        pd.show();
    }

    public void showTwoBtnDialog(String title, String msg, boolean cancelable, final OnTwoBtnClick onTwoBtnClick) {
        View convertView = LayoutInflater.from(this).inflate(R.layout.layout_two_btn_dialog, null);
        AutoUtils.auto(convertView);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        TextView tvContent = (TextView) convertView.findViewById(R.id.tv_msg);
        tvContent.setText(msg);
        twoBtnD = new Dialog(this, R.style.transparentWindow);
        Button btnOk = (Button) convertView.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) convertView.findViewById(R.id.btn_cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideTwoBtnDialog();
                if (onTwoBtnClick != null) {
                    onTwoBtnClick.onOk();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideTwoBtnDialog();
                if (onTwoBtnClick != null) {
                    onTwoBtnClick.onCancel();
                }
            }
        });
        Window window = twoBtnD.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        twoBtnD.onWindowAttributesChanged(wl);
        twoBtnD.setCanceledOnTouchOutside(cancelable);
        twoBtnD.setContentView(convertView);
        twoBtnD.show();
    }

    //Fixme by zz 2017/7/19 下午12:18 修改内容：添加两个按钮输入对话框
    public void showTwoBtnEditDialog(String title, String hint, boolean cancelable, final OnTwoBtnEditClick onTwoBtnClick) {
        View convertView = LayoutInflater.from(this).inflate(R.layout.layout_two_btn_et_dialog, null);
        AutoUtils.auto(convertView);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        final EditText etContent = (EditText) convertView.findViewById(R.id.tv_msg);
        etContent.setHint(hint);
        twoBtnEtD = new Dialog(this, R.style.transparentWindow);
        Button btnOk = (Button) convertView.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) convertView.findViewById(R.id.btn_cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideTwoBtnEditDialog();
                if (onTwoBtnClick != null) {
                    String content = etContent.getText().toString().trim();
                    onTwoBtnClick.onOk(content);
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideTwoBtnEditDialog();
                if (onTwoBtnClick != null) {
                    onTwoBtnClick.onCancel();
                }
            }
        });
        Window window = twoBtnEtD.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        twoBtnEtD.onWindowAttributesChanged(wl);
        twoBtnEtD.setCanceledOnTouchOutside(cancelable);
        twoBtnEtD.setContentView(convertView);
        twoBtnEtD.show();
    }

    public void hidePd() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    public void hideTwoBtnDialog() {
        if (twoBtnD != null) {
            twoBtnD.dismiss();
            twoBtnD = null;
        }
    }

    public void hideTwoBtnEditDialog() {
        if (twoBtnEtD != null) {
            twoBtnEtD.dismiss();
            twoBtnEtD = null;
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
        unSubscribe(subscription1);
        unSubscribe(subscription2);
        unSubscribe(subscription3);
        if (compositeSubscription != null)
            compositeSubscription.unsubscribe();
    }
}
