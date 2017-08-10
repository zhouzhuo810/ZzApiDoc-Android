package me.zhouzhuo810.zzapidoc.ui.widget.autolayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

import com.zhy.autolayout.AutoLayoutActivity;


/**
 * Created by hupei on 2016/3/7 16:44.
 */
public class AutoLayoutWidgetActivity extends AutoLayoutActivity {

    private static final String RADIO_GROUP = "android.widget.RadioGroup";
    private static final String FLOW_LAYOUT = "com.zhy.view.flowlayout.FlowLayout";

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;

        if (name.equals(RADIO_GROUP)) {
            view = new RadioGroup(context, attrs);
        }

        if (name.equals(FLOW_LAYOUT)) {
            view = new AutoFlowLayout(context, attrs);
        }

        if (view != null) return view;
        return super.onCreateView(name, context, attrs);
    }
}