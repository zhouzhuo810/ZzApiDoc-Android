package me.zhouzhuo810.zzapidoc.ui.act;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetDictionaryResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import rx.Subscriber;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */

public class AddInterfaceActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private LinearLayout llRequestMethod;
    private TextView tvRequestMethod;
    private EditText etInterfaceName;
    private ImageView ivClearInterfaceName;
    private EditText etPath;
    private ImageView ivClearPath;
    private EditText etPs;
    private ImageView ivClearPs;
    private Button btnSubmit;

    private String projectId;
    private String groupId;
    private boolean show;
    private String methodId;


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_interface;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        llRequestMethod = (LinearLayout) findViewById(R.id.ll_request_method);
        tvRequestMethod = (TextView) findViewById(R.id.tv_request_method);
        etInterfaceName = (EditText) findViewById(R.id.et_interface_name);
        ivClearInterfaceName = (ImageView) findViewById(R.id.iv_clear_interface_name);
        etPath = (EditText) findViewById(R.id.et_path);
        ivClearPath = (ImageView) findViewById(R.id.iv_clear_path);
        etPs = (EditText) findViewById(R.id.et_ps);
        ivClearPs = (ImageView) findViewById(R.id.iv_clear_ps);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void initData() {
        projectId = getIntent().getStringExtra("projectId");
        groupId = getIntent().getStringExtra("groupId");
    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });

        llRequestMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!show) {
                    chooseMethod();
                }
            }
        });

        setEditListener(etInterfaceName, ivClearInterfaceName);
        setEditListener(etPath, ivClearPath);
        setEditListener(etPs, ivClearPs);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInterface();
            }
        });


    }

    private void chooseMethod() {
        show = true;
        showPd(getString(R.string.loading_text), false);
        Api.getApi0()
                .getDictionary("method")
                .compose(RxHelper.<GetDictionaryResult>io_main())
                .subscribe(new Subscriber<GetDictionaryResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        show = false;
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                        hidePd();
                    }

                    @Override
                    public void onNext(final GetDictionaryResult getDictionaryResult) {
                        hidePd();
                        if (getDictionaryResult.getCode() == 1) {
                            List<String> strings = dicToList(getDictionaryResult.getData());
                            showListDialog(strings, true, new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    show = false;
                                }
                            }, new OnItemClick() {
                                @Override
                                public void onItemClick(int position, String content) {
                                    show = false;
                                    methodId = getDictionaryResult.getData().get(position).getId();
                                    tvRequestMethod.setText(content);
                                }
                            });
                        } else {
                            ToastUtils.showCustomBgToast(getDictionaryResult.getMsg());
                        }
                    }
                });
    }

    private void addInterface() {
        String name = etInterfaceName.getText().toString().trim();
        if (name.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.interface_name_not_nul_text));
            return;
        }
        String path = etPath.getText().toString().trim();
        if (path.length() == 0) {
            ToastUtils.showCustomBgToast(getString(R.string.path_not_nul_text));
            return;
        }
        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .addInterface(name, groupId, methodId, getUserId(), projectId, path, null, null)
                .compose(RxHelper.<AddInterfaceResult>io_main())
                .subscribe(new Subscriber<AddInterfaceResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(AddInterfaceResult addProjectResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(addProjectResult.getMsg());
                        if (addProjectResult.getCode() == 1) {
                            closeAct();
                        }
                    }
                });
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void saveState(Bundle bundle) {

    }

    @Override
    public void restoreState(@Nullable Bundle bundle) {

    }
}
