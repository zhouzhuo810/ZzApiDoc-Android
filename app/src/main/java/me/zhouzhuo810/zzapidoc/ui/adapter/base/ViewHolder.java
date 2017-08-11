package me.zhouzhuo810.zzapidoc.ui.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.autolayout.utils.AutoUtils;


public class ViewHolder {

    private SparseArray<View> mViews;
    private int mPostion;
    private View mConvertView;
    private Context context;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position, boolean isNeedScale) {
        this.context = context;
        this.mPostion = position;
        this.mViews = new SparseArray<View>();
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        //动态设置
        if (isNeedScale) {
            AutoUtils.auto(mConvertView);
        }
        this.mConvertView.setTag(this);
    }

    /**
     * 获取Holder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position, boolean isNeedScale) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position, isNeedScale);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPostion = position;
            return holder;
        }
    }

    /**
     * 获取item的位置
     *
     * @return
     */
    public int getPosition() {
        return mPostion;
    }

    /**
     * 通过id获取view
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getmConvertView() {
        return mConvertView;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置可见性
     *
     * @param viewId
     * @param visible
     * @return
     */
    public ViewHolder setVisible(int viewId, int visible) {
        View view = getView(viewId);
        view.setVisibility(visible);
        return this;
    }


    /**
     * 设置文字颜色
     *
     * @param viewId
     * @param color
     * @return
     */
    public ViewHolder setTextColor(int viewId, int color) {
        TextView tv = getView(viewId);
        tv.setTextColor(context.getResources().getColor(color));
        return this;
    }

    /**
     * 设置星级
     *
     * @param viewId
     * @param num
     * @return
     */
    public ViewHolder setRating(int viewId, float num) {
        RatingBar ratingBar = getView(viewId);
        ratingBar.setRating(num);
        return this;
    }

    /**
     * 设置View背景
     *
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setBackgroundDrawable(int viewId, int resId) {
        View view = getView(viewId);
        Drawable drawable = context.getResources().getDrawable(resId);
        view.setBackgroundDrawable(drawable);
        return this;
    }

    /**
     * 设置ImageView的src
     *
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    /**
     * 设置ImageView的bitmap
     *
     * @param viewId
     * @param bitmap
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }


    /**
     * 设置背景资源
     *
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setBackgroundResource(int viewId, int resId) {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    /**
     * 设置ImageView的url
     *
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageUrl(int viewId, String url) {
        ImageView view = getView(viewId);
        //ImageLoader.getInstance().loadImg(view, url);
        Glide.with(context).load(url).centerCrop().crossFade().into(view);
        return this;
    }

    /**
     * 设置ImageView的url
     *
     * @param viewId
     * @return
     */
    public ViewHolder setImageURI(int viewId, String uri) {
        ImageView view = getView(viewId);
        //ImageLoader.getInstance().loadImg(view, url);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        view.setImageBitmap(BitmapFactory.decodeFile(Uri.parse(uri).getPath()));
        return this;
    }



    /**
     * 设置checkbox选中状态
     *
     * @param viewId
     * @param checked
     * @return
     */
    public ViewHolder setChecked(int viewId, boolean checked) {
        CompoundButton cb = getView(viewId);
        cb.setChecked(checked);
        return this;
    }

    /**
     * 设置Checkbox状态改变监听事件
     *
     * @param viewId
     * @param listener
     * @return
     */
    public ViewHolder setOnCheckedListener(int viewId, OnCheckedChangeListener listener) {
        CompoundButton cb = getView(viewId);
        cb.setOnCheckedChangeListener(listener);
        return this;
    }


    /**
     * 设置点击监听
     *
     * @param viewId
     * @param listener
     * @return
     */
    public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

}
