package me.zhouzhuo810.zzapidoc.ui.widget.autolayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.utils.AutoLayoutHelper;
import com.zhy.view.flowlayout.FlowLayout;

/**
 * Created by zhouzhuo810 on 2017/7/20.
 */

public class AutoFlowLayout extends FlowLayout {

    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AutoFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoFlowLayout(Context context) {
        super(context);
    }

    @Override
    public AutoFrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new AutoFrameLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (!isInEditMode())
        {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



    public static class LayoutParams extends FlowLayout.LayoutParams
            implements AutoLayoutHelper.AutoLayoutParams
    {
        private AutoLayoutInfo mAutoLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs)
        {
            super(c, attrs);

            mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs);
        }

        public LayoutParams(int width, int height)
        {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source)
        {
            super(source);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source)
        {
            super(source);
        }

        public LayoutParams(FrameLayout.LayoutParams source)
        {
            super((ViewGroup.MarginLayoutParams) source);
        }

        public LayoutParams(AutoFlowLayout.LayoutParams source)
        {
            this((FlowLayout.LayoutParams) source);
            mAutoLayoutInfo = source.mAutoLayoutInfo;
        }

        @Override
        public AutoLayoutInfo getAutoLayoutInfo()
        {
            return mAutoLayoutInfo;
        }


    }

}
