package me.zhouzhuo810.zzapidoc.ui.adapter.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<T> mDatas;
	protected int layoutId;
	protected boolean isNeedScale;


	public CommonAdapter(Context context, List<T> datas, int layoutId, boolean isNeedScale) {
		this.mContext = context;
		this.mDatas = datas;
		this.layoutId = layoutId;
		this.isNeedScale = isNeedScale;
	}

	public void setmDatas(List<T> mDatas) {
		this.mDatas = mDatas;
	}

	public List<T> getmDatas() {
		return mDatas;
	}

	@Override
	public int getCount() {
		return mDatas == null ? 0 : mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas == null ? null : mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder = ViewHolder.get(mContext, convertView, parent, 
				layoutId, position, isNeedScale);
		convert(holder, getItem(position));
		return holder.getmConvertView();
	}
	
	public abstract void convert(ViewHolder holder, T t);

}
