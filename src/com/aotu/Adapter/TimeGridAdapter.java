package com.aotu.Adapter;

import java.util.List;

import com.auto.aotuge.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;




public class TimeGridAdapter extends BaseAdapter {

	private Context mContext;
	private List<String> mData;
	public int mType;

	public TimeGridAdapter(Context context, List<String> aData) {
		mContext = context;
		mData = aData;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		String item = mData.get(arg0);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.project_buy_item, null);
			viewHolder = new ViewHolder();
			viewHolder.mViewText = (TextView) convertView.findViewById(R.id.text);
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();

		viewHolder.mViewText.setText(item);
		return convertView;
	}

	class ViewHolder {
		public TextView mViewText;
	}

}
