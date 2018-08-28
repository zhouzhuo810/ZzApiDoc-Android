package me.zhouzhuo810.zzapidoc.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.zzapidoc.R;
import me.zhouzhuo810.zzapidoc.common.api.Api;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddTodoListResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddVersionProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetUserListResult;
import me.zhouzhuo810.zzapidoc.common.base.BaseActivity;
import me.zhouzhuo810.zzapidoc.common.rx.RxHelper;
import me.zhouzhuo810.zzapidoc.common.utils.ToastUtils;
import me.zhouzhuo810.zzapidoc.common.utils.ZSharedUtil;
import rx.Subscriber;
import zhouzhuo810.me.zzandframe.common.utils.SharedUtils;
import zhouzhuo810.me.zzandframe.ui.act.IBaseActivity;

/**
 * 添加待办
 * Created by zhouzhuo810 on 2017/10/26.
 */

public class AddTodoActivity extends BaseActivity {
    private RelativeLayout rlBack;
    private RelativeLayout rlRight;
    private EditText etTodoTitle;
    private ImageView ivClearTodoTitle;
    private EditText etTodoContent;
    private LinearLayout llFinishProgress;
    private AppCompatSeekBar seekProgress;
    private TextView tvProgress;
    private LinearLayout llHandlePerson;
    private TextView tvHandlePerson;
    private Button btnSubmit;

    private void assignViews() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
        etTodoTitle = (EditText) findViewById(R.id.et_todo_title);
        ivClearTodoTitle = (ImageView) findViewById(R.id.iv_clear_todo_title);
        etTodoContent = (EditText) findViewById(R.id.et_todo_content);
        llFinishProgress = (LinearLayout) findViewById(R.id.ll_finish_progress);
        seekProgress = (AppCompatSeekBar) findViewById(R.id.seek_progress);
        tvProgress = (TextView) findViewById(R.id.tv_progress);
        llHandlePerson = (LinearLayout) findViewById(R.id.ll_handle_person);
        tvHandlePerson = (TextView) findViewById(R.id.tv_handle_person);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }


    private String handlerPersonId;


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_todo;
    }

    @Override
    public boolean defaultBack() {
        return false;
    }

    @Override
    public void initView() {
        assignViews();
    }

    @Override
    public void initData() {
        handlerPersonId = getUserId();
        tvHandlePerson.setText(ZSharedUtil.getRealName());
    }

    @Override
    public void initEvent() {
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAct();
            }
        });

        llHandlePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseHandlerPerson();
            }
        });

        seekProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvProgress.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void chooseHandlerPerson() {
        Api.getApi0()
                .getUserList(getUserId())
                .compose(RxHelper.<GetUserListResult>io_main())
                .subscribe(new Subscriber<GetUserListResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetUserListResult getUserListResult) {
                        if (getUserListResult.getCode() == 1) {
                            final List<GetUserListResult.DataEntity> data = getUserListResult.getData();
                            List<String> items = new ArrayList<>();
                            for (GetUserListResult.DataEntity datum : data) {
                                items.add(datum.getName());
                            }
                            showListDialog(items, false, null, new IBaseActivity.OnItemClick() {
                                @Override
                                public void onItemClick(int i, String s) {
                                    GetUserListResult.DataEntity dataEntity = data.get(i);
                                    handlerPersonId = dataEntity.getId();
                                    tvHandlePerson.setText(dataEntity.getName());
                                }
                            });
                        }
                    }
                });
    }

    private void submit() {
        String title = etTodoTitle.getText().toString().trim();
        String content = etTodoContent.getText().toString().trim();

        showPd(getString(R.string.submiting_text), false);
        Api.getApi0()
                .addTodoList(getUserId(), handlerPersonId, title, content, seekProgress.getProgress(), seekProgress.getProgress() == 100 ? 1 : 0)
                .compose(RxHelper.<AddTodoListResult>io_main())
                .subscribe(new Subscriber<AddTodoListResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePd();
                        ToastUtils.showCustomBgToast(getString(R.string.no_net_text) + e.toString());
                    }

                    @Override
                    public void onNext(AddTodoListResult addTodoListResult) {
                        hidePd();
                        ToastUtils.showCustomBgToast(addTodoListResult.getMsg());
                        if (addTodoListResult.getCode() == 1) {
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
